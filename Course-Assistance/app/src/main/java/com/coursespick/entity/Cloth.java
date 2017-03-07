package com.coursespick.entity;

import com.coursespick.R;

import java.util.Calendar;



/**
 * Created by eva on 2017/1/28.
 */

public class Cloth {
    private String cloth_title;
    private String user_name;
    private String upload_time;
    private String imgUrl;
    private int srcId;
    public Cloth()
    {
        cloth_title="none";
        user_name="admin";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second  = calendar.get(Calendar.SECOND);
        upload_time = year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
        srcId= R.drawable.nav_header_bg;
        imgUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
    }
    public Cloth(int id,String title,String user)
    {
        srcId=id;
        cloth_title = title;
        user_name=user;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour =calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second  = calendar.get(Calendar.SECOND);
        upload_time = year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
        imgUrl = "http://pica.nipic.com/2007-10-09/200710994020530_2.jpg";
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public void setCloth_title(String cloth_title) {
        this.cloth_title = cloth_title;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String toString() {
        return user_name;
    }

    public String getCloth_title() {
        return cloth_title;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public int getSrcId() {
        return srcId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getUser_name() {
        return user_name;
    }

}
