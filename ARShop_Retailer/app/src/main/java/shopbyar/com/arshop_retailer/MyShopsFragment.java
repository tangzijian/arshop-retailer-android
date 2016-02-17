package shopbyar.com.arshop_retailer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shopbyar.com.arshop_retailer.model.User;


public class MyShopsFragment extends Fragment {

    public MyShopsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_my_shops);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shops, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.myshopsList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        if (User.currentUser != null) {
            MyShopsAdaptor adaptor = new MyShopsAdaptor(User.currentUser.getShops());
            recList.setAdapter(adaptor);
        }
        return view;
    }
}
