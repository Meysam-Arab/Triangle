package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/4/2018.
 */

public class CurrencyModel
{
    public BigInteger getCurrencyId() {
        return CurrencyId;
    }

    public void setCurrencyId(BigInteger currencyId) {
        CurrencyId = currencyId;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    public String getCurrencyPrice() {
        return CurrencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        CurrencyPrice = currencyPrice;
    }

    public String getCurrencyChange() {
        return CurrencyChange;
    }

    public void setCurrencyChange(String currencyChange) {
        CurrencyChange = currencyChange;
    }

    public String getCurrencyPercent() {
        return CurrencyPercent;
    }

    public void setCurrencyPercent(String currencyPercent) {
        CurrencyPercent = currencyPercent;
    }

    private BigInteger CurrencyId;
    private String CurrencyName;
    private String CurrencyPrice;
    private String CurrencyChange;
    private String CurrencyPercent;

}
