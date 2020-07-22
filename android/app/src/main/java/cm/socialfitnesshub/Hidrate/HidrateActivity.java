package cm.socialfitnesshub.Hidrate;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.Models.HidrateImageAdapter;
import cm.socialfitnesshub.R;

public class HidrateActivity extends ActivityWithBar implements java.util.Observer, SensorEventListener {

    private DrinkManager drinkManager = DrinkManager.getInstance();
    private static final String PREFS_NAME = "Hidrate";

    // UI
    private ProgressBar progressBar;
    private TextView tvProgress, tvGoal;
    private ScrollView scrollView;

    private SharedPreferences settings;
    private boolean sendNotification;
    private Timer timer = new Timer();
    private int[] flags = new int[]{
            R.drawable.water_bottle,
            R.drawable.water_bottle_gym,
            R.drawable.water_glass,
            R.drawable.tea,
            R.drawable.pint,
            R.drawable.coffee
    };

    private SensorManager mSensorManager;
    private Sensor mTemperature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.h_main);

        setupUI();

        createNotificationChannel();
        updateScheduler();
        drinkManager.addObserver(this);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }
        if (mTemperature == null) {
            //NOT SUPPORTED
        }

    }

    private void setupUI() {
        settings = getSharedPreferences(PREFS_NAME, 0);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.progressText);
        tvGoal = findViewById(R.id.hidrate_goal);

        scrollView = findViewById(R.id.drinksList);

        final Context context = this;

        FloatingActionButton fabAddDrink = findViewById(R.id.add_drink);
        fabAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Add Drink Dialog");
                DrinkAddDialog addDialog = new DrinkAddDialog(context);
                addDialog.show();
            }
        });

        FloatingActionButton fabNotification = findViewById(R.id.hidrate_set_notification);
        fabNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HidrateNotificationDialog notificationDialog = new HidrateNotificationDialog(context, settings);
                notificationDialog.show();
            }
        });
    }


    private void updateUI() {
        System.out.println("\n\nUpdating UI....\n\n");
        int progress = drinkManager.getProgress();


        //Update Progress bar and it's label
        progressBar.setProgress(progress);
        progressBar.setMax(drinkManager.getHidrationGoal());
        tvProgress.setText(String.valueOf(progress));



        tvGoal.setText(DrinkManager.getInstance().getHidrationGoal() + "");

        final List<Drink> drinks = drinkManager.getDrinks();
        List<ImageView> drinksImages = new ArrayList<>();

        ImageView drinkImage = new ImageView(this);
        drinkImage.setImageResource(R.drawable.water_bottle);
        for (Drink d : drinks) {
            drinksImages.add(drinkImage);
        }

        GridView gv = new GridView(this);
        gv.setNumColumns(4);
        gv.setPadding(0, 0, 0, 0);

        // Data bind GridView with ArrayAdapter (String Array elements)
        gv.setAdapter(new HidrateImageAdapter(this, drinks));

        scrollView.removeAllViews();
        scrollView.addView(gv);
    }

    /**
     * Updates the notification Scheduler
     */
    private void updateScheduler() {
        int intervalHour = settings.getInt("notificationIntervalHour", 0);
        int intervalMinute = settings.getInt("notificationIntervalMinute", 0);
        sendNotification = settings.getBoolean("sendNotification", true);
        long interval = getInterval(intervalHour, intervalMinute);
        if (interval == 0) {
            interval = 1800000;
        }

        final Handler handler = new Handler();
        TimerTask asyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showNotification();
                    }
                });
            }
        };

        timer.cancel();
        timer.purge();
        timer = new Timer();
        if (sendNotification) {
            timer.schedule(asyncTask, interval, interval);
            System.out.println("Scheduled notification : " + interval);
        }
    }

    private long getInterval(int intervalHour, int intervalMinute) {
        int hoursMilliseconds = intervalHour * 60 * 60 * 1000;
        int minutesMilliseconds = intervalMinute * 5 * 60 * 1000;
        return hoursMilliseconds + minutesMilliseconds;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
        updateScheduler();
    }

    private void showNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.water_bottle)
                .setContentTitle("Drink up!")
                .setContentText("Drink some water_bottle...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateUI();
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float ambient_temperature = event.values[0];
        DrinkManager.getInstance().setTemperature(ambient_temperature);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


/*
@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
        }


@Override
public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_settings:
        // User chose the "Settings" item, show the app settings UI...
        return true;


default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);

        }
        }*/