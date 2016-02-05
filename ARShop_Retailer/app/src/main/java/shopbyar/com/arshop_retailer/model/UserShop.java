package shopbyar.com.arshop_retailer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zijiantang on 29/1/16.
 */
public class UserShop {
    @SerializedName("shop_id")
    private Integer shopId;
    @SerializedName("shop_name")
    private String shopName;

    public UserShop() {

    }

    public Integer getShopId() {
        return shopId;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
