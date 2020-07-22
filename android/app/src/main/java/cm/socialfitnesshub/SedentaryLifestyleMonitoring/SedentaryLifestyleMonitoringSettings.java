package cm.socialfitnesshub.SedentaryLifestyleMonitoring;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import java.util.Map;
import java.util.Set;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.R;

public class SedentaryLifestyleMonitoringSettings extends ActivityWithBar {

    private RadioGroup methodGroup;
    private SeekBar sensitivityBar;
    private Button confirmButton;
    private Button scheduleButton;
    private Button cancelButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.activity_sedentary_lifestyle_monitoring_settings);

        sharedPreferences = getSharedPreferences("SedentaryLifestyleMonitoringPreferences", MODE_PRIVATE);

        methodGroup = findViewById(R.id.slmRadioGroup);
        sensitivityBar = findViewById(R.id.slmSensivityBar);
        confirmButton = findViewById(R.id.slmConfirmButton);
        scheduleButton = findViewById(R.id.slmScheduleButton);
        cancelButton = findViewById(R.id.slmCancelButton);

        setUpValues();
        setUpListeners();
    }

    private void setUpListeners() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("method", methodGroup.getCheckedRadioButtonId());
                editor.putInt("sensitivity", sensitivityBar.getProgress());
                editor.apply();
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSchedule();
            }
        });
    }

    private void setUpValues() {
        sensitivityBar.setProgress(sharedPreferences.getInt("sensitivity", 10));
        methodGroup.check(sharedPreferences.getInt("method", R.id.slmSensorRadioButton));
    }

    private void openSchedule() {
        Intent i = new Intent(this, SedentaryLifestyleMonitoringSchedule.class);
        startActivity(i);
    }
}
