package cm.socialfitnesshub.GroupActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cm.socialfitnesshub.R;


public class CompanyAddActivity extends AppCompatActivity {

    Button btnConfirm, btnCancel;
    ImageView btnPickAddress;
    TextInputEditText inputName, inputWebsite, inputAddress;

    private FusedLocationProviderClient fusedLocationProviderClient;
    int PLACE_PICKER_REQUEST = 1;
    private Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ga_company_add);

        inputName = findViewById(R.id.company_name);
        inputWebsite = findViewById(R.id.company_website);
        inputAddress = findViewById(R.id.company_address);

        btnConfirm = findViewById(R.id.add_company_confirm);
        btnCancel = findViewById(R.id.add_company_cancel);

        btnPickAddress = findViewById(R.id.company_pick_address);

        btnPickAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(CompanyAddActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String website = inputWebsite.getText().toString();
                String address = inputAddress.getText().toString();

                addCompany(name, website, address);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                place = PlacePicker.getPlace(CompanyAddActivity.this, data);
                String address = String.format("%s", place.getAddress());
                inputAddress.setText(address);
            }
        }
    }

    public void addCompany(String name, String website, String address) {
        Company c = new Company(name, website, address);

        CompanyManager.getInstance().addCompany(c);
        finish();
    }


    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CompanyAddActivity.this);

        try {
            if (ActivityCompat.checkSelfPermission(CompanyAddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(CompanyAddActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task location = fusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();

                    } else {
                        Toast.makeText(CompanyAddActivity.this, "Não conseguimos aceder á sua localização", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
