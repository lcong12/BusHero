package com.example.bushero;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    LocationManager locationManager;
    LocationListener locationListener;
    // private Marker myMarker;
    RecyclerView recyclerView;
    BusRouteAdapter adapter;
    List<Bus> busList;
    SQLiteDatabase myDatabase;
    CardView cardView;
    TextView textView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {

            case R.id.settings:
                Log.i("Menu", "settings");
                break;
            case R.id.login:
                Log.i("Menu", "login");
                break;

            default:
                return false;
        }

        return true;
    }

    public void centerOnLocation(Location location, String string) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        // mMap.clear();
        mMap.addMarker(new MarkerOptions().position(userLocation).title(string));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 19));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Get position permission");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, locationListener);
                    Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    centerOnLocation(lastKnowLocation, "Your Location");
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        try{
//            myDatabase = this.openOrCreateDatabase("BusHero", MODE_PRIVATE, null);
//            myDatabase.execSQL("DROP TABLE IF EXISTS STOP");
//            myDatabase.execSQL("DROP TABLE IF EXISTS Route");
//            myDatabase.execSQL("DROP TABLE IF EXISTS Bus");
//            myDatabase.execSQL("DROP TABLE IF EXISTS Stop2Route");
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Stop (location VARCHAR PRIMARY KEY, stop_name VARCHAR)");
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Route (id VARCHAR PRIMARY KEY)");
//            // myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Bus (bus_id VARCHAR PRIMARY KEY, occupation VARCHAR, route_id REFERENCES Route(id))");
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Bus (bus_id VARCHAR PRIMARY KEY, occupation VARCHAR, rest_arrival_time VARCHAR, route_id REFERENCES Route(id))");
//            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS Stop2Route (stop VARCHAR REFERENCES Stop(location), route_id VARCHAR REFERENCES Route(id), PRIMARY KEY(stop, route_id))");
//
//            myDatabase.execSQL("INSERT INTO Stop (location, stop_name) VALUES ('40.450703, -79.951273','N Craig and Bayard St.')");
//            myDatabase.execSQL("INSERT INTO Route (id) VALUES ('71C')");
//            myDatabase.execSQL("INSERT INTO Route (id) VALUES ('54')");
//            myDatabase.execSQL("INSERT INTO Bus (bus_id, occupation, rest_arrival_time, route_id) VALUES ('1','no info','3min','54')");
//            myDatabase.execSQL("INSERT INTO Bus (bus_id, occupation, rest_arrival_time, route_id) VALUES ('2','100 %','5min','54')");
//            myDatabase.execSQL("INSERT INTO Bus (bus_id, occupation, rest_arrival_time, route_id) VALUES ('3','0 %','18min','71C')");
//            myDatabase.execSQL("INSERT INTO Bus (bus_id, occupation, rest_arrival_time, route_id) VALUES ('4','50 %','25min','71C')");
//            myDatabase.execSQL("INSERT INTO Stop2Route (stop, route_id) VALUES ('40.450703, -79.951273','54')");
//            myDatabase.execSQL("INSERT INTO Stop2Route (stop, route_id) VALUES ('40.450703, -79.951273','71C')");
//        }
//        catch (Exception e){
//            Log.i("ttttttt","QQ");
//            e.printStackTrace();
//        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("On location changed", location.toString());
                // Add a marker and move the camera
                centerOnLocation(location, "Your location");
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2, locationListener);
            Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            centerOnLocation(lastKnowLocation, "Your Location");
        }

        markStop();
    }

    private List<Double> parseLocation(String localtion) {
        List<Double> res = new ArrayList<>();
        String[] ls = localtion.split(", ");
        res.add(Double.parseDouble(ls[0]));
        res.add(Double.parseDouble(ls[1]));
        return res;
    }

    private void markStop() {
        mMap.setOnMarkerClickListener(this);

        ParseQuery<ParseObject> queryStop = ParseQuery.getQuery("Stop");
        queryStop.whereExists("location");

        queryStop.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("Get all stop name", "length " + objects.size());
                } else {
                    Log.i("Fetch error", e.toString());
                }
                for (ParseObject o : objects) {
                    Log.i("stopName", o.getString("stopName"));
                    String locationString = o.getString("location");
                    String stopName = o.getString("stopName");
                    Log.i("hihi","QQ");
                    Log.i("StopResult - location", locationString);
                    Log.i("StopResult - stopName", stopName);

                    List<Double> locations = parseLocation(locationString);
                    Bitmap bm = createStopIcon();
                    LatLng latLng = new LatLng(locations.get(0), locations.get(1));
                    mMap.addMarker(new MarkerOptions().position(latLng)
                            .title(stopName).icon(BitmapDescriptorFactory.fromBitmap(bm)));
                    Log.i("mark stop", stopName);
                }
            }
        });

        // Cursor c = myDatabase.rawQuery("Select * FROM STOP", null);
