package cm.socialfitnesshub.GroupActivities;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import cm.socialfitnesshub.Database.Database;
import cm.socialfitnesshub.Hidrate.Drink;

public class CompanyManager extends Observable {

    private static CompanyManager instance = new CompanyManager();

    public static CompanyManager getInstance() {
        return instance;
    }

    private final Database db = Database.getInstance();
    private final String companiesPath = "companies/" + db.getCurrentUserId();
    private DatabaseReference companiesRef = db.getDatabaseReference(companiesPath);

    private List<Company> companies;

    private CompanyManager() {

        companies = new ArrayList<>();

        companiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> rows = dataSnapshot.getChildren();

                //Clears the companies list
                companies.clear();

                // For each row converts it to a Company object and adds it to the list
                for (DataSnapshot data : rows) {
                    Company c = data.getValue(Company.class);
                    c.setCompanyId(data.getKey());
                    companies.add(c);
                }

                setChanged();
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        if (company == null) {
            return;
        }

        DatabaseReference newProfileRef = companiesRef.push();
        newProfileRef.setValue(company);
    }

    public void removeCompany(Company company) {
        if (company == null) {
            return;
        }

        DatabaseReference removeCompanyRef = companiesRef.child(company.getCompanyId());
        removeCompanyRef.removeValue();
    }

    public void updateCompany(Company company) {
        if (company == null) {
            return;
        }

        DatabaseReference updateCompanyRef = companiesRef.child(company.getCompanyId());
        updateCompanyRef.setValue(company);
    }

}
