package com.coursespick.fragment.realCourse;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangziqiang on 2017/2/21.
 */

public class PostCourseData {
    private static String rootUrl = "http://shucourse.stevenming.com.cn/";
    /**
     * 给定课号，教师号post课程，单点查询，返回一门课answer
     *
     */
    public static realCourseData course_inf(String cid,String tid){
        String url = rootUrl + "course_info";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cId",cid);
        params.put("tId",tid);
        String result = requestPostBySynWithForm(url,params);
        Log.d("1",result);
        int startpos = result.indexOf(":");
        int endpos = result.lastIndexOf(",");
        String res =  result.substring(startpos+1,endpos);
        Log.d("1",res);
        realCourseData answer =  new realCourseData();
        try{
            //JSONArray jsonArray = new JSONArray(res);
            JSONObject jsonObject = new JSONObject(res);
            answer.setcId(jsonObject.getString("cId"));
            answer.setcName(jsonObject.getString("cName"));
            answer.setcCedit(jsonObject.getString("cCredit"));
            answer.settId(jsonObject.getString("tId"));
            answer.settName(jsonObject.getString("tName"));
            answer.setcTime(jsonObject.getString("cTime"));
            answer.setcLocation(jsonObject.getString("cLocation"));
            answer.setcSize(jsonObject.getString("cSize"));
            answer.setcNumber(jsonObject.getString("cNumber"));
            answer.setcCampus(jsonObject.getString("cCampus"));
            answer.setcLimit(jsonObject.getString("cLimit"));
            answer.setQaTime(jsonObject.getString("qaTime"));
            answer.setQaLocation(jsonObject.getString("qaLocation"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return  answer;
    }


    /**
     *  模糊查询,输入任意多个信息，返回所有满足条件的课程数组answer2[]
     */
    public static realCourseData[] course_list(String cId, String cName, String cCredit, String tId, String tName,
                                               String cTime, String cLocation, String cSize, String cNumber, String cCampus, String cLimit,
                                               String qaTime, String qaLocation){
        String url = rootUrl + "course_list";
        Map<String, String> params = new HashMap<String, String>();
        if(cId != null) params.put("cId", cId);
        if(cName!=null) params.put("cName", cName);
        if(cCredit!=null)  params.put("cCredit", cCredit);
        if(tId != null) params.put("tId", tId);
        if(tName != null) params.put("tName", tName);
        if(cTime != null)params.put("cTime", cTime);
        if(cLocation != null) params.put("cLocation", cLocation);
        if(cSize != null)params.put("cSize", cSize);
        if(cNumber != null)params.put("cNumber", cNumber);
        if(cCampus != null)params.put("cCampus", cCampus);
        if(cLimit != null) params.put("cLimit", cLimit);
        if(qaTime != null)params.put("qaTime", qaTime);
        if(qaLocation != null)params.put("qaLocation", qaLocation);
        String result = requestPostBySynWithForm(url,params);
        int startpos = result.indexOf("[");
        int endpos = result.lastIndexOf("]");
        String res =  result.substring(startpos,endpos+1);
        int totcount = 0;
        try{
            JSONArray jsonArray = new JSONArray(res);
            totcount = jsonArray.length();
        }catch (Exception e){
            e.printStackTrace();
        }
        realCourseData[] answer2 = new realCourseData[totcount];
        try{
            JSONArray jsonArray = new JSONArray(res);
            Log.d("1",String.valueOf(jsonArray.length()));
            for(int i=0;i<jsonArray.length();++i){
                realCourseData answer = new realCourseData();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                answer.setcId(jsonObject.getString("cId"));
                answer.setcName(jsonObject.getString("cName"));
                answer.setcCedit(jsonObject.getString("cCredit"));
                answer.settId(jsonObject.getString("tId"));
                answer.settName(jsonObject.getString("tName"));
                answer.setcTime(jsonObject.getString("cTime"));
                answer.setcLocation(jsonObject.getString("cLocation"));
                answer.setcSize(jsonObject.getString("cSize"));
                answer.setcNumber(jsonObject.getString("cNumber"));
                answer.setcCampus(jsonObject.getString("cCampus"));
                answer.setcLimit(jsonObject.getString("cLimit"));
                answer.setQaTime(jsonObject.getString("qaTime"));
                answer.setQaLocation(jsonObject.getString("qaLocation"));
                answer2[i] = answer;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return answer2;
    }

    public static String requestPostBySynWithForm(String actionUrl, Map<String, String> paramsMap) {
        String str = null;
        try {
            //创建一个FormBody.Builder
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //创建一个请求
            Request request = new Request.Builder()
                    .url(actionUrl).post(formBody).build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                str = response.body().string();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return str;
    }
}
