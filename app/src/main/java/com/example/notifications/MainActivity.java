package com.example.notifications;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notifications.networkk.APIService;
import com.example.notifications.networkk.Client;
import com.example.notifications.notificationbody.NotificationBody;
import com.example.notifications.notificationbody.NotificationResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";
    final String topic = "cricketscore";
    Button sendinoti,sendnotiall;
    EditText title,message;
    MyFirebaseMessaging myFirebaseMessaging;
    APIService apiService;
    TextView token;
    static String refreshedToken,yourList;
    String strtitle,strmessage,strtoken ;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
        title=findViewById(R.id.titlenew);
        message=findViewById(R.id.messagenew);
        sendinoti=findViewById(R.id.sendnoti);
        token=findViewById(R.id.token);
        sendnotiall=findViewById(R.id.sendnotiall);
        Log.d("VERSIONCOE",""+getVersionCode());
        HashMap<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("new_version_code", String.valueOf(getVersionCode()));

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10) // change to 3600 on published app
                .build();

        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(defaultsRate);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    final String new_version_code = mFirebaseRemoteConfig.getString("new_version_code");

                    if(Integer.parseInt(new_version_code) > getVersionCode())
                        showTheDialog("com.facebook.lite", new_version_code );
                }
                else Log.e("MYLOG", "mFirebaseRemoteConfig.fetchAndActivate() NOT Successful");

            }
        });

        sendinoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validation()){
                        refreshedToken = FirebaseInstanceId.getInstance().getToken();
                        Log.d(TAG, "Refreshed token: " + refreshedToken);
                        sendNotifications();
                        throw new RuntimeException("Test Crash");
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

        sendnotiall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                   if(validation()){
                       sendNotificationstoall();
                   }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });





    }

    public boolean validation() {
        strtitle = title.getText().toString();
       strmessage = message.getText().toString();

        String strValueStatus = "";
        boolean flag = false;

        if (strtitle.isEmpty()) {
           title.setError(" Please Enter title");
            strValueStatus = "false ";
        }

        if (strmessage.isEmpty()) {
            message.setError(" Please Enter Message");
            strValueStatus = " false";
        } else {
            strValueStatus = "true ";
        }

        if (strValueStatus.contains("false")) {
            flag = false;
        } else {
            flag = true;

        }
        return flag;
    }

    public void sendNotifications() {
         NotificationBody notificationBody = new NotificationBody();
        notificationBody.setDeviceToken(refreshedToken);
        notificationBody.setMessage(strmessage);
        notificationBody.setTitle(strtitle);

        Gson gson = new Gson();
        yourList = gson.toJson(notificationBody);
        Log.d("Register", "Register" + yourList);
        Call<NotificationResponse> responceCall = Client.getApiClient(MainActivity.this).sendNotifcation(notificationBody);
        responceCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                Log.d("Notificationsend", "" + call.request().url().toString()+ " "+response);
                if (response.code() == 200) {
                    if(response.body().getStatus().equals("invalid userid")){
                        Toast.makeText(MainActivity.this, "Failed ", Toast.LENGTH_LONG);
                    }


                }

            }
            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }

        });
    }



    public void sendNotificationstoall() {
        NotificationBody notificationBody = new NotificationBody();
        notificationBody.setDeviceToken("/topics/cricketscore");
        notificationBody.setMessage(strmessage);
        notificationBody.setTitle(strtitle);

        Gson gson = new Gson();
        yourList = gson.toJson(notificationBody);
        Log.d("Register", "Register" + yourList);
        Call<NotificationResponse> responceCall = Client.getApiClient(MainActivity.this).sendNotifcation(notificationBody);
        responceCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                Log.d("Notificationsend", "" + call.request().url().toString()+ " "+response);
                if (response.code() == 200) {
                    if(response.body().getStatus().equals("invalid userid")){
                        Toast.makeText(MainActivity.this, "Failed ", Toast.LENGTH_LONG);
                    }


                }

            }
            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }

        });
    }


    private void showTheDialog(final String appPackageName, String versionFromRemoteConfig){
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Update")
                .setMessage("This is the old version , please update to version: "+versionFromRemoteConfig)
                .setPositiveButton("UPDATE", null)
                .show();

        dialog.setCancelable(false);

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }
    private PackageInfo pInfo;
    public int getVersionCode() {
        pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("MYLOG", "NameNotFoundException: "+e.getMessage());
        }
        return pInfo.versionCode;
    }
}