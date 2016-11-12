package com.example.andrewlewis.peoplemon.Views;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.andrewlewis.peoplemon.MainActivity;
import com.example.andrewlewis.peoplemon.Models.Account;
import com.example.andrewlewis.peoplemon.Models.User;
import com.example.andrewlewis.peoplemon.Network.RestClient;
import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Stages.CaughtPeopleListStage;
import com.example.andrewlewis.peoplemon.Stages.NearbyPeopleListStage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.android.gms.wearable.DataMap.TAG;

public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private Context context;
    private Location mLastLocation;
    private double lat = 37.816380;
    private double lng = -82.809195;
    private Handler checkNear;
    private Handler checkingIn;
    public static Location mLocation;
    public Bitmap myIcon;
    public static Bitmap decodedByte;

    LatLng Home = new LatLng(lat, lng);

    RestClient restClient;


    @Bind(R.id.map)
    MapView mapView;


    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    protected void onFinishInflate() {


        ButterKnife.bind(this);
        super.onFinishInflate();
        mapView.getMapAsync(this);
        checkNear = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                checkNearby();
                checkNear.postDelayed(this, 2000);
            }
        };
        checkNear.postDelayed(r, 2000);

        checkingIn = new Handler();
        final Runnable ci = new Runnable() {
            public void run() {
                setCheckIn();
                checkingIn.postDelayed(this, 2000);
            }
        };
        checkingIn.postDelayed(ci, 2000);


        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();

        restClient = new RestClient();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {

            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){

                    //Get user that is returned
                    Account authUser = response.body();
                    String encodedImage = authUser.getAvatarBase64();
                    byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                    mMap.setOnMyLocationChangeListener(myLocationChangeListener);
                    mLocation = new Location("");

                    mLocation.setLatitude(lat);
                    mLocation.setLongitude(lng);
                    Toast.makeText(context, "Map loaded", Toast.LENGTH_SHORT).show();
                    //Refer to documentation in slack
                    UiSettings uiSettings = mMap.getUiSettings();
                    uiSettings.setMyLocationButtonEnabled(true);
                    mMap.getUiSettings().setCompassEnabled(true);


                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 16));


                    mMap.addMarker(new MarkerOptions()
                            .position(Home)
                            .title("Marker in DR")
                            .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                            .snippet("#EFAImpact")
                            .draggable(true));

                    //add try catch *security exception e*
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Toast.makeText(context, "Can't pinpoint your exact location", Toast.LENGTH_SHORT).show();
                    }
                    mMap.clear();



                }else{
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });








    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //set user id for each marker.
        // use for caught user id for catch call.
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Location location = new Location("");
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        User user = new User(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        return true;
    }

    // Should get location working and show users... maybe.
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {


            lat = location.getLatitude();
            lng = location.getLongitude();
            Home = new LatLng(lat, lng);
            mLocation = location;
            String pos = Home + "";
            Log.d("*****", pos);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 16));
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(Home)
                    .title("Marker in DR")
                    .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                    .snippet("#EFAImpact")
                    .draggable(true));
            //Calls server to get list of nearby users
            restClient.getApiService().findNearby(2000).enqueue(new Callback<User[]>() {
                @Override
                public void onResponse(Call<User[]> call, Response<User[]> response) {


                    //Added image overlay to better show distance
                    GroundOverlayOptions radar = new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.mipmap.radar3))
                            .position(Home, 500f, 500f);
                    GroundOverlay imageOverlay = mMap.addGroundOverlay(radar);


                    //Added animation to image overlay
                    final Circle circle = mMap.addCircle(new CircleOptions().center(Home)
                            .strokeColor(Color.BLACK).radius(250));
                    ValueAnimator vAnimator = new ValueAnimator();
                    vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
                    vAnimator.setIntValues(0, 150);
                    vAnimator.setDuration(1000);
                    vAnimator.setEvaluator(new IntEvaluator());
                    vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float animatedFraction = valueAnimator.getAnimatedFraction();
                            // Log.e("", "" + animatedFraction);
                            circle.setRadius(animatedFraction * 250);
                        }
                    });
                    vAnimator.start();


