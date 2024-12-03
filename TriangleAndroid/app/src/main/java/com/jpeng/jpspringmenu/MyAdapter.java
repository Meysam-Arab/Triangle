package com.jpeng.jpspringmenu;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.ui.Util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ir.fardan7eghlim.tri.Models.LogModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
//import ir.fardan7eghlim.tri.Services.TimerService;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;
import ir.fardan7eghlim.tri.Views.Home.OverlayButton;
import ir.fardan7eghlim.tri.Views.Home.SettingActivity;
import ir.fardan7eghlim.tri.Views.Note.IndexNoteActivity;
import ir.fardan7eghlim.tri.Views.help.FirstPageActivity;
import ir.fardan7eghlim.tri.Views.help.UsActivity;

/**
 * Author: jpeng
 * Date: 17-9-12 下午6:50
 * E-mail:peng8350@gmail.com
 * Description:
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private ListBean[] mDatas;

    public MyAdapter(Context context, ListBean[] mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_text);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mDatas[position].getTitle());
        holder.iv.setImageResource(mDatas[position].getResource());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,mDatas[position].getTitle()+"被点击!!!",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                        Intent intent = new Intent(context, FirstPageActivity.class);
                        context.startActivity(intent);
                        break;
                    case 1:
                        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                        intent = new Intent(context, SettingActivity.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        //meysam - bazzar stars
                        if(Utility.isPackageInstalled(context.getApplicationContext(),"com.farsitel.bazaar"))
                        {
                            rate();
                        }
                        else
                        {
                            Utility.displayToast(context.getApplicationContext(),"برنام کافه بازار نصب نمی باشد",Toast.LENGTH_SHORT);
                        }

                        break;
                    case 3:
                        //meysam -share app
                        share();
                        break;
                    case 4:
                        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                        intent = new Intent(context, UsActivity.class);
                        context.startActivity(intent);
                        break;
                    default:
                        //exit app
                        context.stopService(new Intent(context, OverlayButton.class));
                        //meysam - commented in 13970318
//                        context.stopService(new Intent(context, TimerService.class));

                        //meysam - added in 13970318
                        TriiiBroadcastReceiver.hideNotification(context);
                        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_STARTED,false);
                        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_CLOSED,true);
                        ///////////////////////////////
                        TriiiBroadcastReceiver.cancelAllBroadcasts(context);

                        System.exit(0);
                        break;

                }

            }
        });
        return convertView;
    }

    private void share()
    {
        try {

            Intent sharingIntent = new Intent();
            sharingIntent.setAction(Intent.ACTION_SEND);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.ShareSubject));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, context.getString(R.string.ShareBody));
            sharingIntent.setType("text/plain");
            context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.ShareVia)));


        } catch (ActivityNotFoundException ex) {

            //on catch, show the download dialog
            Utility.displayToast(context, "error", Toast.LENGTH_LONG);
            LogModel log = new LogModel();
            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
            log.setContollerName(this.getClass().getName());
            log.insert();
        }
    }

    public void rate() {
        try {

            String PACKAGE_NAME = context.getApplicationContext().getPackageName();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(Uri.parse("bazaar://details?id=" + PACKAGE_NAME));
            intent.setPackage("com.farsitel.bazaar");
            context.startActivity(intent);


        } catch (ActivityNotFoundException ex) {

            //on catch, show the download dialog
            Utility.displayToast(context, "error", Toast.LENGTH_LONG);
            LogModel log = new LogModel();
            log.setErrorMessage("message: " + ex.getMessage() + " CallStack: " + ex.getStackTrace());
            log.setContollerName(this.getClass().getName());
            log.insert();
        }
    }

    static class ViewHolder {
        TextView tv;
        ImageView iv;
    }
}
