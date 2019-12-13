package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegistrationFragment extends Fragment {
    private TextView signUp,logIn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFragment(new SignupFragment());
        signUp = view.findViewById(R.id.user_signup);
        logIn = view.findViewById(R.id.user_login);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn.setTextColor(getResources().getColor(R.color.grey_40));
                signUp.setTextColor(getResources().getColor(R.color.bottom_orange));
                loadFragment(new SignupFragment());

            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn.setTextColor(getResources().getColor(R.color.bottom_orange));
                signUp.setTextColor(getResources().getColor(R.color.grey_40));
                loadFragment(new LoginFragment());

            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.reg_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
