package www.fjutoj.com.fjutacmer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BlockDecActivity extends BaseFragmentActivity implements View.OnClickListener{

    private TextView title;
    private Button back;
    private WebView dec;
    private static final String TITLE = "title";
    private static final String TEXT_DEC = "dec";
    private String txTitle;
    private String wbDec;
    @Override
    protected void rootViewFind() {
        rootView = findViewById(R.id.block_dec_id);
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_block_dec);
        InitView();
        InitData();

    }

    private void InitData() {
        Intent intent = getIntent();
        txTitle = intent.getStringExtra(TITLE);
        wbDec = intent.getStringExtra(TEXT_DEC);
        title.setText(txTitle);
        dec.loadDataWithBaseURL(null,wbDec,"text/html","UTF-8",null);
    }

    private void InitView() {
        title = (TextView) findViewById(R.id.block_title);
        dec = (WebView) findViewById(R.id.block_dec);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.back:
               endRootAnimation();
        }
    }
}
