package cm.socialfitnesshub.Hidrate;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import cm.socialfitnesshub.R;

public class HidrateNotificationDialog extends Dialog implements android.view.View.OnClickListener {

    private NumberPicker npHours, npMinutes;
    private ImageButton btnToggleNotification;
    private Button btnCancel, btnSet;
    private boolean notification;
    private int intervalHour, intervalMinute;
    private SharedPreferences settings;

    public HidrateNotificationDialog(@NonNull Context context, SharedPreferences settings) {
        super(context);
        this.settings = settings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.h_notification_settings);

        notification = settings.getBoolean("sendNotification", true);
        intervalHour = settings.getInt("notificationIntervalHour", 0);
        intervalMinute = settings.getInt("notificationIntervalMinute", 0);


        final String[] hours = new String[13];
        final String[] minutes = new String[12];

        for (int i = 0; i < 12; i++) {
            hours[i] = i + " hr";
            minutes[i] = (i * 5) + " min";
        }

        hours[12] = "12 hr";

        npHours = findViewById(R.id.hidrate_notificaion_interval_hour);
        npMinutes = findViewById(R.id.hidrate_notificaion_interval_minutes);

        btnCancel = findViewById(R.id.hidrate_notification_cancel);
        btnSet = findViewById(R.id.hidrate_notification_set);

        btnToggleNotification = findViewById(R.id.hidrate_toggle_notification);
        btnToggleNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotification();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHours = npHours.getValue();
                int selectedMinutes = npMinutes.getValue();

                updateNotificationPreferences(notification, selectedHours, selectedMinutes);
                DrinkManager.getInstance().updateNotificationSchedule();
                dismiss();
            }
        });


        npHours.setDisplayedValues(hours);
        npMinutes.setDisplayedValues(minutes);

        npHours.setMinValue(0);
        npHours.setMaxValue(12);
        npHours.setValue(intervalHour);
        npMinutes.setMinValue(0);
        npMinutes.setMaxValue(11);
        npMinutes.setValue(intervalMinute);

        //npHours.setWrapSelectorWheel(true);
        //npMinutes.setWrapSelectorWheel(true);
        updateNotificationIcon();
    }


    private void toggleNotification() {
        notification = !notification;
        updateNotificationIcon();
    }

    private void updateNotificationIcon() {
        if (notification) {
            btnToggleNotification.setImageResource(R.drawable.notifications_bell_button);
        } else {
            btnToggleNotification.setImageResource(R.drawable.turn_notifications_off_button);
        }
    }

    private void updateNotificationPreferences(boolean notification, int hours, int minutes) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("sendNotification", notification);
        editor.putInt("notificationIntervalHour", hours);
        editor.putInt("notificationIntervalMinute", minutes);
        editor.commit();
    }


    @Override
    public void onClick(View v) {

    }
}
