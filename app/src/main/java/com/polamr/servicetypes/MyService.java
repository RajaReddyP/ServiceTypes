package com.polamr.servicetypes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.show("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.show("onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i= 0 ;i<10;i++) {
                    try {
                        Thread.sleep(2000);
                        LogUtils.show("Service running : "+i +"Thread : "+Thread.currentThread().getName());
                    } catch(Exception e) {

                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.show("onBind");
        return null;
    }
}
