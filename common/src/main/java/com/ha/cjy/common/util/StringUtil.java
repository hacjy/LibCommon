package com.ha.cjy.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cjy on 2018/7/23.
 */

public class StringUtil {
    private static final String EMAIL_PATTERN = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private static final DecimalFormat FORMAT = new DecimalFormat("#00.0");
    public static final String CHARSET_UTF_8 = "UTF-8";
    static DecimalFormat df = new DecimalFormat("#.0");

    public StringUtil() {
    }

    public static String StrForResId(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    public static boolean isContainBlank(CharSequence str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isBlank(CharSequence str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isWhitespace(char ch) {
        return ch == 12288?true:Character.isWhitespace(ch);
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() <= 0;
    }

    public static boolean isAnyEmpty(String... strs) {
        int N = strs.length;

        for(int i = 0; i < N; ++i) {
            if(isEmpty(strs[i])) {
                return true;
            }
        }

        return false;
    }

    public static String getString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        return index != -1?cursor.getString(index):null;
    }

    public static boolean equal(String s1, String s2) {
        return s1 == s2?true:(s1 != null && s2 != null?s1.equals(s2):false);
    }

    public static String getFileSuffix(String fileName) {
        int end = fileName.lastIndexOf(46);
        return end >= 0?fileName.substring(end + 1):null;
    }

    public static String renameRes(String path) {
        return path == null?null:path.replace(".png", ".a").replace(".jpg", ".b");
    }

    public static String getNotNullString(String s) {
        return s == null?"":s;
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception var3) {
            return defaultValue;
        }
    }

    public static long parseLong(String str, Long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception var3) {
            return defaultValue.longValue();
        }
    }

