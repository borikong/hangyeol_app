package com.naver.naverspeech.client.dayeong;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import com.naver.naverspeech.client.R;


/*
MS Face API로 구현
 */
public class VirtualInterview_faceActivitity extends AppCompatActivity implements SurfaceHolder.Callback{
    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;
    private final String apiEndpoint = "https://eastasia.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "4fa503372b5f47b9b8fbbf85d37dd43b";

    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean previewing = false;
    private int mDisplayOrientation;

    private int[] happiness, neutral, surprise, anger, contempt, disgust, fear, sadness; //감정변수
    private int timervar;//타이머변수(시간을 구분하는 단위)
    private int emotionvar;
    private int questionvar;
    private ArrayList<String> question;

    //3*3=9이니까 9초마다 시기바뀜 총 9*3=27초
    private static final int TIME_PORTION = 3; //사진 캡쳐하는 시간 단위(초)
    private static final int TIMER_VAR = 3; //TIME_PORTION이 TIME_VAR만큼 지나면 시기(초반, 중반, 후반)가 바뀜 ->5초일때는 12, 10초일때는 6(60초를 한 시기로 정했을대)
    private static final int EMOTION_VAR = 3; //0이면 초반 1이면 중반 2이면 후반

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualinterview_camera);

        final Button buttonStartCameraPreview = (Button) findViewById(R.id.startcamerapreview);
        final TextView questionTv=(TextView)findViewById(R.id.question);
        final LinearLayout linearlayout_question=(LinearLayout)findViewById(R.id.linearlayout_question);
        final Chronometer timer = (Chronometer) findViewById(R.id.timer);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        //화면이 켜진 상태 유지
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initialEmotionScore();//감정변수초기화
        timervar = 0;
        emotionvar = 0;
        questionvar=0; //문제가 3개이기 때문에 emotionvar과 같음
        question=new ArrayList<String>();
        question.add("회사에 입사하기 위해 한 노력을 말해주세요.");
        question.add("당신이 브랜드라면, 모토가 무엇입니까?");
        question.add("롤 모델은 누구이며 이유는 무엇인가요?");

        questionTv.setText((emotionvar+1)+"."+question.get(emotionvar));

        questionTv.setVisibility(View.GONE);
        linearlayout_question.setVisibility(View.GONE);

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int realTime = Integer.parseInt(time / 1000 + "");

                if (realTime >= TIME_PORTION) {
                    timervar++;
                    chronometer.stop();
                    capture();
                    timer.setBase(SystemClock.elapsedRealtime());

                    if (timervar == TIMER_VAR) { //한 시기를 다 측정했음
                        timervar = 0;
                        emotionvar++;//중반이나 후반으로 넘어감
                        if(emotionvar<3){
                            questionTv.setText((emotionvar+1)+"."+question.get(emotionvar));
                        }
                    }
                    chronometer.start();
                }
            }
        });

        //가상면접시작 버튼을 눌렀을 때
        buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!previewing && requestPermissionCamera() == true) { //카메라 접근권한이 확인 되면
                    camera = Camera.open(1); //매개변수가 0이면 후면 1이면 전면(카메라 객체)
                    Camera.Parameters param = camera.getParameters();
                    camera.setDisplayOrientation(90);
                    int m_resWidth;

                    int m_resHeight;

                    m_resWidth = camera.getParameters().getPictureSize().width;

                    m_resHeight = camera.getParameters().getPictureSize().height;

                    Camera.Parameters parameters = camera.getParameters();
                    //해상도 변경
                    m_resWidth = 420;
                    m_resHeight = 280;

                    parameters.setPictureSize(m_resWidth, m_resHeight);
                    camera.setParameters(param);
                    if (camera != null) {
                        try {
                            camera.setPreviewDisplay(surfaceHolder);
                            camera.startPreview();
                            previewing = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    questionTv.setVisibility(View.VISIBLE); //문제 텍스트뷰 보여주기
                    linearlayout_question.setVisibility(View.VISIBLE);
                    buttonStartCameraPreview.setVisibility(View.GONE); //가상면접시작하기 버튼 숨기기
                    timer.start();//타이머 시작
                }
            }
        });
    }
    //감정 변수 초기화
    private void initialEmotionScore() {
        happiness = new int[3];
        neutral = new int[3];
        surprise = new int[3];
        anger = new int[3];
        contempt = new int[3];
        disgust = new int[3];
        fear = new int[3];
        sadness = new int[3];

        for (int i = 0; i < 3; i++) {
            happiness[i] = 0;
            neutral[i] = 0;
            surprise[i] = 0;
            anger[i] = 0;
            contempt[i] = 0;
            disgust[i] = 0;
            fear[i] = 0;
            sadness[i] = 0;
        }
    }

    //액티비티 바꾸기, 각 시기의 감정에 대한 점수를 배열로 넘겨줌
    private void changeActivity() {
        Intent intent = new Intent(VirtualInterview_faceActivitity.this, VirtualInterview_ResultActivity.class);
        intent.putExtra("happiness", happiness);
        intent.putExtra("neutral", neutral);
        intent.putExtra("surprise", surprise);
        intent.putExtra("anger", anger);
        intent.putExtra("contempt", contempt);
        intent.putExtra("disgust", disgust);
        intent.putExtra("fear", fear);
        intent.putExtra("sadness", sadness);

        startActivity(intent);
    }

    //캡쳐하기
    private void capture() {

        if (emotionvar >= EMOTION_VAR) {
            changeActivity();
        }

        //cameraView에 있는 capture() 메서드 실행
        capture(new Camera.PictureCallback() {

            // JPEG 사진파일 생성후 호출됨
            // byte[] data - 사진 데이타
            public void onPictureTaken(byte[] data, Camera camera) {
                if (requestPermissionStorage() == true) { //저장소 접근 권한이 확인 되면
                    try {
                        if(data!=null){
                            Matrix rotate = new Matrix();
                            rotate.postRotate(270); //비트맵 270도 회전해야 함... 아니면 가로로 보내져서 얼굴인식 절대안됨....

                            BitmapFactory.Options options = new BitmapFactory.Options(); //비트맵 크기 줄이는 옵션
                            options.inSampleSize = 4;

                            // 사진데이타를 비트맵 객체로 저장
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                            Bitmap bitmap2=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),rotate,false); //rotate속성 적용

                            //캡쳐후 바로 얼굴인식 API실행
                            detectAndFrame(bitmap2);
                        }
                        // 다시 미리보기 화면 보여줌
                        camera.startPreview();
                    } catch (Exception e) {
                        Log.e("SampleCapture", "Failed to insert image.", e);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "접근권한 거부", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // 사진을 찍을때 호출되는 함수
    private boolean capture(Camera.PictureCallback handler) {
        if (camera != null) {
            camera.takePicture(null, null, handler);
            return true;
        } else {
            return false;
        }
    }

    //카메라 접근 권한 확인
    private boolean requestPermissionCamera() {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VirtualInterview_faceActivitity.this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            } else {
                return true;
            }
        } else {  // version 6 이하일때
            return true;
        }
        return true;
    }

    //저장소 접근 권한 확인
    private boolean requestPermissionStorage() {

        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VirtualInterview_faceActivitity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                ActivityCompat.requestPermissions(VirtualInterview_faceActivitity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

            } else {
                return true;
            }
        } else {  // version 6 이하일때
            return true;
        }
        return true;
    }

    //얼굴인식 API
    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            Log.d("시도","시도함");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                              // returnFaceAttributes:
                                new FaceServiceClient.FaceAttributeType[] {
                                    FaceServiceClient.FaceAttributeType.Age,
                                    FaceServiceClient.FaceAttributeType.Gender,
                                    FaceServiceClient.FaceAttributeType.Emotion}
                            );

                            double happiness=result[0].faceAttributes.emotion.happiness;
                            double neutral=result[0].faceAttributes.emotion.neutral;
                            double surprise=result[0].faceAttributes.emotion.surprise;
                            double anger=result[0].faceAttributes.emotion.anger;
                            double contempt=result[0].faceAttributes.emotion.contempt;
                            double disgust=result[0].faceAttributes.emotion.disgust;
                            double fear=result[0].faceAttributes.emotion.fear;
                            double sadness=result[0].faceAttributes.emotion.sadness;
                            addEmotionScore(happiness,neutral,surprise, anger, contempt, disgust, fear, sadness);

                            Log.d("결과","anger"+anger+" happiness"+happiness+"neutral"+neutral);

                            if (result == null){
                                Log.d("얼굴 감지 실패","실패");
                                return null;
                            }
                            return result;
                        } catch (Exception e) {
                            Log.d("에러", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        if (result == null) return;

                    }
                };

        detectTask.execute(inputStream);

    }
    //감정 하나 감지할때마다 감정 점수 1씩 증가
    private void addEmotionScore(double dhappiness, double dneutral, double dsurprise, double danger, double dcontempt, double ddisgust, double dfear, double dsadness) {
        if(emotionvar<EMOTION_VAR) { //후반까지 측정을 하지 않았으면
            happiness[emotionvar]=(int)(dhappiness*1000)+happiness[emotionvar];
            neutral[emotionvar]=(int)(dneutral*1000)+neutral[emotionvar];
            surprise[emotionvar]=(int)(dsurprise*1000)+surprise[emotionvar];
            anger[emotionvar]=(int)(danger*1000)+anger[emotionvar];
            contempt[emotionvar]=(int)(dcontempt*1000)+contempt[emotionvar];
            disgust[emotionvar]=(int)(ddisgust*1000)+disgust[emotionvar];
            fear[emotionvar]=(int)(dfear*1000)+fear[emotionvar];
            sadness[emotionvar]=(int)(dsadness*1000)+sadness[emotionvar];
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }})
                .create().show();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
