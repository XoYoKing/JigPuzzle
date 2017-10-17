package io.github.zanyxdev.jigpuzzle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
private GamePanel gamePanel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gamePanel = new GamePanel(this);
    setContentView(gamePanel);
  }
  @Override
  protected void onResume() {
    super.onResume();

    // Tell the gameView resume method to execute
    gamePanel.resume();
  }

  // This method executes when the player quits the game
  @Override
  protected void onPause() {
    super.onPause();

    // Tell the gameView pause method to execute
    gamePanel.pause();
  }

}