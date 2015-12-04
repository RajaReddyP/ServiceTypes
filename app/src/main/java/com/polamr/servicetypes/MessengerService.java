package com.polamr.servicetypes;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;


public class MessengerService extends Service {

    static final int MSG_SAY_HELLO = 1;
    Messenger mMessenger = new Messenger(new IncomingHandler());
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Bundle bundle = msg.getData();
                    String hello = (String) bundle.get("hello");
                    LogUtils.show("hello : "+hello);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.show("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.show("onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.show("onBind");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.show("onUnBind");
        return false;
    }
}
