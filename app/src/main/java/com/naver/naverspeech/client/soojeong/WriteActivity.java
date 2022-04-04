package com.naver.naverspeech.client.soojeong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.naver.naverspeech.client.QuestionItem2;
import com.naver.naverspeech.client.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class WriteActivity extends AppCompatActivity {

    private EditText titleTxt;
    private EditText contentTxt;
    private Button okBtn,uploadBtn;
    private Uri filePath;
    private String file="";

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //feeback 이라는 자식의 데이터 몽땅 가져온다.
        databaseReference = FirebaseDatabase.getInstance().getReference("feedback");

        titleTxt = (EditText) findViewById(R.id.title);
        contentTxt = (EditText) findViewById(R.id.content);
        okBtn = (Button) findViewById(R.id.ok_Btn);
        uploadBtn = (Button) findViewById(R.id.upload_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title = titleTxt.getText().toString();
                String content = contentTxt.getText().toString();
                uploadFile();
                //writeNewUser() 함수를 통해 새로운 글을 추가한다.
           //     Toast.makeText(getApplicationContext(), file.toString(),Toast.LENGTH_LONG).show();
                if(file.equals("")) {
                    writeNewUser("testId", content, title);
                    finish();
                }
                else
                    writeNewUser("testId", content, title, file);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "음성 파일을 선택하세요."), 0);

            }
        });
    }


    private void writeNewUser(String userId, String content, String title) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );
        String temp =  date.substring(0,4)+"."+  date.substring(4,6)+"."+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10);
//        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();

                //추가하길 원하는 item class를 만든다.
        FeedbackItem feedback = new FeedbackItem(userId, content, title, date);

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child(date).setValue(feedback);

    }

    private void writeNewUser2() {

        QuestionItem2 qq = new QuestionItem2("Pronunciation","내가 그린 기린 그림은 긴 기린 그림이고 니가 그린 기린 그림은 안 긴 기린 그림이다.", "생각이란 생각하면 생각 할 수록 생각나는 것이 생각이므로 생각하지 않는 생각이 좋은 생각이라 생각한다.",
                "작년에 온 솥 장수는 헌 솥 장수이고, 금년에 온 솥 장수는 새 솥 장수다. ", "귀돌이네 담밑에서 귀뚜라미가 귀뚤뚤뚤 귀뚤뚤뚤, 똘똘이네 담밑에서 귀뚜라미가 똘돌돌돌 똘돌돌돌 ","나풀 나풀 나비가 나팔꽃에 날아가 놀고 있는데 늴리리 늴리리 나팔소리에 놀라 나팔꽃에서 놀지 못하고 나리꽃으로 날아갔데요.");

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child("trainingQuestion").setValue(qq);

    }

     private void writeNewUser(String userId, String content, String title, String file) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format ( currentTime );
        String temp =  date.substring(0,4)+"."+  date.substring(4,6)+"."+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10);

        //추가하길 원하는 item class를 만든다.
        InterviewDataItem2 feedback = new InterviewDataItem2(userId, content, title, date, file);

        //date 값을 상위 자식으로 설정(기본키 비슷하한 역할)하고 만들어 둔 item을 추가한다.
        databaseReference.child(date).setValue(feedback);

    }


    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
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
            file = "feedback" +formatter.format(now) + ".mp3";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://apptest-caea3.appspot.com").child("audio/" + file);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            finish();

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
           // Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }

    }
}
