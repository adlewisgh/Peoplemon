package com.example.andrewlewis.peoplemon.Stages;

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/10/16.
 */

public class CaughtPeopleListStage extends IndexedStage {

    private final SlideRigger rigger;

    public CaughtPeopleListStage(Application context){
        super(CaughtPeopleListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public CaughtPeopleListStage() {
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId() {
        return R.layout.caught_people_listview;

    }

    @Override
    public Rigger getRigger() {
        return rigger;

    }
}
