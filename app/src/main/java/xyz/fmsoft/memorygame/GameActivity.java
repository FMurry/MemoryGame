package xyz.fmsoft.memorygame;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gameLayout;
    private TextView points;
    private TextView info;
    private TextView timerDisplay;
    private int pointCounter;
    private CountDownTimer timer;
    private GridView gridView;
    public long totalTime = 31000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        pointCounter = 0;
        gameLayout = (GridView)findViewById(R.id.game_layout);
        points = (TextView)findViewById(R.id.point_counter);
        info = (TextView)findViewById(R.id.info_button);
        info.setOnClickListener(this);
        timerDisplay = (TextView)findViewById(R.id.game_counter);
        points.setText("Points: "+pointCounter);
        setTimer();


        //TODO: Gather 10 android icons and place them in GridLayout Randomly
        //TODO: Create flipping card Animation
        gridView = (GridView)findViewById(R.id.game_layout);
        gridView.setAdapter(new ImageAdapter(this));

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_button:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment helpDialog = HelpDialog.newInstance();
                helpDialog.show(fragmentTransaction,"Help");
                timer.cancel();
                timerDisplay.setText("Paused");


                break;
        }
    }

    public void setTimer(){
        timer = new CountDownTimer(totalTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerDisplay.setText("Seconds remaining: "+millisUntilFinished/1000+"s");
                totalTime = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timerDisplay.setText("Seconds remaining: 0");
                Toast.makeText(GameActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
            }


        }.start();
    }
}
