package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.io.IOException;
import java.util.ArrayList;

import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_contact;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialerFragment extends BaseFragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView lv_list_contacts;
    private ArrayList<Contact> contacts_list = new ArrayList<Contact>();
    private ArrayList<Contact> fav_contacts_list = new ArrayList<Contact>();
    private ArrayList<Contact> search_contacts_list = new ArrayList<Contact>();
    boolean canSearch = false;
    private Cursor phones;
    private ContentResolver resolver;
    private EditText edt_contcact_search;
    private CustomAdapterList_contact CALM;
    private ImageView btn_add_contact;
    private ImageView btn_dialer_contact;
    private FrameLayout fl_contact_loading;

    private static final int INITIAL_REQUEST_CALL_PHONE = 1338;
    private static final int INITIAL_REQUEST_READ_CONTACTS = 1339;
    private String[] INITIAL_PERMS_CALL_PHONE = {Manifest.permission.CALL_PHONE};
    private String[] INITIAL_PERMS_READ_CONTACTS = {Manifest.permission.READ_CONTACTS};

    private String phoneToDial;

    private ViewGroup header;
    private ViewGroup footer;


    private OnFragmentInteractionListener mListener;

    public DialerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialerFragment.
     */
    //  Rename and change types and number of parameters
    public static DialerFragment newInstance(String param1, String param2) {
        DialerFragment fragment = new DialerFragment();
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
        resolver = getActivity().getApplicationContext().getContentResolver();

        if (MainActivity.PD != null)
            MainActivity.PD.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //header and footer
        lv_list_contacts = getView().findViewById(R.id.lv_list_contacts);
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.fav_contact_header, lv_list_contacts, false);
        lv_list_contacts.addHeaderView(header, null, false);
        footer = (ViewGroup) inflater.inflate(R.layout.fav_contact_footer, lv_list_contacts, false);
        lv_list_contacts.addFooterView(footer, null, false);
        LinearLayout btn_on_footer = footer.findViewById(R.id.btn_on_footer);
        btn_on_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillList();
            }
        });
        //dialer button
        btn_dialer_contact = getActivity().findViewById(R.id.btn_dialer_contact);
        btn_dialer_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = edt_contcact_search.getText().toString();
                if (!temp.equals("") && isPhoneNumber(temp)) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        // Marshmallow+
                        if (!canAccessCallPhone()) {
                            phoneToDial = temp;
                            requestPermissions(INITIAL_PERMS_CALL_PHONE, INITIAL_REQUEST_CALL_PHONE);
                            return;
                        } else {
                            dialContactPhone(temp);
                        }
                    } else {
                        // Pre-Marshmallow
                        dialContactPhone(temp);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    startActivity(intent);
                }
            }
        });
        //search box
        edt_contcact_search = getActivity().findViewById(R.id.edt_contcact_search);
        edt_contcact_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!s.toString().equals("") && isPhoneNumber(String.valueOf(s))) {
                    btn_dialer_contact.setImageResource(R.drawable.btn_icon_call);
                    searchingPhones(s);
                } else {
                    btn_dialer_contact.setImageResource(R.drawable.dialer_contact);
                    searchingContacts(s);
                }
                if (s.toString().equals("")) {
                    fav_fillList();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });
        //get list of contacts
//        phones = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");


        //add new contact
        btn_add_contact = getActivity().findViewById(R.id.btn_add_contact);
        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = edt_contcact_search.getText().toString();
                Intent intent = new Intent(Intent.ACTION_INSERT,
                        ContactsContract.Contacts.CONTENT_URI);
                if (!temp.equals("")) {
                    if (isPhoneNumber(temp)) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, temp.toString());
                    } else {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, temp.toString());
                    }
