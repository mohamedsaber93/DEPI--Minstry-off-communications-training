package com.example.contactsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText contactName, contactNumber;
    Button saveButton;
    ListView contactList;
    ArrayList<Contact> contacts;
    ArrayAdapter<String> adapter;
    ArrayList<String> contactNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request CALL_PHONE permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

        db = new DatabaseHelper(this);

        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);
        saveButton = findViewById(R.id.saveButton);
        contactList = findViewById(R.id.contactList);

        contacts = db.getAllContacts();
        contactNames = new ArrayList<>();

        for (Contact contact : contacts) {
            contactNames.add(contact.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactNames);
        contactList.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contactName.getText().toString().trim();
                String phone = contactNumber.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = db.insertContact(name, phone);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Contact saved", Toast.LENGTH_SHORT).show();
                    contacts.add(new Contact(name, phone));
                    contactNames.add(name);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Contact save failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = contacts.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + selectedContact.getPhone()));
                startActivity(intent);
            }
        });
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "CALL_PHONE Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CALL_PHONE Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