//        int locIndex = c.getColumnIndex("location");
//        int stopNameIndex = c.getColumnIndex("stop_name") ;
//        //c.moveToFirst();
//        while (c.moveToNext()) {
//
//            String locationString = c.getString(locIndex);
//            String stopName = c.getString(stopNameIndex);
//
//            Log.i("hihi","QQ");
//            Log.i("StopResult - location", locationString);
//            Log.i("StopResult - stopName", stopName);
//
//            List<Double> locations = parseLocation(locationString);
//            Bitmap bm = createStopIcon();
//            LatLng latLng = new LatLng(locations.get(0), locations.get(1));
//            mMap.addMarker(new MarkerOptions().position(latLng)
//                    .title(stopName).icon(BitmapDescriptorFactory.fromBitmap(bm)));
//            Log.i("mark stop", stopName);
//            //c.moveToNext();
//        }
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_directions_bus_black_24dp);


    }

    private Bitmap createStopIcon() {
        //int mpx = R.dimen.stop_icon_size;
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Drawable shape = ContextCompat.getDrawable(MapsActivity.this, R.drawable.bus_stop);
        shape.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        shape.draw(c);
        return bitmap;
    }

    private List<Bus> fetchBusesBasedOnLocation(String location) {
        Log.i("In parse application", "query get run");

        ParseQuery<ParseObject> queryStopName = ParseQuery.getQuery("Stop");
        queryStopName.setLimit(1);
        queryStopName.whereEqualTo("location", location);
        final String[] stopName = new String[1];

        queryStopName.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("Get stop name", "length " + objects.size());
                } else {
                    Log.i("Fetch error", e.toString());
                }
                for (ParseObject o : objects) {
                    Log.i("stop name", o.getString("stopName"));
                    stopName[0] = o.getString("stopName");
                }
            }
        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stop2Route");
        query.whereEqualTo("location", location);
        final List<Bus> res = new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.i("route list", "length " + objects.size());
                } else {
                    Log.i("Fetch error", e.toString());
                }
                List<String> routeList = new ArrayList<>();
                for (ParseObject o : objects) {
                    routeList.add(o.getString("routeId"));
                }
                Log.i("routeList", "length" + routeList.size());
                for (String r : routeList) {
                    ParseQuery<ParseObject> queryRoute = ParseQuery.getQuery("Bus");
                    queryRoute.whereEqualTo("routeId", r);

                    queryRoute.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                Log.i("bus list", "length " + objects.size());
                            } else {
                                Log.i("Fetch error", e.toString());
                            }
                            for (ParseObject o : objects) {
                                Log.i("buses id", o.getString("busId"));
                                res.add(new Bus(o.getString("busId"), o.getString("routeId"), stopName[0], o.getString("restTime"), o.getString("occupation")));
                            }
                        }
                    });
                }
            }
        });

        return res;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String latitude = String.valueOf(marker.getPosition().latitude);
        String longitude = String.valueOf(marker.getPosition().longitude);
        final String stopName = marker.getTitle();
        textView = (TextView) findViewById(R.id.busTitle);
        textView.setText(stopName);

        String location = latitude + ", " + longitude;
        Log.i("query", "location "  + location);

        Thread mainThread = Thread.currentThread();
        Log.i("main thread", mainThread.getName() + mainThread.getId());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stop2Route");
        query.whereEqualTo("location", location);
        final List<Bus> res = new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                Thread queryThread = Thread.currentThread();
                Log.i("query thread", queryThread.getName() + queryThread.getId());
                if (e == null) {
                    Log.i("route list", "length " + objects.size());
                } else {
                    Log.i("Fetch error", e.toString());
                }
                List<String> routeList = new ArrayList<>();
                for (ParseObject o : objects) {
                    routeList.add(o.getString("routeId"));
                }
                Log.i("routeList", "length" + routeList.size());
                for (String r : routeList) {
                    ParseQuery<ParseObject> queryRoute = ParseQuery.getQuery("Bus");
                    queryRoute.whereEqualTo("routeId", r);

                    queryRoute.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            Log.i("query 2 thread", Thread.currentThread().getId() + Thread.currentThread().getName());
                            if (e == null) {
                                Log.i("bus list", "length " + objects.size());
                            } else {
                                Log.i("Fetch error", e.toString());
                            }
                            for (ParseObject o : objects) {
                                Log.i("buses id", o.getString("busId"));
                                res.add(new Bus(o.getString("busId"), o.getString("routeId"), stopName, o.getString("restTime"), o.getString("occupation")));
                            }
                            Log.i("length of res", "in query" + res.size());
                            Log.i("click marker", "clicked!!!!!!!!!!!!");
//                            Log.i("mark latitude", latitude);
//                            Log.i("mark longitude", longitude);
                            Log.i("bus list ", "length " + res.size());
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
                            BusRouteAdapter adapter = new BusRouteAdapter(MapsActivity.this, res);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BusRouteAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    Bus curRoute = busList.get(position);
                                    Log.i("card clicked", "click at " + position);
                                }
                            });
                        }
                    });
                    Log.i("length of res", "" + res.size());
                }
            }
        });

        // busList = res;
        // Cursor c = myDatabase.rawQuery("Select Stop2route.route_id, Bus.occupation from Stop2route left join Bus ON Stop2route.route_id = Bus.route_id where stop = ?", new String[]{location});
        // Cursor c = myDatabase.rawQuery("Select Stop2route.route_id, Bus.occupation, Bus.bus_id, Bus.rest_arrival_time from Stop2route left join Bus ON Stop2route.route_id = Bus.route_id where stop = ?", new String[]{location});
