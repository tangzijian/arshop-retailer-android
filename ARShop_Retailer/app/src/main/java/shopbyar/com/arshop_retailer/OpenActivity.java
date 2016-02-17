package shopbyar.com.arshop_retailer;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
    }

    @Override
    protected void onResume() {
        Log.d("OpenActity", "onResume");
        super.onResume();
        final User currentUser = User.fetchCurrentUser(PreferenceManager.getDefaultSharedPreferences(this));
        if (currentUser == null) {
            gotoLoginActivity();
        } else {
            gotoMainActiviy();
//            Call<User> call = RestClient.getSharedInstance().getApiService().userVerifyToken(currentUser);
//            final OpenActivity self = this;
//            call.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Response<User> response, Retrofit retrofit) {
//                    User user = User.currentUser;
//                    if (response.body().code.equals("200")) {
//                        User.currentUser = response.body();
//                        User.saveCurrentUser(PreferenceManager.getDefaultSharedPreferences(self));
//                        gotoMainActiviy();
//                    } else {
//                        gotoLoginActivity();
//                    }
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    gotoLoginActivity();
//                }
//            });
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
