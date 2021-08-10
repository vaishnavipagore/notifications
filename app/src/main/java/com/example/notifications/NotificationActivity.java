package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    String title,meassage;
    TextView notititle,notimessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notititle=findViewById(R.id.titlenewdisplay);
        notimessage=findViewById(R.id.messagenewdisplay);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
           title = extras.getString("notititle");
           notititle.setText(title);
           meassage= extras.getString("notimessage");
           notimessage.setText(meassage);

        }
    }
}