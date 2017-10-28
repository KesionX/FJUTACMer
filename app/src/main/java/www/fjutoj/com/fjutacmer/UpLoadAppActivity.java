package www.fjutoj.com.fjutacmer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import url.MYURL;

public class UpLoadAppActivity extends BaseFragmentActivity implements View.OnClickListener{

    private int versionCode;
    private String versionName;
    private String versionInfo;
    private String apkUrl;
    private TextView versionNameTx;
    private WebView versionDec;
    private Button back;
    private Button upload;
    private Button finish;


    @Override
    protected void rootViewFind() {
        rootView = findViewById(R.id.version_lay);
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_up_load_app_layout);
        InitGetIntent();
        initFindView();
        initData();
        initListener();
    }

    private void initListener() {
        back.setOnClickListener(this);
        upload.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    private void initData() {
        versionNameTx.setText(versionName);
        versionDec.loadDataWithBaseURL(null,versionInfo,"text/html","UTF-8",null);

    }

    private void initFindView() {
        versionNameTx = (TextView) findViewById(R.id.version_name);
        versionDec = (WebView) findViewById(R.id.version_dec);
        back = (Button) findViewById(R.id.back_bt);
        upload = (Button) findViewById(R.id.version_upload);
        finish = (Button) findViewById(R.id.version_finish);
    }

    private void InitGetIntent() {

        Intent intent = getIntent();
        versionCode = intent.getIntExtra(MYURL.VERSION_CODE,0);
        versionName= intent.getStringExtra(MYURL.VERSION_NAME);
        versionInfo = intent.getStringExtra(MYURL.VERSION_INFO);
        apkUrl = intent.getStringExtra(MYURL.APK_URL);
        Log.d("version",versionCode+" "+versionName+" "+versionInfo+" "+apkUrl);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_bt:
                endRootAnimation();
                break;
            case R.id.version_upload:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(MYURL.LOAD_OLD_ROOT_URL+apkUrl));
                startActivity(intent);
                break;
            case R.id.version_finish:
                endRootAnimation();
                break;
        }
    }
}
