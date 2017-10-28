package www.fjutoj.com.fjutacmer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Administrator on 2016/10/3.
 */
public abstract class BaseFragmentActivity extends FragmentActivity{
    public View rootView;
    public Animation animationBegin;
    public Animation animationEnd;
    public Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        onBaseCreate(savedInstanceState);

        rootViewFind();
        InitAnima();
        startRootAnimation(savedInstanceState);
    }


    protected abstract void rootViewFind();

    private void InitAnima(){
        animationBegin = AnimationUtils.loadAnimation(this,R.anim.tran_in_amian2);
        animationEnd = AnimationUtils.loadAnimation(this,R.anim.tran_out_amian2);
    };

    public abstract void onBaseCreate(Bundle savedInstanceState);

    public void startRootAnimation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rootView.setAnimation(animationBegin);
                    return true;
                }
            });
        }
    }

    public void endRootAnimation() {
        animationEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                context.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animationEnd);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        endRootAnimation();

        return false;
    }
}
