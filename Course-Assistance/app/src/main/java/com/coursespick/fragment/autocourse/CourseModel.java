package com.coursespick.fragment.autocourse;

import android.util.Log;

import com.coursespick.fragment.realCourse.realCourseData;

import java.io.Serializable;

/**
 *
 * CourseModel:用来存放UI输出的课程类
 */
public class CourseModel implements Serializable{
    private String id; //课程ID
    private String courseName;//课名
    private String tname;//教师名
    private String tid;//教师号
    private int section; //从第几节课开始
    private int sectionSpan; //跨几节课
    private int week; //周几 1-5
    private String classRoom; //教室
    private int courseFlag; //课程背景颜色
    private int necessary;//是否是必修课


    private String necessaryteacher; //必选老师
    private String negativeteacher;//不选老师ok
    private int negativetime;//不选课的时间———— 1：早上1-2节  2：中午5-6节   3: 下午9——10节  4：晚上11——13节
    private String location;//校区ok
    private String full;//选课人数是否限制ok
    private int uppercredit;//学分上限ok
    private String academy;//学院ok
    private int typeofgeneral;//通识课类别———— 1：理工  2：人文  3：经管 ok

    public CourseModel() {}

    public CourseModel(String id, String courseName, int section, int sectionSpan, int week, String classRoom, int courseFlag, int Necessary) {
        this.id = id;
        this.courseName = courseName;
        this.section = section;
        this.sectionSpan = sectionSpan;
        this.week = week;
        this.classRoom = classRoom;
        this.courseFlag = courseFlag;
        this.necessary = Necessary;
    }

    public static CourseModel[] realCourseData2CourseModel(realCourseData realcourse[]){
        int cnt = 0;
        for(int i=0;i<realcourse.length;++i){
            realCourseData temp = realcourse[i];
            String date = temp.getcTime();
            int start = 0;
            while(date.indexOf("-",start)!=-1){
                cnt ++;
                start = date.indexOf("-",start) + 1;
            }
        }
        CourseModel[] answer = new CourseModel[cnt];

        cnt = 0;
        int start = 0;
        realCourseData temp = null;
        for(int i=0;i<realcourse.length;++i){
            temp = realcourse[i];
            String date = temp.getcTime();
            String[] regex = {"一","二","三","四","五"};
            for(int j=0;j<5;++j){
                int ST = 0;
                while (date.indexOf(regex[j], ST) != -1) {
                    start = date.indexOf(regex[j], ST);
                    int end = date.indexOf("-", start);
                    int end2 = date.indexOf(" ", end);
                    String courseStart = date.substring(start + 1, end);
                    Log.i("转换过程中的课程的开始时间",courseStart);
                    Log.i("转换过程中的课程的两个end",String.valueOf(end)+' '+String.valueOf(end2));
                    String courseEnd;
                    if(end2 == -1){
                        courseEnd = date.substring(end+1,date.length());
                        start = Integer.valueOf(courseStart);
                        end = Integer.valueOf(courseEnd);
                        CourseModel coursemodel = new CourseModel(temp.getcId(), temp.getcName(), start, end - start + 1, j + 1, temp.getcLocation(), 1, 1);
                        Log.i("coursemodel里获取两个人数分别为",String.valueOf(end)+' '+temp.getcId()+' '+temp.getcName()+' '+temp.gettId()+' '+temp.gettName()+' '+temp.getcCredit()+' '+temp.getcNumber()+' '+temp.getcSize());
                        //coursemodel.setNumstudent(Integer.parseInt(temp.getcNumber()));
                        //coursemodel.setSumstudent(Integer.parseInt(temp.getcSize()));
                        coursemodel.setNecessary(temp.getNecessary());
                        answer[cnt++] = coursemodel;
                        break;
                    }
                    else {
                        courseEnd= date.substring(end + 1, end2);
                        start = Integer.valueOf(courseStart);
                        end = Integer.valueOf(courseEnd);
                        ST = end2 + 1;
                        CourseModel coursemodel = new CourseModel(temp.getcId(), temp.getcName(), start, end - start + 1, j + 1, temp.getcLocation(), 1, 1);
                        coursemodel.setNecessary(temp.getNecessary());
                        //coursemodel.setNumstudent(Integer.parseInt(temp.getcNumber()));
                        //coursemodel.setSumstudent(Integer.parseInt(temp.getcSize()));
                        answer[cnt++] = coursemodel;
                    }

                }
            }
        }
        return answer;
    }

