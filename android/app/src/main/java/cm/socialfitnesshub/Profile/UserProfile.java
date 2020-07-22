package cm.socialfitnesshub.Profile;

import com.google.firebase.database.Exclude;

import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cm.socialfitnesshub.Models.Sports;

import cm.socialfitnesshub.Database.Database;

public class UserProfile {
    @Exclude
    private String profileId;

    private String username;
    private Date birthDate;
    private double weight;
    private double height;
    private Gender gender;
    private String firstName;
    private String lastName;

    private List<Sports> sportsPracticed;

    public UserProfile() {
        sportsPracticed = new ArrayList<>();
        this.profileId = Database.getInstance().getCurrentUserId();
    }

    @Exclude
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }
        Calendar bd = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        bd.setTime(birthDate);

        int age = today.get(Calendar.YEAR) - bd.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < bd.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Exclude
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        String str = "User: ";
        str += getFullName();
        str += "{\n";
        str += "\tGender:" + this.gender + "\n";
        str += "\tAge:" + getAge() + "\n";
        str += "\tWeight:" + this.weight + "\n";
        str += "\tHeight:" + this.height + "\n";
        str += "\n}";
        return str;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Exclude
    public String getProfileId() {
        return profileId;
    }

    @Exclude
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void addSportPracticed(Sports sport) {
        if (sport == null) {
            return;
        }
        sportsPracticed.add(sport);
    }

    public List<Sports> getSportsPracticed() {
        return sportsPracticed;
    }

    public void setSportsPracticed(List<Sports> sportsPracticed) {
        this.sportsPracticed = sportsPracticed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
