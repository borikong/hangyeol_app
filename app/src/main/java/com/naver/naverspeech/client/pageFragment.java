package com.naver.naverspeech.client;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.naver.naverspeech.client.seohyun.Speed;
import com.naver.naverspeech.client.soojeong.Vocabulary;
import com.naver.naverspeech.client.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class pageFragment extends Fragment {
    private Handler handler2;
    private Handler handler3;
    P_Thread p_thread;

    private int vocabulary;
    private int pronunciation;
    private int continuity;
    private int speed;
    private int logicScore;

    TextView question;
    TextView type;
    String mFilePath =  null;
    int count = 0;

    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    SQLiteHelper1 dbHelper1;
    static boolean calledAlready = false;

    private String vocabularyQuestion, pronunciationQuestion, continuityQuestion, speedQuestion, logicQuestion;

    private String userName = "SONG";
    CircularProgressBar circularProgressBar1;
    ProgressBar bar;
    private static final String TAG = pageFragment.class.getSimpleName();
    private static final String CLIENT_ID = "wg7auzkd2t";
    // 1. "??? ??????????????????"?????? Client ID??? ???????????? ????????? ???????????????.
    // 2. build.gradle (Module:app)?????? ??????????????? ?????? ??????????????? ?????????????????? ????????? '??????????????? ??? ????????? ??????'?????? ?????? ?????????

    private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;

    private static String mResult;

    String getTime;

    private Chronometer timer;

    private AudioWriterPCM writer;

    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest" );
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                Log.e("SONGTest","??????:"+mResult);
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
                Log.e("SONGTest","?????? ?????? ?????????:"+mResult);
                mResult = strBuf.toString();
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                Log.e("SONGTest","??????:"+mResult);
                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                break;
        }
    }
    public static Fragment getInstance(int position) {
        pageFragment key = new pageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        key.setArguments(args);

        return key;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("SONGTest","?????? ?????? ????????????");
        getQuestion();

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(getActivity(), handler, CLIENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        handler2 = new Handler();
        dbHelper1 = new SQLiteHelper1(getActivity(), "Score.db", null, 1);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("dailyTestScore");

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.page,container,false);


        question = (TextView)linearLayout.findViewById(R.id.question);
        type = (TextView)linearLayout.findViewById(R.id.type);
        TextView stage = (TextView)linearLayout.findViewById(R.id.stage);
        bar = (ProgressBar)linearLayout.findViewById(R.id.stageLevel);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        getTime = sdf.format(date);



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
                //?????? ?????? ?????????
                break;
        }

        circularProgressBar1 = (CircularProgressBar)linearLayout.findViewById(R.id.circularProgress);
        circularProgressBar1.setProgressColor(Color.RED);
        circularProgressBar1.setProgressWidth(30);

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

                Log.e("SONGTest","????????? ??????"+p_thread.getState().toString());

                Log.e("SONGTest","????????? ??????????");

                if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mResult = "";
                    Log.e("SONGTest","??????");
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
                Log.e("SONGTest","??????????????? : "+realTime);
                Log.e("SONGTest","???????????? ????????????.:"+mResult);
                if(realTime>=60) {

                    chronometer.stop();

                    naverRecognizer.getSpeechRecognizer().stop();

                    switch (getArguments().getInt("position")) {
                        case 0:
                            Vocabulary voca = new Vocabulary(mResult, vocabularyQuestion);
                            vocabulary = voca.getScore();
                            dbHelper1.update2(userName,vocabulary);
                            Log.e("SONGTest","?????? ???0 : " );
                            count =1;
                            break;
                        case 1:
                            //???????????? ??????
                            Pronunciation pronunciationInstance = new Pronunciation(pronunciationQuestion, mResult);
                            pronunciation = pronunciationInstance.getScore();
                            dbHelper1.update2(userName,pronunciation);
                            Log.e("SONGTest","?????? ???1 : " );
                            count =2;
                            break;
                        case 2:
                            //???????????? ??????
                            Continuity continuityInstance=new Continuity(mResult);
                            continuity=continuityInstance.getScore();
                            dbHelper1.update3(userName,continuity);
                            Log.e("SONGTest","?????? ???2 : " );
                            count =3;
                            break;
                        case 3:
                            //???????????? ??????
                            Speed speedInstance = new Speed(mResult);
                            speed=speedInstance.getScore();
                            System.out.println(speed+"?????? ??? ?????????.");
                            dbHelper1.update4(userName,speed);
                            Log.e("SONGTest","?????? ???3 : " );
                            count =4;
                            break;
                        case 4:
                            //???????????? ??????
                            showDialogDemo();
                            count =5;
                            //????????? ???????????? ?????????????????? ????????????????????? ????????? ??????
                            break;
                    }
                    try {
                        mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getTime;
                        String nowFile = mFilePath  + count + ".mp4";
                        encodeSingleFile(nowFile);
                    } catch (Exception e) {
                        Log.e("SONGTest","Exception while creating tmp file1");
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

        //btnStart.setText(R.string.str_start);
        //btnStart.setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<pageFragment> mActivity;

        RecognitionHandler(pageFragment activity) {
            mActivity = new WeakReference<pageFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            pageFragment activity = mActivity.get();
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
    private void showDialogDemo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);


        final View Viewlayout = inflater.inflate(R.layout.dialog,null);

        final TextView item = (TextView)Viewlayout.findViewById(R.id.txtItem);

        builder.setTitle("????????? ????????? ???????????????.");
        builder.setView(Viewlayout);

        //  seekBar1
        final SeekBar seek = (SeekBar) Viewlayout.findViewById(R.id.seekBar);
        seek.setMax(10);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                item.setText(progress+"");
                Log.e("SONGTest","????????? : " + progress);
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub

            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }
        });
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logicScore = seek.getProgress();
                Log.e("SONGTest","??????????????? : " + logicScore);

                Log.e("SONGTest","?????? ?????????????????????" );
                dbHelper1.update5(userName,logicScore);
                Log.e("SONGTest","?????? ?????????????????????2" );
                setScore(userName, dbHelper1.getScore(userName));

                startActivity(new Intent(getActivity(), ResultActivity.class));
            }
        });
        builder.create();
        builder.show();
    }


    public void setScore(String userName, int score[]){
        Log.e("SONGTest","?????? ??? : " +score[0]+ score[1]+ score[2]+ score[3]+ score[4]);

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA );
        Date currentTime = new Date();
        String date = mSimpleDateFormat.format(currentTime);

        //???????????? ????????? item class??? ?????????.
        ScoreItem scoreItem = new ScoreItem(userName, 1, score[0], score[1], score[2], score[3], score[4]);

        //date ?????? ?????? ???????????? ??????(????????? ???????????? ??????)?????? ????????? ??? item??? ????????????.
        databaseReference1.child(date).setValue(scoreItem);

        Log.e("SONGTest","?????? ??? : " + scoreItem);
    }

    public void getQuestion(){
        //feeback ????????? ????????? ????????? ?????? ????????????.
        databaseReference2 = FirebaseDatabase.getInstance().getReference("dailyTestQuestion");
        databaseReference2.getDatabase();

        // databaseReference??? ??????(?????????)??? ???????????? ???????????? ???????????????.
        databaseReference2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //???????????? ????????? FeedbackItem??? ????????????.
                QuestionItem questionItem = dataSnapshot.getValue(QuestionItem.class);

                vocabularyQuestion = questionItem.getVocabulary();

                pronunciationQuestion = questionItem.getPronunciation();
                continuityQuestion = questionItem.getContinuity();
                speedQuestion = questionItem.getSpeed();
                logicQuestion = questionItem.getLogic();
                Log.e("SONGTest","?????? ????????? : "+ vocabularyQuestion);

                switch (getArguments().getInt("position")) {
                    case 0:
                        question.setText(vocabularyQuestion+"??? ???????????? 1??? ?????? ????????????.");
                        type.setText("????????? ??????");
                        Log.e("SONGTest", "????????? ?????? ??????0 : " + vocabularyQuestion);
                        break;
                    case 1:
                        question.setText(pronunciationQuestion);
                        type.setText("?????? ??????");
                        Log.e("SONGTest", "????????? ?????? ??????1 : " + pronunciationQuestion);
                        break;
                    case 2:
                        question.setText(continuityQuestion);
                        type.setText("????????? ??????");
                        Log.e("SONGTest", "????????? ?????? ??????2 : " + continuityQuestion);
                        break;
                    case 3:
                        question.setText(speedQuestion);
                        type.setText("?????? ??????");
                        Log.e("SONGTest", "????????? ?????? ??????3 : " + speedQuestion);
                        break;
                    case 4:
                        question.setText(logicQuestion);
                        type.setText("?????? ??????");
                        Log.e("SONGTest", "????????? ?????? ??????4 : " + logicQuestion);
                        break;
                    case 5:
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
            Log.e("SONGTest","??????");
        }
    }

    private void encodeSingleFile(final String outputPath) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(encodeTask(1, outputPath));
    }

    private Runnable encodeTask(final int numFiles, final String outputPath) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    final PCMEncoder pcmEncoder = new PCMEncoder(48000, 16000, 1);
                    pcmEncoder.setOutputPath(outputPath);
                    pcmEncoder.prepare();
                    File directory = new File("storage/emulated/0/NaverSpeechTest/Test.pcm");
                    for (int i = 0; i < numFiles; i++) {
                        Log.d(TAG, "Encoding: " + i);
                        //InputStream inputStream = getAssets().open("test.wav");
                        InputStream inputStream = new FileInputStream(directory);
                        inputStream.skip(44);
                        pcmEncoder.encode(inputStream, 16000);
                    }
                    pcmEncoder.stop();
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Encoded file to: " + outputPath, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "Cannot create FileInputStream", e);
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public static void delay()
    {
        try { Thread.sleep(2000); } catch (Exception e) { }
        Log.e("SONGTest","????????????");
    }
}

