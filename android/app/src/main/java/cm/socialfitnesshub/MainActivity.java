package cm.socialfitnesshub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cm.socialfitnesshub.Challenges.ChallengesActivity;
import cm.socialfitnesshub.GroupActivities.GroupActivity;
import cm.socialfitnesshub.Hidrate.HidrateActivity;

import cm.socialfitnesshub.PartnerFinder.PartnerFinderActivity;

import cm.socialfitnesshub.PartnerFinder.PartnerFinderActivity;
import cm.socialfitnesshub.Hidrate.HidrateActivity;
import cm.socialfitnesshub.Profile.ProfileActivity;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.SedentaryLifestyleMonitoring.MotionReaderService;
import cm.socialfitnesshub.SedentaryLifestyleMonitoring.SedentaryLifestyleMonitoring;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProfileManager.getInstance();

        LinearLayout hidrateButton = findViewById(R.id.hidrateButton);
        hidrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHidrate();
            }
        });

        LinearLayout partnerFinderButton = findViewById(R.id.partnerFinderButton);
        partnerFinderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPartnerFinder();
            }
        });

        LinearLayout settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });


        LinearLayout groupActivitiesButton = findViewById(R.id.groupActivitiesButton);
        groupActivitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGroupActivity();
            }
        });

        LinearLayout challengesButton = findViewById(R.id.weeklyChallengeButton);
        challengesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChallenges();
            }
        });

        LinearLayout slmButton = findViewById(R.id.sedentaryLifestyleButton);
        slmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSedentaryLifestyleMonitoring();
            }
        });

        Intent intent = new Intent(getApplicationContext(), MotionReaderService.class );
        startService(intent);
    }

    private void openHidrate() {
        startActivity(new Intent(this, HidrateActivity.class));
    }

    private void openPartnerFinder() {
        startActivity(new Intent(this, PartnerFinderActivity.class));
    }

    private void openProfile() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        openMain();
                    }
                });
    }

    private void openMain() {
        Intent i = new Intent(this, FirebaseUIActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    private void openGroupActivity() {
        startActivity(new Intent(this, GroupActivity.class));
    }

    private void openChallenges() {
        startActivity(new Intent(this, ChallengesActivity.class));
    }

    private void openSedentaryLifestyleMonitoring() {
        Intent i = new Intent(this, SedentaryLifestyleMonitoring.class);
        startActivity(i);
    }
}
