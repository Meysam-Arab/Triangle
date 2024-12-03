package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.dropdownview.DropDownView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import ir.fardan7eghlim.tri.Controllers.PaymentController;
import ir.fardan7eghlim.tri.Models.PaymentModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.AppConfig;
import ir.fardan7eghlim.tri.Utils.ProgressDialog;
import ir.fardan7eghlim.tri.Utils.RequestRespond;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChargeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChargeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChargeFragment extends BaseFragment implements Observer {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AutoCompleteTextView edt_phone_for_charge;

    private OnFragmentInteractionListener mListener;

    private DropDownView dropDownView;
    private ArrayList<String> charge_list;
    private LinearLayout ll_typed_prce_charge;
    private EditText edt_typed_price_for_charge;
    private LinearLayout ll_purch_charge;

    private TextView btn_rightel;
    private TextView btn_irancell;
    private TextView btn_hamrahaval;

    private PaymentController pc;

    private String operator;
    private String amount;

    private ProgressDialog PD;

    public ChargeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChargeFragment.
     */
    //  Rename and change types and number of parameters
    public static ChargeFragment newInstance(String param1, String param2) {
        ChargeFragment fragment = new ChargeFragment();
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

        pc = new PaymentController(getContext());
        pc.addObserver(this);

        PD = new ProgressDialog(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_charge, container, false);
    }

    //  Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        //phone
        edt_phone_for_charge=getActivity().findViewById(R.id.edt_phone_for_charge);
        ArrayAdapter adapter = new
                ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1,session.getStringList(SessionModel.KEY_CHARGE_RECENT_NUMBERS));

        edt_phone_for_charge.setAdapter(adapter);
        edt_phone_for_charge.setThreshold(1);

        edt_phone_for_charge.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(phone_validation(view,edt_phone_for_charge.getText().toString())){
                    edt_phone_for_charge.setBackgroundResource(R.drawable.rounded_edittext_focused_green);
                }else{
                    edt_phone_for_charge.setBackgroundResource(R.drawable.rounded_edittext_focused);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {

            }
        });

        //
        edt_typed_price_for_charge=getActivity().findViewById(R.id.edt_typed_price_for_charge);
        edt_typed_price_for_charge.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(edt_typed_price_for_charge.getText().toString().equals("")) {
                    ll_purch_charge.setVisibility(View.GONE);
                }else{
                    ll_purch_charge.setVisibility(View.VISIBLE);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {

            }
        });
        ll_purch_charge=getActivity().findViewById(R.id.ll_purch_charge);
        ll_purch_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // meysam - open web page for charge...

                if(edt_typed_price_for_charge.getText().toString() != null && amount == null)
                {
                    if(Integer.valueOf(edt_typed_price_for_charge.getText().toString()) < 1000)
                    {
                        Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_not_enough_amount),Toast.LENGTH_SHORT);

                    }
                    else
                    {
                        amount = edt_typed_price_for_charge.getText().toString();
                    }
                }
                if(amount != null)
                {

                    session.addToList(SessionModel.KEY_CHARGE_RECENT_NUMBERS, edt_phone_for_charge.getText().toString(),15);
//                    try {
//                        byte[] bytes = amount.getBytes();
//                        amount = new String (bytes, "UTF-8");
                        amount = Utility.faToEn(amount);
//                        amount = URLDecoder.decode(amount, "UTF-8");
                        String link = AppConfig.PaymentsPurchase + "/"+Utility.getDeviceCode(getContext().getApplicationContext())+ "/"+ edt_phone_for_charge.getText().toString()+"/"+amount+"/"+"1";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        startActivity(browserIntent);

//                        android.os.Process.killProcess(android.os.Process.myPid());
//                    } catch (UnsupportedEncodingException e) {
//                        Utility.displayToast(getContext().getApplicationContext(),getString(R.string.msg_OperationError),Toast.LENGTH_SHORT);
//                    }

                }
                else
                {
                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.msg_ChooseAmount),Toast.LENGTH_SHORT);
                }

            }
        });

        //list of prices
        ll_typed_prce_charge=getActivity().findViewById(R.id.ll_typed_prce_charge);
        charge_list=new ArrayList<>();
        dropDownView = getActivity().findViewById(R.id.dropdownview);
        dropDownView.setOnSelectionListener(new DropDownView.OnSelectionListener() {
            @Override
            public void onItemSelected(DropDownView view, int position) {
                amount = null;
                edt_typed_price_for_charge.setText("");
                if(charge_list.get(position).equals("مبلغ دلخواه خود را وارد نمایید")) {
                    Utility.displayToast(getContext(),charge_list.get(position)+"",Toast.LENGTH_SHORT);

                    ll_typed_prce_charge.setVisibility(View.VISIBLE);
                    if(edt_typed_price_for_charge.getText().toString().equals("")) {
                        ll_purch_charge.setVisibility(View.GONE);
                    }else{
                        ll_purch_charge.setVisibility(View.VISIBLE);
                    }
                }else{
                    ll_typed_prce_charge.setVisibility(View.GONE);
                    ll_purch_charge.setVisibility(View.VISIBLE);
                    amount = charge_list.get(position).substring(0,charge_list.get(position).indexOf(' ')).toString();
                }
            }
        });

        //buttons
        btn_rightel=getActivity().findViewById(R.id.btn_rightel);
        btn_irancell=getActivity().findViewById(R.id.btn_irancell);
        btn_hamrahaval=getActivity().findViewById(R.id.btn_hamrahaval);
        btn_rightel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_validation(view,edt_phone_for_charge.getText().toString())){

                    PD.show();

                    charge_list.clear();
                    dropDownView.setVisibility(View.GONE);
                    operator = PaymentModel.OPERATOR_RIGHTEL;
                    pc.list(PaymentModel.OPERATOR_RIGHTEL);
                }else{
                    Utility.displayToast(getContext(),getString(R.string.error_invalid_phone_number),Toast.LENGTH_SHORT);

                }
            }
        });
        btn_irancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_validation(view,edt_phone_for_charge.getText().toString())){

                    PD.show();

                    charge_list.clear();
                    dropDownView.setVisibility(View.GONE);
                    operator = PaymentModel.OPERATOR_IRANCELL;
                    pc.list(PaymentModel.OPERATOR_IRANCELL);
                }else{
                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_invalid_phone_number),Toast.LENGTH_SHORT);

                }
            }
        });
        btn_hamrahaval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone_validation(view,edt_phone_for_charge.getText().toString())){

                    PD.show();

                    charge_list.clear();
                    dropDownView.setVisibility(View.GONE);
                    operator = PaymentModel.OPERATOR_HAMRAH_AVAL;
                    pc.list(PaymentModel.OPERATOR_HAMRAH_AVAL);
                }else{
                    Utility.displayToast(getContext(),getString(R.string.error_invalid_phone_number),Toast.LENGTH_SHORT);

                }
            }
        });
    }

    private boolean phone_validation(View view,String phone) {
        if(phone.length()!=11)
            return false;
        if(!phone.substring(0,2).equals("09"))
            return false;
        hideSoftKey(view);
        return true;
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
        if(arg != null)
        {
            if (arg instanceof Boolean)
            {
                if(Boolean.parseBoolean(arg.toString()) == false )
                {
                    Utility.displayToast(getContext(),getContext().getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
            }
            else if(arg instanceof ArrayList)
            {
                if(((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_PAYMENTS_LIST))
                {
                    ArrayList<String> charges =(ArrayList<String>) ((ArrayList) arg).get(1);
                    // amir - charges is what you want ...
                    for(String charge:charges){
                        if(!charge.equals("مبلغ دلخواه خود را وارد نمایید")) {
                            charge_list.add(Utility.enToFa(charge) + " تومان");
                        }
                        else{
                            charge_list.add(charge);
                        }
                    }

                    dropDownView.setDropDownListItem(charge_list);
                    dropDownView.setVisibility(View.VISIBLE);
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
    private void hideSoftKey(View view){
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        if(PD != null)
        {
            PD.dismiss();
            PD = null;
        }
        super.onDestroy();
    }
}
