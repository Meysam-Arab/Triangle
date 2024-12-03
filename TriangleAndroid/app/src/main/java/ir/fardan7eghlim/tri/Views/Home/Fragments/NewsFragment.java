package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Controllers.RssController;
import ir.fardan7eghlim.tri.Models.NewsModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.ProgressDialog;
import ir.fardan7eghlim.tri.Utils.RequestRespond;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_news;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment implements Observer {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spr_category_news;
    private ListView lsv_full_screen_news;
    private CustomAdapterList_news CALM;
    private ArrayList<NewsModel> list_of_news = new ArrayList<NewsModel>();

    private OnFragmentInteractionListener mListener;

    private RssController rc;
    private ProgressDialog PD;
    private Integer lastSelectedIndex;
    private Boolean isInCreate;

    private SwipeRefreshLayout swipeRefreshLayout;

    //meysam - added in 13970318
    private Handler customHandler = new Handler();
    private int timeWaitBeforeRefresh = 60000;//meysam - must be 60000
    private Runnable refreshTimerThread = new Runnable() {

        public void run() {



            if(CALM != null)
            {
                //meysam - on minute tick
                CALM.notifyDataSetChanged();
                //////////////////////////////////////////////////////////
            }

                //meysam - for continuing timer
                customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
                ////////////////////////////////

        }

    };
    ////////////////////////////////////////////////////////

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //meysam - added in 13970318
        if (customHandler == null)
            customHandler = new Handler();
        customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
        /////////////////////////////////

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        rc = new RssController(getContext());
        rc.addObserver(this);

        PD = new ProgressDialog(getContext());

        lastSelectedIndex = 0;
        isInCreate = true;

//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMeysamBroadcastReceiver,
//                new IntentFilter("news_fragment_broadcast"));

        if(MainActivity.PD != null)
            MainActivity.PD.hide();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        lsv_full_screen_news = getActivity().findViewById(R.id.lsv_full_screen_news);
        spr_category_news = getActivity().findViewById(R.id.spr_category_news);
        addItemsOnSpinner_categories();
        spr_category_news.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                if (Utility.isNetworkAvailable(getActivity().getApplicationContext()))
                {
                    if(isInCreate) {
                        String lastType = session.getStringItem(SessionModel.KEY_NEWS_LAST_CATEGORY_ID, null);

                        if (lastType != null)
                        {
                            if(!Integer.valueOf(lastType).equals(Integer.valueOf(position)))
                            {
                                spr_category_news.setSelection(Integer.valueOf(lastType));
                            }
                            else
                            {
                                PD.show();
                                getNews();
                            }

                        }
                        else
                        {
                            PD.show();
                            rc.get(null,25);
                        }
                        isInCreate = false;
                    }
                    else
                    {
                        PD.show();
                        getNews();
                    }
                }
                else
                {
                    Utility.displayToast(getActivity().getApplicationContext(),getString(R.string.error_connecting_to_server),Toast.LENGTH_SHORT);
                    spr_category_news.setSelection(lastSelectedIndex);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        //pull to refresh
        swipeRefreshLayout = getActivity().findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getNews();
                    }
                }
        );
    }

    private void getNews() {
        // meysam : fetch from parsik base on category
        if(spr_category_news.getSelectedItemId() == 0)
            rc.get(null,25);
        else if(spr_category_news.getSelectedItemId() == 1)
            rc.get(NewsModel.TYPE_IRAN,25);
        else if(spr_category_news.getSelectedItemId() == 2)
            rc.get(NewsModel.TYPE_WORLD,25);
        else if(spr_category_news.getSelectedItemId() == 3)
            rc.get(NewsModel.TYPE_ECONOMIC,25);
        else if(spr_category_news.getSelectedItemId() == 4)
            rc.get(NewsModel.TYPE_SCIENCE,25);
        else if(spr_category_news.getSelectedItemId() == 5)
            rc.get(NewsModel.TYPE_IT,25);
        else if(spr_category_news.getSelectedItemId() == 6)
            rc.get(NewsModel.TYPE_ART,25);
        else if(spr_category_news.getSelectedItemId() == 7)
            rc.get(NewsModel.TYPE_SOCIAL,25);
        else if(spr_category_news.getSelectedItemId() == 8)
            rc.get(NewsModel.TYPE_SPORT,25);
        else if(spr_category_news.getSelectedItemId() == 9)
            rc.get(NewsModel.TYPE_ACCIDENT,25);
        else if(spr_category_news.getSelectedItemId() == 10)
            rc.get(NewsModel.TYPE_OTHER,25);
        else
            rc.get(null,25);

        session.saveItem(SessionModel.KEY_NEWS_LAST_CATEGORY_ID,String.valueOf(spr_category_news.getSelectedItemId()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        PD.hide();
        swipeRefreshLayout.setRefreshing(false);
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getContext().getApplicationContext(), getContext().getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                    spr_category_news.setSelection(lastSelectedIndex);
                }
            } else if (arg instanceof ArrayList) {

                if (((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_RSS_NEWS_GET)) {
                    // amir - here you have the latest news list
                    lastSelectedIndex = spr_category_news.getSelectedItemPosition();
                    list_of_news = (ArrayList<NewsModel>) ((ArrayList) arg).get(1);
                    fillList();
                } else {
                    Utility.displayToast(getContext().getApplicationContext(), getString(R.string.msg_MessageNotSpecified), Toast.LENGTH_SHORT);
                    spr_category_news.setSelection(lastSelectedIndex);
                }

            } else if (arg instanceof Integer) {

                Utility.displayToast(getContext().getApplicationContext(), new RequestRespond(getContext()).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
                spr_category_news.setSelection(lastSelectedIndex);

            } else {
                Utility.displayToast(getContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                spr_category_news.setSelection(lastSelectedIndex);
            }
        } else {
            Utility.displayToast(getContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            spr_category_news.setSelection(lastSelectedIndex);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
//        if(Utility.isServerAvailable(getActivity().getApplicationContext(),"http://www.parseek.com"))

        super.onResume();
    }

    //add items to spinner
    public void addItemsOnSpinner_categories() {
        List<String> list = new ArrayList<String>();
        list.add("آخرین خبرها");
        list.add("ایران");
        list.add("جهان");
        list.add("اقتصادی");
        list.add("علم و فناوری");
        list.add("فناوری اطلاعات");
        list.add("هنر");
        list.add("فرهنگ و جامعه");
        list.add("ورزش");
        list.add("حوادث");
        list.add("متفرقه");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_category_news.setAdapter(dataAdapter);
    }

    //
    private void fillList() {
        lsv_full_screen_news.setAdapter(null);
        CALM = new CustomAdapterList_news(getActivity().getApplicationContext(),
                new ArrayList<Object>(list_of_news)
                , null);

        lsv_full_screen_news.setAdapter(CALM);
        lsv_full_screen_news.invalidateViews();
    }

//    // Our handler for received Intents. This will be called whenever an Intent
//// with an action named "custom-event-name" is broadcasted.
//    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//
//            if (intent.getStringExtra("timerTick") != null) {
//
//                CALM.notifyDataSetChanged();
//            }
//        }
//
//    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        rc.deleteObservers();

        //meysam - added in 13970318
        if (customHandler != null) {
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;
            timeWaitBeforeRefresh = 60000;
        }
        ///////////////////////////////

//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMeysamBroadcastReceiver);

        if(PD != null)
        {
            PD.dismiss();
            PD = null;
        }
        super.onDestroy();
    }
}
