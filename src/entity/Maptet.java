/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxmaps.swing.MapView;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.Marker;

public class Maptet extends MapView {

    /**
     * The map object
     */
    private static Map map;

    public Marker generateMarker(LatLng pos) {

        Marker marker = new Marker(map);
        marker.setPosition(pos);
        map.setCenter(pos);

        System.out.println("Graficado marker");
        return marker;
    }
    double x;
    double y;

    public Maptet() {

        JFrame frame = new JFrame("Ariana-Data: ");

        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
                    mapOptions.setMapTypeControlOptions(controlOptions);

                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(36.8665, 10.1647));
                    map.setCenter(new LatLng(36.8093, 10.1647));
                    map.setZoom(10);
                    Marker marker = new Marker(map);
                    Marker marker1 = new Marker(map);
                    LatLng pos = new LatLng(36.8665, 10.0863);
                    marker.setPosition(map.getCenter());

                }
            }

        });

        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Maptet(double x, double y) {
        this.x = x;
        this.y = y;

        JFrame frame = new JFrame("Location-Data: ");

        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
                    mapOptions.setMapTypeControlOptions(controlOptions);

                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(x, y));

                    map.setZoom(10);
                    Marker marker = new Marker(map);

                    marker.setPosition(map.getCenter());

                }
            }

        });

        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
