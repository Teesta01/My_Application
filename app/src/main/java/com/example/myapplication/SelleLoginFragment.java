package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelleLoginFragment extends Fragment {
    TextView forgot_password;
    TextInputEditText login_email,login_password;
    MaterialButton seller_login_btn;
    FirebaseAuth auth;
    final String KEY ="type";
    final String ID_VALUE = "userType";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seller_login_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgot_password = view.findViewById(R.id.seller_forgot_password);
        login_email =view.findViewById(R.id.seller_ogin_email);
        login_password =view.findViewById(R.id.seller_login_password);
        seller_login_btn = view.findViewById(R.id.seller_login_btn);
        auth = FirebaseAuth.getInstance();
        seller_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                final String email,password;
                email = login_email.getText().toString().trim();
                password = login_password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    login_email.setError("Empty Field");
                    return;
                } else if (!email.matches(Expn)) {
                    login_email.setError("Invalid email");
                    return;
                }else if (TextUtils.isEmpty(password)) {
                    login_password.setError("Empty Field");
                    return;
                }else{
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String fbuserID = task.getResult().getUser().getUid();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("SellerInfo");
                                ref.child(fbuserID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        SellerInfo s = dataSnapshot.getValue(SellerInfo.class);
                                        String type = s.getType();

                                        SharedPreferences preferences = getActivity().getSharedPreferences(ID_VALUE, 0);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString(KEY, type);
                                        editor.commit();

                                        startActivity(new Intent(getActivity(),SellerActivity.class));
                                        getActivity().finish();

                                        Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }else{
                                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SellerForgotPassword());
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
