package com.example.calendarlistviewsearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Calendar extends AppCompatActivity {

    public String readDay,readFlag = null;;
    public String str, str1 = null;
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView diaryTextView, textView2,textView3;
    public EditText contextEditText,flag;
    public RadioGroup radioGroup1;
    public RadioButton radioButton1, radioButton2;
/*    private CheckBox cb1;
    private CheckBox cb2;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        //Intent intent = getIntent();


        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        save_Btn = findViewById(R.id.save_Btn); //저장
        del_Btn = findViewById(R.id.del_Btn);   //삭제
        cha_Btn = findViewById(R.id.cha_Btn);  //수정
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        flag = findViewById(R.id.flag); //진료/일상
        contextEditText = findViewById(R.id.contextEditText);
        radioGroup1 = findViewById(R.id.radioGroup1);
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
/*        cb1 = (CheckBox)findViewById(R.id.checkBox1);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);
        tv=(TextView)findViewById(R.id.textView);
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);*/
        //final EditText flag = (EditText)findViewById(R.id.flag);
        RadioGroup radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton select = (RadioButton)findViewById(checkedId);
                flag.setText("["+select.getText() + "] 을(를) 기록하는 날입니다.");
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //일자 클릭
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                diaryTextView.setVisibility(View.VISIBLE); //몇날 며칠 알려주는 칸
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                flag.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("오늘은 %d년 %d월 %d일 !",year, month + 1,dayOfMonth));
                contextEditText.setText("");
                flag.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });
        /////////////////////////////////////////////////////////////////////

        //저장버튼 리스너
        save_Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                saveDiary(readDay);
                saveFlag(readFlag);
                //saveKind(readSort);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                str1 = flag.getText().toString();
                textView3.setText(str1);
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                flag.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
            }
        });
    }

    /*public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //체크박스를 클릭해서 상태가 바꾸었을 경우 호출되는 콜백 메서드
        String result = "";
        if(cb1.isChecked()) result += "진료를 기록하는 날입니다.";
        if(cb2.isChecked()) result += "일상을 기록하는 날입니다.";
        if(cb1.isChecked() && cb2.isChecked()) {
            Toast.makeText(getApplicationContext(),"한가지만 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        tv.setText(result + " ");
    }*/



    public void checkDay(int cYear, int cMonth, int cDay)
    {
        readDay ="" + cYear + "-" + (cMonth + 1) +""+"-"+cDay+".txt";
        FileInputStream fis;
        //txt파일 이름을 다르게 해야 다른 날짜 갔다 와도 두 edittext가 살아있음. 근데 이름이 같으면 다른 날짜 갔다 올때
        //flag 내용만 txt 파일에 저장돼서 두 edittext파일에 flag내용이 나타나게 됨
        readFlag ="" + cYear + "-" + (cMonth + 1) +""+"-"+cDay+"1.txt";
        FileInputStream fis1;

        try
        {
            fis = openFileInput(readDay);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();
            str=new String(fileData);

            fis1 = openFileInput(readFlag);
            byte[] fileData1 = new byte[fis1.available()];
            fis1.read(fileData1);
            fis1.close();
            str1=new String(fileData1);

            contextEditText.setVisibility(View.INVISIBLE);
            flag.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);
            textView3.setVisibility(View.VISIBLE);
            textView3.setText(str1);

            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            //수정버튼 리스너
            cha_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    flag.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.INVISIBLE);
                    flag.setText(str1);

                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    textView2.setText(contextEditText.getText());
                    textView3.setText(flag.getText());
                }
            });
            //삭제버튼 리스너
            del_Btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Calendar.this);
                    builder.setTitle(cYear + "-" + (cMonth + 1) +""+"-"+cDay)
                            .setMessage("일지를 삭제하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    textView2.setVisibility(View.INVISIBLE);
                                    contextEditText.setText("");
                                    contextEditText.setVisibility(View.VISIBLE);
                                    textView3.setVisibility(View.INVISIBLE);
                                    flag.setText("");
                                    flag.setVisibility(View.VISIBLE);

                                    save_Btn.setVisibility(View.VISIBLE);
                                    cha_Btn.setVisibility(View.INVISIBLE);
                                    del_Btn.setVisibility(View.INVISIBLE);
                                    removeDiary(readDay);
                                    removeFlag(readFlag);
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }
            });
            if(textView2.getText() == null){
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }
            if(textView3.getText() == null){
                textView3.setVisibility(View.INVISIBLE);
                flag.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                flag.setVisibility(View.VISIBLE);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //쓴 것을 삭제하기 위해
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //진료/일상을 삭제하기 위해
    @SuppressLint("WrongConstant")
    public void removeFlag(String readFlag)
    {
        FileOutputStream fos1;
        try
        {
            fos1 = openFileOutput(readFlag, MODE_NO_LOCALIZED_COLLATORS);
            String content1 = "";
            fos1.write((content1).getBytes());
            fos1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //쓴 것을 저장하기 위해
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay)
    {
        FileOutputStream fos;
        try
        {
            fos = openFileOutput(readDay, MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //진료/일상을 저장하기 위해
    @SuppressLint("WrongConstant")
    public void saveFlag(String readFlag)
    {
        FileOutputStream fos1;
        try
        {
            fos1 = openFileOutput(readFlag, MODE_NO_LOCALIZED_COLLATORS);
            String content1 = flag.getText().toString();
            fos1.write((content1).getBytes());
            fos1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
