package com.kamenov.martin.snake.contracts;

import android.graphics.Canvas;

/**
 * Created by Martin on 31.10.2017 г..
 */

public interface GameObject {
    void draw(Canvas canvas);

    void update();
}
