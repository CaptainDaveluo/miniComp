package com.debla.minicomp.Activity.Public;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Dave-PC on 2017/2/18.
 */

public class VibratorUtil {
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
    public static void Vibrate(Context context, long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
