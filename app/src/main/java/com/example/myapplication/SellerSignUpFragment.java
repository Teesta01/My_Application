package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ProgressBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class SellerSignUpFragment extends Fragment {
    CheckBox app_acess,app_men,app_women,auto_mobile,beauty,cell,computer,cosumer_elc,
                elc_comp,furniture,home,homr_app,jewel,luaggage,kids,shoes,sports,tools,
                toys,underwear,watches;
    MaterialButton select_category,add_cateoory,sign_up;
    TextView selected_categories;
    TextInputEditText email_edt,username_edt,shopname_edt,shop_address_edt,
            mobile_no_edt,password_edt,c_password_edt;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    SellerInfo sellerInfo;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seller_signup_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar= view.findViewById(R.id.seller_signup_progress);
        select_category = view.findViewById(R.id.select_category_btn);
        selected_categories = view.findViewById(R.id.select_category_txt);
        email_edt = view.findViewById(R.id.seller_signup_email);
        username_edt = view.findViewById(R.id.seller_signup_username);
        shopname_edt = view.findViewById(R.id.seller_signup_shopname);
        shop_address_edt = view.findViewById(R.id.seller__location);
        mobile_no_edt = view.findViewById(R.id.signup_seller_phoneno);
        password_edt = view.findViewById(R.id.seller_signup_password);
        c_password_edt = view.findViewById(R.id.seller_signup_c_password);
        sign_up = view.findViewById(R.id.seller_signup_btn);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
                final String email,username,shopname,shop_address,category,mobile_no,password,c_password;
                email = email_edt.getText().toString().trim();
                username = username_edt.getText().toString().trim();
                shopname = shopname_edt.getText().toString().trim();;
                shop_address = shop_address_edt.getText().toString().trim();
                category = selected_categories.getText().toString().trim();
                mobile_no = mobile_no_edt.getText().toString().trim();
                password =password_edt.getText().toString().trim();
                c_password =c_password_edt.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    email_edt.setError("Empty Field");
                    return;
                } else if (!email.matches(Expn)) {
                    email_edt.setError("Invalid email");
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    username_edt.setError("Empty Field");
                    return;
                }
                else if(TextUtils.isEmpty(shopname)){
                    shopname_edt.setError("Empty Field");
                    return;
                }else if(TextUtils.isEmpty(shop_address)){
                    shop_address_edt.setError("Empty Field");
                    return;

                }
                else if (TextUtils.isEmpty(mobile_no)) {
                    mobile_no_edt.setError("Empty Field");
                    return;

                } else if (mobile_no.length() != 10) {
                    mobile_no_edt.setError("should consists of 10 digits");
                    return;
                }
                else if(category.matches("select product categories")){
                    selected_categories.setText("no category selected");
                    selected_categories.setTextColor(getResources().getColor(R.color.error));
                    return;

                }else if (TextUtils.isEmpty(password)) {
                    password_edt.setError("Empty Field");
                    return;
                } else if (TextUtils.isEmpty(c_password)) {
                    c_password_edt.setError("Empty Field");
                    return;
                } else if (!TextUtils.equals(password, c_password)) {
                    c_password_edt.setError("password is not matching");
                    return;
                } else if (password.length() < 8) {
                    password_edt.setError("password should have at least 8 characters");
                    return;
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                String userID = task.getResult().getUser().getUid();
                                String createdON = DateFormat.getDateTimeInstance().format(new Date());
                                //userInfo = new UserInfo(email, username, mobile_no, createdON);
                                sellerInfo = new SellerInfo(email,username,shopname,shop_address,mobile_no,category,createdON,"seller");
                                DatabaseReference myRef = databaseReference.child("SellerInfo").child(userID);
                                myRef.setValue(sellerInfo);
                                Toast.makeText(getActivity(), "User Created", Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                email_edt.setText("");
                                username_edt.setText("");
                                shopname_edt.setText("");
                                shop_address_edt.setText("");
                                mobile_no_edt.setText("");
                                selected_categories.setText("select product categories");
                                password_edt.setText("");
                                c_password_edt.setText("");

                            }else {
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }
        });
        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryCheckBoxDialog();

            }
        });
    }
    private void showCategoryCheckBoxDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.all_categories_dialog);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        app_acess = dialog.findViewById(R.id.cat_app_acces);
        app_men = dialog.findViewById(R.id.cat_app_men);
        app_women = dialog.findViewById(R.id.cat_app_women);
        auto_mobile = dialog.findViewById(R.id.cat_app_auto);
        beauty = dialog.findViewById(R.id.cat_app_beauty);
        cell = dialog.findViewById(R.id.cat_app_cell);
        cosumer_elc = dialog.findViewById(R.id.cat_app_electronics);
        elc_comp = dialog.findViewById(R.id.cat_app_elec_compo);
        computer = dialog.findViewById(R.id.cat_app_computer);
        furniture = dialog.findViewById(R.id.cat_app_Furniture);
        home = dialog.findViewById(R.id.cat_app_home);
        homr_app = dialog.findViewById(R.id.cat_app_home_app);
        jewel = dialog.findViewById(R.id.cat_app_jewelry);
        luaggage = dialog.findViewById(R.id.cat_app_luggage);
        kids = dialog.findViewById(R.id.cat_app_kids);
        shoes = dialog.findViewById(R.id.cat_app_shoes);
        sports = dialog.findViewById(R.id.cat_app_sports);
        tools = dialog.findViewById(R.id.cat_app_tools);
        toys = dialog.findViewById(R.id.cat_app_toys);
        underwear = dialog.findViewById(R.id.cat_app_underwear);
        watches = dialog.findViewById(R.id.cat_app_watches);
        add_cateoory = dialog.findViewById(R.id.add_categories);
        add_cateoory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if (app_acess.isChecked())
                    result += ","+getString(R.string.apparel_accessories);
                if (app_men.isChecked())
                    result += ","+getString(R.string.apparel_for_men);
                if (app_women.isChecked())
                    result += ","+getString(R.string.apparel_for_women);
                if (auto_mobile.isChecked())
                    result += ","+getString(R.string.automobile_and_motorcycles);
                if (beauty.isChecked())
                    result += ","+getString(R.string.beauty_and_health);
                if (cell.isChecked())
                    result += ","+getString(R.string.cellphones);
                if (computer.isChecked())
                    result += ","+getString(R.string.computer_and_office);
                if (cosumer_elc.isChecked())
                    result += ","+getString(R.string.consumer_electronics);
                if (elc_comp.isChecked());
                    result += ","+getString(R.string.electronic_components);
                if (furniture.isChecked())
                    result += ","+getString(R.string.furniture);
                if (home.isChecked())
                    result += ","+getString(R.string.home_and_garden);
                if (homr_app.isChecked())
                    result += ","+getString(R.string.home_appliances);
                if (jewel.isChecked())
                    result += ","+getString(R.string.jewelry_and_accessories);
                if (kids.isChecked())
                    result += ","+getString(R.string.mother_and_kids);
                if (shoes.isChecked())
                    result += ","+getString(R.string.shoes);
                if (sports.isChecked())
                    result += ","+getString(R.string.sports);
                if (toys.isChecked())
                    result += ","+getString(R.string.toys);
                if (tools.isChecked())
                    result += ","+getString(R.string.watches);
                if (underwear.isChecked())
                    result += ","+getString(R.string.underwear_and_sleepwears);
                if (watches.isChecked())
                    result += ","+getString(R.string.watches);
                selected_categories.setText(result);
                selected_categories.setTextColor(getResources().getColor(R.color.grey_40));
                selected_categories.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
