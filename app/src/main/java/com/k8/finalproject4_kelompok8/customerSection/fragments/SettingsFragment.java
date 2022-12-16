package com.k8.finalproject4_kelompok8.customerSection.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.customerSection.ChangePasswordActivity;
import com.k8.finalproject4_kelompok8.customerSection.EditProfileActivity;
import com.k8.finalproject4_kelompok8.customerSection.ReservattionHistoryActivity;
import com.k8.finalproject4_kelompok8.userAccount.UserAccountActivity;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {
    Button logout;
    CardView profileCard,aboutCard,viewReservations,passwordCard;
    CircleImageView setprofile;
    TextView aboutUsDesTV, up,down, setname, setemail, setcontact;
    boolean showAboutContent = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout=view.findViewById(R.id.logoutBtn);
        profileCard=view.findViewById(R.id.profileCard);
        aboutCard=view.findViewById(R.id.aboutCard);
        setprofile=view.findViewById(R.id.setprofile);
        setemail=view.findViewById(R.id.setemail);
        setname=view.findViewById(R.id.setname);
        setcontact=view.findViewById(R.id.setcontact);
        up=view.findViewById(R.id.upArrow);
        down=view.findViewById(R.id.downArrow);
        aboutUsDesTV=view.findViewById(R.id.aboutUsDesTV);
        viewReservations=view.findViewById(R.id.viewReservations);
        passwordCard=view.findViewById(R.id.passwordCard);

        onClickListeners();
        setMethod();
    }

    private void setMethod() {
        setname.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.NAME));
        setemail.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.EMAIL));
        setcontact.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.CONTACT));
        String imageURL=SharedPreferenceUtils.getStringPreference(getContext(),Constants.PROFILE_PIC);
        Picasso.get().load(imageURL).into(setprofile);
    }

    @Override
    public void onResume() {
        super.onResume();
        setname.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.NAME));
        setemail.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.EMAIL));
        setcontact.setText(SharedPreferenceUtils.getStringPreference(getContext(),Constants.CONTACT));
        String imageURL=SharedPreferenceUtils.getStringPreference(getContext(),Constants.PROFILE_PIC);
        Picasso.get().load(imageURL).into(setprofile);
    }

    private void onClickListeners() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED_IN_KEY, false);
                SharedPreferenceUtils.setStringPreference(getContext(), Constants.API_KEY, "");
                Intent intent=new Intent(getActivity(), UserAccountActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Pending Work", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        viewReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), ReservattionHistoryActivity.class);
                  startActivity(intent);
            }
        });

        passwordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showAboutContent){
                    aboutUsDesTV.setVisibility(View.VISIBLE);
                    down.setVisibility(View.GONE);
                    up.setVisibility(View.VISIBLE);
                    showAboutContent=false;
                }else{
                    aboutUsDesTV.setVisibility(View.GONE);
                    up.setVisibility(View.GONE);
                    down.setVisibility(View.VISIBLE);
                    showAboutContent=true;
                }

            }
        });
    }
}