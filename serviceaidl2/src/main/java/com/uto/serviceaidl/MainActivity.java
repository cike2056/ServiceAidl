package com.uto.serviceaidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.uto.servicelink.MyAidlInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                MyAidlInterface myBind = MyAidlInterface.Stub.asInterface(service);
                myBind.startDownload();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_bind:
                Intent intent = new Intent();
                intent.setAction("com.uto.servicelink.MyAidlInterface");
                intent.setPackage("com.uto.servicelink");
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
        }
    }
}
