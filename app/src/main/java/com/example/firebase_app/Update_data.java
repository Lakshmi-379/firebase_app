package com.example.firebase_app;

import static com.google.firebase.firestore.core.UserData.Source.Update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Update_data extends AppCompatActivity {
    EditText tn1,tn2;
    Button btn1,btn2;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        tn1 = findViewById(R.id.tn1);
        tn2 = findViewById(R.id.tn2);
        btn1 = findViewById(R.id.bn1);
        btn2 = findViewById(R.id.bn2);
        fb = FirebaseFirestore.getInstance();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tn1.getText().toString();
                tn1.setText("");
                DeleteData(name);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tn1.getText().toString().trim();
                String rename = tn2.getText().toString().trim();
                tn1.setText("");
                tn2.setText("");
                UpdateData(name, rename);

            }
        });
    }

    private void DeleteData(String name) {

        fb.collection("students").whereEqualTo("name", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    String documentID = documentSnapshot.getId();




                    fb.collection("students").document(documentID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Update_data.this, "deleted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Update_data.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    startActivity(new Intent(Update_data.this,Read_data.class));
                }
            }

        });
    }

    private void UpdateData(String name, String rename) {
                Map<String ,Object> userdetails = new HashMap<>();
                userdetails.put("name",rename);
                fb.collection("students").whereEqualTo("name",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String DocumentId = documentSnapshot.getId();


                            fb.collection("students").document(DocumentId).update(userdetails).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Update_data.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Update_data.this, "update failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            startActivity(new Intent(Update_data.this,Read_data.class));


                    }

                    }


        });

    }
}