package com.example.notifications.networkk;

import com.example.notifications.notificationbody.NotificationBody;
import com.example.notifications.notificationbody.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
//    @Headers(
//            {
//
//                    "Authorization:key=AAAAXwbaLKw:APA91bGQZNKik_uZzAiLB4eAyDrK4UBWJN9baS6wPLKlMXBGWRgFBN04O8-DDRLjK2V3CHZOhf0kqVsSUnj1aguQtRZKrTcaQnCiRNR4oHAyokmUYwUYG2zvZHKtYthy9GopGh6abonQ"
//
//
//            }
//    )
//
//    @POST("fcm/send")
//    Call<MyResponse> sendNotifcation(@Body NotificationSender body);


    @Headers({"Content-Type:application/json",
            "apikey: a"})
    @POST("inappnotification/pushnotification")
    Call<NotificationResponse> sendNotifcation(@Body NotificationBody body);



}

