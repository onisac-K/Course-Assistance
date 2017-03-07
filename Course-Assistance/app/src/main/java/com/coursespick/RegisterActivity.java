package com.coursespick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eva on 2017/2/12.
 */

public class RegisterActivity extends AppCompatActivity {
    private TextView title;
    private ImageView back;
    private EditText edtUsername,edtUserPwd,edtUserPwd_confirm,edtUseremail;
    private Button btnSubmit;
    private String result="";
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1)
            {
                Toast.makeText(RegisterActivity.this,result,Toast.LENGTH_SHORT);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();
        edtUsername = (EditText)findViewById(R.id.user_name_register);
        edtUserPwd = (EditText)findViewById(R.id.user_password_register);
        edtUserPwd_confirm = (EditText)findViewById(R.id.user_password_confirm_register);
        edtUseremail = (EditText)findViewById(R.id.user_email_register);

        btnSubmit = (Button)findViewById(R.id.submit_register);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Register();
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);

                    }
                }).start();
            }
        });
    }
    public void initToolbar()
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_register);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        title = (TextView)findViewById(R.id.title_toolbar);
        back = (ImageView)findViewById(R.id.back_toolbar);
        setTitle("注册新用户");
        setBackBtn();
    }

    protected void setTitle(String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }
    protected void setBackBtn() {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else {
        }
    }
    public void Register() {
        try {
            String password_confirm = edtUserPwd_confirm.getText().toString();
            String password = edtUserPwd.getText().toString();
            String username = edtUsername.getText().toString();
            String email = edtUseremail.getText().toString();

            if(username==null||"".equals(username.trim())||password==null||"".equals(password.trim())||password_confirm==null||"".equals(password_confirm.trim())){
                Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_SHORT);
                return;
            }
            if(!password.equals(password_confirm))
            {
                Toast.makeText(this,"两次输入密码不相同！",Toast.LENGTH_SHORT);
                return;
            }
            String data = "method=register&username=" + URLEncoder.encode(username, "UTF-8") +
                    "&password=" + URLEncoder.encode(password, "UTF-8") + "&email=" + URLEncoder.encode(email,"UTF-8");
//                Log.d("data",data);
            String httpUrl = "http://192.168.19.101:8080/hello3/LoginServlet";
            Map<String, String> params = new HashMap<String, String>();
            params.put("username",username);
            params.put("password",password);
//                byte [] data = params.toString().getBytes();
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(3000);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));

            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            int response = connection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(is);
                StringBuffer stringBuffer = new StringBuffer();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                result = stringBuffer.toString();

                is.close();
            }


            os.close();
            connection.disconnect();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
