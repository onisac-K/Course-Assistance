package com.coursespick.fragment.autocourse;

/**
 * Created by zhangziqiang on 2017/2/18.
 *
 */

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AutoCourseDao {
    static List<CourseModel> courseModels[] = new ArrayList[7];

    //  课程包含了不想选的老师 true:没选不想选的老师
    public static boolean Negativeteacher(CourseModel a){
        String a_teacher = a.getTname();
        String b_teacher = a.getNegativeteacher();
        if(a_teacher.equals(b_teacher)) return false;
        return true;
    }

    //  选择了非本部的课程 true:本部
    public static boolean locationConflict(CourseModel a){
        String location = a.getLocation();
        if(location.equals("本部")) return true;
        return false;
    }

    //  判断是否限制人数 true:限制
    public static boolean isfull(CourseModel a){
        String str = a.getFull();
        if(str.equals("限制人数")) return true;
        return false;
    }

    // 判断是否选了非本学院的课程 true:没选
    public static boolean academyConflict(CourseModel a){
        String cid = a.getId();
        if(cid.indexOf("0830") == 0) return true;
        return false;
    }

    //  判断是否超出学分上限 true:超出
    public static boolean creditConflict(CourseModel a){
        int uppercredit = a.getUppercredit();
        if(uppercredit > 35) return false;
        return true;
    }

    //  获得该门课程是否为通识课:0非通识，1理工 2人文 3经管
    public static int gettypeofgeneral(CourseModel a){
        String cid = a.getId();
        if(cid.indexOf("L")>=0) {
            a.setTypeofgeneral(1);
            return 1;
        }
        if(cid.indexOf("R")>=0) {
            a.setTypeofgeneral(2);
            return 2;
        }
        if(cid.indexOf("J")>=0) {
            a.setTypeofgeneral(3);
            return 3;
        }
        return 0;
    }




    // 时间冲突
    public static boolean timeConflict(CourseModel a, CourseModel b){
        if(a == null || b == null) return false;
        int a_st = a.getSection();
        int a_ed = a_st + a.getSectionSpan() - 1;
        int b_st = b.getSection();
        int b_ed = b_st + b.getSectionSpan() - 1;
        if(a_ed < b_st || b_ed < a_st || a.getWeek()!=b.getWeek())return false;
        else return true;
    }

    //相同课程
    public static boolean sameCourse(CourseModel a,CourseModel b){
        if(a == null || b == null) return false;
        if(a.getCourseName() == b.getCourseName() && a.getId() == b.getId())return true;
        return false;
    }

    // 课程冲突
    public static boolean courseConflict(CourseModel a,CourseModel b){
        if(a == null || b == null) return false;
        if(a.getCourseName() == b.getCourseName() && !sameCourse(a,b))return true;
        return false;
    }

    /*
        无特殊要求
        return true : sucess
        return false : conflict
    */
    public static boolean SetCourseData(CourseModel[] courseData){
        /*
            necessary_course : 必修课
            selected_course ： 除必修课之外的选修课
            下面要做的是选择必修课
            1. 将course中标记necessary的课程放入necessary_C中
            2. 除去有冲突的课程并以课程数最多为最优
        */
        // 步骤一将与必修课程有冲突的课程去掉
        int N = courseData.length;
        CourseModel[] necessary_C = new CourseModel[N];
        List<CourseModel> necessary_course = new ArrayList<>();

        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        int cnt_C = 0;


        //-----------------------------------------暂时的修改
        //courseData[0].setNecessary(1);
        //暂时的修改-----------------------------------------

        for(int i=0;i<courseData.length;++i){
            if(courseData[i] == null) continue;
            if(courseData[i].getNecessary() == 1){
                necessary_C[cnt_C++] = courseData[i];
                Log.i("看看惨状之前是必修课",courseData[i].getCourseName());
            }
            else{
                Log.i("看看惨状之前不是必修课",courseData[i].getCourseName());
            }
        }
        Log.i("看看惨状之前",String.valueOf(cnt_C));
        //----------NO Problem!!!---------------------

        // 步骤二从必修课中挑选出不冲突的课程,并且要求课程数最多
        int MaxOfCourse = 0;
        int answer[] = new int[N];
        int sel[] = new int[N];
        for(int i=1;i<(1<<cnt_C);++i){
            Log.i("看看循环的情况",String.valueOf(i));
            for(int j=0;j<cnt_C;++j)sel[j] = 0;
            for(int j=0;j<cnt_C;++j){
                if((i&(1<<j))>0){
                    sel[j] = 1;
                }
            }
            boolean conflict_flag = false;
            // 加入相同的课程
            for(int j=0;j<cnt_C;++j){
                if(sel[j] == 0)continue;
                else {
                    for(int k=0;k<cnt_C;++k){
                        if(j==k) continue;
                        if(sameCourse(necessary_C[j],necessary_C[k])){
                            Log.i("看看惨状1.5","竟然这都有相同的课程？");
                            sel[k] = 1;
                        }
                    }
                }
            }
            // 判断二进制选择的这些没有冲突
            int numOfCourse = 0;

            for(int j=0;j<cnt_C;++j){
                if(conflict_flag == true)break;
                if(sel[j] == 0)continue;
                else {
                    //```````````````````````````````````冲突判断改进
                    numOfCourse++;
                    for(int k=j+1;k<cnt_C;++k){
                        //............................................
                        if(sel[k] == 0 || j==k)continue;
                        else {
                            numOfCourse += 1;
                            CourseModel a = necessary_C[j];
                            CourseModel b = necessary_C[k];
                            if(timeConflict(a,b) || courseConflict(a,b)){
                                conflict_flag = true;
                                break;
                            }
                        }
                    }
                }
            }
            Log.i("看看循环的情况之numofcourse和max",String.valueOf(numOfCourse)+' '+String.valueOf(MaxOfCourse));
            if(conflict_flag == true)continue;
            else{
                if(numOfCourse > MaxOfCourse){
                    MaxOfCourse = numOfCourse;
                    for(int j=0;j<cnt_C;++j){
                        answer[j] = sel[j];
                    }
                }
            }
            if(MaxOfCourse == cnt_C)break;
        }
        for(int i=0;i<cnt_C;++i){
            if(answer[i] == 1){
                necessary_course.add(necessary_C[i]);
            }
        }
        // ------------NO problem-----------------------
        Log.i("看看惨状1",String.valueOf(necessary_course.size()));
        /*
            necessary_course是已选择的无冲突的必修课程
            下面要做的是选择选修课
            1. 将选修课中与必修课无冲突的课程拿出来
            2. 将已经选出的选修课中相互冲突的去掉
        */
        // 步骤一将与必修课程有冲突的课程去掉
        for(int i=0;i<N;++i){
            sel[i] = 0;
        }
        for(int i=0;i<courseData.length;++i){
            CourseModel a = courseData[i];
            for(int j=0;j<necessary_course.size();++j){
                CourseModel b = necessary_course.get(j);
                if(timeConflict(a,b) || courseConflict(a,b) || sameCourse(a,b)){
                    sel[i] = 1;
                    for(int k=0;k<courseData.length;++k){
                        if(sameCourse(courseData[k],a)){
                            sel[k] = 1;
                        }
                    }
                }
            }
        }
        cnt_C = 0;
        for(int i=0;i<courseData.length;++i){
            if(sel[i] == 0 && courseData[i]!=null){
                necessary_C[cnt_C++] = (courseData[i]);
            }
        }

        Log.i("看看惨状之选修课个数1.5",String.valueOf(cnt_C));
        // 步骤二将已经选出的选修课中相互冲突的去掉

        MaxOfCourse = 0;
        for(int i=1;i<(1<<cnt_C);++i){
            for(int j=0;j<cnt_C;++j)sel[j] = 0;
            for(int j=0;j<cnt_C;++j){
                if((i&(1<<j))>0){
                    sel[j] = 1;
                }
            }
            boolean conflict_flag = false;
            // 加入相同的课程
            for(int j=0;j<cnt_C;++j){
                if(sel[j] == 0)continue;
                else {
                    for(int k=0;k<cnt_C;++k){
                        if(sameCourse(necessary_C[j],necessary_C[k])){
                            sel[k] = 1;
                        }
                    }
                }
            }
            // 判断二进制选择的这些没有冲突
            int numOfCourse = 0;

            for(int j=0;j<cnt_C;++j){
                if(conflict_flag == true)break;
                if(sel[j] == 0)continue;
                else {
                    for(int k=0;k<cnt_C;++k){
                        if(sel[k] == 0 || j==k)continue;
                        else {
                            numOfCourse += 1;
                            CourseModel a = necessary_C[j];
                            CourseModel b = necessary_C[k];
                            if(timeConflict(a,b) || courseConflict(a,b)){
                                conflict_flag = true;
                                break;
                            }
                        }
                    }
                }
            }
            if(conflict_flag == true)continue;
            else{
                if(numOfCourse > MaxOfCourse){
                    MaxOfCourse = numOfCourse;
                    for(int j=0;j<cnt_C;++j){
                        answer[j] = sel[j];
                    }
                }
            }
            if(MaxOfCourse == cnt_C)break;
        }
        for(int i=0;i<cnt_C;++i){
            if(answer[i] == 1){
                necessary_course.add(necessary_C[i]);
            }
        }

        //更改颜色
        int color = 0;
        int[] vis = new int[necessary_course.size()];
        for(int i=0;i<necessary_course.size();++i)vis[i] = 0;
        for(int i=0;i<necessary_course.size();++i){
            if(vis[i] == 1 || necessary_course.get(i)==null) continue;
            int Color = color;
            color += 1;
            necessary_course.get(i).setCourseFlag(Color);
            for(int j=i+1;j<necessary_course.size();++j){
                if(vis[j] == 1)continue;
                if(sameCourse(necessary_course.get(i),necessary_course.get(j))){
                    necessary_course.get(j).setCourseFlag(Color);
                    vis[j] = 1;
                }
            }
        }
        Log.i("看看惨状2",String.valueOf(necessary_course.size()));
        for(int i=0;i<necessary_course.size();++i){
            CourseModel a = necessary_course.get(i);
            Log.i("自动排课的结果中这门课看看",necessary_course.get(i).getCourseName());
            courseModels[a.getWeek()-1].add(a);
        }
        return true;

    }

    /*
        有学分要求
        return true : sucess
        return false : conflict
    */
    public static boolean SetCourseData(CourseModel[] courseData,int credit){
        List<CourseModel> necessary_course = new ArrayList<>();
        List<CourseModel> selected_course = new ArrayList<>();
        //courseModels[0] = new ArrayList[7];
        for (int i = 0; i < courseModels.length; i++) {
            courseModels[i] = new ArrayList<>();
        }
        for(int i=0;i<courseData.length;++i){
            if(courseData[i].getNecessary() == 1){
                necessary_course.add(courseData[i]);
                selected_course.add(courseData[i]);
            }
        }
        // 检查必修的课程中有没有冲突的课程
        for(int i=0;i<necessary_course.size();++i){
            for(int j=0;j<necessary_course.size();++j){
                if(i==j){
                    continue;
                }
                else {
                    CourseModel a = necessary_course.get(i);
                    CourseModel b = necessary_course.get(j);
                    if(timeConflict(a,b) || courseConflict(a,b)){
                        return false;
                    }
                }
            }
        }

        // 将与必修课程有冲突的课程去掉
        for(int i=0;i<courseData.length;++i){
            CourseModel a = courseData[i];
            boolean conflict_flag = false;
            for(int j=0;j<necessary_course.size();++j){
                CourseModel b = necessary_course.get(j);
                if(timeConflict(a,b) || courseConflict(a,b)){
                    conflict_flag = true;
                    break;
                }
            }
            if(conflict_flag == false){
                selected_course.add(a);
            }
        }
        for(int i=0;i<selected_course.size();++i){
            CourseModel a = selected_course.get(i);
            courseModels[a.getWeek()-1].add(a);
        }
        return true;
    }


    public static List<CourseModel>[] getCourseData() {
        return courseModels;
    }
}