//                    intent.putExtra(ContactsContract.Contacts.STARRED, true);
                }
                startActivity(intent);
            }
        });


        if (Build.VERSION.SDK_INT >= 23) {
            Toast.makeText(getContext(), "در چک اجازه دیالر", Toast.LENGTH_SHORT).show();

            // Marshmallow+
            if (!canAccessReadContacts()) {
                Toast.makeText(getContext(), "درخواست اجازه دیالر", Toast.LENGTH_SHORT).show();

                requestPermissions(INITIAL_PERMS_READ_CONTACTS, INITIAL_REQUEST_READ_CONTACTS);
//                return;
            } else {
                Toast.makeText(getContext(), "اجازه دیالر لازم نیست", Toast.LENGTH_SHORT).show();

                phones = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Contacts.STARRED, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");
                LoadFavContact LoadFavContact = new LoadFavContact();
                LoadFavContact.execute();
            }
        } else {
            // Pre-Marshmallow
            phones = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Contacts.STARRED, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");
            LoadFavContact LoadFavContact = new LoadFavContact();
            LoadFavContact.execute();
        }
    }

    private boolean isPhoneNumber(String str) {
        return str.matches("[0-9]+") || (str.startsWith("+") && str.substring(1).matches("[0-9]+")) || str.startsWith("*");
    }

    private void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //  Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getActivity().startActivity(intent);
    }

    private void searchingContacts(CharSequence s) {
        if(!canSearch) return;
        search_contacts_list.clear();
        for(Contact contact:contacts_list){
            if(contact.getName().toLowerCase().contains(s.toString().toLowerCase())){
                search_contacts_list.add(contact);
            }
        }
        CALM.updateAdapter(new ArrayList<Object>(search_contacts_list));
        TextView txt_fav_header=header.findViewById(R.id.txt_fav_header);
        txt_fav_header.setText("نتایج جست و جو");
        if(lv_list_contacts.getFooterViewsCount()==0)
            lv_list_contacts.addFooterView(footer);
    }
    private void searchingPhones(CharSequence s) {
        if(!canSearch) return;
        search_contacts_list.clear();
        for(Contact contact:contacts_list){
            if((contact.getPhone()+"").toLowerCase().contains(s.toString().toLowerCase())){
                search_contacts_list.add(contact);
            }
        }
        CALM.updateAdapter(new ArrayList<Object>(search_contacts_list));
        TextView txt_fav_header=header.findViewById(R.id.txt_fav_header);
        txt_fav_header.setText("نتایج جست و جو");
        if(lv_list_contacts.getFooterViewsCount()==0)
            lv_list_contacts.addFooterView(footer);
    }

    //  Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (phones != null) {
                if (phones.getCount() == 0) {
                    //no contact found
                }
                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Contact contact_temp=getContactFromName(name);
                    if(contact_temp==null){
                        ArrayList<String> phones=new ArrayList<>();
                        phones.add(phoneNumber);
                        Contact contact=new Contact(id,name,phones,bit_thumb);
                        contacts_list.add(contact);
                    }else{
                        contact_temp.addPhone(phoneNumber);
                    }
                }
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            fillList();
            canSearch=true;
        }
    }
    class LoadFavContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (phones != null) {
                if (phones.getCount() == 0) {
                    //no contact found
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Contact contact_temp=getFavContactFromName(name);
                    if(contact_temp==null){
                        ArrayList<String> phones=new ArrayList<>();
                        phones.add(phoneNumber);
                        Contact contact=new Contact(id,name,phones,bit_thumb);
                        fav_contacts_list.add(contact);
                    }else{
                        contact_temp.addPhone(phoneNumber);
                    }
                }
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fav_fillList();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //get list of contacts
        if(!edt_contcact_search.getText().toString().equals("")) {
            if(!isPhoneNumber(edt_contcact_search.getText().toString()))
                searchingContacts(edt_contcact_search.getText().toString());
            else
                searchingPhones(edt_contcact_search.getText().toString());
        }
    }

    //fill list of contacts
    private void fav_fillList() {
        lv_list_contacts.setAdapter(null);

        if(getContext() != null)
        {
            CALM=new CustomAdapterList_contact(getContext().getApplicationContext(),
                    new ArrayList<Object>(fav_contacts_list)
                    , null);
            lv_list_contacts.setAdapter(CALM);
            lv_list_contacts.invalidateViews();
            if(lv_list_contacts.getFooterViewsCount()==0)
                lv_list_contacts.addFooterView(footer);
            phones = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact LoadContact =new LoadContact();
            LoadContact.execute();
        }

        TextView txt_fav_header=header.findViewById(R.id.txt_fav_header);
        txt_fav_header.setText("مخاطبین مورد علاقه");
    }
    private void fillList() {
        lv_list_contacts.setAdapter(null);
        CALM=new CustomAdapterList_contact(getActivity().getApplicationContext(),
                new ArrayList<Object>(contacts_list)
                , null);
        lv_list_contacts.setAdapter(CALM);
        lv_list_contacts.invalidateViews();
        lv_list_contacts.removeFooterView(footer);
        TextView txt_fav_header=header.findViewById(R.id.txt_fav_header);
        txt_fav_header.setText("همه ی مخاطبین");
        //hide loading
//        loading(false);
    }
    public Contact getFavContactFromName(String name){
        Contact contact=null;
        for(Contact c:fav_contacts_list){
            if(c.getName().equals(name)){
                contact=c;
                break;
            }
        }
        return contact;
    }
    public Contact getContactFromName(String name){
        Contact contact=null;
        for(Contact c:contacts_list){
            if(c.getName().equals(name)){
                contact=c;
                break;
            }
        }
        return contact;
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
        // Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Toast.makeText(getContext(), "در جواب اجازه دیالر", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == INITIAL_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Permission Granted
                dialContactPhone(phoneToDial);
                phoneToDial = null;
            }
            else
            {
                // Permission Denied

                if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                    //user denied the permission but did not check the "never show again" option.
                    //You can ask for the permission again or show a dialog explaining
                    //why you need the permission with a button that requests the permission again on click.
                    Utility.displayToast(getContext().getApplicationContext()," دکمه هرگز نشون نده زده شده", Toast.LENGTH_SHORT);

                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_permission_access), Toast.LENGTH_SHORT);
                }
                else{
                    //user denied the permission and checked the "never show again" option.
                    //Here you can show a dialog explaining the situation and that the user has
                    //to go to the app settings and allow the permission otherwise yor feature
                    //will not be available.
                    Utility.displayToast(getContext().getApplicationContext()," دکمه هرگز نشون نده زده نشده", Toast.LENGTH_SHORT);

                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_permission_access), Toast.LENGTH_SHORT);

                }
            }
        }
        if(requestCode == INITIAL_REQUEST_READ_CONTACTS)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Permission Granted
                phones = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Contacts.STARRED, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");
                LoadFavContact LoadFavContact =new LoadFavContact();
                LoadFavContact.execute();

            }
            else
            {
                // Permission Denied
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    //user denied the permission but did not check the "never show again" option.
                    //You can ask for the permission again or show a dialog explaining
                    //why you need the permission with a button that requests the permission again on click.
                    Utility.displayToast(getContext().getApplicationContext()," دکمه هرگز نشون نده زده شده", Toast.LENGTH_SHORT);

                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_permission_access), Toast.LENGTH_SHORT);
                                    }
                else{
                    //user denied the permission and checked the "never show again" option.
                    //Here you can show a dialog explaining the situation and that the user has
                    //to go to the app settings and allow the permission otherwise yor feature
                    //will not be available.
                    Utility.displayToast(getContext().getApplicationContext()," دکمه هرگز نشون نده زده نشده", Toast.LENGTH_SHORT);

                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_permission_access), Toast.LENGTH_SHORT);
                }

            }
        }

    }

    private boolean canAccessCallPhone() {
        return (hasPermission(Manifest.permission.CALL_PHONE));
    }

    private boolean canAccessReadContacts() {
        return (hasPermission(Manifest.permission.READ_CONTACTS));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkCallingOrSelfPermission(getContext(),perm));
    }



