package com.example.imagetranslator;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class speechActivity extends AppCompatActivity {

    private String translatedText;
    private TextView translatedTexttextView;
    TextToSpeech t1;
    private Button translate_button;
    private String language_choice;
    private Bundle x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        initElements();
        getDataFromIntent();
        displayTranslatedText();
        initTranslation();
    }

    private void initTranslation() {
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    Locale lan_choice_locale = new Locale(language_choice);
                    int result = t1.setLanguage(lan_choice_locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                }
            }
        });

        translate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = translatedText;
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH,null);

            }
        });
    }

    private void displayTranslatedText() {
        translatedTexttextView.setText(translatedText);
    }

    private void initElements() {
        translate_button = findViewById(R.id.translate_button);
        translatedTexttextView = findViewById(R.id.translated_text);
    }

    private void getDataFromIntent() {
        Intent i = getIntent();
        translatedText = i.getStringExtra("translatedText");
        language_choice = i.getStringExtra("localeLanguage");
    }

    public void onStop(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onStop();
    }
}
