package com.example.andrewlewis.peoplemon.Stages;

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/7/16.
 */

public class PeoplemonStage extends IndexedStage {

    private final SlideRigger rigger;

    public PeoplemonStage(Application context){
        super(PeoplemonStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public PeoplemonStage() {
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
