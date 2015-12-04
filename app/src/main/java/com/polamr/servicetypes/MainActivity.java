package com.polamr.servicetypes;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean isBound = false;
    Messenger mMessenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.show("onServiceConnected component : "+name);
            isBound = true;

            // Create the Messenger object
            mMessenger = new Messenger(service);

            // Create a Message
            // Note the usage of MSG_SAY_HELLO as the what value
            Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);

            // Create a bundle with the data
            Bundle bundle = new Bundle();
            bundle.putString("hello", "world");

            // Set the bundle data to the Message
            msg.setData(bundle);

            // Send the Message to the Service (in another process)
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.show("onServiceDisconnected component : "+name);
            mMessenger = null;
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startSer = (Button) findViewById(R.id.start_service);
        startSer.setOnClickListener(this);

        Button bindSer = (Button) findViewById(R.id.bind_service);
        bindSer.setOnClickListener(this);

        Button is = (Button) findViewById(R.id.intent_service);
        is.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.intent_service:
                Intent intent1 = new Intent(this, MyIntentService.class);
                intent1.setAction(MyIntentService.ACTION_FOO);
                intent1.setAction(MyIntentService.ACTION_BAZ);
                intent1.putExtra(MyIntentService.EXTRA_PARAM1, "Param a");
                intent1.putExtra(MyIntentService.EXTRA_PARAM2, "Param b");
                startService(intent1);
                break;
            case R.id.bind_service:
                Intent intent2 = new Intent(this, MessengerService.class);
                bindService(intent2, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
        }
    }
}
