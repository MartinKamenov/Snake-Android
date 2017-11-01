package com.kamenov.martin.snake.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kamenov.martin.snake.contracts.Constants;
import com.kamenov.martin.snake.contracts.Direction;
import com.kamenov.martin.snake.gameObjects.Food;
import com.kamenov.martin.snake.gameObjects.SnakePlayer;

/**
 * Created by Martin on 31.10.2017 г..
 */

public class GamePannel extends SurfaceView implements SurfaceHolder.Callback {
    private MyThread thread;
    private SnakePlayer snakePlayer;
    private GestureDetector detector;
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    private static int MIN_DISTANCE = 4000;
    private Food food;
    public int score;

    public GamePannel(Context context) {
        super(context);
        getHolder().addCallback(this);

        setFocusable(true);
        score = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        snakePlayer = new SnakePlayer();
        Point point = getFoodCordinates();
        food = new Food(point.x, point.y ,Color.YELLOW);
        this.thread = new MyThread(getHolder(), GamePannel.this);
        thread.setRunning(true);

        thread.start();
    }

    private Point getFoodCordinates() {
        int randX = 0;
        int randY = 0;
        while (true) {
            boolean rightCordinates = true;
            randX = (int)(Math.random() * Constants.COLS) * Constants.CELL_WIDTH;
            randY = (int)(Math.random() * Constants.ROWS) * Constants.CELL_HEIGHT;
            for (Rect rect : snakePlayer.getRectangles()) {
                if (rect.left == randX && rect.top == randY) {
                    rightCordinates = false;
                    break;
                }

            }
            if(rightCordinates) {
                break;
            }
        }
        return new Point(randX, randY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e) {
                retry = false;
            }

        }
    }

    public void update() {
        snakePlayer.update();
        if(checkForColisionWithSnake()) {
            thread.setRunning(false);
            return;
        }
        if(checkForColisionWithWall()) {
            thread.setRunning(false);
            return;
        }
        if(checkForColisionWithFood()) {
            snakePlayer.addTale();
            score++;
            Point p = getFoodCordinates();
            food.setCordinates(p.x, p.y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), new Paint(Color.WHITE));
        food.draw(canvas);
        snakePlayer.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;
                /*if (Math.abs(deltaX) + Math.abs(deltaY) > MIN_DISTANCE)
                {

                }*/
                if(Math.abs(deltaX)>Math.abs(deltaY)) {
                    if(deltaX > 0) {
                        if(snakePlayer.getDirection()!= Direction.Left) {
                            snakePlayer.setDirection(Direction.Rigth);
                        }
                    }
                    else if(deltaX < 0) {
                        if(snakePlayer.getDirection()!=Direction.Rigth) {
                            snakePlayer.setDirection(Direction.Left);
                        }
                    }
                }
                else if(Math.abs(deltaY)>=Math.abs(deltaX)) {
                    if(deltaY > 0) {
                        if(snakePlayer.getDirection()!=Direction.Up) {
                            snakePlayer.setDirection(Direction.Down);
                        }
                    }
                    else if(deltaY < 0) {
                        if(snakePlayer.getDirection()!=Direction.Down) {
                            snakePlayer.setDirection(Direction.Up);
                        }
                    }
                }

                break;
        }
        return true;
    }

    public boolean checkForColisionWithFood() {
        Point pFood = food.getCordinates();
        Rect last = snakePlayer.getRectangles().get(snakePlayer.getRectangles().size() - 1);
        if(last.left == pFood.x && last.top == pFood.y) {
            return true;
        }
        return false;
    }

    public boolean checkForColisionWithSnake() {
        Rect last = snakePlayer.getRectangles().get(snakePlayer.getRectangles().size() - 1);
        for(int i = 0; i < snakePlayer.getRectangles().size() - 5; i++) {
            Rect current = snakePlayer.getRectangles().get(i);
            if(current.left==last.left && current.top == last.top) {
                return true;
            }
        }

        return false;
    }

    public boolean checkForColisionWithWall() {
        Rect last = snakePlayer.getRectangles().get(snakePlayer.getRectangles().size() - 1);
        if(last.left>=Constants.CELL_WIDTH * Constants.COLS ||
                last.top>=Constants.CELL_HEIGHT * Constants.ROWS ||
                last.right<=0 ||
                last.bottom<=0) {
            return true;
        }

        return false;
    }
}
