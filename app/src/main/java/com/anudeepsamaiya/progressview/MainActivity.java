package com.anudeepsamaiya.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ProgressView progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressView) findViewById(R.id.progressBar);
        progressBar.setGoal(100);

       button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetProgress();
            }
        });
    }

    public void resetProgress() {
        final Random random = new Random();
        int prog = random.nextInt(100);
        progressBar.setProgress(prog);
    }
}
