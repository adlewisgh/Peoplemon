package com.example.andrewlewis.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrewlewis.peoplemon.Models.Account;
import com.example.andrewlewis.peoplemon.Network.RestClient;
import com.example.andrewlewis.peoplemon.Network.UserStore;
import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Stages.MapStage;
import com.example.andrewlewis.peoplemon.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.andrewlewis.peoplemon.Components.Constants.GRANT_TYPE;

/**
 * Created by andrewlewis on 11/5/16.
 */

public class LoginView extends LinearLayout {
    private Context context;

    @Bind(R.id.username_field)
    EditText emailField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;


    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView() {
        Flow flow = PeoplemonApplication.getMainFlow();
        History newHistory = flow.getHistory()
                .buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login() {
        //This removes the keyboard.
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromInputMethod(passwordField.getWindowToken(), 0);

        String grantType = GRANT_TYPE;
        String username = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, R.string.provide_username_and_password, Toast.LENGTH_LONG).show();
        } else {
            loginButton.setEnabled(false);
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);
        }
        //Account account = new Account(username, password);
        RestClient restClient = new RestClient();
        restClient.getApiService().getToken(grantType, username, password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {

                if (response.isSuccessful()) {
                    Account authAccount = response.body();
                    UserStore.getInstance().setToken(authAccount.getToken());
                    UserStore.getInstance().setTokenExpiration(authAccount.getExpiration());
                    //This will set up the flow of the application to show the next view upon successful login
                    Flow flow = PeoplemonApplication.getMainFlow();
                    History newHistory = History.single(new MapStage());
                    flow.setHistory(newHistory, Flow.Direction.REPLACE);

                } else {

                    //This will return if the user has entered info but it is incorrect
                    resetView();
                    Toast.makeText(context, R.string.login_failed + ": " + response.code(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

                //This will show up if the data didn't come back from the server correctly or there is a timeout.
                resetView();
                Toast.makeText(context, R.string.login_failed, Toast.LENGTH_LONG).show();
            }
        });


        }

    private void resetView () {

        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }

            }

