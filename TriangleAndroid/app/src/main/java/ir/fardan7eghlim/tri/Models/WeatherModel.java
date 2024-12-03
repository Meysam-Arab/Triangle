package ir.fardan7eghlim.tri.Models;

import java.util.ArrayList;

/**
 * Created by Meysam on 5/4/2018.
 */

public class WeatherModel
{
    public ArrayList<DayModel> getDays() {
        return days;
    }

    public void setDays(ArrayList<DayModel> days) {
        this.days = days;
    }

    private ArrayList<DayModel> days;
}
