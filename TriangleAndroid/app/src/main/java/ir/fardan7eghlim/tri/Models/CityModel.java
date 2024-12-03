package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/4/2018.
 */

public class CityModel
{


    public BigInteger getCityId() {
        return CityId;
    }

    public void setCityId(BigInteger cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityProvince() {
        return CityProvince;
    }

    public void setCityProvince(String cityProvince) {
        CityProvince = cityProvince;
    }

    public String getCityLatitude() {
        return CityLatitude;
    }

    public void setCityLatitude(String cityLatitude) {
        CityLatitude = cityLatitude;
    }

    public String getCityLongitude() {
        return CityLongitude;
    }

    public void setCityLongitude(String cityLongitude) {
        CityLongitude = cityLongitude;
    }

    private BigInteger CityId;
    private String CityName;
    private String CityProvince;
    private String CityLatitude;//عرض جغرافیایی برای شیراز 29
    private String CityLongitude;//طول جغرافیایی


    public CityModel()
    {
        CityName = null;
        CityProvince = null;
        CityLatitude = null;
        CityLongitude = null;
    }

    public CityModel(String cityName, String provinceName, String latitude, String longitude)
    {
        CityName = cityName;
        CityProvince = provinceName;
        CityLatitude = latitude;
        CityLongitude = longitude;
    }
}
