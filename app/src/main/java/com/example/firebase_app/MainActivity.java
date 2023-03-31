package com.example.firebase_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText name,registerno,course;
    Button add;
    String name1,course1,roll1;
    String TAG = "Read Data Activity";
    FirebaseFirestore Fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fstore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);
        registerno = findViewById(R.id.registerno);
        course = findViewById(R.id.course);
        add = findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                name1 = name.getText().toString().trim();
                roll1 = registerno.getText().toString().trim();
                course1 = course.getText().toString().trim();

                Map<String ,Object> student = new HashMap<>();
                student.put("name",name1);
                student.put("roll",roll1);
                student.put("course",course1);

                Fstore.collection("students").add(student).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MainActivity.this, "student details stored", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,Read_data.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "data not stored", Toast.LENGTH_SHORT).show();
                    }
                });

                Fstore.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "data read", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG,document.getId()+"=>"+document.getData());
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }
}