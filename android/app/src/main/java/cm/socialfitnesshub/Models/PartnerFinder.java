package cm.socialfitnesshub.Models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.Date;

import com.google.firebase.database.Exclude;

@Keep
public class PartnerFinder implements Parcelable {
    private static final String TAG = PartnerFinder.class.getSimpleName();


    @Exclude
    private String activityId;

    private String userId;
    private String nickName;
    private String eventsName;
    private LocationActivity location;
    private Date date;
    private Sports sport;
    private String description;

    public PartnerFinder() {
    }

    public PartnerFinder(String userId, String nickName, String eventsName,
                         LocationActivity location, Date date, Sports sport, String description) {
        this.userId = userId;
        this.nickName = nickName;
        this.eventsName = eventsName;
        this.location = location;
        this.date = date;
        this.sport = sport;
        this.description = description;
    }


    public String getUserId() {
        return this.userId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getActivityId() {
        return this.activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEventsName() {
        return this.eventsName;
    }

    public LocationActivity getLocation() {
        return this.location;
    }

    public Date getDate() {
        return this.date;

    }

    public Sports getSport() {
        return this.sport;
    }

    public String getDescription() {
        return this.description;
    }

    public void setEventsName(String eventsName) {
        this.eventsName = eventsName;
    }

    public void setLocation(LocationActivity location) {
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSport(Sports sport) {
        this.sport = sport;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityId);
        dest.writeString(this.userId);
        dest.writeString(this.nickName);
        dest.writeString(this.eventsName);
        dest.writeSerializable(this.location);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.sport == null ? -1 : this.sport.ordinal());
        dest.writeString(this.description);
    }

    protected PartnerFinder(Parcel in) {
        this.activityId = in.readString();
        this.userId = in.readString();
        this.nickName = in.readString();
        this.eventsName = in.readString();
        this.location = (LocationActivity) in.readSerializable();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        int tmpSport = in.readInt();
        this.sport = tmpSport == -1 ? null : Sports.values()[tmpSport];
        this.description = in.readString();
    }

    public static final Creator<PartnerFinder> CREATOR = new Creator<PartnerFinder>() {
        @Override
        public PartnerFinder createFromParcel(Parcel source) {
            return new PartnerFinder(source);
        }

        @Override
        public PartnerFinder[] newArray(int size) {
            return new PartnerFinder[size];
        }
    };
}
