package com.example.andrewlewis.peoplemon.Stages;

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/6/16.
 */

public class LoginStage   extends IndexedStage {

    private final SlideRigger rigger;

    public LoginStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public LoginStage() {
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;

    }

    @Override
    public Rigger getRigger() {
        return rigger;

    }
}
