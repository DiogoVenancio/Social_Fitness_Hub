package cm.socialfitnesshub.Models;

import android.net.Uri;
import android.support.annotation.Keep;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Keep
public class LocationActivity implements Place, Serializable {


    private double lat;
    private double lon;
    private String id;
    private String address;
    private List<Integer> placeTypes;
    private Locale locate;
    private String name;


    public LocationActivity() {
    }
    public LocationActivity(Place place) {

        setLat(place.getLatLng().latitude);
        setLon(place.getLatLng().longitude);
        setId(place.getId());
        setAddress(place.getAddress().toString());
        setPlaceTypes(place.getPlaceTypes());
        setLocate(place.getLocale());
        setName(place.getName().toString());
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return this.placeTypes;
    }

    @Override
    public CharSequence getAddress() {
        return this.address;
    }

    @Override
    public Locale getLocale() {
        return this.locate;
    }

    @Override
    public CharSequence getName() {
        return this.name;
    }

    @Override
    @Exclude
    public LatLng getLatLng() {
        LatLng latLng = new LatLng(getLat(), getLon());
        return latLng;
    }

    public Double getLon() {
        return this.lon;
    }

    public Double getLat() {
        return this.lat;
    }

    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    @Override
    public Uri getWebsiteUri() {
        return null;
    }

    @Override
    public CharSequence getPhoneNumber() {
        return null;
    }

    @Override
    public float getRating() {
        return 0;
    }

    @Override
    public int getPriceLevel() {
        return 0;
    }

    @Override
    public CharSequence getAttributions() {
        return null;
    }

    @Override
    public Place freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlaceTypes(List<Integer> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public void setLocate(Locale locate) {
        this.locate = locate;
    }

    public void setName(String name) {
        this.name = name;
    }

}
