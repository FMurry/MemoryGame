package xyz.fmsoft.memorygame;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO: If user hits back button let them resume their current game
//TODO: Listen for winner
public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.game_layout)GridView gameLayout;
    @BindView(R.id.point_counter)TextView points;
    @BindView(R.id.info_button)TextView info;
    @BindView(R.id.game_counter)TextView timerDisplay;
    private int pointCounter;
    private CountDownTimer timer;
    private GridView gridView;
    public long totalTime = 181000;
    private boolean won;
    ArrayList<ImageView> activeCards;
    Integer[] gameArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        won = false;
        activeCards = new ArrayList<>();
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        pointCounter = 0;
        info.setOnClickListener(this);
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
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setTimer();
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
            if(!won) {
                timerDisplay.setText("Paused");
            }
        }

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        Intent mainMenu = new Intent(this, MainActivity.class);
        onPause();
         mainMenu.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mainMenu.putExtra("state","true");
        timer.cancel();
        startActivity(mainMenu);
    }

    /**
     * Initializes the game timer
     */
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
            }


        }.start();
    }

    /**
     * Method that handles the game logic. Checks if cards are similar, Handles card animation
     * @param card the card that is being checked
     * @param position the position of the card
     */
    public void checkGame(View card, int position) {

        if((int)(((ImageView)card).getTag()) != R.drawable.checkmark && activeCards.size() < 1) {
            activeCards.add((ImageView) card);
            ((ImageView) card).setImageResource(gameArray[position]);
            ((ImageView) card).setTag(gameArray[position]);
        }
        //So user doesn't press same image twice
        else if((int)(((ImageView)card).getTag()) != R.drawable.checkmark && !((ImageView)card).equals(activeCards.get(0))){
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

                        //Winning Case
                        if(pointCounter >= 10){
                            won = true;
                            timer.cancel();
                            timerDisplay.setText("You Win!!!");
                        }

                    }
                    gridView.setEnabled(true);

                }
            }, 500);
        }
    }

    /**
     * Returns whether the player won the match or nut
     * @return the boolean stating whether player won or not
     */
    public boolean playerWon(){
        return won;
    }

    /**
     * Shuffles card game so that unsolved cards are below all of the solved cards
     */
    public void smartShuffle(){

    }
}
