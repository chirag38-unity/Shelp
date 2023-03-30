package com.example.shelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.util.*;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button_userdata, button_contacts, button_send_SOS;
    SharedPreferences UserData;

    private static final String SHARED_PREF_NAME = "userdata";
    private static final String KEY_NAME = "name";
    private static final String KEY_BGP = "blood-grp";
    private static final String KEY_PHN = "phone-number";
    private final static int REQUEST_CODE = 100;

    DBHelper DB;
    private ArrayList<String> number = new ArrayList<>();
    private String Add_Latitude, Add_Longitude, Add_Address, Add_City, Add_Country;
    private FusedLocationProviderClient fusedLocationProvideClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this, "ContactsData.db", 1);
        fusedLocationProvideClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        button_userdata = findViewById(R.id.HRegBT);
        button_contacts = findViewById(R.id.HContacts);
        button_send_SOS = findViewById(R.id.send_SOS);

        getLastLocation();
        UserData = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name = UserData.getString(KEY_NAME,null);
        String blood_grp = UserData.getString(KEY_BGP, null);
        if(name != null){
            button_userdata.setText(R.string.view_user);
        }
        if(name == null){
            Toast.makeText(this, "Add User Details please...", Toast.LENGTH_SHORT).show();
            Intent I = new Intent(MainActivity.this, UserDetails_register.class);
            MainActivity.this.startActivity(I);
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

        button_send_SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();
                String add = "\nLatitude: " + Add_Latitude + "\nLongitude: " + Add_Longitude + "\nAddress: " + Add_Address + "\nCity: " + Add_City + "\nCountry: " + Add_Country;
                String info =  "\nBloodGrp: " + blood_grp;
                String msg = "Hello my name is " + name + "and I am in danger " + info + add ;
                SmsManager smsManager = SmsManager.getDefault();
                Cursor cursor = DB.getdata();

                try{

                    if(cursor != null){
                        while (cursor.moveToNext()) {
                            number.add(cursor.getString(1));
                        }
                        for(int j=0; j < number.size(); j++){
                            smsManager.sendTextMessage(number.get(j), null, msg, null, null);
                        }
                        Toast.makeText(MainActivity.this, "SOS Sent", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "There are no contacts", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Message Sending Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getLastLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProvideClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Add_Latitude = String.valueOf(location.getLatitude());
                            Add_Longitude = String.valueOf(location.getLongitude());
                            Add_Address = String.valueOf(addresses.get(0).getAddressLine(0));
                            Add_City = String.valueOf(addresses.get(0).getLocality());
                            Add_Country = String.valueOf(addresses.get(0).getCountryName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this, "Require Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}