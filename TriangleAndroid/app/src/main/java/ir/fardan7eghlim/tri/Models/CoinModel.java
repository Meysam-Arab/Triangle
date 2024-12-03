package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/5/2018.
 */

public class CoinModel
{
    public BigInteger getCoinId() {
        return CoinId;
    }

    public void setCoinId(BigInteger coinId) {
        CoinId = coinId;
    }

    public String getCoinName() {
        return CoinName;
    }

    public void setCoinName(String coinName) {
        CoinName = coinName;
    }

    public String getCoinPrice() {
        return CoinPrice;
    }

    public void setCoinPrice(String coinPrice) {
        CoinPrice = coinPrice;
    }

    public String getCoinChange() {
        return CoinChange;
    }

    public void setCoinChange(String coinChange) {
        CoinChange = coinChange;
    }

    public String getCoinPercent() {
        return CoinPercent;
    }

    public void setCoinPercent(String coinPercent) {
        CoinPercent = coinPercent;
    }

    private BigInteger CoinId;
    private String CoinName;
    private String CoinPrice;
    private String CoinChange;
    private String CoinPercent;
}
