package xyz.fmsoft.memorygame;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button playButton;
    private Button helpButton;
    private Button restartButton;
    private Bundle keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = (Button)findViewById(R.id.play_button);
        helpButton = (Button)findViewById(R.id.help_button);
        restartButton = (Button)findViewById(R.id.restart_button);
        restartButton.setVisibility(View.GONE);
        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        restartButton.setOnClickListener(this);
        //Hiding actionbar gives cleaner look
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        keys = getIntent().getExtras();
        if(keys != null) {
            if (!(keys.getString("state").equals(null))) {
                playButton.setText("Resume");
                restartButton.setVisibility(View.VISIBLE);
            }
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
        keys = getIntent().getExtras();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play_button:
                ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(5);
                Log.d("MAIN",""+list.size());
                if(list.size()>=2){
                    Intent resumeGame = new Intent(this,GameActivity.class);
                    resumeGame.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(resumeGame);
                    finish();


                }
                else {
                    startActivity(new Intent(this, GameActivity.class));
                }
                break;
            case R.id.help_button:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment helpDialog = HelpDialog.newInstance();
                helpDialog.show(fragmentTransaction,"Help");
                break;
            case R.id.restart_button:
                startActivity(new Intent(this,GameActivity.class));
                break;
        }
    }
}
