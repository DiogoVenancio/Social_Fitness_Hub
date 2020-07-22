package cm.socialfitnesshub.Profile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import cm.socialfitnesshub.CustomViews.ActivityWithBar;
import cm.socialfitnesshub.Database.Utilities;
import cm.socialfitnesshub.R;

public class ProfileActivity extends ActivityWithBar implements Observer {

    private TextInputEditText inputFirstName, inputLastName, inputUsername;
    private EditText inputBirthDate, inputHeight, inputWeight;
    private Spinner spinnerGender;
    private ArrayAdapter<Gender> adapter;
    private Calendar cal = Calendar.getInstance();

    private Button btnSave, btnCancel;
    private UserProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.profile_main);

        profile = ProfileManager.getInstance().getProfile();
        ProfileManager.getInstance().addObserver(this);

        inputUsername = findViewById(R.id.profileUsername);
        inputFirstName = findViewById(R.id.profileFirstName);
        inputLastName = findViewById(R.id.profileLastName);
        inputBirthDate = findViewById(R.id.profileBirthdate);
        spinnerGender = findViewById(R.id.profileSpinnerGender);
        inputHeight = findViewById(R.id.profileHeight);
        inputWeight = findViewById(R.id.profileWeight);

        final Context context = this;
        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.YEAR, year);
                upadteDateInput();
            }

        };

        inputBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context,
                        datePicker,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Gender.values());
        spinnerGender.setAdapter(adapter);

        updateFields();


        btnSave = findViewById(R.id.profileSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        btnCancel = findViewById(R.id.profileCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void upadteDateInput() {
        inputBirthDate.setText(Utilities.dateToString(cal.getTime(), "dd/MM/yyyy"));
    }

    private void updateFields() {
        if (profile != null) {
            inputUsername.setText(profile.getUsername());
            inputFirstName.setText(profile.getFirstName());
            inputLastName.setText(profile.getLastName());
            String birthdateFormatted = Utilities.dateToString(profile.getBirthDate(), null);
            inputBirthDate.setText(birthdateFormatted);
            spinnerGender.setSelection(adapter.getPosition(profile.getGender()));
            inputHeight.setText("" + profile.getHeight());
            inputWeight.setText("" + profile.getWeight());
        }
    }

    private void updateUserProfile() {
        UserProfile profile = new UserProfile();

        String username = inputUsername.getText().toString();
        if (username != null && !username.isEmpty()) {
            profile.setUsername(username);
        }

        String firstName = inputFirstName.getText().toString();
        if (firstName != null && !firstName.isEmpty()) {
            profile.setFirstName(firstName);
        }

        String lastName = inputLastName.getText().toString();
        if (lastName != null && !lastName.isEmpty()) {
            profile.setLastName(lastName);
        }

        String birthDateString = inputBirthDate.getText().toString();
        Date birthdate = Utilities.stringToDate(birthDateString, null);
        profile.setBirthDate(birthdate);

        double height = Double.parseDouble(inputHeight.getText().toString());
        profile.setHeight(height);
        double weight = Double.parseDouble(inputWeight.getText().toString());
        profile.setWeight(weight);

        Gender gender = (Gender) spinnerGender.getSelectedItem();
        profile.setGender(gender);

        ProfileManager pm = ProfileManager.getInstance();
        pm.updateProfile(profile);

    }

    private void loadUserProfile() {
        UserProfile profile = ProfileManager.getInstance().getProfile();
        if (profile == null) {
            return;
        }

        inputFirstName.setText(profile.getFirstName());
    }

    @Override
    public void update(Observable o, Object arg) {
        profile = ProfileManager.getInstance().getProfile();
        updateFields();
    }
}
