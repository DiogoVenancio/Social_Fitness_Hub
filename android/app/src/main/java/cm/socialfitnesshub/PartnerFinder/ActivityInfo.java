package cm.socialfitnesshub.PartnerFinder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import cm.socialfitnesshub.Models.PartnerFinder;
import cm.socialfitnesshub.Models.SaveMarker;
import cm.socialfitnesshub.R;

public class ActivityInfo extends Dialog implements android.view.View.OnClickListener {
    private TextInputEditText location_text_activity, date_activity, spinner_activity;
    private TextView eventName, nickname;
    private EditText description_text_activity;

    private PartnerFinder partnerFinder;
    private SaveMarker saveMarker;

    private TextView accept_button_activity;

    public ActivityInfo(@NonNull Context context, SaveMarker saveMarker, PartnerFinder partnerFinder) {
        super(context);
        this.saveMarker = saveMarker;
        this.partnerFinder = partnerFinder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pf_map_view_activity);

        eventName = findViewById(R.id.eventName);
        nickname = findViewById(R.id.nickName);
        location_text_activity = findViewById(R.id.location_text_activity);
        date_activity = findViewById(R.id.date_activity);
        spinner_activity = findViewById(R.id.spinner_activity);
        description_text_activity = findViewById(R.id.description_text_activity);

        eventName.setText(partnerFinder.getEventsName());
        nickname.setText("By " + partnerFinder.getNickName());
        location_text_activity.setText(partnerFinder.getLocation().getAddress());
        date_activity.setText(partnerFinder.getDate().toString());
        spinner_activity.setText(partnerFinder.getSport().toString());
        description_text_activity.setText(partnerFinder.getDescription());

        accept_button_activity = findViewById(R.id.accept_button_activity);
        accept_button_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
