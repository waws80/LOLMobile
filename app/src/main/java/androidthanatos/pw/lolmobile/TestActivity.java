package androidthanatos.pw.lolmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
