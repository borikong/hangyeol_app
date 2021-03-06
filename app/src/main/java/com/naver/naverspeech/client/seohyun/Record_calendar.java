package com.naver.naverspeech.client.seohyun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.naver.naverspeech.client.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record_calendar extends AppCompatActivity {

    private TextView tv, questionTv;
    private Intent intent;
    private String filename, date, questionVoca, questionSpeed, questionContin, questionPro, questionLogic;
    private int num, areaNum;
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    static boolean calledAlready = false;
    private dailyTestQuestionItem item;
    private dailyTestScoreItem scoreItem;
    private StorageReference mStorageRef;
    private Button chooseBtn, uploadBtn, downBtn, playBtn;
    private Button vocaStopBtn;
    private Uri filePath;
    private ImageView ivPreview;
    private MediaPlayer mediaPlayer;
    private File localFile;
    private int playBackPosition;

    MediaPlayer mp; // ?????? ????????? ?????? ??????
    int pos; // ?????? ?????? ??????
    private ImageButton bStart;
    private ImageButton bPause;
    private ImageButton bRestart;
    private ImageButton bStop;

    SeekBar sb; // ?????? ??????????????? ???????????? ?????????
    boolean isPlaying = false; // ??????????????? ????????? ??????

    class MyThread extends Thread {
        @Override
        public void run() { // ???????????? ???????????? ???????????? ?????????
            // ????????? ????????? ????????? ???????????? (?????? ?????? ????????? ??????)
            while(isPlaying) {
                sb.setProgress(mp.getCurrentPosition());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_calendar);

        if (!calledAlready)
        {
            // FirebaseDatabase.getInstance().setPersistenceEnabled(true); // ?????? ?????????????????? ?????? ??????????????? ??????.
            calledAlready = true;
        }

        /*??????*/

        /*
        bStart = (ImageButton)findViewById(R.id.button1);
        bPause = (ImageButton)findViewById(R.id.button2);
        bRestart=(ImageButton)findViewById(R.id.button3);
        bStop  = (ImageButton)findViewById(R.id.button4);
        */

        /*
        sb = (SeekBar)findViewById(R.id.seekBar1);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                isPlaying = true;
                int ttt = seekBar.getProgress(); // ???????????? ??????????????? ??????
                mp.seekTo(ttt);
                mp.start();
                new MyThread().start();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPlaying = false;
                mp.pause();
            }
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser) {
                if (seekBar.getMax()==progress) {

                    bStart.setVisibility(View.VISIBLE);
                    bStop.setVisibility(View.INVISIBLE);
                    bPause.setVisibility(View.INVISIBLE);
                    bRestart.setVisibility(View.INVISIBLE);
                    isPlaying = false;
                    mp.stop();
                }
            }
        });

        */

        /*
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MediaPlayer ?????? ????????? , ??????

                loadAudio(localFile.getAbsoluteFile()+"");

                mp.start(); // ?????? ?????? ??????

                int a = mp.getDuration(); // ????????? ????????????(miliSecond)
                sb.setMax(a);// ???????????? ?????? ????????? ????????? ?????????????????? ??????
                new MyThread().start(); // ????????? ????????? ????????? ??????
                isPlaying = true; // ????????? ????????? ?????? ?????????

                bStart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.VISIBLE);
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ?????? ??????
                isPlaying = false; // ????????? ??????

                mp.stop(); // ??????
                mp.release(); // ?????? ??????
                bStart.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.INVISIBLE);
                bRestart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.INVISIBLE);

                sb.setProgress(0); // ????????? ?????????
            }
        });

        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ????????????
                pos = mp.getCurrentPosition();
                mp.pause(); // ????????????
                bPause.setVisibility(View.INVISIBLE);
                bRestart.setVisibility(View.VISIBLE);
                isPlaying = false; // ????????? ??????
            }
        });
        bRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ?????? ???????????? ?????????
                mp.seekTo(pos); // ???????????? ???????????? ??????
                mp.start(); // ??????
                bRestart.setVisibility(View.INVISIBLE);
                bPause.setVisibility(View.VISIBLE);
                isPlaying = true; // ??????????????? flag ??????
                new MyThread().start(); // ????????? ??????
            }
        });

        */

        mStorageRef = FirebaseStorage.getInstance().getReference();
        tv=(TextView) findViewById(R.id.date_tv);
        questionTv=(TextView) findViewById(R.id.question);
        vocaStopBtn=(Button) findViewById(R.id.vocaStopBtn);
       // uploadBtn = (Button) findViewById(R.id.uploadBtn);
       // downBtn=(Button) findViewById(R.id.downBtn);
       // chooseBtn=(Button) findViewById(R.id.chooseBtn);

        playBtn=(Button)findViewById(R.id.playBtn);


        cardView1 = (CardView) findViewById(R.id.cardView);
        cardView2=(CardView) findViewById(R.id.cardView2);
        cardView3=(CardView) findViewById(R.id.cardView3);
        cardView4=(CardView) findViewById(R.id.cardView4);
        cardView5=(CardView) findViewById(R.id.cardView5);
        /*Fragment1?????? ????????? ???????????? */
        intent = getIntent();
        date= intent.getStringExtra("date");
        tv.setText(date);


        getDailyTestScore();
        getDailyTestQuestion();


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //questionTv.setText(questionVoca);
                questionTv.setText("????????????, IT,??????, ??????, ???????????? ???????????? 1??? ?????? ????????????.");
                questionTv.setTextSize(25);
                areaNum=1;
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //questionTv.setText(questionPro);
                questionTv.setText("???????????? ???????????? ??? ??????????????? ???????????? ???????????? ??? ???????????????.");
                questionTv.setTextSize(25);
                areaNum=2;
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionTv.setText(questionContin);
                questionTv.setTextSize(25);
                areaNum=3;
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionTv.setText(questionPro);
                questionTv.setTextSize(25);
                areaNum=4;
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //questionTv.setText(questionLogic);
                questionTv.setText("????????? ????????? ????????? ?????????.");
                questionTv.setTextSize(25);
                areaNum=5;
            }
        });


        /*
        chooseBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "?????? ????????? ???????????????."), 0);

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });


        downBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //storage
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageRef = storage.getReference();
                StorageReference islandRef;

                islandRef = storageRef.child("audio/"+filename);

                localFile = null;
                try {
                    localFile = File.createTempFile("recorded", "mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = localFile;
                islandRef.getFile(localFile.getAbsoluteFile()).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        System.out.println("getpath : " + finalLocalFile.getPath()+"?????????");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {


                    }

                });

            }
        });

     */

        playBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //storage
                FirebaseStorage storage = FirebaseStorage.getInstance();

                StorageReference storageRef = storage.getReference();
                StorageReference islandRef;

                islandRef = storageRef.child("audio/"+"feedback20181013133205.mp3");

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

                       // System.out.println("getpath : " + finalLocalFile.getPath()+"?????????");
                        play();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }

                });

            }
        });

        vocaStopBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null){
                    playBackPosition=mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                }
            }
        });
    }



    public void play(){
       loadAudio2(localFile.getAbsoluteFile()+"");
    }

    public void getDailyTestScore() {

        //dailyTestQuestion ????????? ????????? ????????? ?????? ????????????.
        DatabaseReference databaseReferenceScore = FirebaseDatabase.getInstance().getReference("dailyTestScore");
        databaseReferenceScore.getDatabase();

        // databaseReference??? ??????(?????????)??? ???????????? ???????????? ???????????????.
        databaseReferenceScore.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                scoreItem = dataSnapshot.getValue(dailyTestScoreItem.class);

                if(date.equals(scoreItem.getDate())){
                    num=scoreItem.getNum();
 //                   Toast.makeText(getApplicationContext(), num+"num??????", Toast.LENGTH_LONG).show();
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

    public void getDailyTestQuestion() {

        //dailyTestQuestion ????????? ????????? ????????? ?????? ????????????.
        DatabaseReference databaseReferenceDailyTest = FirebaseDatabase.getInstance().getReference("dailyTestQuestion");
        databaseReferenceDailyTest.getDatabase();

        // databaseReference??? ??????(?????????)??? ???????????? ???????????? ???????????????.
        databaseReferenceDailyTest.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                item = dataSnapshot.getValue(dailyTestQuestionItem.class);

                if(num == item.getNum()){
                    questionVoca=item.getVocabulary();
                    questionSpeed=item.getSpeed();
                    questionContin=item.getContinuity();
                    questionPro=item.getPronunciation();
                    questionLogic=item.getLogic();
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

    //?????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request????????? 0?????? OK??? ???????????? data??? ????????? ?????? ?????????
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            //Uri ????????? Bitmap?????? ???????????? ImageView??? ?????? ?????????.
            /*
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
  //          Toast.makeText(getApplicationContext(), filePath.toString()+"?????????", Toast.LENGTH_LONG).show();
        }
    }

    //upload the file
    private void uploadFile() {
        //???????????? ????????? ????????? ??????
        if (filePath != null) {
            //????????? ?????? Dialog ?????????
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("????????????...");
            progressDialog.show();


            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique??? ???????????? ?????????.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            filename = areaNum + formatter.format(now) + ".mp4";
            //storage ????????? ?????? ???????????? ????????? ??????.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://apptest-caea3.appspot.com").child("audio/" + filename);
            //???????????????...
            storageRef.putFile(filePath)
                    //?????????
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //????????? ?????? Dialog ?????? ??????
                            Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //?????????
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "????????? ??????!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //?????????
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //?????? ?????? ?????? ???????????? ????????? ????????????. ??? ??????????
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog??? ???????????? ???????????? ????????? ??????
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPlaying = false; // ????????? ??????
        if (mp!=null) {
            mp.release(); // ????????????
        }

        /*
        bStart.setVisibility(View.INVISIBLE);
        bStop.setVisibility(View.INVISIBLE);
        bPause.setVisibility(View.INVISIBLE);
        bRestart.setVisibility(View.INVISIBLE);

        */
    }

    private boolean loadAudio(String path){ // ????????? ????????? ???????????????.
        mp = new MediaPlayer(); // MediaPlayer ????????? ???????????????.
        try{
            mp.setDataSource(path);
            mp.prepare(); // ????????? ???????????????.
            return true;
        }catch(Exception e){ // ????????? ?????? ????????? ????????????
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false; // false??? ???????????????.
        }
    }

    public void loadAudio2(String url){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();



    }


    public void onDestroy(){ // ??????????????? ????????? ???
        super.onDestroy();
        if(mp != null)
            mp.release(); // MediaPlayer ????????? Release?????????.
        mp = null;
    }

}
