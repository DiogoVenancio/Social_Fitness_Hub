package cm.socialfitnesshub.Hidrate;

import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;

import cm.socialfitnesshub.R;

public class Drink {

    @Exclude
    private String drinkId;

    private int size;
    private Date drinkedDate = new Date();
    private int drawable;

    public Drink(int size, int drawable) {
        this.size = size;
        this.drawable = drawable;
    }

    public Drink() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getDate() {
        return this.drinkedDate;
    }

    public void setDate(Date date) {
        this.drinkedDate = date;
    }

    public String getDrinkId() {
        return this.drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getDrawable() {
        return this.drawable;
    }

    @Exclude
    public String getTimeFormatted() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate());
        return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
    }

    @Override
    public String toString() {
        return "Drink (" + this.size + ")";
    }
}
