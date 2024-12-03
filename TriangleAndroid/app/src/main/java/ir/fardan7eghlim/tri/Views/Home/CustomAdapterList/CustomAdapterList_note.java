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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Models.NoteModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.models.Contact;
import ir.fardan7eghlim.tri.Views.Note.InsertNoteActivity;
import ir.fardan7eghlim.tri.Views.Task.ShowTaskActivity;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class CustomAdapterList_note extends BaseAdapter implements Observer {

    private Context context;
    private String Tag;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_note CAL = this;
    private Object foregn_key_obj;

    public CustomAdapterList_note(Context c, java.util.List<Object> list, String tag) {
        // TODO Auto-generated constructor stub
        List = list;
        Tag = tag;
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CustomAdapterList_note(Context c, java.util.List<Object> list, String tag, Object obj) {
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
        TextView txt_ca_title_note;
        TextView txt_ca_date_note;
        TextView txt_ca_description_note;
        ImageView img_paperclip_note;
        FrameLayout ll_ca_box_note;

        public Holder(TextView txt_ca_title_note, TextView txt_ca_date_note, TextView txt_ca_description_note, ImageView img_paperclip_note,FrameLayout ll_ca_box_note) {
            this.txt_ca_title_note = txt_ca_title_note;
            this.txt_ca_date_note = txt_ca_date_note;
            this.txt_ca_description_note = txt_ca_description_note;
            this.img_paperclip_note=img_paperclip_note;
            this.ll_ca_box_note=ll_ca_box_note;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()),  new TextView(context.getApplicationContext()),  new ImageView(context.getApplicationContext()),  new FrameLayout(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.custom_adapter_note, null);
        holder.txt_ca_title_note = rowView.findViewById(R.id.txt_ca_title_note);
        holder.txt_ca_date_note = rowView.findViewById(R.id.txt_ca_date_note);
        holder.txt_ca_description_note = rowView.findViewById(R.id.txt_ca_description_note);
        holder.img_paperclip_note = rowView.findViewById(R.id.img_paperclip_note);
        holder.ll_ca_box_note = rowView.findViewById(R.id.ll_ca_box_note);
        //sets
        final NoteModel note = (NoteModel) List.get(position);
        holder.txt_ca_title_note.setText(note.getNoteTitle());
        holder.txt_ca_date_note.setText(note.getNoteDateTime());
        holder.txt_ca_description_note.setText(note.getNoteDescription());
        holder.ll_ca_box_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_note_dialog(note.getNoteId());
            }
        });
        //return
        return rowView;
    }
    private void detail_note_dialog(BigInteger note_id){
        Intent intent = new Intent(context, InsertNoteActivity.class);
        intent.putExtra("note_id", note_id+"");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_note.inflater = inflater;
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