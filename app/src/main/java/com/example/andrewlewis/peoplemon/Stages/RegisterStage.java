package com.example.andrewlewis.peoplemon.Stages;

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/6/16.
 */

public class RegisterStage  extends IndexedStage {

    private final SlideRigger rigger;

    public RegisterStage(Application context){
        super(RegisterStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public RegisterStage() {
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view;

    }

    @Override
    public Rigger getRigger() {
        return rigger;

    }
}
