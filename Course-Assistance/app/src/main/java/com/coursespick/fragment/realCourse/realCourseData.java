package com.coursespick.fragment.realCourse;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by onisac on 2017/2/21.
 */

public class realCourseData implements Serializable {
    String cId;      // 课程号
    String cName;    // 课程名
    String cCredit;   // 学分
    String tId;      // 老师号
    String tName;    // 老师名
    String cTime;    // 上课时间
    String cLocation;// 地点
    String cSize;   // 容量
    String cNumber; // 人数
    String cCampus;  // 校区
    String cLimit;   // 选课限制
    String qaTime;   // 答疑时间
    String qaLocation;// 答疑地点
    int necessary;
    public realCourseData(){
        this.cId = null;
        this.cName = null;
        this.cCredit = null;
        this.tId = null;
        this.tName = null;
        this.cTime = null;
        this.cLocation = null;
        this.cSize = null;
        this.cNumber = null;
        this.cCampus = null;
        this.cLimit = null;
        this.qaTime = null;
        this.qaLocation = null;
        necessary = 0;
    }
    public realCourseData(String cid, String cname, String credit, String tid, String tname,
                          String ctime, String clocation, String csize, String cnumber, String ccampus,
                          String climit, String qatime, String qalocation){
        this.cId = cid;
        this.cName = cname;
        this.cCredit = credit;
        this.tId = tid;
        this.tName = tname;
        this.cTime = ctime;
        this.cLocation = clocation;
        this.cSize = csize;
        this.cNumber = cnumber;
        this.cCampus = ccampus;
        this.cLimit = climit;
        this.qaTime = qatime;
        this.qaLocation = qalocation;
    }

    public int getNecessary(){return necessary;}
    public void setNecessary(int necessary1){this.necessary = necessary1;}

    public String getQaTime(){return this.qaTime;}
    public void setQaTime(String a){this.qaTime = a;}

    public String getQaLocation(){return this.qaLocation;}
    public void setQaLocation(String a){this.qaLocation = a;}

    public void setcId(String a){this.cId = a;}
    public String getcId(){return this.cId;}

    public void setcName(String a){
        Log.i("123",a);this.cName = a;}
    public String getcName(){return this.cName;}

    public void setcCedit(String a){this.cCredit = a;}
    public String getcCredit(){return this.cCredit;}

    public String gettId(){return this.tId;}
    public void settId(String a){this.tId = a;}

    public String gettName(){return this.tName;}
    public void settName(String a){this.tName = a;}

    public String getcTime(){return this.cTime;}
    public void setcTime(String a){this.cTime = a;}

    public String getcLocation(){return this.cLocation;}
    public void setcLocation(String a){this.cLocation = a;}

    public String getcSize(){return this.cSize;}
    public void setcSize(String a){this.cSize = a;}

    public String getcNumber(){return this.cNumber;}
    public void setcNumber(String a){this.cNumber = a;}

    public String getcCampus(){return this.cCampus;}
    public void setcCampus(String a){this.cCampus = a;}

    public String getcLimit(){return this.cLimit;}
    public void setcLimit(String a){this.cLimit = a;}

}