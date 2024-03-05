package com.android.kotlin.teststate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView {

    private final MainPresenter presenter = new MainPresenter(this, this);

    private final String KEY = "1";

    private int count = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(c -> {
            count++;
            presenter.setCount(count);
            ((TextView) findViewById(R.id.textviewOfUI)).setText(getString(R.string.ui, String.valueOf(count)));
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY, count);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt(KEY);
        ((TextView) findViewById(R.id.textviewOfUI)).setText(getString(R.string.ui, String.valueOf(count)));
    }

    @Override
    public void setText(String count) {
        ((TextView) findViewById(R.id.textviewOfLogic)).setText(getString(R.string.logic, count));
    }
}