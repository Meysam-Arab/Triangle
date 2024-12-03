package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Meysam on 5/4/2018.
 */

public class PriceModel
{
    public static String TAG_CURRENCY = "currency";
    public static String TAG_COIN = "coin";


    public BigInteger getPriceId() {
        return PriceId;
    }

    public void setPriceId(BigInteger priceId) {
        PriceId = priceId;
    }

    public String getPriceStatus() {
        return PriceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        PriceStatus = priceStatus;
    }

    public String getPriceTime() {
        return PriceTime;
    }

    public void setPriceTime(String priceTime) {
        PriceTime = priceTime;
    }

    public ArrayList<CurrencyModel> getCurrencies() {
        return Currencies;
    }

    public void setCurrencies(ArrayList<CurrencyModel> currencies) {
        Currencies = currencies;
    }

    public String getPriceTag() {
        return PriceTag;
    }

    public void setPriceTag(String priceTag) {
        PriceTag = priceTag;
    }

    public ArrayList<CoinModel> getCoins() {
        return Coins;
    }

    public void setCoins(ArrayList<CoinModel> coins) {
        Coins = coins;
    }

    private BigInteger PriceId;
    private String PriceStatus;
    private String PriceTime;
    private String PriceTag;
    private ArrayList<CurrencyModel> Currencies;
    private ArrayList<CoinModel> Coins;

}
