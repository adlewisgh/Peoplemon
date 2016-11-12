package com.example.andrewlewis.peoplemon.Views;

/**
 * Created by andrewlewis on 11/11/16.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by andrewlewis on 11/10/16.
 */

public class NearbyPeopleListView extends LinearLayout {
//
    private Context context;
//    private NearbyPeopleListAdapter nearbyAdapter;
//
//    @Bind(R.id.recycler_view)
//    RecyclerView recyclerView;
//
//
    public NearbyPeopleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        ButterKnife.bind(this);
//
//        nearbyAdapter = new NearbyPeopleListAdapter(new ArrayList<User>(), context);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(nearbyAdapter);
//
//        listCaughtPeople();
//    }
//
//    private void listCaughtPeople(){
//
//        RestClient restClient = new RestClient();
//        restClient.getApiService().findNearby(2000).enqueue(new Callback<User[]>() {
//
//            @Override
//            public void onResponse(Call<User[]> call, Response<User[]> response) {
//                // Is the server response between 200 to 299
//                if (response.isSuccessful()){
//                    int i = 0;
//
//                    nearbyAdapter.users = new ArrayList<User>(Arrays.asList(response.body()));
//
//                    for (User user : nearbyAdapter.users){
//
//                        Log.d(user.getUserName(),"***CAUGHT***");
//                        Log.d(user.getAvatarBase64(),"***CAUGHT***");
//
//                        nearbyAdapter.notifyDataSetChanged();
//                    }
//
//                }else{
//                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User[]> call, Throwable t) {
//                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}
