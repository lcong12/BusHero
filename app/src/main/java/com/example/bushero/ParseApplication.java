package com.example.bushero;

import android.app.Application;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("3819dea734418af44ce783604dc71475ace9e18c")
                // if defined
                .clientKey("8670e6669c2d80035b39efc188e6c008a7f11e6d")
                .server("http://3.95.222.245:80/parse")
                .build()
        );

//        ParseObject testObject = new ParseObject("People");
//        testObject.put("myNumber", "123");
//        testObject.put("myName", "Cong");
//
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//                    Log.i("Parse Result", "Successful");
//                } else {
//                    Log.i("Parse Result", "Failed");
//                }
//            }
//        });
//
//        ParseObject testRelation = new ParseObject("Class");
//        testRelation.put("people", testObject);
//        testRelation.saveInBackground();

//        ParseObject stop = new ParseObject("Stop");
//        stop.put("location", "40.450703, -79.951273");
//        stop.saveInBackground();
//
//        ParseObject route = new ParseObject("Route");
//        route.put("id", "61A");
//        route.saveInBackground();

//
//        ParseObject bus = new ParseObject("Bus");
//        bus.put("busId", "1");
//        bus.put("occupation", "120%");
//        bus.put("restTime", "2 min");
//        bus.put("routeId", "71C");
//        bus.saveInBackground();
//
//        ParseObject stop2Route = new ParseObject("Stop2Route");
//        stop2Route.put("location", "40.444732, -79.9430851");
//        stop2Route.put("routeId", "61A");
//        stop2Route.saveInBackground();

//        Log.i("In parse application", "query get run");
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Stop2Route");
//        query.whereEqualTo("location", "40.450703, -79.951273");
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    Log.i("route list", "length " + objects.size());
//                }
//                List<String> routeList = new ArrayList<>();
//                for (ParseObject o : objects) {
//                    routeList.add(o.getString("routeId"));
//                }
//                Log.i("routeList", "length" + routeList.size());
//                for (String r : routeList) {
//                    ParseQuery<ParseObject> queryRoute = ParseQuery.getQuery("Bus");
//                    queryRoute.whereEqualTo("routeId", r);
//
//                    queryRoute.findInBackground(new FindCallback<ParseObject>() {
//                        @Override
//                        public void done(List<ParseObject> objects, ParseException e) {
//                            if (e == null) {
//                                Log.i("bus list", "length " + objects.size());
//                            }
//                            for (ParseObject o : objects) {
//                                Log.i("buses id", o.getString("busId"));
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }
}
