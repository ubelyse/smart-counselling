package com.example.smartcounselling.Fragments;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcounselling.Activities.ChatWithFriendActivity;
import com.example.smartcounselling.Adapters.ListFriendAdapter;
import com.example.smartcounselling.Models.Account;
import com.example.smartcounselling.Models.AccountRequest;
import com.example.smartcounselling.Models.User;
import com.example.smartcounselling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FriendsFragment extends Fragment {

    private Toolbar toolbar;
    private ImageView btnAddFriend;
    private ListView listViewFriend;

    private HashMap<String,Account> hashMapFriends;
    private ListFriendAdapter listFriendAdapter;

    private DatabaseReference nodeRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);

        toolbar = v.findViewById(R.id.toolBarSearch);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        listViewFriend = (ListView)v.findViewById(R.id.listViewFriend);

        hashMapFriends = new HashMap<>();
        listFriendAdapter = new ListFriendAdapter(getActivity(), R.layout.item_friend_in_list_friend, hashMapFriends);
        listViewFriend.setAdapter(listFriendAdapter);

        listViewFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountRequest fr = (AccountRequest)parent.getAdapter().getItem(position);
                Intent iChat = new Intent(getActivity(), ChatWithFriendActivity.class);
                iChat.putExtra("UID_Friend",fr.getUid());
                iChat.putExtra("Name_Friend",fr.getFullName());
                iChat.putExtra("From","Friend_Fragment");
                startActivity(iChat);
            }
        });

        final FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        nodeRoot = FirebaseDatabase.getInstance().getReference();
        String key = nodeRoot.getKey();


        nodeRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hashMapFriends.clear();

                DataSnapshot nodeDoc = dataSnapshot.child("patients");

                for (DataSnapshot snapshot : nodeDoc.getChildren()) {

                    if (!snapshot.getKey().equals(FirebaseAuth.getInstance().getUid())) {
                            Account account = snapshot.getValue(Account.class);
                            if (!hashMapFriends.containsValue(account) && account.getStatus().equals("New")) {
                                hashMapFriends.put(snapshot.getKey(), account);
                            }


                    }
                }


                listFriendAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

}