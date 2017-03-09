package pw.androidthanatos.irouter;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import pw.androidthanatos.irouter.control.Bind;

/**
 * 基类
 * Created by lxf52 on 2017/3/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private int baseLayoutId=R.layout.activity_base;

    protected FrameLayout contentView;

    private boolean showActionBar=true;

    private boolean showSwipRefresh=true;

    protected AppBarLayout mAppBar;

    private Toolbar mToolBar;

    private SwipeRefreshLayout mSwipRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(baseLayoutId);
        Bind.init(this);

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(baseLayoutId==layoutResID){
            super.setContentView(baseLayoutId);
            contentView= (FrameLayout) findViewById(R.id.contentView);
            mAppBar= (AppBarLayout) findViewById(R.id.base_appbar);
            mToolBar = (Toolbar) findViewById(R.id.base_toolbar);
            mSwipRefresh= (SwipeRefreshLayout) findViewById(R.id.base_refresh);
            contentView.removeAllViews();
            mSwipRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    reFreshListener(mSwipRefresh);
                }
            });
        }else {
            for (int i = 1; i < contentView.getChildCount(); i++) {
                contentView.removeViewAt(i);
            }
            if (contentView.getChildCount()==0){
                View view = View.inflate(this, layoutResID, null);
                contentView.addView(view,0);
            }else {
                if (contentView.getChildAt(0).getVisibility()==View.GONE){
                    contentView.getChildAt(0).setVisibility(View.VISIBLE);
                }

            }

        }

    }

    protected void reFreshListener(SwipeRefreshLayout mSwipRefresh) {

    }

    public boolean isShowActionBar() {
        return showActionBar;
    }

    public void setShowActionBar(boolean showActionBar) {
        this.showActionBar = showActionBar;
        if (showActionBar ){
            mAppBar.setVisibility(View.VISIBLE);

        }else {
            mAppBar.setVisibility(View.GONE);
        }

    }

    public boolean isShowSwipRefresh() {
        return showSwipRefresh;
    }

    public void setShowSwipRefresh(boolean showSwipRefresh) {
        this.showSwipRefresh = showSwipRefresh;
        mSwipRefresh.setEnabled(showSwipRefresh);

    }

    public ActionBar getToolBar() {
        setSupportActionBar(mToolBar);
        return getSupportActionBar();
    }

    public void setExceptionView(@LayoutRes int layoutResID ){
        if (null!=contentView){
            View view = View.inflate(this, layoutResID, null);
            for (int i = 1; i < contentView.getChildCount(); i++) {
                contentView.removeViewAt(i);
            }
            contentView.getChildAt(0).setVisibility(View.GONE);
            contentView.addView(view,contentView.getChildCount());
        }

    }


}
