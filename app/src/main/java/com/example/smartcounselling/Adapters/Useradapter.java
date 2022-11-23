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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcounselling.Models.Doctor;
import com.example.smartcounselling.Models.User;
import com.example.smartcounselling.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Useradapter extends FirebaseRecyclerAdapter<User,Useradapter.myviewholder>
{

    public Useradapter(@NonNull FirebaseRecyclerOptions<User> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final User model)
    {
        holder.name.setText(model.getFullName());
        holder.phone.setText(model.getPhoneNumber());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();

                final CheckBox txtDisease,txtDisease1;
                final RadioGroup radioGroupDisease;

                final EditText address=myview.findViewById(R.id.address);
                final EditText descri=myview.findViewById(R.id.descri);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText phoneno=myview.findViewById(R.id.dphone);

                radioGroupDisease = myview.findViewById(R.id.radioGroupStatus);

                txtDisease = myview.findViewById(R.id.checkbox_Addict);
                txtDisease1= myview.findViewById(R.id.checkbox_Depressed);


                final EditText email=myview.findViewById(R.id.uemail);

                Button submit=myview.findViewById(R.id.usubmit);

                address.setText(model.getAddress());
                descri.setText(model.getDescription());
                name.setText(model.getFullName());
                phoneno.setText(model.getPhoneNumber());
                email.setText(model.getUsername());

                radioGroupDisease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        boolean Status=true;

                        int radioButtonID = radioGroupDisease.getCheckedRadioButtonId();
                        View radioButton = radioGroupDisease.findViewById(radioButtonID);
                        int idx = radioGroupDisease.indexOfChild(radioButton);
                        RadioButton r = (RadioButton)radioGroupDisease.getChildAt(idx);
                        String selectedtext = r.getText().toString();
                        if(selectedtext.equals("New")){
                            Status = true;
                        }
                        else if (selectedtext.equals("In progress")){
                            Status = false;
                        }

                        else if (selectedtext.equals("Completed")){
                            Status = false;
                        }
                    }
                });


                txtDisease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            txtDisease1.setChecked(false);
                        }
                    }
                });

                txtDisease1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            txtDisease.setChecked(false);
                        }
                    }
                });

                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("address",address.getText().toString());
                        map.put("description",descri.getText().toString());
                        map.put("fullName",name.getText().toString());
                        map.put("phoneNumber",phoneno.getText().toString());
                        map.put("email",email.getText().toString());


                        final String[] role = {""};

                        boolean Status=true;


                        radioGroupDisease.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                boolean Status=true;

                                int radioButtonID = radioGroupDisease.getCheckedRadioButtonId();
                                View radioButton = radioGroupDisease.findViewById(radioButtonID);
                                int idx = radioGroupDisease.indexOfChild(radioButton);
                                RadioButton r = (RadioButton)radioGroupDisease.getChildAt(idx);
                                String selectedtext = r.getText().toString();
                                if(selectedtext.equals("New")){
                                    Status = true;
                                }
                                else if (selectedtext.equals("In progress")){
                                    Status = false;
                                }

                                else if (selectedtext.equals("Completed")){
                                    Status = false;
                                }
                            }
                        });


                        if (txtDisease.isChecked()){
                            role[0] ="Addict";
                        }
                        else if(txtDisease1.isChecked()){
                            role[0] ="Depression";
                        }

                        map.put("status",Status);
                        map.put("disease",role[0]);

                        FirebaseDatabase.getInstance().getReference().child("patients")
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
                        FirebaseDatabase.getInstance().getReference().child("patients")
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleusers,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete;
        TextView name,phone;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext);
            phone=(TextView)itemView.findViewById(R.id.upn);
            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }

}
