package com.example.express;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapPOIItem;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class Second_Selected_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {

    private ArrayList<TMapPOIItem> selected_result;
    private ArrayList<String> phone_num;
    private ArrayList<String> time_array;

    class Second_Selected_ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView num;
        private TextView time;
        public  Second_Selected_ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textView5);
            num = itemView.findViewById(R.id.num);
            time = itemView.findViewById(R.id.time);
        }
    }

    public Second_Selected_Adapter(ArrayList<TMapPOIItem> selected_result, ArrayList<String> phone_num, ArrayList<String> time_array){
        this.selected_result = selected_result;
        this.phone_num = phone_num;
        this.time_array = time_array;
    }

    public void setAdapter(ArrayList<TMapPOIItem> selected_result, ArrayList<String> phone_num){
        this.selected_result = selected_result;
        this.phone_num = phone_num;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_search, parent, false);
        return new Second_Selected_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("SS", "---------------------------------------" + position);
        Second_Selected_Adapter.Second_Selected_ViewHolder viewHolder = (Second_Selected_Adapter.Second_Selected_ViewHolder) holder;
        viewHolder.textView.setText(selected_result.get(position).getPOIName());
        viewHolder.num.setText(phone_num.get(position));
        viewHolder.time.setText(time_array.get(position) + " 시");
    }

    @Override
    public int getItemCount() {
        return selected_result.size();
    }
}
