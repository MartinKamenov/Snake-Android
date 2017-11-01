package com.kamenov.martin.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Martin on 31.10.2017 г..
 */

public class SnakePlayer implements GameObject {
    private int size;
    private ArrayList<Rect> rectangles;
    private Direction direction;
    private Paint paint;
    private Rect tale;

    public SnakePlayer() {
        rectangles = new ArrayList<>();
        // left, top, right, bottom
        rectangles.add(new Rect(0, 0, Constants.CELL_WIDTH, Constants.CELL_HEIGHT));
        rectangles.add(new Rect(0, Constants.CELL_HEIGHT, Constants.CELL_WIDTH, 2*Constants.CELL_HEIGHT));
        rectangles.add(new Rect(0, 2*Constants.CELL_HEIGHT, Constants.CELL_WIDTH, 3*Constants.CELL_HEIGHT));
        setSize(3);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        setColor(Color.RED);
        setDirection(Direction.Down);
    }
    @Override
    public void draw(Canvas canvas) {
        for(Rect rect : rectangles) {
            canvas.drawRect(rect, paint);
        }

    }

    public void addTale() {
        rectangles.add(0, tale);
    }

    @Override
    public void update() {
        Rect firstRect = rectangles.get(0);
        tale = new Rect(firstRect.left, firstRect.top, firstRect.right, firstRect.bottom);
        Rect lastRect = rectangles.get(rectangles.size() - 1);
        rectangles.remove(0);
        switch (direction) {
            case Up:
                firstRect.top = lastRect.top - Constants.CELL_HEIGHT;
                firstRect.bottom = lastRect.top;
                firstRect.left = lastRect.left;
                firstRect.right = lastRect.right;
                break;
            case Down:
                firstRect.top = lastRect.bottom;
                firstRect.bottom = lastRect.bottom + Constants.CELL_HEIGHT;
                firstRect.left = lastRect.left;
                firstRect.right = lastRect.right;
                break;
            case Left:
                firstRect.left = lastRect.left - Constants.CELL_WIDTH;
                firstRect.right = lastRect.left;
                firstRect.top = lastRect.top;
                firstRect.bottom = lastRect.bottom;
                break;
            case Rigth:
                firstRect.right = lastRect.right + Constants.CELL_WIDTH;
                firstRect.left = lastRect.right;
                firstRect.top = lastRect.top;
                firstRect.bottom = lastRect.bottom;
                break;
        }
        rectangles.add(firstRect);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public ArrayList<Rect> getRectangles() {
        return rectangles;
    }
}
