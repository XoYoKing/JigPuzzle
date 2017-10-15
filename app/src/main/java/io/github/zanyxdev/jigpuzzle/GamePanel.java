package io.github.zanyxdev.jigpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created by zanyxdev on 15.10.17.
 */

class GamePanel extends SurfaceView implements SurfaceHolder.Callback, Runnable {

  // This is our thread
  Thread gameThread = null;
  // A boolean which we will set and unset  when the game is running- or not.
  volatile boolean playing;

  // A Canvas and a Paint object
  Canvas canvas;
  Paint paint;

  // This variable tracks the game frame rate
  long fps;

  // This is used to help calculate the fps
  private long timeThisFrame;

  // This is new. We need a SurfaceHolder. When we use Paint and Canvas in a thread
  // We will see it in action in the draw method soon.
  SurfaceHolder ourHolder;
  private String timeField;

  public GamePanel(Context context) {

    // The next line of code asks the  SurfaceView class to set up our object.
    super(context);

    setKeepScreenOn(true);

    // Initialize ourHolder and paint objects
    ourHolder = getHolder();
    paint = new Paint();
    new CountDownTimer(30000, 1000) {

      public void onTick(long millisUntilFinished) {
        timeField = new String("seconds remaining: " + millisUntilFinished / 1000);
      }

      public void onFinish() {
        timeField = new String("done");
      }
    }.start();

  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {

  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    //mThread.setSize(width, height);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {

  }

  @Override
  public void run() {
    while (playing) {

      // Capture the current time in milliseconds in startFrameTime
      long startFrameTime = System.currentTimeMillis();

      // Update the frame
      update();

      // Draw the frame
      draw();

      // Calculate the fps this frame. We can then use the result to time animations and more.
      timeThisFrame = System.currentTimeMillis() - startFrameTime;
      if (timeThisFrame > 0) {
        fps = 1000 / timeThisFrame;
      }

    }
  }

  // In later projects we will have dozens (arrays) of objects.
  // We will also do other things like collision detection.
  public void update() {

  }

  // Draw the newly updated scene
  public void draw() {

    // Make sure our drawing surface is valid or we crash
    if (ourHolder.getSurface().isValid()) {
      // Lock the canvas ready to draw
      // Make the drawing surface our canvas object
      canvas = ourHolder.lockCanvas();

      // Draw the background color
      canvas.drawColor(Color.argb(255, 26, 128, 182));

      // Choose the brush color for drawing
      paint.setColor(Color.argb(255, 249, 129, 0));

      // Make the text a bit bigger
      paint.setTextSize(45);

      // Display the current fps on the screen
      canvas.drawText("FPS:" + fps, 20, 40, paint);

     // long millis = System.currentTimeMillis();
      //String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
     //     TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
     //     TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));

      // Display the current fps on the screen
      canvas.drawText("Time:" + timeField, 20, 80, paint);

      // Draw bob at bobXPosition, 200 pixels
      //canvas.drawBitmap(bitmapBob, bobXPosition, 200, paint);

      // Draw everything to the screen
      // and unlock the drawing surface
      ourHolder.unlockCanvasAndPost(canvas);
    }

  }

  // If SimpleGameEngine Activity is paused/stopped
  // shutdown our thread.
  public void pause() {
    playing = false;
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      Log.e("Error:", "joining thread");
    }

  }

  // If SimpleGameEngine Activity is started theb
  // start our thread.
  public void resume() {
    playing = true;
    gameThread = new Thread(this);
    gameThread.start();
  }
}
