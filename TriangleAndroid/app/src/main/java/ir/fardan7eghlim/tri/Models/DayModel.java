package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/4/2018.
 */

public class DayModel
{

//    public static Integer DAY_STATUS_SUNNY = 1;
//    public static Integer DAY_STATUS_SLIGHTLY_CLOUDY = 2;
//    public static Integer DAY_STATUS_PARTIALLY_CLOUDY = 3;
//    public static Integer DAY_STATUS_FULLY_CLOUDY = 4;

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public Integer getSymbol() {
        return Symbol;
    }

    public void setSymbol(Integer symbol) {
        Symbol = symbol;
    }

    public Integer getTemp() {
        return Temp;
    }

    public void setTemp(Integer temp) {
        Temp = temp;
    }

    public Integer getMaxTemp() {
        return MaxTemp;
    }

    public void setMaxTemp(Integer maxTemp) {
        MaxTemp = maxTemp;
    }

    public Integer getMinTemp() {
        return MinTemp;
    }

    public void setMinTemp(Integer minTemp) {
        MinTemp = minTemp;
    }

    public BigInteger getDayId() {
        return DayId;
    }

    public void setDayId(BigInteger dayId) {
        DayId = dayId;
    }

    private BigInteger DayId;
    private String DayName;
    private String Status;
    private String CityName;
    private Integer Symbol;
    private Integer Temp;
    private Integer MaxTemp;
    private Integer MinTemp;
}
