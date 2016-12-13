package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.PlacesApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PlacesApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MovieActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Favorites;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Ratings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Settings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Watchlist;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Place;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PlacesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    PlacesApiInterface apiService;
    MovieApplication mApp;
    private ActionBarDrawerToggle mToogle;
    NavigationView slideMenu;
    DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mApp = (MovieApplication)getApplicationContext();
        apiService=mApp.getPlacesApiInterface();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        if (mApp.getAccount() == null) {
            slideMenu.getMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.login_);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.login);

        } else {
            slideMenu.getMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.logout);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.logout);
        }

        slideMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.maps:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.settings:
                        if (mApp.getAccount() == null) Alert();
                        else
                        {
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                        }
                        break;
                    case R.id.favorites:
                        if (mApp.getAccount() == null) Alert();
                        else
                        {
                            startActivity(new Intent(getApplicationContext(), Favorites.class));
                        }
                        break;
                    case R.id.watchlist:
                        if (mApp.getAccount() == null) Alert();
                        else
                        {
                            startActivity(new Intent(getApplicationContext(), Watchlist.class));
                        }
                        break;
                    case R.id.ratings:
                        if (mApp.getAccount() == null) Alert();
                        else
                        {
                            startActivity(new Intent(getApplicationContext(), Ratings.class));
                        }
                        break;
                    case R.id.logout:
                        if (mApp.getAccount() == null)
                        {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else {
                            mApp.setAccount(null);
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                            Toast.makeText(getApplicationContext(), R.string.logout_done, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });



    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable location", Toast.LENGTH_LONG).show();
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double x = mLastLocation.getLatitude();
            double y = mLastLocation.getLongitude();
            LatLng currLocation = new LatLng(x, y);
            if (mMap!=null)
            {
                mMap.addMarker(new MarkerOptions().position(currLocation).title("Our location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currLocation.latitude, currLocation.longitude), 12.0f));
            }
           Call<PlacesResponse> call= apiService.SearchPlaces("json", PlacesApiClient.PLACES_API_KEY, String.valueOf(x)+","+String.valueOf(y), 10000, "movie_theater");
            call.enqueue(new Callback<PlacesResponse>() {
                @Override
                public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                    for (Place p : response.body().getResults())
                    {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(p.getGeometry().getLocation().getLat(), p.getGeometry().getLocation().getLog())).title(p.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                    }
                }

                @Override
                public void onFailure(Call<PlacesResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setMessage(R.string.message)
                .setTitle(R.string.Sign_in)
                .setPositiveButton(R.string.sign, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
