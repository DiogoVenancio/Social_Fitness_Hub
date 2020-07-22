package cm.socialfitnesshub.Hidrate;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

import cm.socialfitnesshub.Database.Database;
import cm.socialfitnesshub.Profile.Gender;
import cm.socialfitnesshub.Profile.ProfileManager;
import cm.socialfitnesshub.Profile.UserProfile;

class DrinkManager extends Observable{
    private static final DrinkManager instance = new DrinkManager();

    static DrinkManager getInstance() {
        return instance;
    }

    private int progress;
    private int goal;
    private List<Drink> drinks;
    private float temp;
    private boolean useTemperature;

    private final Database db = Database.getInstance();
    private final String drinksPath = "drinks/" + db.getCurrentUserId();
    private DatabaseReference drinksRef = db.getDatabaseReference(drinksPath);

    public DrinkManager() {
        super();

        drinks = new ArrayList<>();


        // Adds a listener to detected drink list changes.
        drinksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Gets all the values under the "drinks/userid" - meaning all the user's drinks
                Query query = drinksRef.orderByChild("date/time").startAt(getEpochForCurrentDay());

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                        //Clears the drink list
                        drinks.clear();

                        // For each row converts it to a Drink object and adds it to the list
                        for (DataSnapshot data : rows) {
                            System.out.println("Rows:" + data.toString());
                            Drink d = data.getValue(Drink.class);
                            d.setDrinkId(data.getKey());
                            drinks.add(d);
                        }
                        updateProgress();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        useTemperature = false;
    }

    public void setTemperature(float temp) {
        this.temp = temp;
        this.useTemperature = true;
    }


    public List<Drink> getDrinks() {
        return drinks;
    }

    public void addDrink(Drink drink) {
        if (drink == null) {
            return;
        }

        DatabaseReference newDrinkRef = drinksRef.push();
        newDrinkRef.setValue(drink);

        updateProgress();
    }

    public void removeDrink(Drink drink) {
        if (drink == null) {
            return;
        }

        DatabaseReference removeDrinkRef = drinksRef.child(drink.getDrinkId());
        removeDrinkRef.removeValue();
    }


    public void updateDrink(Drink drink) {
        if (drink == null) {
            return;
        }

        DatabaseReference removeDrinkRef = drinksRef.child(drink.getDrinkId());
        removeDrinkRef.setValue(drink);
    }

    public void updateNotificationSchedule() {
        setChanged();
        notifyObservers();
    }

    public int getProgress() {
        return progress;
    }

    private void updateProgress() {
        progress = 0;

        for (Drink d : drinks) {
            progress += d.getSize();
        }

        setChanged();
        notifyObservers();
    }

    private long getEpochForCurrentDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis();
    }

    private void updateGoal() {
        UserProfile profile = ProfileManager.getInstance().getProfile();

        if (profile == null) {
            goal = 2500;
            return;
        }

        Gender gender = profile.getGender();
        double weight = profile.getWeight();
        int genderMultiplier = getGenderMultiplier(gender);

        int temperatureAditional = 0;
        if(useTemperature) {
            int roundTemp = Math.round(temp);
            if(roundTemp > 40) {
                temperatureAditional = 500;
            } else if(roundTemp > 35) {
                temperatureAditional = 350;
            } else if(roundTemp > 30) {
                temperatureAditional = 150;
            }
        }

        double racio = weight / genderMultiplier;
        goal = (int) Math.floor(racio * 1000) + temperatureAditional;
    }

    private int getGenderMultiplier(Gender gender) {
        int genderMultiplier = 1;

        if (gender == null) {
            gender = Gender.OTHER;
        }
        switch (gender) {
            case MALE:
                genderMultiplier = 30;
                break;
            case FEMALE:
                genderMultiplier = 35;
                break;
            default:
            case OTHER:
                genderMultiplier = 33;
                break;
        }

        return genderMultiplier;
    }

    public int getHidrationGoal() {
        updateGoal();
        System.out.println("Goal:" + goal);
        return goal;
    }

}
