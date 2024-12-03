package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/1/2018.
 */

public class CalenderEventModel
{

    public static Integer TYPE_SHAMSI = 0;
    public static Integer TYPE_GHAMARI = 1;
    public static Integer TYPE_MILADI = 2;

    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Boolean getHoliday() {
        return IsHoliday;
    }

    public void setHoliday(Boolean holiday) {
        IsHoliday = holiday;
    }

    private BigInteger Id;
    private String Date;
    private Integer Type;
    private String Title;
    private String Description;
    private Boolean IsHoliday;


    public CalenderEventModel()
    {
        Id = null;
        Date = null;
        Title = null;
        Description = null;
        Type = null;
        IsHoliday = null;
    }

    public CalenderEventModel(String date, String title, String description, Integer type, Boolean isHoliday)
    {
        Date = date;
        Title = title;
        Description = description;
        Type = type;
        IsHoliday = isHoliday;
    }
}
