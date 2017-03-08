package androidthanatos.pw.lolmobile;

import android.os.Bundle;
import android.view.View;

import pw.androidthanatos.irouter.BaseActivity;
import pw.androidthanatos.irouter.IRouter;
import pw.androidthanatos.irouter.anotation.Alias;
import pw.androidthanatos.irouter.anotation.BindLayout;
import pw.androidthanatos.irouter.utils.ClassRunning;

@BindLayout(R.layout.activity_test)
@Alias("test")
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void click(View view){
        //IRouter.Builder.build(this).openName("home");
        ClassRunning.isRunning(TestActivity.this);
    }
}
