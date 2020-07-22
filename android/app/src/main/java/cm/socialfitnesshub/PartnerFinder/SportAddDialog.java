package cm.socialfitnesshub.PartnerFinder;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cm.socialfitnesshub.Models.Sports;
import cm.socialfitnesshub.Profile.ProfileManager;

import cm.socialfitnesshub.R;

public class SportAddDialog extends Dialog implements android.view.View.OnClickListener {

    private TextView select_sport;
    public Button save_sports, cancel_sports;

    private List<Sports> sports;
    public CheckBox running_checkbox, cycling_checkbox, soccer_checkbox, tennis_checkbox,
            swimming_checkbox, golf_checkbox, volleyball_checkbox, basketball_checkbox, surf_checkbox;


    private List<Sports> sportsList;


    public SportAddDialog(@NonNull Context context) {
        super(context);
        sports = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pf_tab2_sports_content);


        select_sport = findViewById(R.id.select_sport);

        running_checkbox = findViewById(R.id.running_checkbox);
        cycling_checkbox = findViewById(R.id.cycling_checkbox);
        soccer_checkbox = findViewById(R.id.soccer_checkbox);
        tennis_checkbox = findViewById(R.id.tennis_checkbox);
        swimming_checkbox = findViewById(R.id.swimming_checkbox);
        golf_checkbox = findViewById(R.id.golf_checkbox);
        volleyball_checkbox = findViewById(R.id.volleyball_checkbox);
        basketball_checkbox = findViewById(R.id.basketball_checkbox);
        surf_checkbox = findViewById(R.id.surf_checkbox);

        sportsList = ProfileManager.getInstance().getSportsPracticed();

        for (Sports s : sportsList) {
            if (s.equals(Sports.Running)) {
                running_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Cycling)) {
                cycling_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Soccer)) {
                soccer_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Tennis)) {
                tennis_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Swimming)) {
                swimming_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Golf)) {
                golf_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Volleyball)) {
                volleyball_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Basketball)) {
                basketball_checkbox.setChecked(true);
            }
            if (s.equals(Sports.Surfing)) {
                surf_checkbox.setChecked(true);
            }
        }

        save_sports = findViewById(R.id.save_sports);
        save_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running_checkbox.isChecked()) {
                    sports.add(Sports.Running);
                }
                if (cycling_checkbox.isChecked()) {
                    sports.add(Sports.Cycling);
                }
                if (soccer_checkbox.isChecked()) {
                    sports.add(Sports.Soccer);
                }
                if (tennis_checkbox.isChecked()) {
                    sports.add(Sports.Tennis);
                }
                if (swimming_checkbox.isChecked()) {
                    sports.add(Sports.Swimming);
                }
                if (golf_checkbox.isChecked()) {
                    sports.add(Sports.Golf);
                }
                if (volleyball_checkbox.isChecked()) {
                    sports.add(Sports.Volleyball);
                }
                if (basketball_checkbox.isChecked()) {
                    sports.add(Sports.Basketball);
                }
                if (surf_checkbox.isChecked()) {
                    sports.add(Sports.Surfing);
                }

                addSports(sports);
            }
        });

        cancel_sports = findViewById(R.id.cancel_sports);
        cancel_sports.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void addSports(List<Sports> sports) {
        ProfileManager.getInstance().setSportsPracticed(sports);
        dismiss();
    }


    @Override
    public void onClick(View v) {

    }
}
