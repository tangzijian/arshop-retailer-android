package shopbyar.com.arshop_retailer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shopbyar.com.arshop_retailer.model.UserShop;

/**
 * Created by zijiantang on 17/2/16.
 */
public class MyShopsAdaptor extends RecyclerView.Adapter<MyShopsAdaptor.MyShopsViewHolder> {
    private List<UserShop> shopList;

    public MyShopsAdaptor(List<UserShop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public void onBindViewHolder(MyShopsViewHolder holder, int position) {
        UserShop shop = shopList.get(position);
        holder.shopName.setText(shop.getShopName());
    }

    @Override
    public MyShopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_shops_card_layout, parent, false);
        return new MyShopsViewHolder(view);
    }

    public static class MyShopsViewHolder extends RecyclerView.ViewHolder {
        protected ImageView shopIcon;
        protected TextView shopName;

        public MyShopsViewHolder(View v) {
            super(v);
            shopIcon = (ImageView) v.findViewById(R.id.shop_icon);
            shopName = (TextView) v.findViewById(R.id.shop_name);
        }
    }
}
