package io.github.zanyxdev.jigpuzzle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private GamePanel gamePanel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    gamePanel = new GamePanel(this);
    setContentView(R.layout.activity_main);


    FrameLayout frm=(FrameLayout)findViewById(R.id.frameLayout);
    frm.addView(gamePanel);

    Button btnC=(Button)findViewById(R.id.buttonColor);

    btnC.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        switch (v.getId()) {
          case R.id.buttonColor:
            Toast.makeText(getApplicationContext(), "Color", Toast.LENGTH_LONG).show();
            gamePanel.colorNew();

            break;

          default:
            break;
        }
      }
    });
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