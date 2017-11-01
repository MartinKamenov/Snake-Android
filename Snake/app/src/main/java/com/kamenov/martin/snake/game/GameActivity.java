package com.kamenov.martin.snake.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.kamenov.martin.snake.contracts.Constants;
import com.kamenov.martin.snake.engine.GamePannel;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.COLS = 16;
        Constants.ROWS = 32;
        Constants.CELL_WIDTH = Constants.SCREEN_WIDTH/Constants.COLS;
        Constants.CELL_HEIGHT = Constants.SCREEN_HEIGHT/Constants.ROWS;
        setContentView(new GamePannel(this));
    }
}