    public static String parseLongToKbOrMb(long num, int scale) {
        try {
            float scaleNum;
            switch(scale) {
                case 0:
                    scaleNum = 1.0F;
                    break;
                case 1:
                    scaleNum = 10.0F;
                    break;
                case 2:
                    scaleNum = 100.0F;
                    break;
                case 3:
                    scaleNum = 1000.0F;
                    break;
                case 4:
                    scaleNum = 10000.0F;
                    break;
                default:
                    scaleNum = 1.0F;
            }

            float n = (float)num;
            if(n < 1024.0F) {
                return scale == 0?(int)n + "B":(float)Math.round(n * scaleNum) / scaleNum + "B";
            } else {
                n /= 1024.0F;
                if(n < 1024.0F) {
                    return scale == 0?(int)n + "KB":(float)Math.round(n * scaleNum) / scaleNum + "KB";
                } else {
                    n /= 1024.0F;
                    if(n < 1024.0F) {
                        return scale == 0?(int)n + "MB":(float)Math.round(n * scaleNum) / scaleNum + "MB";
                    } else {
                        n /= 1024.0F;
                        return scale == 0?(int)n + "GB":(float)Math.round(n * scaleNum) / scaleNum + "GB";
                    }
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return "-1B";
        }
    }

    public static String parseLongToKbOrMb(double num, int scale) {
        float scaleNum;
        switch(scale) {
            case 0:
                scaleNum = 1.0F;
                break;
            case 1:
                scaleNum = 10.0F;
                break;
            case 2:
                scaleNum = 100.0F;
                break;
            case 3:
                scaleNum = 1000.0F;
                break;
            case 4:
                scaleNum = 10000.0F;
                break;
            default:
                scaleNum = 1.0F;
        }

        if(num < 1024.0D) {
            return scale == 0?(int)num + "B":(float)Math.round(num * (double)scaleNum) / scaleNum + "B";
        } else {
            num /= 1024.0D;
            if(num < 1024.0D) {
                return scale == 0?(int)num + "KB":(float)Math.round(num * (double)scaleNum) / scaleNum + "KB";
            } else {
                num /= 1024.0D;
                if(num < 1024.0D) {
                    return scale == 0?(int)num + "MB":(float)Math.round(num * (double)scaleNum) / scaleNum + "MB";
                } else {
                    num /= 1024.0D;
                    return scale == 0?(int)num + "GB":(float)Math.round(num * (double)scaleNum) / scaleNum + "GB";
                }
            }
        }
    }

    public static String getNodeText(Node node) {
        String text = null;
        Node tNode = node.getFirstChild();
        if(tNode != null && "#text".equals(tNode.getNodeName())) {
            text = tNode.getNodeValue();
            if(text != null) {
                text = text.trim();
            }
        }

        return text;
    }

    public static boolean isNumberic(String sNum) {
        try {
            Float.parseFloat(sNum);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static boolean checkOnlyContainCharaterAndNumbers(String str) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean checkValidMailAddress(String str) {
        Pattern p1 = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
        Matcher m = p1.matcher(str);
        return m.matches();
    }

    public static boolean checkValidMobilePhoneNumber(String str) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String checkString(String str) {
        return null == str?"":str;
    }

    public static String parseKeyNew(String actName, String packName, String id) {
        actName = actName.toLowerCase();
        packName = packName.toLowerCase();
        id = id.toLowerCase(Locale.getDefault());
        int ret = id.hashCode() + -25;

        try {
            char[] buf = new char[actName.length()];

            int i;
            for(i = 0; i < buf.length; ++i) {
                buf[i] = (char)(actName.toCharArray()[i] << 2);
            }

            for(i = 0; i < buf.length; ++i) {
                if(i % buf[i] == 0) {
                    ret += buf[i];
                } else {
                    ret -= buf[i];
                }
            }

            ret <<= 2;
            ret >>= 3;
            char[] buf2 = new char[packName.length()];

            for(i = 0; i < buf2.length - 2; ++i) {
                buf2[i] = (char)(packName.toCharArray()[i] + buf[i % buf.length] << 3);
                if((buf2[i] * 2 - buf[i] * 2) % 2 == 0) {
                    ret += buf2[i];
                } else {
                    ret -= buf2[i];
                }
            }

            ret = Math.abs(ret);
        } catch (Exception var7) {
            return "";
        }

        return "i" + ret;
    }

    public static List<String> getKeysByValue(HashMap<String, String> map, String value) {
        ArrayList<String> list = new ArrayList();
        Iterator it = map.keySet().iterator();

        while(it.hasNext()) {
            String k = (String)it.next();
            String v = (String)map.get(k);
            if(equal(v, value)) {
                list.add(k);
            }
        }

        return list;
    }

    public static String getAppKey(ResolveInfo info) {
        String appKey = (info.activityInfo.packageName + "|" + info.activityInfo.name).toLowerCase();
        return appKey;
    }

    public static String getAppKey(ComponentName info) {
        String appKey = (info.getPackageName() + "|" + info.getClassName()).toLowerCase();
        return appKey;
    }

    public static String getAppKey(String pck, String clazz) {
        String appKey = (pck.trim() + "|" + clazz.trim()).toLowerCase();
        return appKey;
    }

    public static String getAppKey(ActivityInfo info) {
        String appKey = (info.packageName + "|" + info.name).toLowerCase();
        return appKey;
    }

    public static boolean checkSuffixsWithInStringArray(Context context, String name, int arrayId) {
        String[] fileEndings = context.getResources().getStringArray(arrayId);
        String[] var4 = fileEndings;
        int var5 = fileEndings.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String aEnd = var4[var6];
            if(name.toLowerCase().endsWith(aEnd)) {
                return true;
            }
        }

        return false;
    }

    public static String filtrateInsertParam(CharSequence srcParam) {
        if(isEmpty(srcParam)) {
            return "";
        } else {
            String result = srcParam.toString().replace("'", "''");
            result = result.replace("?", "");
            return result;
        }
    }

    public static int getRandom(int max) {
        return Integer.parseInt(String.valueOf(System.currentTimeMillis() % (long)max));
    }

    public static String getPkgName(String component) {
        return component.substring(component.indexOf("{") + 1, component.lastIndexOf("/"));
    }

    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics localFontMetrics = paint.getFontMetrics();
        return (float)Math.ceil((double)(localFontMetrics.descent - localFontMetrics.ascent));
    }

    public static void getChineseFontSize(Paint paint, Rect outRect) {
        paint.getTextBounds("桌", 0, 1, outRect);
    }

    public static void drawText(Canvas canvas, Paint paint, String text, int width, int paddingTop, int paddingLeft) {
        int height = paint.getFontMetricsInt((Paint.FontMetricsInt)null);
        int verticalSpace = 5;
        int lenght = text.length();
        int validateLength = paint.breakText(text, true, (float)width, (float[])null);
        int count = (lenght - 1) / validateLength + 1;
        int start = 0;
        int top = paddingTop;

        for(int i = 0; i < count; ++i) {
            int end = start + validateLength;
            if(end >= lenght) {
                end = lenght;
            }

            canvas.drawText(text.substring(start, end), (float)paddingLeft, (float)(top + i * (verticalSpace + height)), paint);
            start = end;
        }

    }

    public static String Color2String(int color) {
        String A = "";
        String R = "";
        String G = "";
        String B = "";

        try {
            A = Integer.toHexString(Color.alpha(color));
            A = A.length() < 2?'0' + A:A;
            R = Integer.toHexString(Color.red(color));
            R = R.length() < 2?'0' + R:R;
            G = Integer.toHexString(Color.green(color));
            G = G.length() < 2?'0' + G:G;
            B = Integer.toHexString(Color.blue(color));
            B = B.length() < 2?'0' + B:B;
        } catch (Exception var6) {
            return "#FFFFFF";
        }

        return '#' + A + R + G + B;
    }

    public static String regularSymbolFilter(String input) {
        return TextUtils.isEmpty(input)?"":input.replaceAll("\\$|\\^|\\*|\\(|\\)|\\-|\\+|\\{|\\}|\\||\\.|\\?|\\[|\\]|\\&|\\\\", "");
    }

    public static String utf8URLencode(String url) {
        StringBuffer result = new StringBuffer();

        for(int i = 0; i < url.length(); ++i) {
            char c = url.charAt(i);
            if(c >= 0 && c <= 255) {
                result.append(c);
            } else {
                byte[] b = new byte[0];

                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (Exception var7) {
                    ;
                }

                for(int j = 0; j < b.length; ++j) {
                    int k = b[j];
                    if(k < 0) {
                        k += 256;
                    }

                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        return result.toString();
    }

    public static String utf8URLdecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String subString(String str, int length) {
        if(str != null && str.length() > length) {
            str = str.substring(0, length);
        }

        return str;
    }

    public static boolean isLongType(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static String inputStream2String(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

        return sb.toString();
    }

    public static boolean isEmail(String s) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static int getStringRealLength(String str) throws Exception {
        String str1 = new String(str.getBytes("GBK"), "iso-8859-1");
        return str1.length();
    }

    public static String getPercentage(float percentage) {
        return FORMAT.format((double)percentage).toString() + "%";
    }

    public static String btoKBorMBorGBForDecimals(long b) {
        double integer = 0.0D;
        String unit = "";
        if(b / 1024L < 1L) {
            integer = (double)b;
            unit = "B";
        } else if(b / 1048576L < 1L) {
            integer = (double)b / 1024.0D;
            unit = "KB";
        } else if(b / 1073741824L < 1L) {
            integer = (double)b / 1048576.0D;
            unit = "MB";
        } else if(b / 0L < 1L) {
            integer = (double)b / 1.073741824E9D;
            unit = "GB";
        }

        return integer == 0.0D?"0B":df.format(integer) + unit;
    }

    public static String btoKBorMBorGB(long b) {
        return b / 1024L < 1L?b + "B":(b / 1048576L < 1L?b / 1024L + "KB":(b / 1073741824L < 1L?b / 1048576L + "MB":(b / 0L < 1L?b / 1073741824L + "GB":"0B")));
    }

    public static String getMemorySizeString(long size) {
        float result = (float)size;
        BigDecimal temp;
        if(result < 1024.0F) {
            temp = new BigDecimal((double)result);
            temp = temp.setScale(2, 4);
            return temp + "Bytes";
        } else {
            result /= 1024.0F;
            if(result < 1024.0F) {
                temp = new BigDecimal((double)result);
                temp = temp.setScale(2, 4);
                return temp + "KB";
            } else {
                result /= 1024.0F;
                if(result < 1024.0F) {
                    temp = new BigDecimal((double)result);
                    temp = temp.setScale(2, 4);
                    return temp + "MB";
                } else {
                    result /= 1024.0F;
                    if(result < 1024.0F) {
                        temp = new BigDecimal((double)result);
                        temp = temp.setScale(2, 4);
                        return temp + "GB";
                    } else {
                        result /= 1024.0F;
                        temp = new BigDecimal((double)result);
                        temp = temp.setScale(2, 4);
                        return temp + "TB";
                    }
                }
            }
        }
    }

    public static String getMemoryPercentString(float percent) {
        BigDecimal result = new BigDecimal((double)(percent * 100.0F));
        return result.setScale(2, 4) + "%";
    }
}
