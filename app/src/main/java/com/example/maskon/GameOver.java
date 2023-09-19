package com.example.maskon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView point;
    TextView highest;
    SharedPreferences sharedPreferences;
    ImageView newHighest;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover_layout);
        point = findViewById(R.id.point);
        highest = findViewById(R.id.highest);
        newHighest = findViewById(R.id.newHighest);
        int Final_points = getIntent().getExtras().getInt("points");
        point.setText("" + Final_points);
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int Final_highest = sharedPreferences.getInt("highest", 0);
        if (Final_points > Final_highest){

            newHighest.setVisibility(View.VISIBLE);
            Final_highest = Final_points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest", Final_highest);
            editor.commit();
        }
        highest.setText("" + Final_highest);
    }
    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void exit(View view){
        finish();
    }
}
