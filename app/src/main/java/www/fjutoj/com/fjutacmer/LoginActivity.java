package www.fjutoj.com.fjutacmer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import persenter.loginPersenterimpl;
import persenter.persenterInterface.loginpersenter;
import tool.LogPreferences;
import tool.StatusColor;
import ui.CircleImageView;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.view.LoginView;

public class LoginActivity extends FragmentActivity implements View.OnClickListener,LoginView{

    private CircleImageView touXImage;
    private EditText etAccount;
    private EditText etPass;
    private Button loginButton;
    private Button registerButton;
    private loginpersenter loginpersener;

    private  Context context;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusColor.setTranslucentStatus(this,R.color.colorblue2);
        setContentView(R.layout.login_layout);
        context = this;

        InitView();
        InitData();
        InitListener();

    }

    private void InitListener() {
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                loginpersener.setTouXiang(touXImage,etAccount.getText().toString()+".jpg");
            }
        });
    }

    Handler handler = null;
    private void InitData() {
        handler = new Handler();
        loginpersener = new loginPersenterimpl(this,this,handler);

    }


    /***
     * 初始化View
     * **/
    private void InitView() {
        touXImage = (CircleImageView) findViewById(R.id.login_touxiang);
        etAccount  = (EditText) findViewById(R.id.et_account);
        etPass = (EditText) findViewById(R.id.et_pass);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton  = (Button) findViewById(R.id.register_button);
        //progressBar = (ProgressBar) findViewById(R.id.log_proBar);
    }

    private void startDialog(){
        dialog = new ProgressDialog(this);
        //dialog.set
        dialog =  ProgressDialog.show(this, null, "正在登陆中",
                false, false);

    }

    private void exitDialog(){
        dialog.dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                startDialog();
               // Toast.makeText(this,etAccount.getText().toString(),Toast.LENGTH_LONG).show();
                Log.d("main",etAccount.getText().toString()+"  "+etPass.getText().toString());
                loginpersener.Login(etAccount.getText().toString(),etPass.getText().toString());
               // progressBar.setVisibility(View.VISIBLE);
               // rootview.set
                break;
            case R.id.register_button:
                Intent intent = new Intent(this,RegActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loginSuccess() {
        exitDialog();
       // progressBar.setVisibility(View.INVISIBLE);
        LogPreferences logPreferences = LogPreferences.getLogPreferences(this,LogPreferences.AP_PRE);
        logPreferences.putSharedPreferences(etAccount.getText().toString(),etPass.getText().toString(),true);
        MyToast.ShowToast(this,"登陆成功",Toast.LENGTH_LONG);

        JPushInterface.setAlias(this,etAccount.getText().toString(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });


        Intent intent = new Intent(this,TabHostActivity.class);
        startActivity(intent);
        finish();
      //  Toast.makeText(context,"login success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void WrongPassword() {
        exitDialog();
       // progressBar.setVisibility(View.INVISIBLE);
        MyToast.ShowToast(this,"密码错误",Toast.LENGTH_LONG);
      //  Toast.makeText(context,"wrong password",Toast.LENGTH_LONG).show();
    }

    @Override
    public void NoSuchUser() {
        exitDialog();
      //  progressBar.setVisibility(View.INVISIBLE);
        MyToast.ShowToast(this,"没有此账号",Toast.LENGTH_LONG);
       // Toast.makeText(context,"no such user",Toast.LENGTH_LONG).show();
    }

    @Override
    public void SystemError() {
        dialog.dismiss();
       // progressBar.setVisibility(View.INVISIBLE);
        MyToast.ShowToast(this,"系统错误",Toast.LENGTH_LONG);
       // Toast.makeText(context,"system error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void accountFormatError() {

    }

    @Override
    public void passFormatError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginpersener.destory();
        touXImage = null;
        etAccount = null;
        etPass = null;
        loginButton = null;
        registerButton = null;
        loginpersener = null;
        context = null;
        dialog = null;

    }
}