//    //other function :: still not used
//    public void getCallLog() {
//        String[] callLogFields = {android.provider.CallLog.Calls._ID,
//                android.provider.CallLog.Calls.NUMBER,
//                android.provider.CallLog.Calls.CACHED_NAME /* im not using the name but you can*/};
//        String viaOrder = android.provider.CallLog.Calls.DATE + " DESC";
//        String WHERE = android.provider.CallLog.Calls.NUMBER + " >0"; /*filter out private/unknown numbers */
//
//        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        final Cursor callLog_cursor = getActivity().getContentResolver().query(
//                android.provider.CallLog.Calls.CONTENT_URI, callLogFields,
//                WHERE, null, viaOrder);
//
//        AlertDialog.Builder myversionOfCallLog = new AlertDialog.Builder(
//                getActivity());
//
//        android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int item) {
//                callLog_cursor.moveToPosition(item);
//
//                Log.v("number", callLog_cursor.getString(callLog_cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER)));
//
//                callLog_cursor.close();
//
//            }
//        };
//
//
//        myversionOfCallLog.setCursor(callLog_cursor, listener,
//                android.provider.CallLog.Calls.NUMBER);
//        myversionOfCallLog.setTitle("Choose from Call Log");
//        myversionOfCallLog.create().show();
//    }
}
