package cm.socialfitnesshub.CustomViews;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import cm.socialfitnesshub.Models.Activity;
import cm.socialfitnesshub.Models.LocationActivity;
import cm.socialfitnesshub.Models.PartnerFinder;
import cm.socialfitnesshub.Models.SaveMarker;
import cm.socialfitnesshub.Models.Sports;
import cm.socialfitnesshub.PartnerFinder.ActivityInfo;
import cm.socialfitnesshub.R;
import es.dmoral.toasty.Toasty;

public abstract class MapFinderActivity extends ActivityWithBar implements OnMapReadyCallback, Observer {
    private GoogleMap googleMap;
    private MapView mapView;
    final SaveMarker saveMarker = new SaveMarker();

    private ImageView cycling, running, soccer, swimming, tennis, surf, golf, volleyball, basketball;

    private List<? extends PartnerFinder> activities;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.pf_map);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        Activity.getInstance().addObserver(this);
    }


    protected void setupDataForMap(List<? extends PartnerFinder> activities) {
        this.activities = activities;
        saveMarker.setActivities(this.activities);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        UiSettings uiSettings = this.googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(MapFinderActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapFinderActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapFinderActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!this.googleMap.isMyLocationEnabled()) {
                this.googleMap.setMyLocationEnabled(true);
            }

            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if (myLocation != null) {
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
            }
        }

        Sports_All();

        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == null) {
                    return false;
                }

                for (Map.Entry<Sports, List<PartnerFinder>> entry : saveMarker.getActivitiesMap().entrySet()) {
                    for (PartnerFinder pf : entry.getValue()) {
                        if (marker.getPosition().equals(pf.getLocation().getLatLng())) {
                            ActivityInfo addDialog = new ActivityInfo(MapFinderActivity.this, saveMarker, pf);
                            addDialog.show();
                            return true;
                        }
                    }
                }

                return false;
            }
        });


    }

    private void drawMarker(LatLng point, String event, Date date) {
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Show the name of the street
        //Geocoder myLocation = new Geocoder(PartnerFinderMapActivity.this, Locale.getDefault());
        markerOptions.title("Event: " + event + " Date: " + date.toString());

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);
    }

    private void Sports_All() {
        cycling = findViewById(R.id.cycling);
        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Cycling);
            }
        });

        running = findViewById(R.id.running);
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Running);
            }
        });

        soccer = findViewById(R.id.soccer);
        soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Soccer);
            }
        });

        tennis = findViewById(R.id.tennis);
        tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Tennis);
            }
        });

        swimming = findViewById(R.id.swimming);
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Swimming);
            }
        });

        surf = findViewById(R.id.surf);
        surf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Surfing);
            }
        });

        golf = findViewById(R.id.golf);
        golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Golf);
            }
        });

        volleyball = findViewById(R.id.volleyball);
        volleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Volleyball);
            }
        });

        basketball = findViewById(R.id.basketball);
        basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMarker(Sports.Basketball);
            }
        });
    }

    private void ShowMarker(Sports sports) {
        int count = 0;

        googleMap.clear();
        for (Map.Entry<Sports, List<PartnerFinder>> entry : saveMarker.getActivitiesMap().entrySet()) {
            Sports key = entry.getKey();

            if (key.equals(sports)) {
                for (PartnerFinder pf : entry.getValue()) {
                    LocationActivity la = pf.getLocation();
                    double lat = la.getLat();
                    double lon = la.getLon();
                    LatLng latLng = new LatLng(lat, lon);

                    drawMarker(latLng, pf.getEventsName(), pf.getDate());

                    count++;
                }
            }
        }

        Toasty.info(MapFinderActivity.this, "Foram encontrados " + count + " eventos!",
                Toast.LENGTH_SHORT, true).show();
    }
}