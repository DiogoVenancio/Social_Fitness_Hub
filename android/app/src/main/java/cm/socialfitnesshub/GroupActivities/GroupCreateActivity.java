package cm.socialfitnesshub.GroupActivities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Date;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.Models.GroupActivity;
import cm.socialfitnesshub.Models.LocationActivity;
import cm.socialfitnesshub.Models.PartnerFinder;
import cm.socialfitnesshub.Models.Sports;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.R;
import es.dmoral.toasty.Toasty;

public class GroupCreateActivity extends ActivityWithBar implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    private ImageView pickDate, pick_location;
    private TextInputEditText events_name, location_text, textView;
    private EditText description_text;

    private Place place;

    private Calendar c;
    private Spinner sportsSpinner, companiesSpinner;
    int PLACE_PICKER_REQUEST = 1;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Activity activity = Activity.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.ga_create_activity);

        events_name = findViewById(R.id.events_name);
        pickDate = findViewById(R.id.pick_date);
        textView = findViewById(R.id.date);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GroupCreateActivity.this, GroupCreateActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        location_text = findViewById(R.id.location_text);
        pick_location = findViewById(R.id.pick_location);

        pick_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(GroupCreateActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        sportsSpinner = findViewById(R.id.sports_spinner);
        companiesSpinner = findViewById(R.id.companies_spinner);

        ArrayAdapter<Sports> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Sports.values());
        sportsSpinner.setAdapter(adapter);


        final ArrayAdapter<Company> companiesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CompanyManager.getInstance().getCompanies());
        companiesSpinner.setAdapter(companiesArrayAdapter);

        description_text = findViewById(R.id.description_text);

        TextView cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        TextView accept_button = findViewById(R.id.accept_button);
        accept_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Company company = (Company) companiesSpinner.getSelectedItem();

                c.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
                Date date = c.getTime();

                LocationActivity locationActivity = new LocationActivity(place);

                GroupActivity groupActivity = new GroupActivity(company,
                        ProfileManager.getInstance().getProfile().getProfileId(),
                        ProfileManager.getInstance().getProfile().getUsername(),
                        events_name.getText().toString(),
                        locationActivity, date,
                        (Sports) sportsSpinner.getSelectedItem(),
                        description_text.getText().toString());
                activity.addGroupActivity(groupActivity);
                Toasty.success(GroupCreateActivity.this, "Activity created successfully",
                        Toast.LENGTH_SHORT, true).show();
                finish();
            }
        });


    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();

                    } else {
                        Toast.makeText(GroupCreateActivity.this, "Não conseguimos aceder á sua localização", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = c.get(Calendar.YEAR);
        monthFinal = c.get(Calendar.MONTH);
        dayFinal = i2;

        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(GroupCreateActivity.this,
                GroupCreateActivity.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        textView.setText("" + dayFinal + "/" + monthFinal + "/" + yearFinal + " " + hourFinal + ":" + minuteFinal);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(GroupCreateActivity.this, data);
                LocationActivity locationActivity = new LocationActivity(place);

                String address = String.format("%s", place.getAddress());
                location_text.setText(address);
            }
        }
    }


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
    }
}
