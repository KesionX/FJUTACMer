package www.fjutoj.com.fjutacmer;

import android.animation.Animator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import persenter.persenterInterface.loginpersenter;
import persenter.regPersenterImpl;
import tool.StatusColor;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.view.RegView;

public class RegActivity extends FragmentActivity implements RegView, View.OnClickListener {

    EditText account;
    EditText pass;
    EditText nick;
    EditText email;
    Switch gender;
    EditText school;
    EditText motto;
    EditText rpass;

    Button back;
    Button reg;
    int isNorV = 1;
    private regPersenterImpl regPersenter;
    View rootView;
    Activity activity;
    Animation animation;
    Animation animation2;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusColor.setTranslucentStatus(this,R.color.colortransparent);
        setContentView(R.layout.register_layout);
        rootView = findViewById(R.id.reg_layout_root);
        animation = AnimationUtils.loadAnimation(this,R.anim.tran_in_amain);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.tran_out_amian);

        if (savedInstanceState == null) {
            rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                    startRootAnimation();
                    return true;
                }
            });
        }
        InitView();
        InitListener();
        InitData();

    }

    private void startRootAnimation() {
        rootView.setAnimation(animation);
    }

    private void endRootAnimation() {
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activity.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animation2);

    }
    private void InitData() {
        regPersenter = new regPersenterImpl(this);
        activity = this;
    }

    private void InitListener() {
        reg.setOnClickListener(this);
        back.setOnClickListener(this);
        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isNorV = 2;
                }else{
                    isNorV = 1 ;
                }
            }
        });
    }


    private void InitView() {
        account = (EditText) findViewById(R.id.reg_account);
        nick = (EditText) findViewById(R.id.reg_nick);
        email = (EditText) findViewById(R.id.reg_email);
        gender = (Switch) findViewById(R.id.reg_gender);
        school = (EditText) findViewById(R.id.reg_school);
        motto = (EditText) findViewById(R.id.reg_motto);
        pass = (EditText) findViewById(R.id.reg_pass);
        rpass = (EditText) findViewById(R.id.reg_rpass);

        back = (Button) findViewById(R.id.reg_back);
        reg = (Button) findViewById(R.id.reg_begin);
        dialog = new ProgressDialog(this);
    }

    private void startDialog(){

        //dialog.set
        dialog =  ProgressDialog.show(this, null, "正在注册中",
                false, false);

    }

    private void exitDialog(){
        dialog.dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_back :
                rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        rootView.getViewTreeObserver().removeOnPreDrawListener(this);

                        return true;
                    }
                });
                endRootAnimation();
               // finish();
                break;
            case R.id.reg_begin:

                regPersenter.requestReg(account.getText().toString(),pass.getText().toString(),rpass.getText().toString()
                        ,nick.getText().toString(), isNorV,school.getText().toString(),email.getText().toString(),motto.getText().toString());
                startDialog();
                break;
        }
    }
    @Override
    public void regSuccess() {
        exitDialog();
        MyToast.ShowToast(this,"注册成功", Toast.LENGTH_LONG);
    }

    @Override
    public void regUsernameExist() {
        exitDialog();
        MyToast.ShowToast(this,"此账号以存在", Toast.LENGTH_LONG);
    }

    @Override
    public void regFail() {
        exitDialog();
        MyToast.ShowToast(this,"密码重复输入错误", Toast.LENGTH_LONG);
    }

    @Override
    public void regAccountFormatError() {

    }

    @Override
    public void regPassFormatError() {

    }

    @Override
    public void regNickFormatError() {

    }

    @Override
    public void regSchoolFormatError() {

    }

    @Override
    public void regMottoFormatError() {

    }

    @Override
    public void regRpassError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        regPersenter.destory();
        account = null;
        pass = null;
        nick = null;
        email = null;
        gender = null;
        school = null;
        motto = null;
        rpass = null;
        back = null;
        reg = null;
        regPersenter = null;
        rootView=null;

       activity = null;
       animation = null;
        animation2 = null;
    }
}
