package com.ashok.packt.realtime.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ashok.packt.realtime.database.adapter.RecyclerViewAdapter;
import com.ashok.packt.realtime.database.model.Donor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference myDatabaseReference;
    private String personId;
    private List<Donor> ItemList;
    private RecyclerView mRecyclerview;
    private RecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = (RecyclerView) findViewById(R.id.peopleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);

        // for data persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        myDatabaseReference=FirebaseDatabase.getInstance().getReference("Donor");
        personId= myDatabaseReference.push().getKey();


        (findViewById(R.id.addBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FullName = ((EditText)findViewById(R.id.donorNameInput)).getText().toString();
                String Email = ((EditText)findViewById(R.id.donorEmailInput)).getText().toString();
                String City = ((EditText)findViewById(R.id.donorCityInput)).getText().toString();
                String BloodGroup = ((EditText)findViewById(R.id.donorBloodGroupInput)).getText().toString();


                addPerson(FullName,Email, City, BloodGroup);
            }
        });


        (findViewById(R.id.loadBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });


    }

    private void addPerson(String name, String Email, String city, String Bloodgroup){
        Donor person = new Donor(name, Email, city, Bloodgroup);
        myDatabaseReference.child(personId).setValue(person);
    }
    private void updatePerson(String name,int phoneNumber){
        myDatabaseReference.child(personId).child("fullName").setValue(name);
        myDatabaseReference.child(personId).child("phoneNumber").setValue(phoneNumber);
    }

    private void removePerson(String name){
        myDatabaseReference.child(personId).removeValue();
    }



    private void readData(){
        ItemList = new ArrayList<>();
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while((iterator.hasNext())){
                    Donor donor = iterator.next().getValue(Donor.class);
                    ItemList.add(donor);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new RecyclerViewAdapter(this, ItemList);
        mRecyclerview.setAdapter(mAdapter);

    }


    private void findPerson(String name){
        Query deleteQuery = myDatabaseReference.orderByChild("fullName").equalTo(name);
        deleteQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while((iterator.hasNext())){
                    Log.d("Item found: ",iterator.next().getValue().toString()+"---");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Item not found: ","this item is not in the list");
            }
        });
    }
}