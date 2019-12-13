package com.example.myapplication;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class SignupFragment extends Fragment {
    private TextInputEditText s_email,s_usernmae,s_password,s_c_password,s_mobile_no;
    private MaterialButton signup_btn;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    UserInfo userInfo;
    ProgressBar singupProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        s_email = view.findViewById(R.id.signup_email);
        s_usernmae = view.findViewById(R.id.signup_username);
        s_mobile_no = view.findViewById(R.id.signup_user_phoneno);
        s_password = view.findViewById(R.id.signup_password);
        s_c_password = view.findViewById(R.id.signup_c_password);
        signup_btn = view.findViewById(R.id.signup_btn);
        singupProgress = view.findViewById(R.id.signup_progress);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                final String email, username, mobile_no, password, c_password;
                email = s_email.getText().toString().trim();
                username = s_usernmae.getText().toString().trim();
                password = s_password.getText().toString().trim();
                mobile_no = s_mobile_no.getText().toString().trim();
                c_password = s_c_password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    s_email.setError("Empty Field");
                    return;
                } else if (!email.matches(Expn)) {
                    s_email.setError("Invalid email");
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    s_usernmae.setError("Empty Field");
                    return;
                } else if (TextUtils.isEmpty(mobile_no)) {
                    s_mobile_no.setError("Empty Field");

                } else if (mobile_no.length() != 10) {
                    s_mobile_no.setError("should consists of 10 digits");
                } else if (TextUtils.isEmpty(password)) {
                    s_password.setError("Empty Field");
                    return;
                } else if (TextUtils.isEmpty(c_password)) {
                    s_c_password.setError("Empty Field");
                    return;
                } else if (!TextUtils.equals(password, c_password)) {
                    s_c_password.setError("password is not matching");
                    return;
                } else if (password.length() < 8) {
                    s_password.setError("password should have at least 8 characters");
                } else{
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    singupProgress.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = task.getResult().getUser().getUid();
                                String createdON = DateFormat.getDateTimeInstance().format(new Date());
                                userInfo = new UserInfo(email, username, mobile_no, createdON,"User");
                                DatabaseReference myRef = databaseReference.child("users").child(userID);
                                myRef.setValue(userInfo);
                                Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();
                                singupProgress.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            } else {
                                singupProgress.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
            }


            }
        });


    }


}
