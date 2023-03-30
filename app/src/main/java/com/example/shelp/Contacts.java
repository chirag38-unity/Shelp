package com.example.shelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity implements ContactsDialog.ContactDialogListener{


    RecyclerView recyclerView;
    ArrayList<String> name;
    ArrayList<String> number;
    DBHelper DB;
    MyAdapter adapter;
    ImageButton AddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        DB = new DBHelper(this,"ContactsData.db", 1);
        name = new ArrayList<>();
        number = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this, name, number);
        recyclerView.setAdapter(adapter);
//        adapter.setOnEntryDeleteListener(new MyAdapter.OnEntryDeleteListener() {
//            @Override
//            public void OnEntryDelete(int position) {
//                Boolean checkDeleteData = DB.deleteContact(del_name);
//                if(checkDeleteData == true){
//                    Toast.makeText(Contacts.this, "Contact Inserted", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(Contacts.this, "Insertion failed", Toast.LENGTH_SHORT).show();
//                }
//                Intent I = new Intent(Contacts.this, Contacts.class);
//                Contacts.this.startActivity(I);
//            }
//        });

        adapter.setOnEntryDeleteListener(new MyAdapter.OnEntryDeleteListener() {
            @Override
            public void OnEntryDelete(String name) {
                Boolean checkDeleteData = DB.deleteContact(name);
                if(checkDeleteData == true){
                    Toast.makeText(Contacts.this, "Contact Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Contacts.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                }
                Intent I = new Intent(Contacts.this, Contacts.class);
                Contacts.this.startActivity(I);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        AddContact = findViewById(R.id.button_add);
        AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void displayData() {
        Cursor cursor = DB.getdata();
        if(cursor.getCount() == 0){
            Toast.makeText(Contacts.this, "Please add a contact", Toast.LENGTH_SHORT).show();
            return;
        }else{
            while (cursor.moveToNext()){
                name.add(cursor.getString(0));
                number.add(cursor.getString(1));
            }
        }

    }

    public void openDialog(){
        ContactsDialog dialog = new ContactsDialog();
        dialog.show(getSupportFragmentManager(), "Add Contact");
    }

    @Override
    public void applyTexts(String Name, String Number) {

        Boolean checkInsertData = DB.insertContact(Name, Number);
        if(checkInsertData == true){
            Toast.makeText(Contacts.this, "Contact Inserted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Contacts.this, "Insertion failed", Toast.LENGTH_SHORT).show();
        }
        Intent I = new Intent(Contacts.this, Contacts.class);
        Contacts.this.startActivity(I);
    }
}