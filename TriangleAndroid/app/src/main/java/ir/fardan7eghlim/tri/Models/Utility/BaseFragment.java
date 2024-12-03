package ir.fardan7eghlim.tri.Models.Utility;

import android.support.v4.app.Fragment;
import android.os.Bundle;


import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;


/**
 * Created by Meysam on 3/8/2017.
 */

public class BaseFragment extends Fragment {

    protected SessionModel session;
    protected DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionModel(getActivity().getApplicationContext());
        db = new DatabaseHandler(getActivity().getApplicationContext());

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

}
