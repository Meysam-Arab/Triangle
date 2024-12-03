package ir.fardan7eghlim.tri.Views.Home.CustomAdapterList;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Models.CoinModel;
import ir.fardan7eghlim.tri.Models.CurrencyModel;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.RequestRespond;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;

public class CustomAdapterList_exchange_item extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_exchange_item CAL = this;
    private Object foregn_key_obj;

    public CustomAdapterList_exchange_item(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List = list;
        Tag = tag;
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomAdapterList_exchange_item(Context c, java.util.List<Object> list, String tag, Object obj) {
        // TODO Auto-generated constructor stub
        List = list;
        Tag = tag;
        context = c;
        foregn_key_obj = obj;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

//    public void updateAdapter(java.util.List<Object> list) {
//        this.List = list;
//        //and call notifyDataSetChanged
//        notifyDataSetChanged();
//    }
    public void updateAdapter(java.util.List<Object> newlist) {
        List.clear();
        List.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public class Holder {
        TextView txt_exchange_title;
        TextView txt_exchange_price;
        TextView txt_exchange_change;
        ImageView img_exchange_avatar;
        ImageView img_exchange_change_logo;

        public Holder(TextView txt_exchange_title,TextView txt_exchange_price,TextView txt_exchange_change, ImageView img_exchange_avatar, ImageView img_exchange_change_logo) {
            this.txt_exchange_title = txt_exchange_title;
            this.txt_exchange_price = txt_exchange_price;
            this.txt_exchange_change = txt_exchange_change;
            this.img_exchange_avatar=img_exchange_avatar;
            this.img_exchange_change_logo=img_exchange_change_logo;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new TextView(context.getApplicationContext()),new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.custom_adapter_exchange, null);
        holder.txt_exchange_title = rowView.findViewById(R.id.txt_exchange_title);
        holder.txt_exchange_price = rowView.findViewById(R.id.txt_exchange_price);
        holder.txt_exchange_change = rowView.findViewById(R.id.txt_exchange_change);
        holder.img_exchange_avatar = rowView.findViewById(R.id.img_exchange_avatar);
        holder.img_exchange_change_logo = rowView.findViewById(R.id.img_exchange_change_logo);
        //sets
        switch (Tag){
            case RequestRespond.TAG_CURRENCY_GET:
                CurrencyModel currencyModel=(CurrencyModel) List.get(position);
                holder.txt_exchange_title.setText(currencyModel.getCurrencyName());
                holder.txt_exchange_price.setText(currencyModel.getCurrencyPrice());
                holder.txt_exchange_change.setText(currencyModel.getCurrencyPercent()+"%");
                if(currencyModel.getCurrencyChange().equals("+")){
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_increase);
                }else if(currencyModel.getCurrencyChange().equals("-")){
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_decrease);
                }else{
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_empty);
                }
                holder.img_exchange_avatar.setImageResource(getLogo4Currency(currencyModel.getCurrencyName()));
                break;
            case RequestRespond.TAG_COIN_GET:
                CoinModel coinModel =(CoinModel) List.get(position);
                holder.txt_exchange_title.setText(coinModel.getCoinName());
                holder.txt_exchange_price.setText(coinModel.getCoinPrice());
                holder.txt_exchange_change.setText(coinModel.getCoinPercent()+"%");
                if(coinModel.getCoinChange().equals("+")){
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_increase);
                }else if(coinModel.getCoinChange().equals("-")){
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_decrease);
                }else{
                    holder.img_exchange_change_logo.setImageResource(R.drawable.exchange_empty);
                }
                holder.img_exchange_avatar.setImageResource(getLogo4Coin(coinModel.getCoinName()));
                break;
        }
        //return
        return rowView;
    }

    private int getLogo4Coin(String coinName) {
        switch (coinName){
            case "سکه امامی":
                return R.drawable.xch_coin_emami;
            case "انس طلا":
                return R.drawable.xch_coin_tala;
            case "انس نقره":
                return R.drawable.xch_coin_noghre;
        }
        return R.drawable.xch_coin_azadi;
    }

    private int getLogo4Currency(String currencyName) {
        switch (currencyName){
            case "دلار":
                return R.drawable.xch_dollar;
            case "یورو":
                return R.drawable.xch_euro;
            case "درهم امارات":
                return R.drawable.xch_emarat;
            case "کرون سوئد":
                return R.drawable.xch_sooed;
            case "درام ارمنستان":
                return R.drawable.xch_armanestan;
            case "روپیه پاکستان":
                return R.drawable.xch_pakestan;
            case "ریال عربستان":
                return R.drawable.xch_arabestan;
            case "روپیه هند":
                return R.drawable.xch_hend;
            case "ریال قطر":
                return R.drawable.xch_ghatar;
            case "رینگیت مالزی":
                return R.drawable.xch_malezi;
            case "دلار نیوزیلند":
                return R.drawable.xch_newzland;
            case "دینار بحرین":
                return R.drawable.xch_bahreyn;
            case "دینار عراق":
                return R.drawable.xch_iraq;
            case "کرون نروژ":
                return R.drawable.xch_norvezh;
            case "فرانک سوئیس":
                return R.drawable.xch_soes;
            case "روبل روسیه":
                return R.drawable.xch_roosie;
            case "دینار کویت":
                return R.drawable.xch_koveit;
            case "دلار استرالیا":
                return R.drawable.xch_ostoralia;
            case "دلار کانادا":
                return R.drawable.xch_kanada;
            case "پوند انگلیس":
                return R.drawable.xch_englis;
            case "بات تایلند":
                return R.drawable.xch_tailand;
            case "لیر ترکیه":
                return R.drawable.xch_torkie;
            case "دلار هنگ کنگ":
                return R.drawable.xch_hongkong;
            case "منات آذربایجان":
                return R.drawable.xch_azarbaijan;
            case "کرون دانمارک":
                return R.drawable.xch_danmark;
            case "یوان چین":
                return R.drawable.xch_chin;
            case "ین ژاپن":
                return R.drawable.xch_jhapon;
            case "لیر سوریه":
                return R.drawable.xch_soorie;
        }
        return R.drawable.xch_dollar;
    }


//    public static LayoutInflater getInflater() {
//        return inflater;
//    }
//
//    public static void setInflater(LayoutInflater inflater) {
//        CustomAdapterList_exchange_item.inflater = inflater;
//    }
//
//    private void dialContactPhone(final String phoneNumber) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            //  Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        context.startActivity(intent);
//    }
}