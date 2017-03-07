package com.coursespick.fragment.autocourse;

import java.util.ArrayList;
import java.util.List;

/*
 * created by zhangziqiang on 17/2/18
 */
public class CourseDao {
    /**
     非智能选课，实现选已经排好的课
     courseData需要完全无冲突
     */
    static List<CourseModel> courseModels[] = new ArrayList[7];

    // 时间冲突
    public static boolean timeConflict(CourseModel a, CourseModel b){
        int a_st = a.getSection();
        int a_ed = a_st + a.getSectionSpan() - 1;
        int b_st = b.getSection();
        int b_ed = b_st + b.getSectionSpan() - 1;
        if(a_ed < b_st || b_ed < a_st || a.getWeek()!=b.getWeek())return false;
        else return true;
    }

    //相同课程
    public static boolean sameCourse(CourseModel a,CourseModel b){
        if(a.getCourseName() == b.getCourseName() && a.getId() == b.getId())return true;
        return false;
    }

    // 课程冲突
    public static boolean courseConflict(CourseModel a,CourseModel b){
        if(a.getCourseName() == b.getCourseName() && !sameCourse(a,b))return true;
        return false;
    }

    // 判断课程有没有冲突
    public static boolean courseConflict(CourseModel[] temp){
        int Cnt = temp.length;
        for(CourseModel a: temp){
            for(CourseModel b: temp){
                if(sameCourse(a,b))continue;
                else if(courseConflict(a,b) || timeConflict(a,b)){
                    return false;
                }
            }
        }
        return true;
    }

    public static void setCourseData(CourseModel[] courseData){

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        // 修改颜色
        int color = 0;
        int[] vis = new int[courseData.length];
        for(int i=0;i<courseData.length;++i)vis[i] = 0;
        for(int i=0;i<courseData.length;++i){
            if(vis[i] == 1)continue;
            int Color = color;
            color += 1;
            courseData[i].setCourseFlag(Color);
            for(int j=i+1;j<courseData.length;++j){
                if(vis[j] == 1)continue;
                if(sameCourse(courseData[i],courseData[j])){
                    courseData[j].setCourseFlag(Color);
                    vis[j] = 1;
                }
            }
        }
        for(int i=0;i<courseData.length;++i){
            courseModels[courseData[i].getWeek()-1].add(courseData[i]);
        }
    }

    public static List<CourseModel>[] getCourseData() {
        return courseModels;
    }
}
