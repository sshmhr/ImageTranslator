package com.example.imagetranslator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class speechActivity extends AppCompatActivity {

    private String translatedText;
    private TextView translatedTexttextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);
        initElements();
        getDataFromIntent();
        displayTranslatedText();
    }

    private void displayTranslatedText() {
        translatedTexttextView.setText(translatedText);
    }

    private void initElements() {
        translatedTexttextView = findViewById(R.id.translated_text);
    }

    private void getDataFromIntent() {
        Intent i = getIntent();
        translatedText = i.getStringExtra("translatedText");
    }
}
