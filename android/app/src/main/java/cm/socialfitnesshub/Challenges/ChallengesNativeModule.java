package cm.socialfitnesshub.Challenges;

import android.Manifest;
import android.content.Context;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;


public class ChallengesNativeModule extends ReactContextBaseJavaModule {
    private static final String TAG = "NativeModule";
    private Context context;

    public ChallengesNativeModule(ReactApplicationContext reactContext) {
        super (reactContext);

        this.context = reactContext;
    }

    /**
     * @return the name of this module. This will be the name used to {@code require()} this module
     * from javascript.
     */
    @Override
    public String getName () {
        return "ChallengesNativeModule";
    }

    @ReactMethod
    public void getValue(Callback callback) {
        callback.invoke("working...");
    }

}
