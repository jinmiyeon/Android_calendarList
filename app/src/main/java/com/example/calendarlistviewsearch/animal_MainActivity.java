package com.example.calendarlistviewsearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class animal_MainActivity extends AppCompatActivity {

    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_main);

        animal_ListViewAdapter adapter;

        //Adapter 생성
        adapter = new animal_ListViewAdapter();

        //리스트뷰 참조 및 Adatper달기
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dog1), "강아지",
                   "푸들,말티즈,치와와,포메라니안,비숑프리제,\r\n" +
                        "닥스훈트,요크셔테리어,시추,골든리트리버,\r\n" +
                        "도베르만핀셔,로트바일러,보더콜리,저먼셰퍼드,\r\n" +
                        "시베리안허스키,차우차우,보스턴테리어");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.cat1), "고양이",
                   "코리안숏헤어,페르시안,메인쿤,벵골,스핑크스,\r\n" +
                        "브리티시숏헤어,샴,래그돌,먼치킨,스코티시폴드,\r\n" +
                        "노르웨이숲,터키시앙고라,엑조틱숏헤어,\r\n" +
                           "러시안블루,아비시니안");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.hamster1), "햄스터",
                   "시리아,중가리아,로보로브스키,중국,캠벨난쟁이,\r\n" +
                        "유럽,간쑤비단털(쥐속),비단털등줄,회색난쟁이,\r\n" +
                           "황금비단털(쥐속),비단,페디그리");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.hedghog1), "고슴도치",
                "긴귀고슴도치속(인도긴귀),남방흰가슴,유럽,\r\n" +
                        "메세키누스속(다우리아),북방흰가슴,\r\n" +
                        "파라이키누스속(사막,브란트,인도,마드라스)");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.rabbit1), "토끼",
                "단색(네덜란드드워프,그레이샌드,올블랙,황금얼룩,옐로우브라운,올화이트)\r\n" +
                        "더치(펜더무늬),  드워프오토(마스카라),\r\n" +
                        "친칠라,샴세이블,라이언헤드,렉스,할리퀸");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bird1), "새",
                "코뉴어,모란,검은머리카이큐,뉴기니아,\r\n" +
                        "오색(붉은목,스완슨,노란목),썬코뉴어,청금강,\r\n" +
                        "회색,한스마카우,장미(루비노),세네갈,퀘이커,\r\n" +
                        "오색청해,카카리키,사자나미,왕관,청모자");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.bg_pet), "거북이",
                "붉은귀,옐로우베리,레드벨리,스팟터드터,\r\n" +
                        "리버쿠터,커먼머스크,레이저백머스크,\r\n" +
                        "세줄머드,레드칙머드,화이트립머드,레이저백\r\n" +
                        "페닌슐라쿠터,이스턴머드,미시시피머드,");

        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        animal_TabHost.class
                );
                startActivity(intent);
            }
        });


        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                if(filterText.length() > 0) {
                    listView.setFilterText(filterText);
                }else{
                    listView.clearTextFilter();
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });
    }
}