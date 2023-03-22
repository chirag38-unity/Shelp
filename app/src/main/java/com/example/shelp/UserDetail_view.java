package com.example.shelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UserDetail_view extends AppCompatActivity {

    SharedPreferences UserData;
    private static final String SHARED_PREF_NAME = "userdata";
    private static final String KEY_NAME = "name";
    private static final String KEY_BGP = "blood-grp";
    private static final String KEY_PHN = "phone-number";


    Button edit,home;
    TextView vw_name, vw_blood_grp, vw_phone_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_view);

        vw_name = findViewById(R.id.vw_ed_name);
        vw_blood_grp = findViewById(R.id.vw_ed_bloodgp);
        vw_phone_no = findViewById(R.id.vw_ed_phn);

        UserData = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = UserData.getString(KEY_NAME, null);
        String blood_grp = UserData.getString(KEY_BGP, null);
        String phone_no = UserData.getString(KEY_PHN, null);

        if(name != null && blood_grp != null && phone_no != null){
           vw_name.setText(name);
           vw_blood_grp.setText(blood_grp);
           vw_phone_no.setText(phone_no);
        }

        home = findViewById(R.id.vw_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(UserDetail_view.this, MainActivity.class);
                UserDetail_view.this.startActivity(I);
            }
        });

        edit = findViewById(R.id.vw_registerbtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(UserDetail_view.this, UserDetails_register.class);
                UserDetail_view.this.startActivity(I);
            }
        });

    }


}