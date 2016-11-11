package com.example.andrewlewis.peoplemon.Views;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrewlewis.peoplemon.Models.Account;
import com.example.andrewlewis.peoplemon.Network.RestClient;
import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andrewlewis on 10/31/16.
 */

public class RegisterView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText usernameField;

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register () {
        //The next few lines are to hide the keyboard
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(usernameField.getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(confirmField.getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(emailField.getWindowToken(), 0);




        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        String email = emailField.getText().toString();
        String avatarBase64 = "string";

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() || email.isEmpty()) {

            Toast.makeText(context, R.string.field_empty, Toast.LENGTH_LONG).show();
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(context, R.string.provide_vaild_email, Toast.LENGTH_SHORT).show();

        } else if (!password.equals(confirm)) {

            Toast.makeText(context, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
        } else {

            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);
        }
        Account account = new Account(email, username, avatarBase64, password);
        RestClient restClient = new RestClient();
        restClient.getApiService().register(account).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, R.string.registration_successful, Toast.LENGTH_LONG).show();
                    //This will run if everything comes back good and sets the users token and expiration
//                    User regUser = response.body();
//                    UserStore.getInstance().setToken(regUser.getToken());
//                    UserStore.getInstance().setTokenExpiration(regUser.getExpiration());

                    //This will set up the flow of the application to show the next view upon successful registration
                    Flow flow = PeoplemonApplication.getMainFlow();
                    flow.goBack();
                } else {

                    //This will return if the user has entered info but they have registered before
                    resetView();
                    Toast.makeText(context, R.string.registration_failed + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                //This will show up if the data didn't come back from the server correctly or there is a timeout.
                resetView();
                Toast.makeText(context, R.string.registration_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resetView () {

        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
//extract resources
//