//        int locIndex = c.getColumnIndex("location");
//        int stopNameIndex = c.getColumnIndex("stop_name") ;

//        Log.i("click marker", "clicked!!!!!!!!!!!!");
//        Log.i("mark latitude", latitude);
//        Log.i("mark longitude", longitude);
//        Log.i("bus list ", "length " + busList.size());
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
////        while (c.moveToNext()) {
////
////            String route_id = c.getString(0);
////            String occupation = c.getString(1);
////            String busID = c.getString(2);
////
////            Log.i("hihi","QQ");
////            Log.i("StopResult - location", route_id);
////            Log.i("StopResult - stopName", occupation);
////
////            busList.add(
////                    new Bus(
////                            busID, route_id, stopName, "17 min", occupation));
////        }
//
//        BusRouteAdapter adapter = new BusRouteAdapter(this, busList);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BusRouteAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Bus curRoute = busList.get(position);
//                Log.i("card clicked", "click at " + position);
////                Intent intent = new Intent(this, AddPostActivity.class);
////                intent.putExtra("bus_id", curRoute.getId());
////                intent.putExtra("bus_name", curRoute.getName());
////                intent.putExtra("bus_time", curRoute.getTimeLeft());
////                intent.putExtra("bus_capacity", curRoute.getCapacity());
////                startActivityForResult(intent, 1);
//            }
//        });
        return false;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            Intent refresh = new Intent(this, MapsActivity.class);
//            refresh.putExtra("Date", bundle.getString("Date"));
//            startActivity(refresh);
//            this.finish();
//        }
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
