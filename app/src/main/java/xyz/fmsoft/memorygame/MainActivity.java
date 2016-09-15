package xyz.fmsoft.memorygame;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button playButton;
    private Button helpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = (Button)findViewById(R.id.play_button);
        helpButton = (Button)findViewById(R.id.help_button);
        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        //Hiding actionbar gives cleaner look
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
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
                startActivity(new Intent(this,GameActivity.class));
                break;
            case R.id.help_button:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                DialogFragment helpDialog = HelpDialog.newInstance();
                helpDialog.show(fragmentTransaction,"Help");
                break;
        }
    }
}
