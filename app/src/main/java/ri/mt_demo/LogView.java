package ri.mt_demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Bingo
 */
public class LogView extends TextView {

    private static final String TAG = "LogView";

    public LogView(Context context) {
        this(context, null);
    }

    public LogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContent = new StringBuffer();
    }

    private StringBuffer mContent;
    private static SimpleDateFormat dateFormat =
            new SimpleDateFormat("HH:mm:ss", Locale.CHINA);//日期格式;

    private static int MAX_LENGTH = 1024;

    private String getTime() {
        String format = null;
        try {
            Date date = new Date(System.currentTimeMillis());
            format = dateFormat.format(date);
        } catch (Exception e) {
            Log.e(TAG, "Format time error:" + e.getMessage());
        }
        return format;
    }


    public void setLog(String msg) {
        mContent.append(getTime()).append(" ").append(msg).append("\n");
//        Log.i(TAG , "print msg:" + msg);
        String text = mContent.toString();
        if (mContent == null || text.length() < 5){
            return;
        }
        setText(text, BufferType.NORMAL);
    }

    public String getLog(){
        if (mContent != null){
            return mContent.toString();
        }else {
            return null;
        }
    }

    public void setMaxLength(int length){
        if (length <= 0){
            if (mContent != null){
                setLog("Set Max Length error:length=" + length);
            }
        }else {
            MAX_LENGTH = length;
        }
    }

    public void clear() {
        mContent = new StringBuffer();
        mContent.setLength(MAX_LENGTH);
    }

}
