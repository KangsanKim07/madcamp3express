package com.example.express;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skt.Tmap.TMapPOIItem;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Second_Search_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private ArrayList<TMapPOIItem> search_result;
    private ArrayList<TMapPOIItem> selected_result;
    private ArrayList<String> phone_num;
    private ArrayList<String> time_array;
    private Context context;
    private Second_Selected_Adapter selectedAdapter;
    private RecyclerView selected;
    private RecyclerView.LayoutManager selectedLayoutManager;


    class Second_Search_ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public  Second_Search_ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textView5);
        }
    }

    public Second_Search_Adapter(ArrayList<TMapPOIItem> search_result, ArrayList<TMapPOIItem> selected_result, Context context, RecyclerView selected
    ,ArrayList<String> phone_num, ArrayList<String> time_array){
        this.search_result = search_result;
        this.selected_result = selected_result;
        this.context = context;
        this.selected = selected;
        this.phone_num = phone_num;
        this.time_array = time_array;
    }

    public void setAdapter(ArrayList<TMapPOIItem> search_result){
        this.search_result = search_result;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_search, parent, false);
        return new Second_Search_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Second_Search_ViewHolder viewHolder = (Second_Search_ViewHolder) holder;
        viewHolder.textView.setText(search_result.get(position).getPOIName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(search_result.get(position).getPOIName(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return search_result.size();
    }

    void show(String name, final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.second_info, null);
        builder.setView(view);
        final Button submit = (Button) view.findViewById(R.id.buttonSubmit);
        final EditText num1 = (EditText) view.findViewById(R.id.edittextEmailAddress);
        final EditText time1 = (EditText) view.findViewById(R.id.edittextPassword);

        final AlertDialog dialog = builder.create();
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strnum = num1.getText().toString();
                String strtime = time1.getText().toString();
                phone_num.add(strnum);
                time_array.add(strtime);

                selected_result.add(search_result.get(position));
                selectedLayoutManager = new LinearLayoutManager(context);
                selected.setLayoutManager(selectedLayoutManager);
                selectedAdapter = new Second_Selected_Adapter(selected_result, phone_num, time_array);
                selected.setAdapter(selectedAdapter);

                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        selected_result.remove(viewHolder.getLayoutPosition());
                        selectedAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
                    }
                };
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(selected);


                dialog.dismiss();
            }
        });

        dialog.show();

    }




//        final EditText edittext = new EditText(context);
//        final EditText edittext2 = new EditText(context);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("전화번호를 입력하세요");
//        builder.setMessage(name);
//        builder.setView(edittext);
//        builder.setView(edittext2);
//        builder.setPositiveButton("입력",
//                 new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        phone_num.add(edittext.getText().toString());
//                        Log.d("size", "-----------------------------" + phone_num.get(0));
//                        selected_result.add(search_result.get(position));
//                        selectedLayoutManager = new LinearLayoutManager(context);
//                        selected.setLayoutManager(selectedLayoutManager);
//                        selectedAdapter = new Second_Selected_Adapter(selected_result, phone_num);
//                        selected.setAdapter(selectedAdapter);
//
//                        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//                            @Override
//                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                                return false;
//                            }
//
//                            @Override
//                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                                selected_result.remove(viewHolder.getLayoutPosition());
//                                selectedAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
//                            }
//                        };
//                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//                        itemTouchHelper.attachToRecyclerView(selected);
//                    }
//                });
//        builder.show();
//    }

    public ArrayList<String> getNum(){
        return this.phone_num;
    }
    public ArrayList<String> getTime(){
        return this.time_array;
    }
}
