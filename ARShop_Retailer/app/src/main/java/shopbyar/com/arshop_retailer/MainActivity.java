package shopbyar.com.arshop_retailer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import butterknife.ButterKnife;
import butterknife.InjectView;
import shopbyar.com.arshop_retailer.model.User;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    protected Drawer drawer = null;
    private AccountHeader accountHeader = null;

    private LocationScanner mLocationScanner;
    private OrientationScanner mOrientationScanner;
    private WifiScanner mWifiScanner;

    private static int PERMISSION_REQUEST_CODE = 10086;

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<String> permissionNeeded = new ArrayList<>();
        final List<String> permissionsToBeGranted = new ArrayList<>();
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.CAMERA)) {
            permissionNeeded.add("Camera");
        }
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.ACCESS_WIFI_STATE)) {
            permissionNeeded.add("Access WIFI state");
        }
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.CHANGE_WIFI_STATE)) {
            permissionNeeded.add("Change WIFI state");
        }
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionNeeded.add("Location");
        }
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionNeeded.add("Write External Storage");
        }
        if (!addPermission(permissionsToBeGranted, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionNeeded.add("Read External Storage");
        }
        if (permissionsToBeGranted.size() > 0) {
            if (permissionNeeded.size() > 0) {
                final MainActivity self = this;
                String message = "You need to grant access to " + permissionNeeded.get(0);
                for (int i = 1; i < permissionNeeded.size(); i++)
                    message = message + ", " + permissionNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(self, permissionsToBeGranted.toArray(new String[permissionsToBeGranted.size()]),
                                        PERMISSION_REQUEST_CODE);
                            }
                        });
                return;
            }
            ActivityCompat.requestPermissions(this, permissionsToBeGranted.toArray(new String[permissionsToBeGranted.size()]), PERMISSION_REQUEST_CODE);
            return;
        }

        PermissionCheck.init(this);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        // Handle Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        // Sample Profile
        final IProfile profile = new ProfileDrawerItem().withName("Tang Zijian").withEmail("tangzj77@gmail.com").withIcon("http://lh3.googleusercontent.com/-awMpgJZO4oA/AAAAAAAAAAI/AAAAAAAAABQ/hpbDt1I6g7M/photo.jpg");
        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .withSavedInstance(savedInstanceState)
                .build();
        // Create Drawer
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Camera").withIcon(GoogleMaterial.Icon.gmd_camera).withIdentifier(1),
                        new PrimaryDrawerItem().withName("My Shops").withIcon(GoogleMaterial.Icon.gmd_shopping_basket).withIdentifier(2),
                        new PrimaryDrawerItem().withName("My Photos").withIcon(GoogleMaterial.Icon.gmd_book_photo).withIdentifier(3),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Logout").withIcon(GoogleMaterial.Icon.gmd_power_off).withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        selectItem(drawerItem);
                        return false;
                    }
                })
                .build();

        ActionBar bar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        initLocationScanner();
        initOrientationScanner();
        initWifiScanner();

        if (savedInstanceState == null) {
            drawer.setSelection(1, true);
//            selectItem(drawer.getDrawerItem(1));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 6) {
                drawer.setSelection(1, true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        }
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            return;
        }
        // disable going back to the OpenActivity
        moveTaskToBack(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = accountHeader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    void selectItem(IDrawerItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch ((int)item.getIdentifier()) {
            case 1:
                fragmentClass = CameraFragment.class;
                break;
            case 2:
                fragmentClass = MyShopsFragment.class;
                break;
            case 3:
                fragmentClass = MyPhotosFragment.class;
                break;
            case 4: // Logout
                userLogout();
                return;
            default:
                fragmentClass = CameraFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void userLogout() {
        User.destoryCurrentUser(PreferenceManager.getDefaultSharedPreferences(this));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLocationScanner != null) {
            mLocationScanner.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationScanner.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationScanner.onResume();
        mOrientationScanner.onResume();
        mWifiScanner.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationScanner.onPause();
        mOrientationScanner.onPause();
        mWifiScanner.onPause();
    }


    private void initLocationScanner() {
        mLocationScanner = new LocationScanner(this);
        mLocationScanner.setListener(new LocationScanner.Listener() {
            @Override
            public void set(Location location) {
                switchColor(findViewById(R.id.control_gps));
                ImageMeta.setLocation(location);
            }
        });
        mLocationScanner.onCreate();
    }

    private void initOrientationScanner() {
        mOrientationScanner = new OrientationScanner(this);
        mOrientationScanner.setListener(new OrientationScanner.Listener() {
            @Override
            public void setAzimuthPitchRoll(float[] apr) {
                switchColor(findViewById(R.id.control_orien));
                ImageMeta.setAPR(apr);
            }
        });
        mOrientationScanner.onCreate();
    }

    private void initWifiScanner() {
        mWifiScanner = new WifiScanner(this);
        mWifiScanner.setListener(new WifiScanner.Listener() {
            @Override
            public void setWifiList(List<ScanResult> wifiScanList) {
                switchColor(findViewById(R.id.control_wifi));
                ImageMeta.setWifiList(wifiScanList);
            }
        });
    }

    private void switchColor(View viewById) {
        if (viewById != null) {
            try {
                int color = Color.TRANSPARENT;
                Drawable background = viewById.getBackground();
                if (background instanceof ColorDrawable) {
                    color = ((ColorDrawable) background).getColor();
                }
                if (color == Color.WHITE) {
                    viewById.setBackgroundColor(Color.GREEN);
                }
                else {
                    viewById.setBackgroundColor(Color.WHITE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
