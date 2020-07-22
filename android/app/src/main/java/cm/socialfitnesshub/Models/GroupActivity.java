package cm.socialfitnesshub.Models;

import java.io.Serializable;
import java.util.Date;

import cm.socialfitnesshub.GroupActivities.Company;

public class GroupActivity extends PartnerFinder implements Serializable {

    private Company companyOwner;

    public GroupActivity() {
        super();
    }

    public GroupActivity(Company company, String userId, String nickName, String eventsName,
                         LocationActivity location, Date date, Sports sport, String description) {
        super(userId, nickName, eventsName, location, date, sport, description);
        this.companyOwner = company;
    }


    public Company getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(Company companyOwner) {
        this.companyOwner = companyOwner;
    }
}
