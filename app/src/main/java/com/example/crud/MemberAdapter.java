package com.example.crud;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.model.Members;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
         List<Members> listOfMembers;
         Context context;

        public MemberAdapter(List<Members> listData, Context context) {
            this.listOfMembers = listData;
            this.context=context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.members_list_data,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Members m=listOfMembers.get(position);
            holder.txtfname.setText(m.getFirst_name());
            holder.txtlname.setText(m.getLast_name());
            holder.txtgender.setText(m.getGender());
            holder.txtphone.setText(m.getPhone());

            //delete
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("members")
                            .child(m.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show();
                                    }
                                    }
                            });
                }
            });

            //edit
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   try
                   {
                       DialogPlus dialogPlus=DialogPlus.newDialog(context)
                               .setGravity(Gravity.CENTER)
                               .setMargin(50,0,50,0)
                               .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.edit_layout))
                               .setExpanded(true)
                               .create();
                       View holderview=dialogPlus.getHolderView();
                       EditText fname,lname;

                       fname=holderview.findViewById(R.id.firstname);
                       lname=holderview.findViewById(R.id.lastname);

                       Button update=holderview.findViewById(R.id.update);
                       update.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Map<String,Object>map=new HashMap<>();
                               map.put("first_name",fname.getText().toString());
                               map.put("last_name",lname.getText().toString());
                               FirebaseDatabase.getInstance().getReference()
                                       .child("members")
                                       .child("phone")
                                       .updateChildren(map)
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful())
                                               {
                                                   fname.setText("");
                                                   lname.setText("");
                                                   dialogPlus.dismiss();
                                                   Toast.makeText(context,"Update successful",Toast.LENGTH_SHORT).show();
                                               }
                                               else
                                               {
                                                   Toast.makeText(context,"Failed to update",Toast.LENGTH_SHORT).show();
                                               }


                                           }
                                       });

                           }
                       });
                   }catch (Exception e)
                   {
                       e.printStackTrace();
                   }




                }
            });




//            holder.txtOptions.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //create pop up menu for edit and delete
//                    PopupMenu popupMenu=new PopupMenu(context,holder.txtOptions);
//                    popupMenu.inflate(R.menu.edit_delete);
//                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @SuppressLint("NonConstantResourceId")
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId())
//                            {
//                                case R.id.edit:
//                                    //edit here
//                                    Toast.makeText(context.getApplicationContext(), "Edit option clicked",Toast.LENGTH_SHORT).show();
//                                    break;
//                                case R.id.delete:
//                                    //delete here
//                                    FirebaseDatabase.getInstance().getReference()
//                                            .child("members")
//                                            //.child(FirebaseDatabase.getInstance().getReference().child("members").push().getKey())
//                                            .child(m.getPhone())
//                                            .removeValue()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    Toast.makeText(context.getApplicationContext(), "Member deleted",Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//
//                                    break;
//                            }
//                            return false;
//                        }
//                    });
//                    popupMenu.show();
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return listOfMembers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
             TextView txtfname,txtlname,txtgender,txtphone;
             ImageView edit,delete;
            public ViewHolder(View itemView) {
                super(itemView);
                txtfname=(TextView)itemView.findViewById(R.id.fnametxt);
                txtlname=(TextView)itemView.findViewById(R.id.lnametxt);
                txtgender=(TextView)itemView.findViewById(R.id.gendertxt);
                txtphone=(TextView)itemView.findViewById(R.id.phonetxt);

                //edit,delete
                edit=(ImageView) itemView.findViewById(R.id.edit);
                delete=(ImageView)itemView.findViewById(R.id.delete);
            }
        }
}
