package androidthanatos.pw.lolmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pw.androidthanatos.irouter.BaseActivity;
import pw.androidthanatos.irouter.IRouter;
import pw.androidthanatos.irouter.anotation.Alias;
import pw.androidthanatos.irouter.anotation.BindLayout;
import pw.androidthanatos.irouter.anotation.BindView;
import pw.androidthanatos.irouter.control.Interceptor;
import pw.androidthanatos.irouter.utils.ClassRunning;
import pw.androidthanatos.irouter.utils.ClassType;

@BindLayout(R.layout.activity_main)
@Alias("home")
public class MainActivity extends BaseActivity {
    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTextMessage.setOnClickListener((v)-> {
                IRouter.Builder.build(MainActivity.this).addInterrupter(()->false).openName("test");
               // finish();
        });
    }

}
