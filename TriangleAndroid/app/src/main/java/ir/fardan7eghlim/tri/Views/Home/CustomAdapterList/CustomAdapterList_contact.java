package ir.fardan7eghlim.tri.Views.Home.CustomAdapterList;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class CustomAdapterList_contact extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_contact CAL = this;
    private Object foregn_key_obj;

    public CustomAdapterList_contact(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List = list;
        Tag = tag;
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CustomAdapterList_contact(Context c, java.util.List<Object> list, String tag, Object obj) {
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
        TextView txt_ca_Name;
        ImageView img_ca_avatar;
        LinearLayout ll_ca_box;
        ImageView img_ca_call;

        public Holder(LinearLayout ll_ca_box, TextView txt_ca_Name, ImageView img_ca_avatar, ImageView img_ca_call) {
            this.ll_ca_box = ll_ca_box;
            this.txt_ca_Name = txt_ca_Name;
            this.img_ca_avatar = img_ca_avatar;
            this.img_ca_call=img_ca_call;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new LinearLayout(context.getApplicationContext()), new TextView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.custom_adapter_contact, null);
        holder.ll_ca_box = rowView.findViewById(R.id.ll_ca_box);
        holder.txt_ca_Name = rowView.findViewById(R.id.txt_ca_Name);
        holder.img_ca_avatar = rowView.findViewById(R.id.img_ca_avatar);
        holder.img_ca_call = rowView.findViewById(R.id.img_ca_call);
        //sets
        final Contact contact = (Contact) List.get(position);
        holder.txt_ca_Name.setText(contact.getName());
        String temp = "";
        for (int i = 1; i <= contact.getPhone().size(); i++) {
            temp += contact.getPhone().get(i - 1);
            if (i != contact.getPhone().size())
                temp += "\n";
        }

        if (contact.getAvatar() != null)
            holder.img_ca_avatar.setImageBitmap(contact.getAvatar());
        else
            holder.img_ca_avatar.setImageResource(R.drawable.empty_contact);

        holder.ll_ca_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,
                        String.valueOf(contact.getId()));
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        //call button
        holder.img_ca_call.setImageResource(R.drawable.btn_icon_call);
        holder.img_ca_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    // Marshmallow+
                    if (!canAccessCallPhone())
                    {
                        Utility.displayToast(context.getApplicationContext(),context.getString(R.string.error_permission_access), Toast.LENGTH_SHORT);
                        return;
                    }
                }
                else
                {
                    // Pre-Marshmallow
                    dialContactPhone(contact.getPhone().get(0));
                }
            }
        });
        //return
        return rowView;
    }


    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_contact.inflater = inflater;
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