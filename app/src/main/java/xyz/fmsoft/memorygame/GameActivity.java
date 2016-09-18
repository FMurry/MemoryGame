package xyz.fmsoft.memorygame;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//TODO: If user hits back button let them resume their current game
//TODO: Listen for winner
public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gameLayout;
    private TextView points;
    private TextView info;
    private TextView timerDisplay;
    private int pointCounter;
    private CountDownTimer timer;
    private GridView gridView;
    public long totalTime = 180000;
    ArrayList<ImageView> activeCards;
    Integer[] gameArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeCards = new ArrayList<>();
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
        ImageAdapter adapter =new ImageAdapter(this);
        gameArray = adapter.getArray();
        gridView.setAdapter(adapter);
        for(int i = 0; i < gameArray.length-1;i++){
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    checkGame(view, position);
                }
            });
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.info_button) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            DialogFragment helpDialog = HelpDialog.newInstance();
            helpDialog.show(fragmentTransaction, "Help");
            timer.cancel();
            timerDisplay.setText("Paused");
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

    public void checkGame(View card, int position) {
        if((int)(((ImageView)card).getTag()) != R.drawable.checkmark) {
            activeCards.add((ImageView) card);
            ((ImageView) card).setImageResource(gameArray[position]);
            ((ImageView) card).setTag(gameArray[position]);
        }
        if(activeCards.size() >= 2) {
            gridView.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int size = activeCards.size();
                    if(!(activeCards.get(0).getTag().equals(activeCards.get(1).getTag()))) {
                        for (int i = 0; i < size; i++) {
                            activeCards.get(0).setImageResource(R.drawable.placeholder);
                            activeCards.remove(0);
                        }
                    }
                    else if((int)activeCards.get(0).getTag() != R.drawable.checkmark && (int)activeCards.get(1).getTag() != R.drawable.checkmark){

                        pointCounter+=1;
                        points.setText("Points: "+pointCounter);
                        for(int i = 0; i < activeCards.size();i++){
                            //activeCards.get(i).setImageResource(R.drawable.checkmark);
                            activeCards.get(i).setTag(R.drawable.checkmark);
                        }
                        activeCards.clear();

                    }
                    gridView.setEnabled(true);

                }
            }, 1500);

        }

    }


}
