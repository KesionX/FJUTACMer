package www.fjutoj.com.fjutacmer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bean.ItemProblem;
import persenter.ItemProblemPersenterImpl;
import persenter.loginPersenterimpl;
import persenter.persenterInterface.ItemProblemPersenter;
import persenter.persenterInterface.loginpersenter;
import tool.LogPreferences;
import tool.StatusColor;
import ui.MyToast;
import ui.SildingFinishLayout;
import www.fjutoj.com.fjutacmer.view.ItemProblemView;
import www.fjutoj.com.fjutacmer.view.LoginView;

public class MyProblemActivity extends BaseFragmentActivity implements ItemProblemView,View.OnClickListener ,AdapterView.OnItemSelectedListener,LoginView,SildingFinishLayout.OnSildingFinishListener{

    private TextView titleName;
    private TextView title;
    private TextView pid;
    private TextView int64;
    private TextView timelimit;
    private TextView memoryLimit;
    private TextView oj;
    private TextView ojid;
    private WebView dec;
    private WebView input;
    private WebView output;
    private EditText code_et;
    private Button submit_bt;
    private Button back_bt;
    private LinearLayout linearLayoutInput;
    private LinearLayout linearLayoutOutput;
    private Spinner languageSpinner;
    private ArrayAdapter<?> languageSpinnerAdapter;

    private int pidnum ;
    private int lang = 0;
    private static final String PID = "pid";
    private ItemProblemPersenter itemProblemPersenter;
    private Handler handler;
    private Activity context;

    private loginpersenter mLoginpersenter;
    private String account;
    private String ps;

