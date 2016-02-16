package shopbyar.com.arshop_retailer;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import shopbyar.com.arshop_retailer.model.User;
import shopbyar.com.arshop_retailer.rest.RestClient;


public class OpenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        User currentUser = User.fetchCurrentUser(PreferenceManager.getDefaultSharedPreferences(this));
        if (currentUser == null) {
            gotoLoginActivity();
        } else {
            Call<User> call = RestClient.getSharedInstance().getApiService().userVerifyToken(currentUser);
            final OpenActivity self = this;
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    User.currentUser = response.body();
                    User.saveCurrentUser(PreferenceManager.getDefaultSharedPreferences(self));
                    gotoMainActiviy();
                }

                @Override
                public void onFailure(Throwable t) {
                    gotoLoginActivity();
                }
            });
        }
    }

    public void gotoLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void gotoMainActiviy() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
