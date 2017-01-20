package com.anudeepsamaiya.progressview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    ProgressView progressBar;
    Button button;

    int i = 0;

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
        if (i > 100)
            i = 0;
        i += 1;
        float width = progressBar.getWidth()/100;
        progressBar.setProgress(width);
    }
}
