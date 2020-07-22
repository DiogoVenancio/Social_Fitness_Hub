package cm.socialfitnesshub.SedentaryLifestyleMonitoring;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.SocketOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.PartnerFinder.PartnerFinderActivity;
import cm.socialfitnesshub.R;

public class SedentaryLifestyleMonitoring extends ActivityWithBar {

    private ProgressBar sedentarismIndex;
    private Button findPartnerButton;
    private TextView statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolBar(R.layout.activity_sedentary_lifestyle_monitoring);

        // Initialize views.
        findPartnerButton = findViewById(R.id.slmPartnerFinderButton);
        sedentarismIndex = findViewById(R.id.sedentaryLevel);
        statusMessage = findViewById(R.id.slmStatusMessage);

        setUpValues();

        findViewById(R.id.slmSettingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        findPartnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPartnerFinder();
            }
        });

    }

    private void openSettings() {
        Intent settingsIntent = new Intent(this, SedentaryLifestyleMonitoringSettings.class);
        startActivity(settingsIntent);
    }

    private void openPartnerFinder() {
        startActivity(new Intent(this, PartnerFinderActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpValues();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setUpValues() {
        findPartnerButton.setVisibility(View.INVISIBLE);
        statusMessage.setText(R.string.slm_status_message_active);

        // Setup progress bar.
        int secondsSinceMoved = (int) (System.currentTimeMillis() - getSharedPreferences("SedentaryLifestyleMonitoring", MODE_PRIVATE).getLong("last-moved", 0)) / 1000;
        System.out.println(getSharedPreferences("SedentaryLifestyleMonitoring", MODE_PRIVATE).getInt("index", 0));
        sedentarismIndex.setProgress(getSharedPreferences("SedentaryLifestyleMonitoring", MODE_PRIVATE).getInt("index", 0));
        verifyDanger();
        System.out.println("Last moved " + secondsSinceMoved + " seconds ago.");    // Debug.
    }

    private void verifyDanger() {
        if(sedentarismIndex.getProgress() > 90) {
            // Color: holo_red_light.
            sedentarismIndex.setProgressTintList(ColorStateList.valueOf(Color.argb(255, 255, 68, 68)));
            statusMessage.setText(R.string.slm_status_message_sedentary);
            ((TextView) findViewById(R.id.tipsTextView)).setText(getString(R.string.slm_tips_sedentary_working));
            if(!isOnWorkingHour()) {
                ((TextView) findViewById(R.id.tipsTextView)).setText(getString(R.string.slm_tips_sedentary));
                findPartnerButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean isOnWorkingHour() {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        System.out.println(weekDay + "(" + getSharedPreferences("SedentaryLifestyleMonitoringSchedule", MODE_PRIVATE).getAll().toString() + ")");

        return getSharedPreferences("SedentaryLifestyleMonitoringSchedule", MODE_PRIVATE).getBoolean(weekDay, false);
    }
}
