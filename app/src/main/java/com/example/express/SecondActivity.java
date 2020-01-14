package com.example.express;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.io.Serializable;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class SecondActivity extends AppCompatActivity implements Serializable {
    private ArrayList<TMapPOIItem> search_research = new ArrayList<TMapPOIItem>();
    private ArrayList<TMapPOIItem> selected_result = new ArrayList<TMapPOIItem>();
    private ArrayList<Second_raw_info> changed_result = new ArrayList<Second_raw_info>();
    private ArrayList<String> phone_num = new ArrayList<String>();
    private ArrayList<String> time_array = new ArrayList<String>();
    private Second_Search_Adapter searchAdapter;
    private RecyclerView search_result;
    private RecyclerView.LayoutManager searchLayoutManager;
    private RecyclerView selected;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey("l7xxb102dfe902714215bf9e975fe02574c7");
//        linearLayoutTmap.addView(tMapView);

        Button btn = findViewById(R.id.search_btn);
        final EditText text = findViewById(R.id.text);
        final TMapData tmapdata = new TMapData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                selected_result.clear();
                tmapdata.findAllPOI(text.getText().toString(), new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(ArrayList<TMapPOIItem> arrayList) {
                        search_research.clear();
                        for (int i = 0; i < arrayList.size(); i++) {
                            search_research.add(arrayList.get(i));
                        }
                    }
                });
                Context context = SecondActivity.this;
                selected = findViewById(R.id.today);
                search_result = findViewById(R.id.search);
                searchLayoutManager = new LinearLayoutManager(getApplicationContext());
                search_result.setLayoutManager(searchLayoutManager);
                searchAdapter = new Second_Search_Adapter(search_research, selected_result, context, selected, phone_num, time_array);
                search_result.setAdapter(searchAdapter);

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
            }

        });

        button = (Button)findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changed_result.clear();
                ArrayList<String> num = searchAdapter.getNum();
                ArrayList<String> t = searchAdapter.getTime();
                for(int i = 0; i<selected_result.size(); i++){
                    TMapPOIItem item = selected_result.get(i);
                    Second_raw_info changed = new Second_raw_info(item, num.get(i), t.get(i));
                    changed_result.add(changed);
                }

                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("selected", changed_result);
                startActivity(intent);
            }
        });
    }
}



