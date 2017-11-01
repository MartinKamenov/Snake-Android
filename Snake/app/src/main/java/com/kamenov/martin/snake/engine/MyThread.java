package com.kamenov.martin.snake.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.kamenov.martin.snake.engine.GamePannel;

/**
 * Created by Martin on 31.10.2017 г..
 */

public class MyThread extends Thread {
    public static final int MAX_FPS = 30;
    private GamePannel gamePannel;
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private Canvas canvas;
    private double averageFPS;

    public MyThread(SurfaceHolder surfaceHolder, GamePannel pannel) {
        this.surfaceHolder = surfaceHolder;
        this.gamePannel = pannel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePannel.update();
                    this.gamePannel.draw(canvas);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if(canvas!=null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) {
                    this.sleep(300);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTime = System.nanoTime() - startTime;
            frameCount++;

            if(frameCount==MAX_FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}
