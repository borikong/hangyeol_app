package com.naver.naverspeech.client.soojeong;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.naver.naverspeech.client.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeddbackDetailActivity extends AppCompatActivity {

    private TextView userIdTxt, dateTxt, titleTxt, contentTxt;
    private String userId, uDate, title, content, file;
    private ListView listview;
    private ListViewAdapter2 adapter;
    private Button btn;
    private EditText editText;
    private File localFile;
    private LinearLayout layout;

    private ImageButton startBtn, stopBtn, reStartBtn, pauseBtn, deletebtn;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feddback_detail);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        uDate = intent.getStringExtra("date");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        file = intent.getStringExtra("file");
       // Toast.makeText(getApplicationContext(),"file: "+file,Toast.LENGTH_LONG).show();

        layout = (LinearLayout) findViewById(R.id.playLayout);
        downloadFile();
        if(file!=null)
            layout.setVisibility(View.VISIBLE);
        else
            layout.setVisibility(View.GONE);


        userIdTxt = (TextView) findViewById(R.id.userId);
        dateTxt = (TextView) findViewById(R.id.date);
        titleTxt = (TextView) findViewById(R.id.title);
        contentTxt = (TextView) findViewById(R.id.content);
        editText = (EditText) findViewById(R.id.comment);

        listview = (ListView) findViewById(R.id.listView);
        adapter=new ListViewAdapter2();
        getFeedbackData(uDate);

        startBtn = (ImageButton) findViewById(R.id.startBtn);
        stopBtn = (ImageButton) findViewById(R.id.stopBtn);
        reStartBtn = (ImageButton) findViewById(R.id.reStartBtn);
        pauseBtn = (ImageButton) findViewById(R.id.pauseBtn);
        deletebtn = (ImageButton) findViewById(R.id.deletebtn);


        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          Toast.makeText(getApplicationContext(), editText.getText().toString(),Toast.LENGTH_LONG).show();
                writeNewUser("testId",editText.getText().toString(),"??????");
                editText.setText("");
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(localFile.getAbsoluteFile()+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                startBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);

            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                pauseBtn.setVisibility(View.GONE);
                reStartBtn.setVisibility(View.VISIBLE);

            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();

                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                reStartBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.GONE);
            }
        });
        reStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

                pauseBtn.setVisibility(View.VISIBLE);
                reStartBtn.setVisibility(View.GONE);

            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listview.setAdapter(adapter);
        userIdTxt.setText(userId);
        dateTxt.setText(uDate);
        titleTxt.setText(title);
        contentTxt.setText(content);
    }

    private void writeNewUser(String cUserId, String content, String title) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date2 = mSimpleDateFormat.format ( currentTime );

        //???????????? ????????? item class??? ?????????.
        FeedbackItem feedback = new FeedbackItem(cUserId, content, title, date2);

        //date ?????? ?????? ???????????? ??????(????????? ???????????? ??????)?????? ????????? ??? item??? ????????????.

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback");
        databaseReference.child(uDate).child("comment").child(date2).setValue(feedback);
    }

    public void getFeedbackData(String date) {

        //feeback ????????? ????????? ????????? ?????? ????????????.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("feedback");
        databaseReference.getDatabase();


      //  Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        // databaseReference??? ??????(?????????)??? ???????????? ???????????? ???????????????.

        databaseReference.child(date).child("comment").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //???????????? ????????? FeedbackItem??? ????????????.
                FeedbackItem item = dataSnapshot.getValue(FeedbackItem.class);
                adapter.addItem("?????????>",item.getContent(),item.getDate(),item.getUserId());
                adapter.notifyDataSetChanged();



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

    public void onDestroy(){ // ??????????????? ????????? ???
        super.onDestroy();
        if(mediaPlayer != null)
            mediaPlayer.release(); // MediaPlayer ????????? Release?????????.
        mediaPlayer = null;
    }

    public boolean downloadFile() {
        //storage
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        StorageReference islandRef;


        final boolean flag = false;
        String temp;
        islandRef = storageRef.child("audio/"+file);

        localFile = null;
        try {
            localFile = File.createTempFile("recorded", "mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File finalLocalFile = localFile;
        islandRef.getFile(localFile.getAbsoluteFile()).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created

    //            Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
    //            Toast.makeText(getApplicationContext(), finalLocalFile.getPath() + "", Toast.LENGTH_SHORT).show();
                System.out.println("getpath : " + finalLocalFile.getPath()+"?????????");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
      //          Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
            }

        });
        if(localFile.getAbsoluteFile()!=null)
            return true;
        else
            return false;
    }
}
