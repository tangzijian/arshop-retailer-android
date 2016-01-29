package shopbyar.com.arshop_retailer.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import shopbyar.com.arshop_retailer.rest.service.APIService;

/**
 * Created by zijiantang on 29/1/16.
 */
public class RestClient {

    private static RestClient sharedInstance = null;

    private static final String BASE_URL = "http://dbgpucluster-2.d2.comp.nus.edu.sg/ARShop/api/";
    private APIService apiService;

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public static RestClient getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new RestClient();
        }
        return sharedInstance;
    }

    public APIService getApiService() {
        return apiService;
    }
}
