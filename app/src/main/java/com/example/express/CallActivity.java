package com.example.express;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.co.shineware.nlp.komoran.model.Token;

import static android.speech.tts.TextToSpeech.ERROR;

public class CallActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private static final int REQUEST_CODE = 1234;
    ArrayList<String> matches_text;
    private ArrayList<Second_raw_info> data;
    int k = 0;
    String result = "";
    String place = "";
    private int position;
    private ArrayList<String> yesno;
    private ArrayList<String> take;
    private String time;
    private String pure_YN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView gif = (ImageView)findViewById(R.id.gif);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gif);
        Glide.with(this).load(R.raw.call).into(gif);

        Intent intent = getIntent();
        time = intent.getExtras().getString("time");
        String location1 = intent.getExtras().getString("location");
        String num1 = intent.getExtras().getString("num");
        data = (ArrayList<Second_raw_info>) intent.getExtras().getSerializable("data");
        position = intent.getExtras().getInt("position");
        yesno = intent.getExtras().getStringArrayList("yesno");
        take = intent.getExtras().getStringArrayList("take");

        TextView location = findViewById(R.id.location);
        TextView num = findViewById(R.id.num);

        location.setText(location1);
        num.setText(num1);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR){
                    tts.setLanguage(Locale.KOREAN);
                    tts.speak("안녕하세요.몰입택배입니다. "+time+"시에 "+location1+"에 계시나요?", TextToSpeech.QUEUE_FLUSH, null);
                    new Waiter().execute();
                }
            }
        });
    }
    class Waiter extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (tts.isSpeaking()){
                try{Thread.sleep(500);}catch (Exception e){}
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isConnected()){
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                startActivityForResult(intent, REQUEST_CODE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (k == 2){
                k = 1;
                String ptp = placetoput(matches_text.get(0));
                speakCheckInBackground(ptp);
            }else{
            try {
                result = new DeepLearning1(CallActivity.this, MainActivity.komoran, MainActivity.retMap).getAnswer(matches_text.get(0));
                if(result.equals("Y")) {
                    k = 1;
                    pure_YN = "Y";
                    result = "그럼 " + time +"시에 뵙겠습니다.";
                }
                else if(result.equals("아아, 그렇군요. 그럼 어디에 맡겨둘까요?")){
                    k = 2;
                    pure_YN = "N";
                }
                Log.d("t", "========================================" + k);
                speakCheckInBackground(result);
            } catch(IOException e){
            }
        }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void speakCheckInBackground(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        if(k != 1) new Waiter().execute();
        else{
            while (tts.isSpeaking()){
                try{Thread.sleep(500);}catch (Exception e){}
            }
            Context context = CallActivity.this;
            Intent intent1 = new Intent(context, FourthActivity.class);
            intent1.putExtra("key", 1);
            intent1.putExtra("result", data);
            intent1.putExtra("yesno", yesno);
            intent1.putExtra("take", place);
            intent1.putExtra("position", position);
            intent1.putExtra("t", take);
            intent1.putExtra("pureYN", pure_YN);
            startActivity(intent1);
        }
    }

    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public String placetoput (String input){
        List<Token> tokens = MainActivity.komoran.analyze(input).getTokenList();
        place = "경비실";
        for(int i =tokens.size()-1;i>=0;i--){
            if (tokens.get(i).getPos().equals("NNG")){
                place = tokens.get(i).getMorph();
                Log.d("t", "------------");
            }
        }
        return "알겠습니다! 그럼 " + place + " 에 맡겨두겠습니다.";
    }


}
