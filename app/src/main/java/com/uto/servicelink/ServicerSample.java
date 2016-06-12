package com.uto.servicelink;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 项目名称：ServiceLink
 * 类描述：
 * 创建人：steven
 * 创建时间：2016/6/12 11:03
 * 修改人：Administrator
 * 修改时间：2016/6/12 11:03
 * 修改备注：
 */
public class ServicerSample extends Service {

    private MyAidlInterface.Stub myBinder = new MyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void startDownload() throws RemoteException {
            Log.e(getClass().getSimpleName(), " startDownload stub");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(getClass().getSimpleName(), "onCreate");
        Log.e(getClass().getSimpleName(), "currentThread: " + Thread.currentThread().getId());
        Notification.Builder notification = new Notification.Builder(getApplicationContext());
        notification.setSmallIcon(R.mipmap.add_icon).setContentTitle("有通知到来").setWhen(System.currentTimeMillis()).setContentText("这是通知的内容");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        notification.setContentIntent(pendingIntent);
        startForeground(1, notification.build());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getHaveNewMessage();
            }
        }, 0, 3000);
    }

    private Timer timer = new Timer();
    private static final String TOKEN = "665a525cb08fcdd189539646e7ab237d";
    private static int num = 0;

    /**
     * 判断有没有新消息消息
     */
    private void getHaveNewMessage() {
        num++;
        Log.e(getClass().getSimpleName(), "num " + num + "");
        HttpClientUtils.get(Apiconfig.UTO_HAVA_NEW_MESSAGE,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable arg0, String arg1) {
                        super.onFailure(arg0, arg1);
                        Log.e(getClass().getSimpleName(), "onFailure " + num);
                    }

                    @Override
                    public void onSuccess(int arg0, String arg1) {
                        super.onSuccess(arg0, arg1);
                        Log.e(getClass().getSimpleName(), "onSuccess " + "num " + arg1);
                    }
                }, TOKEN);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(getClass().getSimpleName(), "onStartCommand" + " intent " + intent.toString() + " flags " + flags + " startId " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        Log.e(getClass().getSimpleName(), "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


}
