package com.example.thom.firebase_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInActivity extends AppCompatActivity {

    private FirebaseAuth authTest;
    private FirebaseAuth.AuthStateListener authListenerTest;
    private static final String TAG = "Firebase_test";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        authTest = FirebaseAuth.getInstance();
        setListener();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void setListener() {
//         authListenerTest = (FirebaseAuth.AuthStateListener) (firebaseAuth) - {
//             FirebaseUser user = firebaseAuth.getCurrentUser();
//
//             if (user != null){
//                 // user is signed in
//                 Log.d(TAG, "onAuthStateChanged:signed_in: " + user.getUid());
//             } else {
//                 Log.d(TAG, "onAuthStateChanged:signed_out");
//                 goToRegisterLoginActivity();
//             }
//         };
    }


    private void goToRegisterLoginActivity(){
        startActivity(new Intent(LoggedInActivity.this, MainActivity.class));
    }

    public void addToDb(View view){

        EditText editText1 = (EditText) findViewById(R.id.editText1);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        String name = editText1.getText().toString();
        String colour = editText2.getText().toString();

        // add object to database
        Fruit aFruit = new Fruit(name, colour);

        mDatabase.child("fruitbasket").child("fruit1").setValue(aFruit);
    }

    public void getFromDb(View view){

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get object from database
                Fruit aFruit = dataSnapshot.child("fruitbasket").child("fruit1").getValue(Fruit.class);
                TextView textViewData = findViewById(R.id.textViewData);
                textViewData.setText(aFruit.name + " " + aFruit.colour);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addValueEventListener(postListener);

    }
}
