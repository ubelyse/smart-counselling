package com.example.smartcounselling.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcounselling.Activities.ChangePassWordActivity;
import com.example.smartcounselling.Activities.MainPatientActivity;
import com.example.smartcounselling.Logins.LoginActivity;
import com.example.smartcounselling.Models.Doctor;
import com.example.smartcounselling.R;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;


public class DoctorSettingsFragment extends Fragment implements View.OnClickListener{

    private Button btnLogout, btnChangePassWord;
    private Button btnPrivatePolicy, btnAboutApp, btnPayAPp;

    FirebaseAuth firebaseAuth;
    private String uid;
    private Doctor account;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    String status = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        btnLogout = v.findViewById(R.id.btnLogout);
        btnChangePassWord = v.findViewById(R.id.btnAccountPolicy);
        btnPayAPp = v.findViewById(R.id.btnPayApp);

        btnPayAPp.setOnClickListener(this);

        btnLogout.setOnClickListener(this);
        btnChangePassWord.setOnClickListener(this);
        ;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnAboutApp = v.findViewById(R.id.btnAboutApp);

        btnAboutApp.setOnClickListener(this);

        account = new Doctor();

        Bundle bundle = getArguments();
        if (bundle != null) {
            uid = bundle.getString("UID");
        }

        if (uid != null) {
            Log.d("Check Bundle: ", uid);
        }


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                firebaseAuth.getInstance().signOut();
                saveFile();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnAccountPolicy:
                Intent intent2 = new Intent(getActivity(), ChangePassWordActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnAboutApp:
                Toast.makeText(getActivity(), "SMART COUNSELLING MOBILE APP",
                        Toast.LENGTH_SHORT).show();

            case R.id.btnPayApp:

                Bundle bundle = getArguments();
                if (bundle != null) {
                    uid = bundle.getString("UID");
                }
                if (uid != null) {
                    Log.d("Check Bundle: ", uid);

                    databaseReference.child("doctors").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> node = snapshot.getChildren();

                            for (DataSnapshot child : node) {
                                if (child.getKey().equals(uid)) {
                                    account = child.getValue(Doctor.class);
                                }

                                if (null != account) {
                                    new RaveUiManager(DoctorSettingsFragment.this).setAmount(100)
                                            .setCurrency("RWF")
                                            .setEmail(account.getUsername())
                                            .setfName(account.getFullName())
                                            .setlName(account.getFullName())
                                            .setNarration("narration")
                                            .setPublicKey("FLWPUBK_TEST-b731960372b6afd5d5d4dd2adcf5336d-X")
                                            .setEncryptionKey("FLWSECK_TESTd6b28b6e96b3")
                                            .setTxRef("txRef")
                                            .setPhoneNumber(account.getPhoneNumber(), true)
                                            .acceptAccountPayments(false)
                                            .acceptCardPayments(true)
                                            .acceptMpesaPayments(false)
                                            .acceptAchPayments(false)
                                            .acceptGHMobileMoneyPayments(false)
                                            .acceptUgMobileMoneyPayments(false)
                                            .acceptZmMobileMoneyPayments(false)
                                            .acceptRwfMobileMoneyPayments(true)
                                            .acceptSaBankPayments(false)
                                            .acceptUkPayments(false)
                                            .acceptBankTransferPayments(false)
                                            .acceptUssdPayments(false)
                                            .acceptBarterPayments(false)
                                            .allowSaveCardFeature(false)
                                            .onStagingEnv(false)
                                            .withTheme(R.style.MyCustomTheme)
                                            .initialize();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                break;
        }

    }



    public  void saveFile()
    {
        try {

            // Opens a file recording stream.
            FileOutputStream out = getActivity().openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this.getActivity(), "SUCCESS \n " + "THANK YOU FOR SUCCESSFULY WORKING WITH SMART COUNSELING", Toast.LENGTH_SHORT).show();

            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this.getActivity(), "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this.getActivity(), "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}