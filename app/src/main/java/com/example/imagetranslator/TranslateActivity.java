package com.example.imagetranslator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {
    String langChoice = "hi" ;
    private TextView ocr_text_area;
    String result = "" ;
    private static final String API_KEY = "AIzaSyBTw-JSbO96CgAUSxsDMC1uHj4pZWen9fY" ;
    private String extractedText;
    private ArrayList<languages> langList;
    private Spinner spinner_language;
    private ArrayList<String> langNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
        displayText();
    }



    public void translate(){
        final Handler textViewHandler = new Handler();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(API_KEY)
                        .build();
                Translate translate = options.getService();
                final Translation translation =
                        translate.translate(extractedText,
                                Translate.TranslateOption.targetLanguage(langChoice));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                            result = (translation.getTranslatedText());
                            Intent i = new Intent(TranslateActivity.this , speechActivity.class);
                            i.putExtra("translatedText", result);
                            startActivity(i);

                    }
                });
                return null;
            }
        }.execute();
    }

    private void displayText() {
        Intent i = getIntent();
        extractedText = i.getStringExtra("extractedText");
        ocr_text_area.setText(extractedText);
    }

    private void initComponents() {
        ocr_text_area = findViewById(R.id.text_extracted_display);
        spinner_language = findViewById(R.id.spinner_language_select);
        langList = new ArrayList<>();
        langNameList = new ArrayList<>();
        seedList();
        ArrayAdapter<String> langListAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, langNameList);
        langListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_language.setAdapter(langListAdapter);
    }

    private void seedList() {
        langList.add(new languages("Afrikaans","af"));
        langList.add(new languages("Albanian","sq"));
        langList.add(new languages("Amharic","am"));
        langList.add(new languages("Arabic","ar"));
        langList.add(new languages("Bengali","bn"));
        langList.add(new languages("Bulgarian","bg"));
        langList.add(new languages("Chinese (Simplified)","zh-CN"));
        langList.add(new languages("Danish","da"));
        langList.add(new languages("Dutch	","nl"));
        langList.add(new languages("English","en"));
        langList.add(new languages("Finnish","fi"));
        langList.add(new languages("French","fr"));
        langList.add(new languages("German","de"));
        langList.add(new languages("Greek	","el"));
        langList.add(new languages("Gujarati","gu"));
        langList.add(new languages("Hindi	","hi"));
        langList.add(new languages("Japanese","ja"));
        langList.add(new languages("Kannada","kn"));
        langList.add(new languages("Marathi","mr"));
        langList.add(new languages("Nepali","ne"));
        langList.add(new languages("Punjabi","pa"));
        langList.add(new languages("Russian","ru"));
        langList.add(new languages("Spanish","es"));
        langList.add(new languages("Tamil","ta"));
        langList.add(new languages("Telugu","te"));

        for(languages i : langList){
            langNameList.add(i.langName);
        }

        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                langChoice = langList.get(position).lId;
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

}
