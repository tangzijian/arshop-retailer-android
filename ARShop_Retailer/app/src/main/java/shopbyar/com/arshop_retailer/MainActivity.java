package shopbyar.com.arshop_retailer;

import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import shopbyar.com.arshop_retailer.model.User;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private Drawer drawer = null;
    private AccountHeader accountHeader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (savedInstanceState == null) {
            drawer.setSelection(1, false);
            selectItem(drawer.getDrawerItem(1));
        }
        ActionBar bar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
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
                fragmentClass = CameraFragment.class;
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
}
