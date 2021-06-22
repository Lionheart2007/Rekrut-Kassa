package com.pat.rekrutkassa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {
public static final String EXTRA_SETTING_OPTION = "options";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String option = getIntent().getStringExtra(EXTRA_SETTING_OPTION);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(option == null){
            finish();
            return;
        }

        switch(option) {
            case "newOrder": {
                fragmentManager.beginTransaction()
                        .add(R.id.detailContainer, new NewDetailFragment())
                        .commit();
                break;
            }
            case "inventory": {
                fragmentManager.beginTransaction()
                        .add(R.id.detailContainer, new InventoryDetailFragment())
                        .commit();
                break;
            }
            case "options": {
                fragmentManager.beginTransaction()
                        .add(R.id.detailContainer, new OptionsDetailFragment())
                        .commit();
                break;
            }
        }
    }
}