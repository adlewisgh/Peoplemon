package com.example.andrewlewis.peoplemon.Network;

import com.example.andrewlewis.peoplemon.Models.Account;
import com.example.andrewlewis.peoplemon.Models.User;
import com.google.android.gms.nearby.messages.Messages;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by andrewlewis on 10/31/16.
 */

public interface ApiService {
    //Calling Account info

    @POST("/api/Account/UserInfo")
    Call<Account> updateInfo(@Body Account account);

    @POST("/api/Account/Logout")
    Call<Account> logout();

    @POST("/api/Account/Register")
    Call<Void> register(@Body Account account);

    //Get User Info Call
    @GET("/api/Account/UserInfo")
    Call<Account>getUserInfo();

    @POST("/api/Account/UserInfo")
    Call<Void>postUserInfo(@Body Account account);


    //Calling for everybody else's info

    @GET("v1/User/Nearby")
    Call<User[]> findNearby(@Query("radiusInMeters") Integer radiusInMeters);

    @POST("/v1/User/CheckIn")
    Call<Void> checkIn(@Body User user);

    @POST("/v1/User/Catch")
    Call<Void> catchUser(@Body User user);

    @GET("/v1/User/Caught")
    Call<User[]> caughtUsers();

    @GET("/v1/User/Conversations")
    Call<Messages[]> getConversations(@Query("pageSize") Integer pageSize,
                                      @Query("pageNumber") Integer pageNumber);

    @GET("/v1/User/Conversation")
    Call<Messages[]> getConversations(@Query("id") Integer id,
                                      @Query("pageSize") Integer pageSize,
                                      @Query("pageNumber") Integer pageNumber);

    @POST("/v1/User/Conversation")//RecipientId, Message
    Call<Messages> sendMessage(@Body Messages message);


    //Calling Auth

    @FormUrlEncoded
    @POST("/token")
    Call<Account> getToken(@Field(value = "grant_type", encoded = true) String grantType,
                           @Field(value = "username", encoded = true) String username,
                           @Field(value = "password", encoded = true) String password);
}