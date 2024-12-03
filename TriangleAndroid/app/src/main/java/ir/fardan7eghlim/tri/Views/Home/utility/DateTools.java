package ir.fardan7eghlim.tri.Views.Home.utility;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Amir on 5/7/2018.
 */

public class DateTools {

    //convert number of month to shamsi month
    public static String convertNum2StrShamsiMonth(int month){
        String out="";
        switch (month){
            case 1:
                out="فروردین";
                break;
            case 2:
                out="اردیبهشت";
                break;
            case 3:
                out="خرداد";
                break;
            case 4:
                out="تیر";
                break;
            case 5:
                out="مرداد";
                break;
            case 6:
                out="شهریور";
                break;
            case 7:
                out="مهر";
                break;
            case 8:
                out="آبان";
                break;
            case 9:
                out="آذر";
                break;
            case 10:
                out="دی";
                break;
            case 11:
                out="بهمن";
                break;
            case 12:
                out="اسفند";
                break;
            default:
                out=month+"";
                break;
        }
        return out;
    }
    //convert name of week from miladi 2 shamsi
    public static String convertWeeksNameMiladi2Shamsi(String day){
        String out="";
        day=day.toLowerCase();
        switch (day){
            case "sat":
                out="شنبه";
                break;
            case "sun":
                out="یک\u200Cشنبه";
                break;
            case "mon":
                out="دوشنبه";
                break;
            case "tue":
                out="سه\u200Cشنبه";
                break;
            case "wed":
                out="چهارشنبه";
                break;
            case "thu":
                out="پنج\u200Cشنبه";
                break;
            case "fri":
                out="آدینه";
                break;
            default:
                out=day+"";
                break;
        }
        return out;
    }
    //convert num of ghamari 2 string
    public static String convertNum2StrGhamariMonth(int month){
        String out="";
        switch (month){
            case 1:
                out="محرم";
                break;
            case 2:
                out="صفر";
                break;
            case 3:
                out="ربیع\u200Cالاول";
                break;
            case 4:
                out="ربیع\u200Cالثانی";
                break;
            case 5:
                out="جمادی\u200Cالاول";
                break;
            case 6:
                out="جمادی\u200Cالثانی";
                break;
            case 7:
                out="رجب";
                break;
            case 8:
                out="شعبان";
                break;
            case 9:
                out="رمضان";
                break;
            case 10:
                out="شوال";
                break;
            case 11:
                out="ذیقعده";
                break;
            case 12:
                out="ذیحجه";
                break;
            default:
                out=month+"";
                break;
        }
        return out;
    }
    //convert num of ghamari 2 string
    public static String convertNum2StrMiladiMonth(int month){
        String out="";
        switch (month){
            case 1:
                out="ژانویه";
                break;
            case 2:
                out="فوریه";
                break;
            case 3:
                out="مارس";
                break;
            case 4:
                out="آوریل";
                break;
            case 5:
                out="مه";
                break;
            case 6:
                out="ژوئن";
                break;
            case 7:
                out="ژوئیه";
                break;
            case 8:
                out="اوت";
                break;
            case 9:
                out="سپتامبر";
                break;
            case 10:
                out="اکتبر";
                break;
            case 11:
                out="نوامبر";
                break;
            case 12:
                out="دسامبر";
                break;
            default:
                out=month+"";
                break;
        }
        return out;
    }
    public static String convertNum2StrMiladiMonthEng(int month){
        String out="";
        switch (month){
            case 1:
                out="January";
                break;
            case 2:
                out="February";
                break;
            case 3:
                out="March";
                break;
            case 4:
                out="April";
                break;
            case 5:
                out="May";
                break;
            case 6:
                out="June";
                break;
            case 7:
                out="July";
                break;
            case 8:
                out="August";
                break;
            case 9:
                out="September";
                break;
            case 10:
                out="October";
                break;
            case 11:
                out="November";
                break;
            case 12:
                out="December";
                break;
            default:
                out=month+"";
                break;
        }
        return out;
    }
    //fixed zero before time
    public static String fixZeroBeforeTime(String time){
        if(time.startsWith("0") && !time.substring(0,2).equals("00"))
            time=time.substring(1);
        return time;
    }
    //ghamari date fixer
    public static LocalDate fixGhamariDate(LocalDate ld,String date){
        //add after this days
        if(compareTwoDateOrTime("2018/06/14",date,"/")<0){
            ld=ld.plusDays(1);
        }
        if(compareTwoDateOrTime("2018/09/10",date,"/")<0){
            ld=ld.plusDays(1);
        }
        if(compareTwoDateOrTime("2019/01/07",date,"/")<0){
            ld=ld.minusDays(1);
        }
        if(compareTwoDateOrTime("2019/04/06",date,"/")<0){
            ld=ld.plusDays(1);
        }
        if(compareTwoDateOrTime("2019/05/05",date,"/")<0){
            ld=ld.minusDays(1);
        }
        //exception days
        if(compareTwoDateOrTime("2019/01/07",date,"/")==0){
            ld=new LocalDate(1440,4,30);
        }
        if(compareTwoDateOrTime("2019/05/05",date,"/")==0){
            ld=new LocalDate(1440,8,30);
        }
        return ld;
    }
    //compae two date or time
    public static byte compareTwoDateOrTime(String value1,String value2,String delimeter){
        int v1= Integer.parseInt(value1.replaceAll(delimeter,""));
        int v2= Integer.parseInt(value2.replaceAll(delimeter,""));
        if(v1>v2){
            return 1;
        }else if(v1<v2){
            return -1;
        }
        return 0;
    }
    //minues two time
    public static String minuesTwoTime(String date1,String date2){
        int h1= Integer.parseInt(date1.substring(0,date1.indexOf(":")));
        int m1= Integer.parseInt(date1.substring(date1.indexOf(":")+1));
        int h2= Integer.parseInt(date2.substring(0,date2.indexOf(":")));
        int m2= Integer.parseInt(date2.substring(date2.indexOf(":")+1));
        int M1=h1*60+m1;
        int M2=h2*60+m2;
        int M=M2-M1;
        return M>0?(M/60)+":"+(M%60):"00:00";
    }
    public static String minuesTimeWithNow(String time){
        DateFormat df = new SimpleDateFormat("HH:mm");
        String now = df.format(Calendar.getInstance().getTime());
        return minuesTwoTime(now,time);
    }
}
