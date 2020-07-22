package cm.socialfitnesshub.Models;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import cm.socialfitnesshub.Database.Database;

import cm.socialfitnesshub.GroupActivities.Company;

public class Activity extends Observable {

    private static final Activity ourInstance = new Activity();

    public static Activity getInstance() {
        return ourInstance;
    }

    private List<PartnerFinder> partnerFinders;
    private List<GroupActivity> groupActivities;

    private final Database db = Database.getInstance();
    private final String activityPath = "activities/";
    private DatabaseReference activityRef = db.getDatabaseReference(activityPath);

    private final String groupActivityPath = "group_activities/";
    private DatabaseReference groupActivityRef = db.getDatabaseReference(groupActivityPath);

    private final String companiesPath = "companies/";
    private DatabaseReference companiesRef = db.getDatabaseReference(companiesPath);

    public Activity() {
        super();

        partnerFinders = new ArrayList<>();
        groupActivities = new ArrayList<>();

        // Adds a listener to detected activity list changes.
        activityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                //Clears the drink list
                partnerFinders.clear();

                for (DataSnapshot data : rows) {
                    System.out.println("Rows:" + data.toString());
                    PartnerFinder d = data.getValue(PartnerFinder.class);
                    d.setActivityId(data.getKey());
                    partnerFinders.add(d);
                }
                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        groupActivityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                //Clears the drink list
                groupActivities.clear();

                for (DataSnapshot data : rows)
                {
                    System.out.println("Rows:" + data.toString());
                    final GroupActivity ga = data.getValue(GroupActivity.class);
                    ga.setActivityId(data.getKey());

                    //Connect to Company

                    if(ga.getCompanyOwner() != null)
                    {
                        Query query = companiesRef.child(ga.getCompanyOwner().getCompanyId());
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Company company = dataSnapshot.getValue(Company.class);
                                ga.setCompanyOwner(company);

                                setChanged();
                                notifyObservers();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    groupActivities.add(ga);
                }
                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public List<PartnerFinder> getActivities() {
        return partnerFinders;
    }

    public List<GroupActivity> getGroupActivities() {
        return groupActivities;
    }

    public void addActivity(PartnerFinder partnerFinder) {
        if (partnerFinder == null) {
            return;
        }

        DatabaseReference newActivityRef = activityRef.push();
        newActivityRef.setValue(partnerFinder);

        partnerFinders.add(partnerFinder);
    }

    public void addGroupActivity(GroupActivity groupActivity) {
        if (groupActivity == null) {
            return;
        }

        DatabaseReference newGroupActivityRef = groupActivityRef.push();
        newGroupActivityRef.setValue(groupActivity);

    }

    public void removeActivity(PartnerFinder partnerFinder) {
        if (partnerFinder == null) {
            return;
        }

        DatabaseReference removeActivityRef = activityRef.child(partnerFinder.getActivityId());
        removeActivityRef.removeValue();

        partnerFinders.remove(partnerFinder);
    }


    public void updateActivity(PartnerFinder partnerFinder) {
        if (partnerFinder == null) {
            return;
        }


        DatabaseReference removeActivityRef = activityRef.child(partnerFinder.getActivityId());
        removeActivityRef.setValue(partnerFinder);

        for (int i = 0; i < partnerFinders.size(); i++) {
            if (partnerFinders.get(i).equals(partnerFinder)) {
                partnerFinders.set(i, partnerFinder);
            }
        }
    }

    public void updateGroupActivity(GroupActivity groupActivity) {
        if (groupActivity == null) {
            return;
        }

        DatabaseReference updateGroupActivityRef = groupActivityRef.child(groupActivity.getActivityId());
        updateGroupActivityRef.setValue(groupActivity);


    }

}
