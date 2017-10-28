package fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by Administrator on 2016/9/24.
 */
public abstract class  BaseFragment extends Fragment{

    public View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getRootView(inflater);
    }

    private  View getRootView(LayoutInflater inflater) {
        if(rootView==null){
            rootView = InitView(inflater);
            InitFindView();
        }
        ViewGroup viewGroup = (ViewGroup) rootView.getParent();
        if(viewGroup!=null) {
            viewGroup.removeView(rootView);
        }

        return rootView;
     }

    public abstract View InitView(LayoutInflater inflate);
    public abstract void InitFindView();
    public abstract void InitData();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActivityCreated();
    }

    public  void ActivityCreated(){

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    /**
     *设置下拉刷新headview
     **/
    public void initRefreshHead(PtrFrameLayout ptrFrameLayout) {
        final StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        header.initWithString("FJUT ACMER...");
        header.setTextColor(0x880ea);
        header.setDropHeight(140);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
    }
}
