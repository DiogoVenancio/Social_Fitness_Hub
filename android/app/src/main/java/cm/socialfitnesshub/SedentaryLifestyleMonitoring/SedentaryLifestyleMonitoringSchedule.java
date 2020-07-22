package cm.socialfitnesshub.SedentaryLifestyleMonitoring;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableRow;

import java.util.LinkedList;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.R;

public class SedentaryLifestyleMonitoringSchedule extends ActivityWithBar {

    private Button confirmButton;
    private Button resetButton;
    private Button cancelButton;
    private LinkedList<TableRow> weekdaysRowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.activity_sedentary_lifestyle_monitoring_schedule);

        confirmButton = findViewById(R.id.slmConfirmScheduleButton);
        resetButton = findViewById(R.id.slmResetDefault);
        cancelButton = findViewById(R.id.slmCancelScheduleButton);
        weekdaysRowList = new LinkedList<>();
        weekdaysRowList.add((TableRow) findViewById(R.id.slmMondayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmTuesdayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmWednesdayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmThursdayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmFridayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmSaturdayRow));
        weekdaysRowList.add((TableRow) findViewById(R.id.slmSundayRow));

        for(TableRow row : weekdaysRowList) {
            CheckBox cb = (CheckBox) row.getChildAt(0);
            cb.setChecked(getSharedPreferences("SedentaryLifestyleMonitoringSchedule", MODE_PRIVATE).getBoolean(cb.getText().toString(), false));

            (row.getChildAt(1)).setVisibility(View.INVISIBLE);
            (row.getChildAt(2)).setVisibility(View.INVISIBLE);
        }

        setUpListeners();
    }

    private boolean isWeekday(String day) {
        return !(day.equals("Saturday") || day.equals("Sunday"));
    }

    private void setDefaults() {
        for(TableRow row : weekdaysRowList) {
            CheckBox cb = (CheckBox) row.getChildAt(0);
            cb.setChecked(isWeekday(cb.getText().toString()));

            (row.getChildAt(1)).setVisibility(View.INVISIBLE);
            (row.getChildAt(2)).setVisibility(View.INVISIBLE);
        }

        ((RadioButton) findViewById(R.id.slmGlobalTimeInterval)).setChecked(true);
        ((RadioButton) findViewById(R.id.slmSpecificTimeInterval)).setChecked(false);
    }

    private void setUpListeners() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("SedentaryLifestyleMonitoringSchedule", MODE_PRIVATE).edit();
                for(TableRow row : weekdaysRowList) {
                    CheckBox cb = (CheckBox) row.getChildAt(0);
                    editor.putBoolean(cb.getText().toString(), cb.isChecked());

                }
                editor.apply();
                finish();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaults();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
