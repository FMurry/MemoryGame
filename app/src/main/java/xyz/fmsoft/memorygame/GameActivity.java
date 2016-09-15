package xyz.fmsoft.memorygame;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private GridView gameLayout;
    private TextView points;
    //TODO: Replace info textview with button
    private TextView info;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        counter = 0;
        gameLayout = (GridView)findViewById(R.id.game_layout);
        points = (TextView)findViewById(R.id.point_counter);
        info = (TextView)findViewById(R.id.info_button);
        points.setText("Points: "+counter);

        //TODO: Gather 10 android icons and place them in GridLayout Randomly
        //TODO: Create flipping card Animation
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
                break;
        }
    }
}
