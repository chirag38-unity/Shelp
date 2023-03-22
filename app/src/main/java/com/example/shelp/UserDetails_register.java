package com.example.shelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class UserDetails_register extends AppCompatActivity {

    SharedPreferences UserData;
    EditText name,  phone_number;
    Spinner blood_grp;
    String name_v, blood_grp_v, phone_number_v;
    Button register;

    private static final String SHARED_PREF_NAME = "userdata";
    private static final String KEY_NAME = "name";
    private static final String KEY_BGP = "blood-grp";
    private static final String KEY_PHN = "phone-number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reg);

        UserData = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        name = findViewById(R.id.reg_ed_name);
        blood_grp = findViewById(R.id.reg_ed_bloodgp);
        phone_number = findViewById(R.id.reg_ed_phn);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.blood_grp, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        blood_grp.setAdapter(adapter);

        register = findViewById(R.id.reg_registerbtn);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences.Editor editor = UserData.edit();
               name_v = name.getText().toString();
               blood_grp_v = blood_grp.getSelectedItem().toString();
               phone_number_v = phone_number.getText().toString();

               if( name_v != null && blood_grp_v != null && phone_number_v != null){
                   editor.putString(KEY_NAME,name_v);
                   editor.putString(KEY_BGP, blood_grp_v);
                   editor.putString(KEY_PHN,phone_number_v);
                   editor.commit();
                   Intent I = new Intent(UserDetails_register.this, UserDetail_view.class);
                   UserDetails_register.this.startActivity(I);
                   Toast.makeText(UserDetails_register.this, "Details saved", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(UserDetails_register.this, "Enter all details", Toast.LENGTH_SHORT).show();
               }

            }
        });
    }
}
