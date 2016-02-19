package shopbyar.com.arshop_retailer;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import shopbyar.com.arshop_retailer.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhotosFragment extends Fragment {


    public MyPhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_my_photos);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_photos, container, false);
        RecyclerView recList = (RecyclerView) view.findViewById(R.id.myphotosList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        File dir = getActivity().getExternalFilesDir(null);
        List<String> list = FileUtils.getPhotoFolderNames(dir);
        list.add(0, getString(R.string.add_new_folder));
        MyPhotosAdaptor adaptor = new MyPhotosAdaptor(list);
        recList.setAdapter(adaptor);
        return view;
    }
}
