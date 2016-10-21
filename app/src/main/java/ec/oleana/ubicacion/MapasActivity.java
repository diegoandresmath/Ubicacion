package ec.oleana.ubicacion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;

import static java.lang.Math.round;

/**
 * Created by ddelacruz on 20/10/2016.
 */

public class MapasActivity extends Activity implements LocationListener {
    LocationManager locationManager;
    LocationListener locationListener;

    //text views to display latitude and longitude
    TextView latituteField;
    TextView longitudeField;
    TextView currentSpeedField;
    TextView kmphSpeedField;
    TextView avgSpeedField;
    TextView avgKmphField;

    //objects to store positional information
    protected double lat;
    protected double lon;

    //objects to store values for current and average speed
    protected double currentSpeed;
    protected double kmphSpeed;
    protected double avgSpeed;
    protected double avgKmph;
    protected double totalSpeed;
    protected double totalKmph;

    //counter that is incremented every time a new position is received, used to calculate average speed
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DLC", "MapasActivity.onCreate");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);

        run();
        super.onResume();

    }

    @Override
    protected void onResume() {
        Log.d("DLC", "MapasActivity.onResume");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
        super.onPause();
    }

    private void run() {
        final Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        //Acquire a reference to the system Location Manager

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location newLocation) {
                counter++;

                //current speed fo the gps device
                currentSpeed = round(newLocation.getSpeed(), 3, BigDecimal.ROUND_HALF_UP);
                kmphSpeed = round((currentSpeed * 3.6), 3, BigDecimal.ROUND_HALF_UP);

                //all speeds added together
                totalSpeed = totalSpeed + currentSpeed;
                totalKmph = totalKmph + kmphSpeed;

                //calculates average speed
                avgSpeed = round(totalSpeed / counter, 3, BigDecimal.ROUND_HALF_UP);
                avgKmph = round(totalKmph / counter, 3, BigDecimal.ROUND_HALF_UP);

                //gets position
                lat = round(((double) (newLocation.getLatitude())), 3, BigDecimal.ROUND_HALF_UP);
                lon = round(((double) (newLocation.getLongitude())), 3, BigDecimal.ROUND_HALF_UP);

                latituteField = (TextView) findViewById(R.id.lat);
                longitudeField = (TextView) findViewById(R.id.lon);
                currentSpeedField = (TextView) findViewById(R.id.speed);
                kmphSpeedField = (TextView) findViewById(R.id.avgspeed);
                avgSpeedField = (TextView) findViewById(R.id.avgspeed);
                avgKmphField = (TextView) findViewById(R.id.avgkmph);

                latituteField.setText("Current Latitude:        " + String.valueOf(lat));
                longitudeField.setText("Current Longitude:      " + String.valueOf(lon));
                currentSpeedField.setText("Current Speed (m/s):     " + String.valueOf(currentSpeed));
                kmphSpeedField.setText("Cuttent Speed (kmph):       " + String.valueOf(kmphSpeed));
                avgSpeedField.setText("Average Speed (m/s):     " + String.valueOf(avgSpeed));
                avgKmphField.setText("Average Speed (kmph):     " + String.valueOf(avgKmph));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public static double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
