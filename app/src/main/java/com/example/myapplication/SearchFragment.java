package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    EditText search_product_edt;
    ImageView messages;
    final String SHARED_NAME_STRING = "search_sharedpref";
    final String USER_NAME_STRING = "search_bar_data";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_product_edt = view.findViewById(R.id.search_product_edt);


        SharedPreferences prefs = getActivity().getSharedPreferences(SHARED_NAME_STRING,Context.MODE_PRIVATE);
        search_product_edt.setText(prefs.getString(USER_NAME_STRING,null));


        messages = view.findViewById(R.id.message);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        String edt_data = search_product_edt.getText().toString();
        if (search_product_edt != null){
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(SHARED_NAME_STRING, Context.MODE_PRIVATE).edit();
            editor.putString(USER_NAME_STRING,edt_data);
            editor.apply();




        }
    }


}
