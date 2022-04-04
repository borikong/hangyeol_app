package com.naver.naverspeech.client;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naver.naverspeech.client.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

public class pageFragment2 extends Fragment {
    private Handler handler2;
    private Handler handler3;
    P_Thread p_thread;

    TextView question;
    TextView type;
    String mFilePath =  null;
    int count = 0;

    private DatabaseReference databaseReference2;
    SQLiteHelper1 dbHelper1;
    static boolean calledAlready = false;

    private String question1, question2, question3, question4, question5;

    private String userName = "SONG";
    private int score5;
    CircularProgressBar circularProgressBar1;
    ProgressBar bar;
    private static final String TAG = pageFragment2.class.getSimpleName();
    private static final String CLIENT_ID = "wg7auzkd2t";
    // 1. "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    // 2. build.gradle (Module:app)에서 패키지명을 실제 개발자센터 애플리케이션 설정의 '안드로이드 앱 패키지 이름'으로 바꿔 주세요

    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;

    String mResult;

    private Chronometer timer;

    private AudioWriterPCM writer;

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                Log.e("SONGTest","중간:"+mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                for(String result : results) {
                    strBuf.append(result);
                    strBuf.append("\n");
                }
                Log.e("SONGTest","이게 진짜 결과냐:"+mResult);
                mResult = strBuf.toString();
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                Log.e("SONGTest","에러:"+mResult);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                break;
        }
    }
    public static Fragment getInstance(int position) {
        pageFragment2 key = new pageFragment2();
        Bundle args = new Bundle();
        args.putInt("position", position);
        key.setArguments(args);

        return key;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!calledAlready)
        {
            calledAlready = true;
        }
        Log.e("SONGTest","이게 언제 실행되니");
        getQuestion();

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(getActivity(), handler, CLIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        handler2 = new Handler();
        dbHelper1 = new SQLiteHelper1(getActivity(), "Score.db", null, 1);

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.page2,container,false);


        question = (TextView)linearLayout.findViewById(R.id.question);
        type = (TextView)linearLayout.findViewById(R.id.type);
        TextView stage = (TextView)linearLayout.findViewById(R.id.stage);
        bar = (ProgressBar)linearLayout.findViewById(R.id.stageLevel);

        switch (dbHelper1.getNum(userName)){
            case 1:
                type.setText("어휘력 영역");
                break;
            case 2:
                type.setText("발음 영역");
                break;
            case 3:
                type.setText("계속성 영역");
                break;
            case 4:
                type.setText("속도 영역");
                break;
            case 5:
                type.setText("논리성 영역");
                break;
        }

        Log.e("SONGTest","posion : " + getArguments().getInt("position"));
        switch (getArguments().getInt("position")) {
            case 0:

                stage.setText("1/5");
                bar.setProgress(20);
                break;
            case 1:
                stage.setText("2/5");
                bar.setProgress(40);
                break;
            case 2:
                stage.setText("3/5");
                bar.setProgress(60);
                break;
            case 3:
                stage.setText("4/5");
                bar.setProgress(80);
                break;
            case 4:
                stage.setText("5/5");
                bar.setProgress(1000);
                break;
            case 5:
                //싯팔 이거 어떡해
                break;
        }

        circularProgressBar1 = (CircularProgressBar)linearLayout.findViewById(R.id.circularProgress);
        circularProgressBar1.setProgressColor(Color.RED);
        circularProgressBar1.setProgressWidth(30);
        //circularProgressBar1.setProgress(60);
        circularProgressBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();

                handler3 = new Handler();
                p_thread = new P_Thread();
                p_thread.start();
                p_thread.stop = false;
                p_thread.work =true;

                Log.e("SONGTest","스레드 상태"+p_thread.getState().toString());

                Log.e("SONGTest","녹음이 잘되니?");

                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    Log.e("SONGTest","시작");
                    naverRecognizer.recognize();
                } else {
                    Log.e("SONGTest", "stop and wait Final Result");
                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });

        timer = (Chronometer)linearLayout.findViewById(R.id.timer);

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime()- chronometer.getBase();
                int realTime = Integer.parseInt(time/1000+"");
                circularProgressBar1.setProgress(60-realTime);
                Log.e("SONGTest","현재시각은 : "+realTime);
                Log.e("SONGTest","확인삼아 출력한다.:"+mResult);
                if(realTime>=60) {

                    chronometer.stop();

                    naverRecognizer.getSpeechRecognizer().stop();

                    switch (getArguments().getInt("position")) {
                        case 0:
                            dbHelper1.update1(userName,9);
                            Log.e("SONGTest","확인 중0 : " );
                            count =0;
                            break;
                        case 1:
                            //알고리즘 넣기
                            dbHelper1.update2(userName,8);
                            Log.e("SONGTest","확인 중1 : " );
                            count =1;
                            break;
                        case 2:
                            //알고리즘 넣기
                            dbHelper1.update3(userName,7);
                            Log.e("SONGTest","확인 중2 : " );
                            count =2;
                            break;
                        case 3:
                            //알고리즘 넣기
                            dbHelper1.update4(userName,6);
                            Log.e("SONGTest","확인 중3 : " );
                            count =3;
                            break;
                        case 4:
                            //알고리즘 넣기
                            dbHelper1.update5(userName,5);
                            count =4;
                            startActivity(new Intent(getActivity(), ResultActivity2.class));
                            //논리성 데이터는 딜레이시간상 다이얼로그에서 쌓기로 넘김
                            break;
                    }
                }
            }
        });

        return linearLayout;
    }


    @Override
    public void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<pageFragment2> mActivity;

        RecognitionHandler(pageFragment2 fragment) {
            mActivity = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            pageFragment2 activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    public void onInfo(MediaRecorder mr, int what, int extra) {
        switch( what ) {
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED :
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED :
                break;
        }
    }

    public void getQuestion(){
        //feeback 이라는 자식의 데이터 몽땅 가져온다.
        databaseReference2 = FirebaseDatabase.getInstance().getReference("trainingQuestion");
        databaseReference2.getDatabase();

        // databaseReference에 자식(데이터)가 추가되면 발행하는 이벤트이다.
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               //데이터에 추가된 FeedbackItem을 가져온다.
                QuestionItem2 questionItem = dataSnapshot.getValue(QuestionItem2.class);
                question1 = questionItem.getQuestion1();

                question2 = questionItem.getQuestion2();
                question3 = questionItem.getQuestion3();
                question4 = questionItem.getQuestion4();
                question5 = questionItem.getQuestion5();

                switch (getArguments().getInt("position")) {
                    case 0:
                        question.setText(question1);
                        break;
                    case 1:
                        question.setText(question2);
                        break;
                    case 2:
                        question.setText(question3);
                        break;
                    case 3:
                        question.setText(question4);
                        break;
                    case 4:
                        question.setText(question5);
                        break;
                    case 5:
                        //싯팔 이거 어떡해
                        break;
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
    private class P_Thread extends Thread{
        public boolean stop = false;
        public boolean work = true;
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                if (work) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the progress bar
                    handler3.post(new Runnable() {
                        public void run() {
                        }
                    });
                } else {
                    if (Thread.currentThread().getState().equals(State.RUNNABLE)) {
                        try {
                            Thread.sleep(800);
                        } catch (Exception e) {
                        }
                    }
                    if(stop){
                        Log.e("hello", " " + Thread.currentThread().getState());
                        handler3.post(new Runnable() {
                            public void run() {
                            }
                        });
                        break;
                    }
                }
            }
            Log.e("SONGTest","끝남");
        }
    }

}
