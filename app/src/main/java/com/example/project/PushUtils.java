package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

public class PushUtils {
    private static final String TAG=PushUtils.class.getSimpleName();
    private static PowerManager.WakeLock wakeLock;
    @SuppressLint("InvalidWakeLockTag")
    public static void acquireWakeLock(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE );
        wakeLock=pm.newWakeLock( PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.ON_AFTER_RELEASE,"WAKEUP" );
        wakeLock.acquire(3000);
    }
    public  static void releaseWakeLock(){
        if(wakeLock!=null){
            wakeLock.release();
            wakeLock=null;
        }
    }
}
