package com.example.spinner_region;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RegionActivity extends AppCompatActivity {
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
    private Spinner spinner_region;
    private ArrayList<Region> regionArrayList;//파싱하면서 Region클래스로 이루어진 List를 만든다.
    private ArrayList<String> regions; //지역 이름만 뽑아 String array를 만든다.
    private static final String TAG = "showParse";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "start");
        //xml을 파싱해서 regionArrayList에 추가..
        try {
            //루프 돌면서 xml 파싱. 파싱해서 Region객체를 만들어 regionArrayList에 추가한다.
            set_parser();
            parsing_loop();
            Log.d(TAG, "parsing start");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Spinner 목록에 보여줄 지역 이름을 가진 array를 만든다.
        for (int i = 0; i < regionArrayList.size(); i++) {
            regions.add(regionArrayList.get(i).getName());
        }

        spinner_region = (Spinner) findViewById(R.id.spinner_region); //스피너 선언

        //<String> adapter에 <String> array를 넣어줌.
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions);

        //드롭다운 모양 설정
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //스피너를 어답터에 연결
        spinner_region.setAdapter(spinnerArrayAdapter);

    }

    public void set_parser() throws IOException, XmlPullParserException {
        Log.d(TAG, "parsing start");
        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        String myKey="bywYnmxO22GvVcjVqX51LynXlyzrFTtMzTRM88W6rKGfO8VQtw6HycfhvB9ci6s9nH1fPDCOWQnabI9Htz7WDg==";
        String path = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/areaCode?" +
                "serviceKey="+myKey+
                "&numOfRows=10&pageSize=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest";

        URL url = new URL(path);
        InputStream is = url.openStream();
        parser.setInput(new InputStreamReader(is, "UTF-8"));


    }

    public void parsing_loop() throws XmlPullParserException, IOException {
        Log.d(TAG, "parsing start");
        final int STEP_NONE = 0;
        final int STEP_CODE = 1;
        final int STEP_NAME = 2;
        final int STEP_RNUM = 3;

        int step = STEP_NONE;
        String code = null;
        String name = null;
        String rnum = null;

        //   int eventType = parser.getEventType();
//xml 형식:
//<item>
//<code>1</code>
//<name>서울</name>
//<rnum>1</rnum>
//</item>
        // XML 파일의 시작.
        int eventType = parser.getEventType();
        Log.d(TAG, "parsing start");
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                parser.next();
                eventType = parser.getEventType();
             }
            if (eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();

                if (startTag.equals("code")) {
                    step = STEP_CODE;

                } else if (startTag.equals("name")) {
                    step = STEP_NAME;
                } else if (startTag.equals("rnum")) {
                  step = STEP_RNUM;
                } else {
                    step = STEP_NONE;
                }
            } else if (eventType == XmlPullParser.TEXT && step != STEP_NONE) {
                String text = parser.getText();
                if (step == STEP_CODE) {
                    code = text;
                } else if (step == STEP_NAME) {
                    name = text;
                } else if (step == STEP_RNUM) {
                    rnum = text;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if (endTag.equals("item")) {

                    regionArrayList.add(
                            new Region(code, name, rnum));
                    step = STEP_NONE;
                }
            }
            eventType = parser.next();
        }
    }

    public void getSelectorRegion(View v) {
        Region region = (Region) spinner_region.getSelectedItem();
    }

}