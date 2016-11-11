package com.example.andrewlewis.peoplemon.Stages;

import android.app.Application;

import com.example.andrewlewis.peoplemon.PeoplemonApplication;
import com.example.andrewlewis.peoplemon.R;
import com.example.andrewlewis.peoplemon.Riggers.SlideRigger;

/**
 * Created by andrewlewis on 11/7/16.
 */

public class MapStage extends IndexedStage {

    private final SlideRigger rigger;

    public MapStage(Application context){
        super(MapStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public MapStage() {
        this(PeoplemonApplication.getInstance());

    }

    @Override
    public int getLayoutId() {
        return R.layout.map_page_view;

    }

    @Override
    public Rigger getRigger() {
        return rigger;

    }
}
