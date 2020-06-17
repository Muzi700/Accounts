package com.example.accounts.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.accounts.R;
import com.example.accounts.base.DatabaseUtil;
import com.example.accounts.bean.Student;
import com.example.accounts.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_login_name;
    private EditText et_login_password;
    private Button btnLogin;
    private Button btn_login_Re;

    private EditText et_re_name;
    private EditText et_re_password;
    private Button btnRe;
    TabHost tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tab = (TabHost) findViewById(android.R.id.tabhost);

        //初始化TabHost容器
        tab.setup();
        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tab.addTab(tab.newTabSpec("tab1").setIndicator("用户登录" , null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("用户注册" , null).setContent(R.id.tab2));
        et_login_name = findViewById(R.id.et_login_name);
        et_login_password = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btnLogin);
        btn_login_Re = findViewById(R.id.btn_Login_Re);
        et_re_name = findViewById(R.id.et_re_name);
        et_re_password = findViewById(R.id.et_re_password);
        btnRe = findViewById(R.id.btnRe);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_login_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startregister();
            }
        });

        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    //注册
    private void register() {
        String name = et_re_name.getText().toString().trim();
        String pasw = et_re_password.getText().toString().trim();
        if (name.isEmpty()){
            showMsg("用户名不能为空");
        }else if (pasw.isEmpty()){
            showMsg("密码不能为空");
        }else {
            Student student = new Student();
            student.setName(name);
            student.setPassword(pasw);
            Student user = DatabaseUtil.getInstance().queryStudent(name);
            if (user.getName()==null){
                long flag = DatabaseUtil.getInstance().inisertStudent(student);
                if (flag == 1){
                    showMsg("注册成功");
                    et_re_name.getText().clear();
                    et_re_password.getText().clear();
                    tab.setCurrentTab(0);
                }
            }else {
                showMsg("该用户已存在");
            }

        }
    }

    //调转到注册
    private void startregister() {
        tab.setCurrentTab(1);
    }
    //登录
    private void login() {
        String name = et_login_name.getText().toString().trim();
        String pasw = et_login_password.getText().toString().trim();
        if (name.isEmpty()){
            showMsg("用户名不能为空");
        }else if (pasw.isEmpty()){
            showMsg("密码不能为空");
        }else {
            Student student = DatabaseUtil.getInstance().queryStudent(name);
            if (student.getName()==null){
                showMsg("该用户还未注册");
            }else {
                boolean flag = DatabaseUtil.getInstance().isLogin(name,pasw);
                if (flag){
                    showMsg("登录成功");
                    SPUtils.put(MainActivity.this,"name",name);
                    Intent intent  = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    showMsg("用户或密码不正确");
                }
            }
        }
    }

    private void showMsg(String msg){
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}