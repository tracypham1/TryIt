package com.example.tryit.ui.stores;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tryit.MainActivity;
import com.example.tryit.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

import android.location.Address;
import java.util.List;

public class StoresFragment extends Fragment implements LocationListener {

    private StoresViewModel storesViewModel;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    private static final int REQUEST_LOCATION = 101;

    Button btlocation;

    GoogleMap googleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storesViewModel =
                ViewModelProviders.of(this).get(StoresViewModel.class);

        View root = inflater.inflate(R.layout.fragment_stores, container, false);

        supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        btlocation = root.findViewById(R.id.location);

        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {

                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
                            return;

                        }

                        googleMap.setMyLocationEnabled(true);
                        Criteria criteria = new Criteria();
                        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                        String provider = locationManager.getBestProvider(criteria, true);
                        Location location = locationManager.getLastKnownLocation(provider); //testing: getCurrentLocation(provider);
                        //----------testing-----------
//                        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//                        System.out.println("getting");
//                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Location> task) {
//                                System.out.println("here1");
//                                Location loc = task.getResult();
//                                System.out.println("here");
//                                try {
//                                    if(loc != null) {
//                                        System.out.println("getting1");
//                                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                                        List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
//                                        System.out.println(addresses);
//
//                                    } else System.out.println("getting2");
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
                        //----------------
                        if(location == null){
                            System.out.println("issue");
                        }else{
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            LatLng myPosition = new LatLng(latitude, longitude);
                            MarkerOptions markerOptions = new MarkerOptions().position(myPosition).title("here");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10));
                            googleMap.addMarker(markerOptions);
                        }

                    }
                });
            }
        });

         return root;
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}
