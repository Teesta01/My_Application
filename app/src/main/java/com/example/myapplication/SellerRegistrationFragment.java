package com.example.myapplication;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SellerRegistrationFragment extends Fragment {
    TextView login,signup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seller_registration_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFragment(new SellerSignUpFragment());
        login = view.findViewById(R.id.user_login);
        signup = view.findViewById(R.id.user_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setTextColor(getResources().getColor(R.color.bottom_orange));
                signup.setTextColor(getResources().getColor(R.color.grey_40));
                loadFragment(new SelleLoginFragment());


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setTextColor(getResources().getColor(R.color.grey_40));
                signup.setTextColor(getResources().getColor(R.color.bottom_orange));
                loadFragment(new SellerSignUpFragment());


            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.seller_reg_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
