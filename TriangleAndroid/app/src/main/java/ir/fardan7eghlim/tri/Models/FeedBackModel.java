package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Meysam on 5/1/2018.
 */

public class FeedBackModel
{

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    private String Description;
    private String Email;
    private String Phone;
    private String Title;



    public FeedBackModel()
    {
        Title = null;
        Description = null;
        Email = null;
        Phone = null;
    }
}
