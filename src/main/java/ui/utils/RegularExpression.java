package ui.utils;

/**
 * Created by vevinmoza on 8/30/17.
 */
public class RegularExpression {

    public static final String WEEKDAY = "\\b((Mon|Tues|Wed(nes)?|Thur(s)?|Fri|Sat(ur)?|Sun)(day)?)\\b";
    public static final String MONTH = "\\b(?i:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(" +
            "?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|(Nov|Dec)(?:ember)?)\\b";
    public static final String DATE = "(3[01]+|[12]\\d|[1-9])";
    public static final String MONTH_DATE_TIME = "(?i:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)" +
            "?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|(Nov|Dec)(?:ember)?)\\s(3[01]+|[12" +
            "]\\d|[1-9])\\s(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
    public static final String CITY_STATE = "([A-Za-z]+(?: [A-Za-z]+)*),{1} ([A-Za-z]{2})";
    public static final String UPS_TRACKING_NUMBER = "[0-9]ZW\\d{15}";
    public static final String NUMBER="\\d+";
    public static final String NONWHITESPACE ="\\S+";
    public static final String DATE_TIME="^(((((0[13578])|([13578])|(1[02]))[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9])|(3[01])))|((([469])|(11))[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9])|(30)))|((02|2)[\\-\\/\\s]?((0[1-9])|([1-9])|([1-2][0-9]))))[\\-\\/\\s]?\\d{4})(\\s(((0[1-9])|([1-9])|(1[0-2]))\\:([0-5][0-9])((\\s)|(\\:([0-5][0-9])\\s))([AM|PM|am|pm]{2,2})))?$";
    public static final String ALPHA_NUMERIC="\\w+";

}
