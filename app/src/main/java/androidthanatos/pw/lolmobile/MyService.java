package androidthanatos.pw.lolmobile;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import pw.androidthanatos.irouter.anotation.Alias;
@Alias("service")
public class MyService extends Service {
    private static final String TAG = "MyService";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate: 我是服务" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy: " );
    }
}
