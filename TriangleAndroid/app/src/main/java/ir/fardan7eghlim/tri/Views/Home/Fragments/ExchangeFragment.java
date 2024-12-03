package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Controllers.CoinController;
import ir.fardan7eghlim.tri.Controllers.CurrencyController;
import ir.fardan7eghlim.tri.Models.CoinModel;
import ir.fardan7eghlim.tri.Models.CurrencyModel;
import ir.fardan7eghlim.tri.Models.DayModel;
import ir.fardan7eghlim.tri.Models.PriceModel;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.WeatherModel;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.RequestRespond;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_contact;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_exchange_item;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExchangeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExchangeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExchangeFragment extends BaseFragment implements Observer {
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv_list_view;
    private CustomAdapterList_exchange_item CALM;
    private ArrayList<CurrencyModel> CurrencyModel_list= new ArrayList<CurrencyModel>();
    private ArrayList<CoinModel> CoinModel_list= new ArrayList<CoinModel>();
    private boolean isCurrency=true;

    private TextView lastUpdate;

    private OnFragmentInteractionListener mListener;

    private CurrencyController cuc;
    private CoinController coc;

    private Boolean inCreating;

    private Boolean inCoin;
    private Boolean inCurrency;

    TextView btn_coin;
    FrameLayout btn_refresh;
    TextView btn_currancy;

    public ExchangeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExchangeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExchangeFragment newInstance(String param1, String param2) {
        ExchangeFragment fragment = new ExchangeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        cuc = new CurrencyController(getContext());
        coc = new CoinController(getContext());

        cuc.addObserver(this);
        coc.addObserver(this);

        inCreating = true;

        inCoin = false;
        inCurrency = true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false);
    }

    // Rename method, update argument and hook method into UI event
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
    public void update(Observable o, Object arg)
    {
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getContext().getApplicationContext(),getContext().getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_CURRENCY_GET))
                {
                    PriceModel price= (PriceModel) ((ArrayList) arg).get(1);
                    db.deleteCurrencies();
                    db.addCurrencies(price.getCurrencies(),true);
                    price.setPriceTag(PriceModel.TAG_CURRENCY);
                    db.deletePriceByTag(PriceModel.TAG_CURRENCY);
                    db.addPrice(price);
                    // amir - price is what you want ...
                    CurrencyModel_list=price.getCurrencies();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(price.getPriceTime()))))));
                    fillList(true);
                    ///////////////////////////////////////////////////////
                }
                if(((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_COIN_GET))
                {
                    PriceModel price= (PriceModel) ((ArrayList) arg).get(1);
                    db.deleteCoins();
                    db.addCoins(price.getCoins(),true);
                    price.setPriceTag(PriceModel.TAG_COIN);
                    db.deletePriceByTag(PriceModel.TAG_COIN);
                    db.addPrice(price);
                    // amir - price is what you want ...
                    CoinModel_list=price.getCoins();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(price.getPriceTime()))))));
                    fillList(false);
                    ///////////////////////////////////////////////////////
                }
                else
                {
                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.msg_MessageNotSpecified), Toast.LENGTH_SHORT);
                }

            }
            else if(arg instanceof Integer)
            {

                Utility.displayToast(getContext().getApplicationContext(),new RequestRespond(getContext()).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);

            }
            else
            {
                Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
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

    //fill list
    private void fillList(boolean isCurrency) {
        lv_list_view=getActivity().findViewById(R.id.lv_list_view);
        lv_list_view.setAdapter(null);
        if(isCurrency) {

                CALM = new CustomAdapterList_exchange_item(getActivity().getApplicationContext(),
                        new ArrayList<Object>(CurrencyModel_list)
                        , RequestRespond.TAG_CURRENCY_GET);


        }else{

                CALM = new CustomAdapterList_exchange_item(getActivity().getApplicationContext(),
                        new ArrayList<Object>(CoinModel_list)
                        , RequestRespond.TAG_COIN_GET);

        }
        lv_list_view.setAdapter(CALM);
        lv_list_view.invalidateViews();
    }

    //meysam - refill without clearing olds...
    private void reFillList(boolean isCurrency) {

        if(isCurrency)
            CALM.updateAdapter(new ArrayList<Object>(CurrencyModel_list));
        else
            CALM.updateAdapter(new ArrayList<Object>(CoinModel_list));
        synchronized(lv_list_view.getAdapter()){
            lv_list_view.getAdapter().notify();
        }
        CALM.notifyDataSetChanged();
    }

    @Override
    public void onResume() {


        if(inCreating)
        {

            lastUpdate = getActivity().findViewById(R.id.txt_last_update_exchange);
            lastUpdate.setText("???");
            ArrayList<CurrencyModel> currencies = db.getCurrencies();
            PriceModel p_currency = db.getPriceByTag(PriceModel.TAG_CURRENCY);
            p_currency.setCurrencies(currencies);
            CurrencyModel_list=p_currency.getCurrencies();
            if(p_currency.getCurrencies().size() > 0)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(p_currency.getPriceTime()))))));
                fillList(true);
            }

            inCreating = false;
            inCoin = false;
            inCurrency = true;
            cuc.get();

            btn_coin=getActivity().findViewById(R.id.btn_coin);
            btn_refresh=getActivity().findViewById(R.id.btn_exchange_refresh);
            btn_currancy=getActivity().findViewById(R.id.btn_currancy);

            btn_coin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // meysam - call when clicking on coin tab
                    if(!inCoin)
                    {
                        ArrayList<CoinModel> coins = db.getCoins();
                        PriceModel p_coin = db.getPriceByTag(PriceModel.TAG_COIN);
                        p_coin.setCoins(coins);
                        CoinModel_list=p_coin.getCoins();
                        if(p_coin.getCoins().size() > 0)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(p_coin.getPriceTime()))))));
                            fillList(false);
                        }

                        inCoin = true;
                        inCurrency = false;
                        coc.get();
                    }
                }
            });
            btn_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inCoin)
                    {
//                        ArrayList<CoinModel> coins = db.getCoins();
//                        PriceModel p_coin = db.getPriceByTag(PriceModel.TAG_COIN);
//                        p_coin.setCoins(coins);
//                        CoinModel_list=p_coin.getCoins();
//                        if(p_coin.getCoins().size() > 0)
//                        {
////                            fillList(false);
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(p_coin.getPriceTime()))))));
//                            reFillList(true);
//                        }

                        coc.get();
                    }
                    if(inCurrency)
                    {
//                        ArrayList<CurrencyModel> currencies = db.getCurrencies();
//                        PriceModel p_currency = db.getPriceByTag(PriceModel.TAG_CURRENCY);
//                        p_currency.setCurrencies(currencies);
//                        CurrencyModel_list=p_currency.getCurrencies();
//                        if(p_currency.getCurrencies().size() > 0)
//                        {
////                            fillList(true);
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(p_currency.getPriceTime()))))));
//                            reFillList(true);
//                        }


                        cuc.get();
                    }
                }
            });
            btn_currancy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!inCurrency)
                    {
                        ArrayList<CurrencyModel> currencies = db.getCurrencies();
                        PriceModel p_currency = db.getPriceByTag(PriceModel.TAG_CURRENCY);
                        p_currency.setCurrencies(currencies);
                        CurrencyModel_list=p_currency.getCurrencies();
                        if(p_currency.getCurrencies().size() > 0)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            lastUpdate.setText(Utility.enToFa(Utility.convertDateTimeGorgeian2Persian(sdf.format(new Date(Long.valueOf(p_currency.getPriceTime()))))));
                            fillList(true);
                        }


                        inCoin = false;
                        inCurrency = true;
                        cuc.get();
                    }
                }
            });
        }

        super.onResume();
    }
}
