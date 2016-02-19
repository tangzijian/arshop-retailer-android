package shopbyar.com.arshop_retailer;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPhotoFolderFragment extends Fragment {
    @InjectView(R.id.input_folder_name)
    EditText mFolderName;
    @InjectView(R.id.btn_add)
    Button mAddBtn;

    public AddPhotoFolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_photo_folder, container, false);
        ButterKnife.inject(this, view);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFolder();
            }
        });
        return view;
    }

    public void addFolder() {
        String name = mFolderName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getActivity().getBaseContext(), "Empty folder name.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!validate()) {
            Toast.makeText(getActivity().getBaseContext(), "Alphabetic and numeric characters or space only.", Toast.LENGTH_LONG).show();
            return;
        }
        File dir = new File(getActivity().getExternalFilesDir(null), name);
        if (dir.exists()) {
            Toast.makeText(getActivity().getBaseContext(), "Folder exists. Please try another name", Toast.LENGTH_LONG).show();
            return;
        }
        boolean success = FileUtils.createFolder(dir);
        if (success) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getActivity().getBaseContext(), "Failed to create folder. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validate() {
        String name = mFolderName.getText().toString().trim();
        if (name.isEmpty()) {
            return false;
        }
        return name.matches("[a-zA-Z0-9 ]+");
    }
}
