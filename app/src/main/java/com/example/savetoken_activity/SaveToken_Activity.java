package com.example.savetoken_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.List;

public class SaveToken_Activity extends AppCompatActivity {
    ListView listView;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    Button add,view;
    List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_token);
       listView=findViewById(R.id.list_id);
        add=findViewById(R.id.Add_id);
        view=findViewById(R.id.View_id);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("TokenUser");
        list=new ArrayList<>();


       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseInstallations.getInstance().getId().addOnCompleteListener(new OnCompleteListener<String>() {
                   @Override
                   public void onComplete(@NonNull Task<String> task) {
                       if (task.isSuccessful())
                       {
                           String Token=task.getResult();
                           Toast.makeText(SaveToken_Activity.this, "Token Created Successfully", Toast.LENGTH_SHORT).show();
                           SaveData(Token);
                       }
                   }
                   private void SaveData(String token) {
                       String Email=firebaseAuth.getCurrentUser().getEmail();
                       Model model=new Model(Email,token);
                       databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful())
                               {

                                   Toast.makeText(SaveToken_Activity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                               }
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(SaveToken_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                   }


               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(SaveToken_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });

           }
       });
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                 databaseReference=FirebaseDatabase.getInstance().getReference("TokenUser");
               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                           Model model=dataSnapshot.getValue(Model.class);
                        list.add(model);

                       }
                       Myadapter myadapter=new Myadapter(SaveToken_Activity.this,list);
                       listView.setAdapter(myadapter);




                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }
       });


    }








}