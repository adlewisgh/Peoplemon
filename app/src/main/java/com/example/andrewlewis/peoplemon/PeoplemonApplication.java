package com.example.andrewlewis.peoplemon;

import android.app.Application;

import com.example.andrewlewis.peoplemon.Stages.PeoplemonStage;

import flow.Flow;
import flow.History;

/**
 * Created by andrewlewis on 11/6/16.
 */

public class PeoplemonApplication extends Application {

    private static PeoplemonApplication application;
    //TODO Set up to display map.
    public final Flow mainFlow = new Flow(History.single(new PeoplemonStage()));
    //TODO may not need this line. May or may not need it.
    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    public static PeoplemonApplication getInstance() {

        return application;
    }

    public static Flow getMainFlow() {

        return getInstance().mainFlow;
    }

}