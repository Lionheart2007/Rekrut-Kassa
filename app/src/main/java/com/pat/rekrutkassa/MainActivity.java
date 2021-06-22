package com.pat.rekrutkassa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.pat.rekrutkassa.R;

public class MainActivity extends AppCompatActivity implements OptionsFragment.OnOptionsClickListener {
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container, new OptionsFragment())
                    .commit();
        }


    }

    @Override
    public void onOptionSelected(String option) {
        if(getResources().getBoolean(R.bool.isTablet)){
            switch (option) {
                case "newOrder":{
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, new NewDetailFragment())
                            .commit();
                    break;
                }
                case "inventory":{
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, new InventoryDetailFragment())
                            .commit();
                    break;
                }
                case "options":{
                    fragmentManager.beginTransaction()
                            .replace(R.id.detailContainer, new OptionsDetailFragment())
                            .commit();
                    break;
                }
            }
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_SETTING_OPTION, option);
            startActivity(intent);
        }
    }
}