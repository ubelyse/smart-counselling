package com.example.smartcounselling.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcounselling.Fragments.DatePickerFragment;
import com.example.smartcounselling.Models.Doctor;
import com.example.smartcounselling.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class myadapter extends FirebaseRecyclerAdapter<Doctor,myadapter.myviewholder>
{

    public myadapter(@NonNull FirebaseRecyclerOptions<Doctor> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Doctor model)
    {
        holder.name.setText(model.getFullName());
        holder.role.setText(model.getRole());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontentdoc))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText address=myview.findViewById(R.id.address);
                final EditText descri=myview.findViewById(R.id.descri);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText phoneno=myview.findViewById(R.id.dphone);



                final CheckBox txtrole = myview.findViewById(R.id.checkbox_Addict);
                final CheckBox txtrole1 = myview.findViewById(R.id.checkbox_Depressed);

                final EditText email=myview.findViewById(R.id.uemail);

                Button submit=myview.findViewById(R.id.usubmit);

                address.setText(model.getAddress());
                descri.setText(model.getDescription());
                name.setText(model.getFullName());
                phoneno.setText(model.getPhoneNumber());


                txtrole.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            txtrole1.setChecked(false);
                        }
                    }
                });

                txtrole1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            txtrole.setChecked(false);
                        }
                    }
                });


                final String[] role = {""};

                email.setText(model.getUsername());

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("address",address.getText().toString());
                        map.put("description",descri.getText().toString());
                        map.put("fullName",name.getText().toString());
                        map.put("phoneNumber",phoneno.getText().toString());



                        if (txtrole.isChecked()){
                            role[0] ="Addict Doctor";
                        }
                        else if(txtrole1.isChecked()){
                            role[0] ="Psychologist";
                        }

                        map.put("role",role[0]);


                        map.put("email",email.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("doctors")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Doctor");
                builder.setMessage("Are You Sure You want to Delete this Person?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("doctors")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView name,role;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            role=(TextView)itemView.findViewById(R.id.crole);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }

}
