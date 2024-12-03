package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Meysam on 5/12/2018.
 */

public class NewsModel
{

    public static Integer DEFAULT_ITEM_COUNT = 15;

    public static String TYPE_IRAN = "IRAN";
    public static String TYPE_WORLD = "WORLD";
    public static String TYPE_ECONOMIC = "ECONOMIC";
    public static String TYPE_SCIENCE = "SCIENCE";
    public static String TYPE_IT = "IT";
    public static String TYPE_ART = "ART";
    public static String TYPE_SOCIAL = "SOCIAL";
    public static String TYPE_SPORT = "SPORT";
    public static String TYPE_ACCIDENT = "ACCIDENT";
    public static String TYPE_OTHER = "OTHER";

    public BigInteger getNewsId() {
        return NewsId;
    }

    public void setNewsId(BigInteger newsId) {
        NewsId = newsId;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return NewsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        NewsDescription = newsDescription;
    }

    public String getNewsLink() {
        return NewsLink;
    }

    public void setNewsLink(String newsLink) {
        NewsLink = newsLink;
    }

    public String getNewsPublicationDate() {
        return NewsPublicationDate;
    }

    public void setNewsPublicationDate(String newsPublicationDate) {
        NewsPublicationDate = newsPublicationDate;
    }

    public String getNewsType() {
        return NewsType;
    }

    public void setNewsType(String newsType) {
        NewsType = newsType;
    }

    private BigInteger NewsId;
    private String NewsTitle;
    private String NewsDescription;
    private String NewsLink;
    private String NewsPublicationDate;
    private String NewsType;

    public static ArrayList<NewsModel> setBulkType(ArrayList<NewsModel> news, String type)
    {
        for(int i = 0; i < news.size(); i++)
        {
           news.get(i).setNewsType(type);
        }

        return news;
    }
}
