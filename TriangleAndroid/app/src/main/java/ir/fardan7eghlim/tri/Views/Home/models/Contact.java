package ir.fardan7eghlim.tri.Views.Home.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Amir on 4/28/2018.
 */

public class Contact {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<String> phone) {
        this.phone = phone;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    private String name;
    private ArrayList<String> phone;
    private Bitmap avatar;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Contact(String name, ArrayList<String> phones) {
        this.name = name;
        this.phone=new ArrayList<>();
        for(String phone:phones)
            this.phone.add(phone.replaceAll("\\s+","").replaceAll("-",""));
        this.avatar=null;
        this.id=null;
    }

    public Contact(String id,String name, ArrayList<String> phones, Bitmap avatar) {

        this.name = name;
        this.phone=new ArrayList<>();
        for(String phone:phones)
            this.phone.add(phone.replaceAll("\\s+","").replaceAll("-",""));
        this.avatar = avatar;
        this.id=id;
    }

    public void addPhone(String phone){
        phone=phone.replaceAll("\\s+","");
        phone=phone.replaceAll("-","");
        if(!this.phone.contains(phone))
            this.phone.add(phone);
    }
}
