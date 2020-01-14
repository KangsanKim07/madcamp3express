package com.example.express;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapPOIItem;

import java.io.Serializable;
import java.util.ArrayList;

public class Fourth_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private ArrayList<Second_raw_info> result;
    private Context context;
    private ArrayList<String> yesno;
    private ArrayList<String> take;

    class Fourth_ViewHolder extends RecyclerView.ViewHolder{
        private TextView location;
        private TextView phone_num;
        private TextView yn;
        private TextView take;
        private Button call;
        private TextView time;

        public  Fourth_ViewHolder(@NonNull View itemView){
            super(itemView);
            location = itemView.findViewById(R.id.location);
            phone_num = itemView.findViewById(R.id.phone_num);
            yn = itemView.findViewById(R.id.yn);
            take = itemView.findViewById(R.id.take);
            call = itemView.findViewById(R.id.button3);
            time = itemView.findViewById(R.id.time);
        }
    }

    public Fourth_Adapter(ArrayList<Second_raw_info> result, Context context, ArrayList<String> yesno, ArrayList<String> take){
        this.result = result;
        this.context = context;
        this.yesno = yesno;
        this.take = take;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fourth_item, parent, false);
        return new Fourth_Adapter.Fourth_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Fourth_Adapter.Fourth_ViewHolder viewHolder = (Fourth_Adapter.Fourth_ViewHolder) holder;
        viewHolder.location.setText(result.get(position).getName());
        viewHolder.phone_num.setText(result.get(position).getPhone_num());
        viewHolder.yn.setText(yesno.get(position));
        viewHolder.take.setText(take.get(position));
        viewHolder.time.setText(result.get(position).getTime());

        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CallActivity.class);
                intent.putExtra("location", result.get(position).getName());
                intent.putExtra("num", result.get(position).getPhone_num());
                intent.putExtra("data", result);
                intent.putExtra("position", position);
                intent.putExtra("yesno", yesno);
                intent.putExtra("take", take);
                intent.putExtra("time", result.get(position).getTime());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }
}
