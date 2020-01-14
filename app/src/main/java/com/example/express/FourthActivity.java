package com.example.express;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {
    private RecyclerView review;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Second_raw_info> result;
    private Fourth_Adapter adapter;
    private ArrayList<String> yesno = new ArrayList<String>();
    private ArrayList<String> take = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        int key = intent.getExtras().getInt("key");
        result = (ArrayList<Second_raw_info>)intent.getExtras().getSerializable("result");
        if(key == 0) {
            for (int i = 0; i < result.size(); i++) {
                yesno.add("");
                take.add("");
            }
        }
        else if(key == 1) {
            yesno = intent.getExtras().getStringArrayList("yesno");
            take = intent.getExtras().getStringArrayList("t");
            int position = intent.getExtras().getInt("position");

            String yesno_result = intent.getExtras().getString("yesno");
            String take1 = intent.getExtras().getString("take");
            String pure_YN = intent.getExtras().getString("pureYN");
            if(pure_YN.equals("Y")){yesno.remove(position); yesno.add(position, "Y");}
            else if (pure_YN.equals("N"))
            {
                yesno.remove(position);
                yesno.add(position, "N");
                take.remove(position);
                take.add(position, take1);
            }
        }
        Context context = FourthActivity.this;
        review = findViewById(R.id.review);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        review.setLayoutManager(layoutManager);
        adapter= new Fourth_Adapter(result, context, yesno, take);
        review.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                result.remove(viewHolder.getLayoutPosition());
                adapter.notifyItemRemoved(viewHolder.getLayoutPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(review);

    }
}