    public static CourseModel[] realCourseData2CourseModel(realCourseData realcourse){
        int cnt = 0;
        realCourseData temp = realcourse;
        String date = temp.getcTime();
        int start = 0;
        while(date.indexOf("-",start)!=-1){
            cnt ++;
            start = date.indexOf("-",start) + 1;
        }

        CourseModel[] answer = new CourseModel[cnt];
        cnt = 0;
        temp = realcourse;
        String[] regex = {"一","二","三","四","五"};
        for(int j=0;j<5;++j) {
            int ST = 0;
            while (date.indexOf(regex[j], ST) != -1) {
                start = date.indexOf(regex[j], ST);
                int end = date.indexOf("-", start);
                int end2 = date.indexOf(" ", end);
                String courseStart = date.substring(start + 1, end);
                Log.i("转换过程中的课程的开始时间",courseStart);
                Log.i("转换过程中的课程的两个end",String.valueOf(end)+' '+String.valueOf(end2));
                String courseEnd;
                if(end2 == -1){
                    courseEnd = date.substring(end+1,date.length());
                    start = Integer.valueOf(courseStart);
                    end = Integer.valueOf(courseEnd);
                    CourseModel coursemodel = new CourseModel(temp.getcId(), temp.getcName(), start, end - start + 1, j + 1, temp.getcLocation(), 1, 1);
                    Log.i("coursemodel里获取两个人数分别为",String.valueOf(end)+' '+temp.getcId()+' '+temp.getcName()+' '+temp.gettId()+' '+temp.gettName()+' '+temp.getcCredit()+' '+temp.getcNumber()+' '+temp.getcSize());
                    //coursemodel.setNumstudent(Integer.parseInt(temp.getcNumber()));
                    //coursemodel.setSumstudent(Integer.parseInt(temp.getcSize()));
                    coursemodel.setNecessary(temp.getNecessary());
                    answer[cnt++] = coursemodel;
                    break;
                }
                else {
                    courseEnd= date.substring(end + 1, end2);
                    start = Integer.valueOf(courseStart);
                    end = Integer.valueOf(courseEnd);
                    ST = end2 + 1;
                    CourseModel coursemodel = new CourseModel(temp.getcId(), temp.getcName(), start, end - start + 1, j + 1, temp.getcLocation(), 1, 1);
                    coursemodel.setNecessary(temp.getNecessary());
                    //coursemodel.setNumstudent(Integer.parseInt(temp.getcNumber()));
                    //coursemodel.setSumstudent(Integer.parseInt(temp.getcSize()));
                    answer[cnt++] = coursemodel;
                }

            }

        }
        return answer;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSection() {
        return section;
    }
    public void setSection(int section) {
        this.section = section;
    }

    public int getSectionSpan() {
        return sectionSpan;
    }
    public void setSectionSpan(int sectionSpan) {
        this.sectionSpan = sectionSpan;
    }

    public int getCourseFlag() {
        return courseFlag;
    }
    public void setCourseFlag(int courseFlag) {
        this.courseFlag = courseFlag;
    }

    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
    }

    public String getClassRoom() {
        return classRoom;
    }
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getNecessary(){return necessary;}
    public void setNecessary(int Necessary){this.necessary = Necessary;}





    public String getTname(){return tname;}
    public void setTname(String tname1){this.tname = tname1;}

    public String getTid(){return tid;}
    public void setTid(String tid1){this.tid = tid1;}

    public String getNecessaryteacher(){return necessaryteacher;}
    public void setNecessaryteacher(String necessaryteacher1){this.necessaryteacher = necessaryteacher1;}

    public String getNegativeteacher(){return negativeteacher;}
    public void setNegativeteacher(String negativeteacher1){negativeteacher  = negativeteacher1;}

    public int getNegativetime(){return negativetime;}
    public void setNegativetime(int negativetime1){negativetime = negativetime1;}

    public String getLocation(){return location;}
    public void setLocation(String location1){this.location = location1;}

    public String getFull(){return full;}
    public void setFull(String full1){full = full1;}

    public int getUppercredit(){return uppercredit;}
    public void setUppercredit(int uppercredit1) {this.uppercredit = uppercredit1;}

    public String getAcademy(){return academy;}
    public void setAcademy(String academy1){academy = academy1;}

    public int getTypeofgeneral(){return typeofgeneral;}
    public void setTypeofgeneral(int typeofgeneral1){typeofgeneral = typeofgeneral1;}
}
/*

private String necessaryteacher; //必选老师
    private String negativeteacher;//不选老师
    private int negativetime;//不选课的时间———— 1：早上1-2节  2：中午5-6节   3: 下午9——10节  4：晚上11——13节
    private String location;//校区
    private boolean full;//选课人数是否限制———— true: 限制选课  false: 选课人数未满
    private int uppercredit;//学分上限
    private String academy;//学院
    private int typeofgeneral;//通识课类别———— 1：理工  2：人文    3：经管

 */