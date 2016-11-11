package com.example.andrewlewis.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import static com.example.andrewlewis.peoplemon.Components.Constants.API_KEY;

/**
 * Created by andrewlewis on 11/6/16.
 */

public class Account {

    //Declarations

    @SerializedName("Id")
    private String id;
    @SerializedName("Email")
    private String email;
    @SerializedName("HasRegistered")
    private Boolean hasRegistered;
    @SerializedName("LoginProvider")
    private String loginProvider;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("AvatarBase64")
    private String avatarBase64;
    @SerializedName("LastCheckInLongitude")
    private double previousLng;
    @SerializedName("LastCheckInLatitude")
    private double previousLat;
    @SerializedName("LastCheckInDateTime")
    private String lastCheckIn;
    @SerializedName("Password")
    private String password;
    @SerializedName("ApiKey")
    private String apiKey;
    @SerializedName("access_token")
    private String token;
    @SerializedName(".expires")
    private Date expiration;

    //Constructors
    public Account() {
    }

    public Account(String id, String email, Boolean hasRegistered, String loginProvider,
                   String fullName, String avatarBase64, double previousLng,
                   double previousLat, String lastCheckIn) {

        this.id = id;
        this.email = email;
        this.hasRegistered = hasRegistered;
        this.loginProvider = loginProvider;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.previousLng = previousLng;
        this.previousLat = previousLat;
        this.lastCheckIn = lastCheckIn;
    }

    public Account(double previousLng, double previousLat) {
        this.previousLng = previousLng;
        this.previousLat = previousLat;
    }

    public Account(String fullName, String avatarBase64) {
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
    }

    public Account(String email, String fullName, String avatarBase64, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = API_KEY;
        this.password = password;
    }

    //Getters and Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHasRegistered() {
        return hasRegistered;
    }

    public void setHasRegistered(Boolean hasRegistered) {
        this.hasRegistered = hasRegistered;
    }

    public String getLoginProvider() {
        return loginProvider;
    }

    public void setLoginProvider(String loginProvided) {
        this.loginProvider = loginProvided;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public double getPreviousLng() {
        return previousLng;
    }

    public void setPreviousLng(double previousLng) {
        this.previousLng = previousLng;
    }

    public double getPreviousLat() {
        return previousLat;
    }

    public void setPreviousLat(double previousLat) {
        this.previousLat = previousLat;
    }

    public String getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(String lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
