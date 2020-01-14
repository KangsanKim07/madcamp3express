package com.example.express;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class ThirdActivity extends AppCompatActivity {

    private ArrayList<Second_raw_info> result = new ArrayList<Second_raw_info>();
    private TMapData tmapdata = new TMapData();
    private TMapPoint startpoint;
    private TMapPoint endpoint;
    private ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        result = (ArrayList<Second_raw_info>)intent.getExtras().getSerializable("selected");

        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        final TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xxb102dfe902714215bf9e975fe02574c7");
        linearLayoutTmap.addView(tMapView);

        tMapView.removeAllMarkerItem();
        //핀 찍기
        for (int i=0; i<result.size(); i++){
            Second_raw_info item = result.get(i);
            TMapPoint tpoint = new TMapPoint(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLon()));
            TMapMarkerItem tItem = new TMapMarkerItem();
            tItem.setTMapPoint(tpoint);
            tItem.setCanShowCallout(true);
            tItem.setCalloutTitle(item.getName());
            // 핀모양으로 된 마커를 사용할 경우 마커 중심을 하단 핀 끝으로 설정.
            tItem.setPosition((float)0.5,(float)1.0);         // 마커의 중심점을 하단, 중앙으로 설정
            tMapView.addMarkerItem(item.getName(), tItem);
            tMapView.setCenterPoint(Double.parseDouble(item.getLon()), Double.parseDouble(item.getLat()));

            if( i == 0 ){ startpoint = tpoint; }
            else if( i == result.size()-1 ){ endpoint = tpoint; }
            else {passList.add(tpoint);}
        }

        //경로 찾기
        tmapdata.findMultiPointPathData(startpoint, endpoint, passList, 0, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {
                tMapView.addTMapPath(tMapPolyLine);
            }
        });

        Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), FourthActivity.class);
                intent1.putExtra("result", result);
                intent1.putExtra("key", 0);
                startActivity(intent1);
            }
        });

    }
}
