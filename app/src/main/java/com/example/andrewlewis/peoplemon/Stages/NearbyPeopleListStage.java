package com.example.andrewlewis.peoplemon.Stages;

/**
 * Created by andrewlewis on 11/11/16.
 */

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/10/16.
 */

public class NearbyPeopleListStage extends IndexedStage {

    private final SlideRigger rigger;

    public NearbyPeopleListStage(Application context){
        super(NearbyPeopleListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public NearbyPeopleListStage() {
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_people_listview;

    }

    @Override
    public Rigger getRigger() {
        return rigger;

    }
}
