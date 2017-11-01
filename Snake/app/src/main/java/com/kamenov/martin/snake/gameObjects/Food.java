package com.kamenov.martin.snake.gameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.kamenov.martin.snake.contracts.Constants;
import com.kamenov.martin.snake.contracts.GameObject;

/**
 * Created by Martin on 31.10.2017 г..
 */

public class Food implements GameObject {
    private Rect rect;
    private Paint paint;

    public Food(int x, int y, int color) {
        rect = new Rect(x, y, Constants.CELL_WIDTH + x, Constants.CELL_HEIGHT + y);
        //rect = new Rect(Constants.CELL_WIDTH, 0, 2  * Constants.CELL_WIDTH, Constants.CELL_HEIGHT);
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }

    public void setCordinates(int x, int y) {
        rect.set(x, y, Constants.CELL_WIDTH + x, Constants.CELL_HEIGHT + y);
    }

    public Point getCordinates() {
        return new Point(rect.left, rect.top);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update() {

    }
}
