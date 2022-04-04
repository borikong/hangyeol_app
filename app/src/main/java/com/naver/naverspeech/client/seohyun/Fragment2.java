package com.naver.naverspeech.client.seohyun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.naverspeech.client.R;
import com.xw.repo.BubbleSeekBar;

public class Fragment2 extends Fragment {

    private Activity mActivity ;
    private Button btn;
    private TextView tv1, tv2,tv3, tv4, tv5;
    private int vocaNum, pronunNum, speedNum, contiNum, logicNum;
    private CheckBox vocaCheck, pronunCheck, speedCheck, continCheck, logicCheck;
    private Boolean voca=false, pronun=false, speed=false, contin=false, logic=false;
    private BubbleSeekBar bubbleSeekBar1, bubbleSeekBar2, bubbleSeekBar3, bubbleSeekBar4, bubbleSeekBar5;
    public static Fragment2 newInstance() {
        return new Fragment2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2, container, false);
        btn = (Button) view.findViewById(R.id.btn1); /*점수 조회 버튼*/

        /* 체크박스 */
        vocaCheck=(CheckBox) view.findViewById(R.id.check1);
        pronunCheck=(CheckBox) view.findViewById(R.id.check2);
        continCheck=(CheckBox) view.findViewById(R.id.check3);
        speedCheck=(CheckBox) view.findViewById(R.id.check4);
        logicCheck=(CheckBox) view.findViewById(R.id.check5);

        /* 텍스트*/
        tv1=(TextView) view.findViewById(R.id.tv1);
        tv2=(TextView) view.findViewById(R.id.tv2);
        tv3=(TextView) view.findViewById(R.id.tv3);
        tv4=(TextView) view.findViewById(R.id.tv4);
        tv5=(TextView) view.findViewById(R.id.tv5);

        /* 시크바 */
        bubbleSeekBar1 = view.findViewById(R.id.seekbar_1);
        bubbleSeekBar2 = view.findViewById(R.id.seekbar_2);
        bubbleSeekBar3 = view.findViewById(R.id.seekbar_3);
        bubbleSeekBar4 = view.findViewById(R.id.seekbar_4);
        bubbleSeekBar5 = view.findViewById(R.id.seekbar_5);

        bubbleSeekBar1.getConfigBuilder()
                .min(0)
                .max(10)
                .progress(5)
                .sectionCount(10)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary2))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        bubbleSeekBar2.getConfigBuilder()
                .min(0)
                .max(10)
                .progress(5)
                .sectionCount(10)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary2))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        bubbleSeekBar3.getConfigBuilder()
                .min(0)
                .max(10)
                .progress(5)
                .sectionCount(10)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary2))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        bubbleSeekBar4.getConfigBuilder()
                .min(0)
                .max(10)
                .progress(5)
                .sectionCount(10)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary2))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();

        bubbleSeekBar5.getConfigBuilder()
                .min(0)
                .max(10)
                .progress(5)
                .sectionCount(10)
                .trackColor(ContextCompat.getColor(mActivity, R.color.color_gray))
                .secondTrackColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .thumbColor(ContextCompat.getColor(mActivity, R.color.color_blue))
                .showSectionText()
                .sectionTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary2))
                .sectionTextSize(18)
                .showThumbText()
                .thumbTextColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .thumbTextSize(18)
                .bubbleColor(ContextCompat.getColor(mActivity, R.color.color_red))
                .bubbleTextSize(18)
                .showSectionMark()
                .seekStepSection()
                .touchToSeek()
                .sectionTextPosition(BubbleSeekBar.TextPosition.BELOW_SECTION_MARK)
                .build();


        /*어휘력 체크*/
        vocaCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    voca=true;
                    tv1.setTextColor(getResources().getColor(R.color.color_red));
                }
                else{
                    voca=false;
                    tv1.setTextColor(getResources().getColor(R.color.color_gray));
                }
            }
        });

         /*발음 체크*/
        pronunCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pronun=true;
                    tv2.setTextColor(getResources().getColor(R.color.color_red));
                    Toast.makeText(getContext(), pronun+"의 값", Toast.LENGTH_LONG).show();
                }
                else{
                    pronun=false;
                    tv2.setTextColor(getResources().getColor(R.color.color_gray));
                }
            }
        });

         /*계속성 체크*/
        continCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    contin=true;
                    tv3.setTextColor(getResources().getColor(R.color.color_red));
                    Toast.makeText(getContext(), contin+"의 값", Toast.LENGTH_LONG).show();
                }
                else{
                    contin=false;
                    tv3.setTextColor(getResources().getColor(R.color.color_gray));
                }
            }
        });

         /*속도 체크*/
        speedCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    speed=true;
                    tv4.setTextColor(getResources().getColor(R.color.color_red));
                    Toast.makeText(getContext(), speed+"의 값", Toast.LENGTH_LONG).show();
                }
                else{
                    speed=false;
                    tv4.setTextColor(getResources().getColor(R.color.color_gray));
                }
            }
        });

        /*논리력 체크*/
        logicCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    logic=true;
                    tv5.setTextColor(getResources().getColor(R.color.color_red));
                }
                else{
                    logic=false;
                    tv5.setTextColor(getResources().getColor(R.color.color_gray));
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), Record_score.class);

                /*체크박스에 표시된 점수만 데이터베이스에 조회하기 */

                if(voca==true){
                    intent.putExtra("voca", Math.round(bubbleSeekBar1.getConfigBuilder().getProgress())+""); //어휘력 점수
                    System.out.println("voca됨");
                }
                if(pronun==true){
                    intent.putExtra("pronounciation", Math.round(bubbleSeekBar2.getConfigBuilder().getProgress())+""); //발음 점수
                    System.out.println("pronun됨");
                }
                if(contin==true){
                    intent.putExtra("continu", Math.round(bubbleSeekBar3.getConfigBuilder().getProgress())+""); //계속성 점수
                    System.out.println("continu됨");
                }
                if(speed==true){
                    intent.putExtra("speed", Math.round(bubbleSeekBar4.getConfigBuilder().getProgress())+""); //속도 점수
                    System.out.println("speed됨");
                }
                if(logic==true){
                    intent.putExtra("logic", Math.round(bubbleSeekBar5.getConfigBuilder().getProgress())+""); //논리성 점수
                    System.out.println("logic됨");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getContext().startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = (Activity) context;
    }
}
