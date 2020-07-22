package cm.socialfitnesshub.Profile;

import android.support.annotation.NonNull;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import cm.socialfitnesshub.Database.Database;
import cm.socialfitnesshub.Hidrate.Drink;
import cm.socialfitnesshub.Models.Sports;

public class ProfileManager extends Observable {
    private static ProfileManager instance = new ProfileManager();

    public static ProfileManager getInstance() {
        return instance;
    }

    private final Database db = Database.getInstance();
    private final String profilePath = "profile/" + db.getCurrentUserId();
    private final String sportsPath = profilePath + "/sportsPracticed";
    private DatabaseReference profileRef = db.getDatabaseReference(profilePath);
    private DatabaseReference sportsRef = db.getDatabaseReference(sportsPath);

    private UserProfile profile;

    private ProfileManager() {

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                profile = dataSnapshot.getValue(UserProfile.class);

                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void updateProfile(UserProfile profile) {
        if (this.profile == null) {
            createProfile(profile);
        }

        DatabaseReference updateProfileRef = profileRef;
        updateProfileRef.setValue(profile);
    }

    public void createProfile(UserProfile profile) {
        if (profile == null) {
            return;
        }

        this.profile = profile;
        System.out.println(this.profile.toString());
        DatabaseReference newProfileRef = profileRef;
        newProfileRef.setValue(profile);
    }

    public UserProfile getProfile() {
        return profile;
    }

    public List<Sports> getSportsPracticed() {
        if (profile == null) {
            return new ArrayList<Sports>();
        }
        return profile.getSportsPracticed();
    }

    public void setSportsPracticed(List<Sports> sports) {
        DatabaseReference updateSportsRef = sportsRef;
        updateSportsRef.setValue(sports);
    }


}
