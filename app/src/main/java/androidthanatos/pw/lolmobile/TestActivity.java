package androidthanatos.pw.lolmobile;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import pw.androidthanatos.irouter.BaseActivity;
import pw.androidthanatos.irouter.IRouter;
import pw.androidthanatos.irouter.anotation.Alias;
import pw.androidthanatos.irouter.anotation.BindLayout;

@BindLayout(R.layout.activity_test)
@Alias("test")
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void click(View view){
        IRouter.Builder.build(this).openName("home");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            ModelApplication.init().exit();
        }
        return super.onKeyDown(keyCode, event);
    }
}