    private SildingFinishLayout mSildingFinishLayout;
    private ScrollView sv;
    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.myproblem_meg_layout);
        InitViewFind();
        InitData();
        StatusColor.setTranslucentStatus(this,R.color.colortransparent);
    }

    private void InitData() {
        context = this;
        handler  = new Handler();
        Intent intent = getIntent();
        pidnum = intent.getIntExtra(PID,1001);
        itemProblemPersenter = new ItemProblemPersenterImpl(this,handler);
        itemProblemPersenter.getProblemData(pidnum);
        InitLangSpinner();
        mLoginpersenter = new loginPersenterimpl(this,this,handler);
        LogPreferences logPreferences = LogPreferences.getLogPreferences(this,LogPreferences.AP_PRE);
        account = logPreferences.getAt();
        ps = logPreferences.getPs();
    }

    private void InitLangSpinner() {
        languageSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.language_list,R.layout.spinner);
        languageSpinnerAdapter.setDropDownViewResource(R.layout.spinner_down);
        languageSpinner.setAdapter(languageSpinnerAdapter);
    }

    private void InitViewFind() {
        titleName = (TextView) findViewById(R.id.myproblem_meg_titlename);
        title  = (TextView) findViewById(R.id.myproblem_meg_title);
        pid = (TextView) findViewById(R.id.myproblem_meg_pid);
        int64 = (TextView) findViewById(R.id.myproblem_meg_int64);
        timelimit = (TextView) findViewById(R.id.myproblem_meg_timelimit);
        memoryLimit = (TextView) findViewById(R.id.myproblem_meg_memorylimit);
        oj = (TextView) findViewById(R.id.myproblem_meg_oj);
        ojid = (TextView) findViewById(R.id.myproblem_ojid);
        dec = (WebView) findViewById(R.id.myproblem_meg_dec);
        input = (WebView) findViewById(R.id.myproblem_meg_input);
        output = (WebView) findViewById(R.id.myproblem_meg_output);
        code_et = (EditText) findViewById(R.id.myproblem_meg_code_et);
        submit_bt = (Button) findViewById(R.id.myproblem_meg_submit_bt);
        back_bt = (Button) findViewById(R.id.myproblem_meg_back_bt);
        linearLayoutInput = (LinearLayout) findViewById(R.id.myproblem_meg_sampleinput_linear);
        linearLayoutOutput = (LinearLayout) findViewById(R.id.myproblem_meg_sampleoutput_linear);
        languageSpinner = (Spinner) findViewById(R.id.myproblem_meg_lang_spinner);

        mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sfl);
        sv = (ScrollView) findViewById(R.id.myproblem_meg_layout_id);
        mSildingFinishLayout.setTouchView(sv);
        mSildingFinishLayout.setOnSildingFinishListener(this);

        submit_bt.setOnClickListener(this);
        back_bt.setOnClickListener(this);
        languageSpinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void rootViewFind() {
        rootView = findViewById(R.id.myproblem_meg_layout_id);
    }

    @Override
    public void getProblemDataSuccess(ItemProblem itemProblem) {
        titleName.setText(itemProblem.getTitle());
        title.setText(itemProblem.getTitle());
        pid.setText(itemProblem.getPid()+"");
        int64.setText(itemProblem.getInt64());
        timelimit.setText(itemProblem.getTimelimit());
        memoryLimit.setText(itemProblem.getMemoryLimit());
        oj.setText(itemProblem.getOj());
        ojid.setText(itemProblem.getOjid()+"");
        dec.loadDataWithBaseURL(null,itemProblem.getDes(),"text/html","UTF-8",null);
        input.loadDataWithBaseURL(null,itemProblem.getInput(),"text/html","UTF-8",null);
        output.loadDataWithBaseURL(null,itemProblem.getOutput(),"text/html","UTF-8",null);
        ArrayList<String > inputs = itemProblem.getSampleinput();
        ArrayList<String > outputs = itemProblem.getSampleoutput();
        for(int i=0,n=inputs.size();i<n;++i){
            WebView webView = new WebView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            webView.setLayoutParams(params);
            webView.setPadding(5,2,5,5);
            linearLayoutInput.addView(webView);
            webView.loadDataWithBaseURL(null,inputs.get(i),"text/html","UTF-8",null);
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
            textView.setLayoutParams(params2);
            textView.setBackgroundResource(R.color.colorgray);
            linearLayoutInput.addView(textView);
        }

        for(int i=0,n=outputs.size();i<n;++i){
            WebView webView = new WebView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            webView.setLayoutParams(params);
            webView.setPadding(5,2,5,5);
            linearLayoutOutput.addView(webView);
            webView.loadDataWithBaseURL(null,outputs.get(i),"text/html","UTF-8",null);
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1);
            textView.setLayoutParams(params2);
            textView.setBackgroundResource(R.color.colorgray);
            linearLayoutOutput.addView(textView);
        }
    }

    @Override
    public void getProblemDataFall() {
        MyToast.ShowToast(context,"加载错误", Toast.LENGTH_LONG);
    }

    @Override
    public void submitSuccess() {
        MyToast.ShowToast(context,"提交成功，请在测试页面查看测试结果！",Toast.LENGTH_LONG);
    }

    @Override
    public void submitFall(String error) {
        MyToast.ShowToast(context,error,Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        titleName = null;
        title = null;
        pid = null;
        int64  = null;
        timelimit = null;
        memoryLimit = null;
        oj = null;
        ojid = null;
        dec = null;
        input = null;
        output = null;
        code_et = null;
        submit_bt = null;
        linearLayoutInput = null;
        linearLayoutOutput = null;

        itemProblemPersenter.destory();
        handler = null;
        itemProblemPersenter=null;
        context =  null ;
    }

    @Override
    public void onClick(View v) {
        String code = code_et.getText().toString();
        switch (v.getId()){
            case R.id.myproblem_meg_submit_bt:
                if(code.length()>20) {
                    mLoginpersenter.Login(account, ps);
                    //   itemProblemPersenter.submitCode(pidnum,code,lang,-1);
                }else{
                    MyToast.ShowToast(this,"代码过短...",Toast.LENGTH_LONG);
                }

                break;
            case R.id.myproblem_meg_back_bt:
                endRootAnimation();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        lang = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void loginSuccess() {
        String code = code_et.getText().toString();
        itemProblemPersenter.submitCode(pidnum,code,lang,-1);
    }

    @Override
    public void WrongPassword() {

    }

    @Override
    public void NoSuchUser() {

    }

    @Override
    public void SystemError() {

    }

    @Override
    public void accountFormatError() {

    }

    @Override
    public void passFormatError() {

    }

    @Override
    public void onSildingFinish() {
        finish();
    }
}
