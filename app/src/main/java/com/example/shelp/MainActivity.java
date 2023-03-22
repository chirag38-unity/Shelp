package com.example.shelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_userdata, button_contacts;
    SharedPreferences UserData;

    private static final String SHARED_PREF_NAME = "userdata";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_userdata = findViewById(R.id.HRegBT);
        button_contacts = findViewById(R.id.HContacts);

        UserData = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = UserData.getString(KEY_NAME,null);
        if(name != null){
            button_userdata.setText(R.string.view_user);
        }

        button_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name != null){
                    Intent I = new Intent(MainActivity.this, UserDetail_view.class);
                    MainActivity.this.startActivity(I);
                }else {
                    Intent I = new Intent(MainActivity.this, UserDetails_register.class);
                    MainActivity.this.startActivity(I);
                }
            }
        });

        button_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, Contacts.class);
                MainActivity.this.startActivity(I);
            }
        });
    }
}