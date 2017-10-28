package fg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import Catch.MyImageLoader;
import bean.SingleMegSelf;
import tool.IsNewMessage;
import ui.CircleImageView;
import url.MYURL;
import www.fjutoj.com.fjutacmer.LoginActivity;
import www.fjutoj.com.fjutacmer.MegSelfActivity;
import www.fjutoj.com.fjutacmer.MyMessageActivity;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/24.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener{

    private CircleImageView circleImageView;
    private TextView nick;
    private TextView acb;
    private TextView motto;
    private ImageLoader imageLoader;
    private View layoutMegSelf;
    private View layoutMyMessage;
    private View layoutChangeAccount;
    private View layoutFinish;
    private TextView newMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.menu_layout,null);
    }

    @Override
    public void InitFindView() {
        circleImageView = (CircleImageView) rootView.findViewById(R.id.menu_touxiang);
        nick = (TextView) rootView.findViewById(R.id.menu_nick);
        acb = (TextView) rootView.findViewById(R.id.menu_acb);
        motto = (TextView) rootView.findViewById(R.id.menu_mottor);
        newMessage  = (TextView) rootView.findViewById(R.id.menu_new_message);
        layoutMegSelf = rootView.findViewById(R.id.menu_megself);
        layoutMegSelf.setClickable(true);
        layoutMyMessage = rootView.findViewById(R.id.menu_mymessage);
        layoutMyMessage.setClickable(true);
        layoutChangeAccount = rootView.findViewById(R.id.menu_change_account);
        layoutChangeAccount.setClickable(true);
        layoutFinish = rootView.findViewById(R.id.menu_finish);
        layoutFinish.setClickable(true);
        InitListener();
        InitData();
    }

    @Override
    public void InitData() {
        nick.setText(SingleMegSelf.getSingleMegSelf().getNick());
        acb.setText(SingleMegSelf.getSingleMegSelf().getAcb()+"");
        motto.setText(SingleMegSelf.getSingleMegSelf().getMotto());
        imageLoader = MyImageLoader.getMyImageLoader(getContext());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(circleImageView,
                R.mipmap.default_touxiang,R.mipmap.default_touxiang);
        imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+
                SingleMegSelf.getSingleMegSelf().getUsername()+".jpg",imageListener);

        IsNewMessage isNewMessage = IsNewMessage.getIsNewMessage(getActivity(),IsNewMessage.IS_NEW_MESSAGE_PRE);
        if(isNewMessage.getIsNew()){
            newMessage.setVisibility(View.VISIBLE);
        }else{
            newMessage.setVisibility(View.INVISIBLE);
        }

      //  Log.d("main",SingleMegSelf.getSingleMegSelf().toString());
     //   InitListener();
    }

    private void InitListener() {
        layoutMegSelf.setOnClickListener(this);
        layoutMyMessage.setOnClickListener(this);
        layoutChangeAccount.setOnClickListener(this);
        layoutFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case   R.id.menu_megself :
                Intent intent = new Intent(getContext(),MegSelfActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_mymessage:
                IsNewMessage isNewMessage = IsNewMessage.getIsNewMessage(getActivity(),IsNewMessage.IS_NEW_MESSAGE_PRE);
                isNewMessage.setPreNewMessage(false);
                Intent intent2 = new Intent(getContext(),MyMessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_change_account:
                Intent intent3 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent3);
                getActivity().finish();
                break;
            case R.id.menu_finish:
                getActivity().finish();
                break;
        }
    }
}
