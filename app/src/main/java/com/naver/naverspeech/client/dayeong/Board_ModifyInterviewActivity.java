package com.naver.naverspeech.client.dayeong;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.R;

import java.util.HashMap;

public class Board_ModifyInterviewActivity extends AppCompatActivity {
    private EditText title, company, specific_type, content;
    private String type,key,id;
    private Button modifybtn;
    private ImageButton cancelBtn;
    private DatabaseReference databaseReference;
    private  Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_interview);
        key=getIntent().getStringExtra("date");

        getData();
        setSpinner();
        setlayout();

    }

    private void setlayout(){
        title= (EditText) findViewById(R.id.title);
        company= (EditText) findViewById(R.id.company);
        type=spinner.getSelectedItem().toString();
        specific_type=(EditText)findViewById(R.id.specific_type);
        content=(EditText)findViewById(R.id.content);
        modifybtn=(Button)findViewById(R.id.modifybtn);
        cancelBtn=(ImageButton) findViewById(R.id.btn_cancel);
        modifybtn.setText("수정하기");

        //글수정 버튼 리스너
        modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titletxt=title.getText().toString();
                String companytxt=company.getText().toString();
                String specific_typetxt=specific_type.getText().toString();
                String contenttxt=content.getText().toString();

                modifyData(id,titletxt,companytxt,type,specific_typetxt,contenttxt);

                finish();

            }
        });

        //취소 버튼 리스너
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Board_ModifyInterviewActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getData(){
        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview").child(key);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str=dataSnapshot.getValue().toString();

                if(dataSnapshot.getKey().equals("company")){
                    company.setText(str);
                }else if(dataSnapshot.getKey().equals("content")){
                    content.setText(str);
                }else if(dataSnapshot.getKey().equals("specific_type")){
                    specific_type.setText(str);
                }else if(dataSnapshot.getKey().equals("title")){
                    title.setText(str);
                }else if(dataSnapshot.getKey().equals("type")){

                }else if(dataSnapshot.getKey().equals("id")){
                    id=str;
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

            }
        });
    }

    private void modifyData(String id, String title, String company, String type, String specific_type, String content){
//        HashMap<String,Object> titlehash=new HashMap<>();
//        HashMap<String,Object> companyhash=new HashMap<>();
//        HashMap<String,Object> typehash=new HashMap<>();
//        HashMap<String,Object> specific_typehash=new HashMap<>();
//        HashMap<String,Object> contenthash=new HashMap<>();
//
//        titlehash.put("title",title);
//        companyhash.put("company",company);
//        typehash.put("type",type);
//        specific_typehash.put("specific_type",specific_type);
//        contenthash.put("content",content);

        HashMap<String,Object> updatedata=new HashMap<>();
        InterviewDataItem item=new InterviewDataItem();
        item.setId(id);
        item.setTitle(title);
        item.setCompany(company);
        item.setType(type);
        item.setSpecific_type(specific_type);
        item.setContent(content);
        updatedata.put(key,item);

        databaseReference = FirebaseDatabase.getInstance().getReference("board_interview").child(key);
        databaseReference.updateChildren(updatedata, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(getApplicationContext(),"수정완료",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Board_ModifyInterviewActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setSpinner(){
        spinner = (Spinner)findViewById(R.id.category_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
