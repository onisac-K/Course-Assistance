    package com.coursespick;

    import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername,edtUserpwd;
    private Button btnCheck,btnRegisterNew;
    private String result=null;
    private Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Toast.makeText(LoginActivity.this,result,Toast.LENGTH_SHORT);
                    if(result.contains("成功"))
                    {
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG);
                        finish();
                    }
                }
            }
        };
        private ImageView back;
        private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        edtUsername = (EditText)findViewById(R.id.username_login);
        edtUserpwd = (EditText)findViewById(R.id.user_password_login);
        btnRegisterNew = (Button)findViewById(R.id.register_new_login);
        btnRegisterNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnCheck =(Button)findViewById(R.id.check_login);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         Login();
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
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_login);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toolbar.setTitle("");
            title = (TextView)findViewById(R.id.title_toolbar);
            back = (ImageView)findViewById(R.id.back_toolbar);
            setTitle("登录");
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
    public void Login() {
        try {
            String password = edtUserpwd.getText().toString();
            String username = edtUsername.getText().toString();

            if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
                Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_SHORT);
                return;
            }
                String data = "method=login&username=" + URLEncoder.encode(username, "UTF-8") +
                        "&password=" + URLEncoder.encode(password, "UTF-8");
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
