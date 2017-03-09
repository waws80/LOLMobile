package androidthanatos.pw.lolmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import pw.androidthanatos.irouter.BaseActivity;
import pw.androidthanatos.irouter.IRouter;
import pw.androidthanatos.irouter.anotation.Alias;
import pw.androidthanatos.irouter.anotation.BindLayout;
import pw.androidthanatos.irouter.anotation.BindView;
import pw.androidthanatos.irouter.control.Bind;

@BindLayout(R.layout.activity_main)
@Alias("home")
public class MainActivity extends BaseActivity {
    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExceptionView(R.layout.error_base);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setShowActionBar(true);
        setShowSwipRefresh(true);
        ActionBar actionBar = getToolBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTextMessage.setOnClickListener((v)-> IRouter.Builder.build(MainActivity.this).addInterrupter(()->false).openName("test"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            ModelApplication.init().exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void reFreshListener(SwipeRefreshLayout mSwipRefresh) {
        super.reFreshListener(mSwipRefresh);
        Log.w(TAG, "reFreshListener: " );
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                mSwipRefresh.setRefreshing(false);
                setContentView(R.layout.activity_main);
                Bind.init(MainActivity.this);
            });
        }).start();
    }
}
