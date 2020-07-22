package cm.socialfitnesshub.PartnerFinder;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.Models.LocationActivity;
import cm.socialfitnesshub.Models.PartnerFinder;
import cm.socialfitnesshub.Models.Sports;
import cm.socialfitnesshub.R;
import es.dmoral.toasty.Toasty;

public class EditActivity extends ActivityWithBar implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    private ImageView pickDate, pick_location;
    private TextInputEditText eventName, location_text_edit, date_edit, spinner1_edit_activity;
    private EditText description_text_edit;

    private Place place;
    private LocationActivity locationActivity;

    private Calendar c;
    private Date date;
    int PLACE_PICKER_REQUEST = 1;

    private List<Sports> sports = new ArrayList<>();

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Activity activity = Activity.getInstance();
    private PartnerFinder pf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.activity_partner_finder_edit_activity);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        eventName = findViewById(R.id.Event_Name_edit);
        pickDate = findViewById(R.id.pick_date_edit);
        date_edit = findViewById(R.id.date_edit);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this, EditActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        location_text_edit = findViewById(R.id.location_text_edit);
        pick_location = findViewById(R.id.pick_location_edit);

        pick_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(EditActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        spinner1_edit_activity = findViewById(R.id.spinner1_edit_activity);

        description_text_edit = findViewById(R.id.description_text_edit);

        Bundle bundle = getIntent().getExtras();
        pf = bundle.getParcelable("EditActivity");

        for (Sports s : Sports.values()) {
            if (s.toString().equals(pf.getSport().toString())) {
                sports.add(s);
                spinner1_edit_activity.setText(pf.getSport().toString());
            }
        }

        eventName.setText(pf.getEventsName());
        date_edit.setText(pf.getDate().toString());
        location_text_edit.setText(pf.getLocation().getAddress());
        description_text_edit.setText(pf.getDescription());


        TextView cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        TextView remove_button = findViewById(R.id.remove_button);
        remove_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(EditActivity.this)
                        .setTitle("Remove Activity")
                        .setMessage("Do you really want to remove this activity?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                activity.removeActivity(pf);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        TextView accept_button = findViewById(R.id.accept_button);
        accept_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if (eventName.getText().toString().equals(""))
                {
                    eventName.setError("Required!");
                }else
                {
                    if (yearFinal == 0) {
                        date = pf.getDate();
                    } else {
                        c.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
                        date = c.getTime();
                    }

                    if (place == null) {
                        locationActivity = pf.getLocation();
                    } else {
                        locationActivity = new LocationActivity(place);
                    }

                    pf.setDate(date);
                    pf.setDescription(description_text_edit.getText().toString());
                    pf.setSport(sports.get(0));
                    pf.setEventsName(eventName.getText().toString());
                    pf.setLocation(locationActivity);
                    activity.updateActivity(pf);

                    Toasty.success(EditActivity.this, "Activity updated successfully",
                            Toast.LENGTH_SHORT, true).show();
                    finish();
                }
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
                        Toast.makeText(EditActivity.this, "Não conseguimos aceder á sua localização", Toast.LENGTH_SHORT).show();
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this,
                EditActivity.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

        date_edit.setText("" + dayFinal + "/" + monthFinal + "/" + yearFinal + " " + hourFinal + ":" + minuteFinal);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(EditActivity.this, data);
                LocationActivity locationActivity = new LocationActivity(place);

                String address = String.format("%s", place.getAddress());
                location_text_edit.setText(address);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
