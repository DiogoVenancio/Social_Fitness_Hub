package cm.socialfitnesshub.CustomViews;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cm.socialfitnesshub.R;

public abstract class ActivityWithBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupToolBar(int contentView) {
        setContentView(contentView);
        Toolbar toolbar = findViewById(R.id.MainToolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            toolbar.setTitleTextColor(Color.WHITE);
        }
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

}
