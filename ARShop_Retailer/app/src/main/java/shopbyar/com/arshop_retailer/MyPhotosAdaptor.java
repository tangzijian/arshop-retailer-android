package shopbyar.com.arshop_retailer;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zijiantang on 18/2/16.
 */
public class MyPhotosAdaptor extends RecyclerView.Adapter<MyPhotosAdaptor.MyPhotosViewHolder> {
    private List<String> folderList;

    public MyPhotosAdaptor(List<String> folderList) {
        this.folderList = folderList;
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    @Override
    public void onBindViewHolder(MyPhotosViewHolder holder, final int position) {
        holder.folderName.setText(folderList.get(position));
    }

    @Override
    public MyPhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_photos_card_layout, parent, false);
        return new MyPhotosViewHolder(view);
    }

    public static class MyPhotosViewHolder extends RecyclerView.ViewHolder {
        protected ImageView folderThumbnail;
        protected TextView folderName;

        public MyPhotosViewHolder(View v) {
            super(v);
            folderThumbnail = (ImageView) v.findViewById(R.id.folder_thumbnail);
            folderName = (TextView) v.findViewById(R.id.folder_name);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPhotosGridFragment fragment = new MyPhotosGridFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("folder_name", folderName.getText().toString());
                    fragment.setArguments(bundle);
                    ((AppCompatActivity) v.getContext()).getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
