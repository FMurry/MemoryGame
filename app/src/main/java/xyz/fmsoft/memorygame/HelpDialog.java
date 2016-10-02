package xyz.fmsoft.memorygame;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredericmurry on 9/15/16.
 */
public class HelpDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.help_ok) Button ok;
    @BindView(R.id.help_exit) TextView exit;

    static HelpDialog newInstance(){
        HelpDialog hd = new HelpDialog();

        return hd;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, getTheme());

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.help_dialog,container,false);
        ButterKnife.bind(this,v);
        ok.setOnClickListener(this);
        exit.setOnClickListener(this);
        return v;

    }

    /**
     * Customized onDismiss method to pause timer and start it back up without resetting the time
     * @param dialog
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        //If GameActivity is the owner of HelpDialog set the timer on dismissal

        super.onDismiss(dialog);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}


