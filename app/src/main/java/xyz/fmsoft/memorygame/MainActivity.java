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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.play_button)Button playButton;
    @BindView(R.id.help_button)Button helpButton;
    @BindView(R.id.restart_button)Button restartButton;
    private Bundle keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        restartButton.setVisibility(View.GONE);
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

    @OnClick(R.id.play_button)
    public void play(){
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
    }

    @OnClick(R.id.help_button)
    public void help(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        DialogFragment helpDialog = HelpDialog.newInstance();
        helpDialog.show(fragmentTransaction,"Help");
    }

    @OnClick(R.id.restart_button)
    public void restart(){
        startActivity(new Intent(this,GameActivity.class));
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */

}