//                        }
//                    }
                }

                // Shows the following Toast if attempt to find nearby users fails.

                @Override
                public void onFailure(Call<User[]> call, Throwable t) {

                    Toast.makeText(context, "Unable to get nearby users", Toast.LENGTH_SHORT).show();
                }
            });
            //LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//            marker = mMap.addMarker(new MarkerOptions().position(loc));
//            visibleMarkers.add(0, marker);

        }


    };

    public void setCheckIn() {
        User checkin = new User(lat, lng);
        RestClient restClient = new RestClient();
        Log.d(TAG, "Checking-In");
        restClient.getApiService().checkIn(checkin).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Toast.makeText(context, "You have been checked in.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Attempt to check-in Failed.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    public void checkNearby() {
        RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(100).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    //mMap.clear();
                    //Toast.makeText(context, "HELLO. FROM THE OTHER SIDE BY ADELE IS MY FAVORITE SONG", Toast.LENGTH_SHORT).show();
                    for (User user : response.body()) {
                        lat = user.getLatitude();
                        lng = user.getLongitude();
                        String userId = user.getUserId();

                        if (user.getAvatarBase64() == null || user.getAvatarBase64().length() <= 100) {

//                        String encodedImage = user.getAvatarBase64();
//                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            final LatLng userpos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions()
                                    .title(user.getUserName())
                                    // correct          .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                    .snippet(user.getUserId())
                                    .position(userpos));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                                    Location userLoc = new Location("");
                                    userLoc.setLatitude(marker.getPosition().latitude);
                                    userLoc.setLongitude(marker.getPosition().longitude);
                                    final String CaughtUserId = marker.getSnippet();
                                    final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "That person is out side your radius", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                    marker.remove();
                                    return false;
                                }
                            });
                        } else {
                            String encodedImage = user.getAvatarBase64();
                            byte[] decodedString;
//                            Bitmap decodedByte;
                            if (encodedImage != null) {
                                if (encodedImage.contains(",")) {
                                    String[] haxor = encodedImage.split(",");
                                    decodedString = Base64.decode(haxor[1], Base64.DEFAULT);
                                } else {
                                    if (encodedImage.length() > 200) {
                                        decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                                    } else {
                                        decodedString = null;
                                    }
                                }
                                if (decodedString != null) {
                                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

//                                    decodedByte = resize(decodedByte);
                                }
                            }
//                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);
                            final LatLng userpos = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions()
                                    .title(user.getUserName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(decodedByte))
                                    .snippet(user.getUserId())
                                    .position(userpos));
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                                    Location userLoc = new Location("");
                                    userLoc.setLatitude(marker.getPosition().latitude);
                                    userLoc.setLongitude(marker.getPosition().longitude);
                                    final String CaughtUserId = marker.getSnippet();
                                    final User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
                                    RestClient restClient = new RestClient();
                                    restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(context, "Person Caught!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(context, "That person is out side your radius", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                    marker.remove();
                                    return false;
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(context, "you done goofed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {

            }
        });
        }
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                //Toast.makeText(context, "You caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
//                Location userLoc = new Location("");
//                userLoc.setLatitude(marker.getPosition().latitude);
//                userLoc.setLongitude(marker.getPosition().longitude);
//                String CaughtUserId = marker.getSnippet();
//                User user = new User(CaughtUserId, mLocation.distanceTo(userLoc));
//                RestClient restClient = new RestClient();
//                restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(context, "You caught ", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "That did not work out so well" + ": " + response.code(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//                marker.remove();
//                return false;
//            }
//        });

    @OnClick(R.id.view_caught_button)
    public void showAddCategoryView(){

        //Magical code for switching between stages
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaughtPeopleListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

    @OnClick(R.id.checkIn_Button)
    public void showfdbffCategoryView(){
        //Magical code for switching between stages
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new NearbyPeopleListStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }
}



