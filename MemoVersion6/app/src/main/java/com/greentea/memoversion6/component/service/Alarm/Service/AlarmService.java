package com.greentea.memoversion6.component.service.Alarm.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.greentea.memoversion6.DB.MemoRepositoryDB;
import com.greentea.memoversion6.DB.data.MemoData;
import com.greentea.memoversion6.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import dagger.android.AndroidInjection;

public class AlarmService extends Service {
    NotificationManager Notifi_M;
    AlarmServiceThread thread;
    Notification notification;
    NotificationChannel notificationChannel;

    private List<MemoData> memoDataList;
    //private MainActivity activity;
    //private ArrayList<String> tempRandomTime;
    SimpleDateFormat mFormat;
    String time;

    int tempHour, comfort_hourA, comfort_hourB;

    @Inject
    MemoRepositoryDB memoRepositoryDB;

    @Override
    public void onCreate() {
        super.onCreate();
        this.configureDagger();
    }

    private void configureDagger(){
        AndroidInjection.inject(this);//서비스 주입입니당~
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean CheckComfort(int hour, int comfortA, int comfortB){
        if(comfortA <= comfortB)
            return comfortA <= hour && hour < comfortB;
        else
            return comfortA <= hour || hour < comfortB;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mFormat=new SimpleDateFormat("yyMMddkkmm");//날짜 형식 지정

        if(memoRepositoryDB.getSettingData() == null){
            comfort_hourA = comfort_hourB = -1;
        }
        else{
            comfort_hourA = memoRepositoryDB.getSettingData().getSleepStartTime();
            comfort_hourB = memoRepositoryDB.getSettingData().getSleepEndTime();
        }

        myServiceHandler handler = new myServiceHandler();
        thread = new AlarmServiceThread(handler);
        thread.start();

        Log.d("test","fine");
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {

        public void checkNotify(MemoData memoData){
            String tempRandomTime = memoData.getRandomTime().substring(memoData.getRandomTime().length()-10,memoData.getRandomTime().length());

            Log.d("tempRT", tempRandomTime);
            if(tempRandomTime.length() != 0){

                Calendar calendar = Calendar.getInstance();
                time=mFormat.format(calendar.getTime());

                Log.d("tempRT", time);
                if(tempRandomTime.equals(time))
                {
                    tempHour = Integer.getInteger(tempRandomTime.substring(6,8));

                    if(!CheckComfort(tempHour,comfort_hourA,comfort_hourB)) {
                        Log.d("testHandler", "pass~");
                        Notifi_M.notify(memoData.getId(), notification);
                        memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length() - 10));
                    }
                }else{
                    Date current = null;
                    Date RT = null;
                    try {
                        current = mFormat.parse(time);
                        RT = mFormat.parse(tempRandomTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int compare = current.compareTo(RT);
                    if(compare>0){
                        memoData.setRandomTime(memoData.getRandomTime().substring(0, memoData.getRandomTime().length()-10));
                    }else{
                        Log.d("tempRT", "finePass");
                    }
                }
                if(memoData.getRandomTime().length() == 0){
                    //서비스 종료
                    //디비에서 지우기

                    Log.d("DBdel", "it");
                    memoRepositoryDB.deleteMemo(memoData);
                }else{
                    memoRepositoryDB.insertMemo(memoData);
                }
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(android.os.Message msg) {
            Log.d("testHandler", "running");
            memoDataList = memoRepositoryDB.getStaticMemoDataList();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                for(int i =0; i<memoDataList.size(); i++){
                    MemoData memoData = memoDataList.get(i);

                    notificationChannel = new NotificationChannel(String.valueOf(memoData.getId()), "channel1", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
//            notificationChannel.getSound();
//            notificationChannel.setSound();
                    notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
//            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                    Notifi_M.createNotificationChannel(notificationChannel);
                    //인텐트에서 받은 메모 초기화 과정...
                    notification = new Notification.Builder(getApplicationContext(), String.valueOf(memoData.getId()))
                            .setContentTitle(memoData.getMemoTitle())
                            .setContentText(memoData.getMemoText())
                            .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_event_black_24dp))
                            .setAutoCancel(true)
                            .build();

                    startForeground(-1,notification);
                    stopForeground(true);

                    notification.defaults = Notification.DEFAULT_SOUND;
                    checkNotify(memoData);

                }
            }else{
                for(int i =0; i<memoDataList.size(); i++){
                    MemoData memoData = memoDataList.get(i);
                    notification = new Notification.Builder(getApplicationContext())
                            .setContentTitle(memoData.getMemoTitle())
                            .setContentText(memoData.getMemoText())
                            .setTicker("알림!!!")
                            .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                            .build();
//노티파이 통일 필요...

                    notification.defaults = Notification.DEFAULT_SOUND;

                    //알림 소리를 한번만 내도록
                    notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                    //확인하면 자동으로 알림이 제거 되도록
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    checkNotify(memoData);
                }
            }
        }
    };
}
