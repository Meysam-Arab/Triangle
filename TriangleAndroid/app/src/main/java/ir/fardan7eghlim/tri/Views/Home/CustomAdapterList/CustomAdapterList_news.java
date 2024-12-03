package ir.fardan7eghlim.tri.Views.Home.CustomAdapterList;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Models.NewsModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class CustomAdapterList_news extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_news CAL = this;
    private Object foregn_key_obj;

    public CustomAdapterList_news(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List = list;
        Tag = tag;
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CustomAdapterList_news(Context c, java.util.List<Object> list, String tag, Object obj) {
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
        TextView txt_news_title;
        TextView txt_news_description;
        LinearLayout ll_news_box;
        TextView txt_news_time;

        public Holder(TextView txt_news_title, TextView txt_news_description, LinearLayout ll_news_box, TextView txt_news_time) {
            this.txt_news_title = txt_news_title;
            this.txt_news_description = txt_news_description;
            this.ll_news_box = ll_news_box;
            this.txt_news_time=txt_news_time;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()),  new LinearLayout(context.getApplicationContext()),  new TextView(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.custom_adapter_news, null);
        holder.txt_news_title = rowView.findViewById(R.id.txt_news_title);
        holder.txt_news_description = rowView.findViewById(R.id.txt_news_description);
        holder.ll_news_box = rowView.findViewById(R.id.ll_news_box);
        holder.txt_news_time = rowView.findViewById(R.id.txt_news_time);
        //sets
        final NewsModel news = (NewsModel) List.get(position);
        holder.txt_news_title.setText(news.getNewsTitle());
        holder.txt_news_description.setText(news.getNewsDescription());
        //meysam:convert date to persian
        holder.txt_news_time.setText(Utility.getTimeDifferenceWithCurrentTime(news.getNewsPublicationDate()));
        holder.ll_news_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.goToUrl(context,news.getNewsLink());
            }
        });

        //return
        return rowView;
    }


    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_news.inflater = inflater;
    }

    private void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }
    private boolean canAccessCallPhone() {
        return (hasPermission(Manifest.permission.CALL_PHONE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkCallingOrSelfPermission(context,perm));
    }

}