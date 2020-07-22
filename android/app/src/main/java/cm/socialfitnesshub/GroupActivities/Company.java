package cm.socialfitnesshub.GroupActivities;

import android.support.annotation.Keep;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

@Keep
public class Company implements Serializable {

    @Exclude
    private String companyId;

    private String name;
    private String website;
    private String address;

    public Company(String name, String website, String address) {
        this.name = name;
        this.website = website;
        this.address = address;
    }

    public Company() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String toString() {
        return getName();
    }
}
