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

    MediaPlayer mp; // 음악 재생을 위한 객체
    int pos; // 재생 멈춘 시점
    private ImageButton bStart;
    private ImageButton bPause;
    private ImageButton bRestart;
    private ImageButton bStop;

    SeekBar sb; // 음악 재생위치를 나타내는 시크바
    boolean isPlaying = false; // 재생중인지 확인할 변수

    class MyThread extends Thread {
        @Override
        public void run() { // 쓰레드가 시작되면 콜백되는 메서드
            // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
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
            // FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;
        }

        /*시작*/

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
                int ttt = seekBar.getProgress(); // 사용자가 움직여놓은 위치
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
                // MediaPlayer 객체 초기화 , 재생

                loadAudio(localFile.getAbsoluteFile()+"");

                mp.start(); // 노래 재생 시작

                int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                isPlaying = true; // 씨크바 쓰레드 반복 하도록

                bStart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.VISIBLE);
            }
        });

        bStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 음악 종료
                isPlaying = false; // 쓰레드 종료

                mp.stop(); // 멈춤
                mp.release(); // 자원 해제
                bStart.setVisibility(View.VISIBLE);
                bPause.setVisibility(View.INVISIBLE);
                bRestart.setVisibility(View.INVISIBLE);
                bStop.setVisibility(View.INVISIBLE);

                sb.setProgress(0); // 씨크바 초기화
            }
        });

        bPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 일시중지
                pos = mp.getCurrentPosition();
                mp.pause(); // 일시중지
                bPause.setVisibility(View.INVISIBLE);
                bRestart.setVisibility(View.VISIBLE);
                isPlaying = false; // 쓰레드 정지
            }
        });
        bRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 멈춘 지점부터 재시작
                mp.seekTo(pos); // 일시정지 시점으로 이동
                mp.start(); // 시작
                bRestart.setVisibility(View.INVISIBLE);
                bPause.setVisibility(View.VISIBLE);
                isPlaying = true; // 재생하도록 flag 변경
                new MyThread().start(); // 쓰레드 시작
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
        /*Fragment1에서 넘겨온 날짜정보 */
        intent = getIntent();
        date= intent.getStringExtra("date");
        tv.setText(date);


        getDailyTestScore();
        getDailyTestQuestion();


        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //questionTv.setText(questionVoca);
                questionTv.setText("인공지능, IT,한계, 발전, 편리성를 사용하여 1분 동안 말하시오.");
                questionTv.setTextSize(25);
                areaNum=1;
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //questionTv.setText(questionPro);
                questionTv.setText("간장공장 공장장은 강 공장장이고 된장공장 공장장은 공 공장장이다.");
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
                questionTv.setText("당신을 사물로 표현해 주세요.");
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
                startActivityForResult(Intent.createChooser(intent, "음성 파일을 선택하세요."), 0);

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

                        System.out.println("getpath : " + finalLocalFile.getPath()+"입니당");
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

                       // System.out.println("getpath : " + finalLocalFile.getPath()+"입니당");
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

        //dailyTestQuestion 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReferenceScore = FirebaseDatabase.getInstance().getReference("dailyTestScore");
        databaseReferenceScore.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReferenceScore.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                scoreItem = dataSnapshot.getValue(dailyTestScoreItem.class);

                if(date.equals(scoreItem.getDate())){
                    num=scoreItem.getNum();
 //                   Toast.makeText(getApplicationContext(), num+"num이다", Toast.LENGTH_LONG).show();
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

        //dailyTestQuestion 이라는 자식의 데이터 몽땅 가져온다.
        DatabaseReference databaseReferenceDailyTest = FirebaseDatabase.getInstance().getReference("dailyTestQuestion");
        databaseReferenceDailyTest.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
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

    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
            /*
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
  //          Toast.makeText(getApplicationContext(), filePath.toString()+"입니당", Toast.LENGTH_LONG).show();
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();


            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date now = new Date();
            filename = areaNum + formatter.format(now) + ".mp4";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://apptest-caea3.appspot.com").child("audio/" + filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPlaying = false; // 쓰레드 정지
        if (mp!=null) {
            mp.release(); // 자원해제
        }

        /*
        bStart.setVisibility(View.INVISIBLE);
        bStop.setVisibility(View.INVISIBLE);
        bPause.setVisibility(View.INVISIBLE);
        bRestart.setVisibility(View.INVISIBLE);

        */
    }

    private boolean loadAudio(String path){ // 오디오 파일을 로드합니다.
        mp = new MediaPlayer(); // MediaPlayer 객체를 생성합니다.
        try{
            mp.setDataSource(path);
            mp.prepare(); // 파일을 준비합니다.
            return true;
        }catch(Exception e){ // 오디오 파일 로드에 실패하면
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false; // false를 반환합니다.
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


    public void onDestroy(){ // 액티비티가 종료될 때
        super.onDestroy();
        if(mp != null)
            mp.release(); // MediaPlayer 객체를 Release합니다.
        mp = null;
    }

}
