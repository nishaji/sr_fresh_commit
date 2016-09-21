package com.example.sprydev5.skillsregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sprydev5.skillsregister.data.ApiData;
import com.example.sprydev5.skillsregister.model.Contact;
import com.sdsmdg.tastytoast.TastyToast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sprydev5 on 26/8/16.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private ArrayList<Contact> contactlist;
    Context context;
    DatabaseHelper databse;
    String editcontact,deletecontact;
    String stcontact,stcategory,ststatus,sttype,stid;

    public ContactAdapter(ArrayList<Contact> mDataSet) {
        this.contactlist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contact, category, type;
        CardView cardView;
        ImageButton edit,delete;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.projcard);
            contact = (TextView) view.findViewById(R.id.edtcontact);
            category = (TextView) view.findViewById(R.id.edtcategory);
            type = (TextView) view.findViewById(R.id.edtype);
            edit=(ImageButton) view.findViewById(R.id.cnedit);
            delete=(ImageButton)view.findViewById(R.id.cndelete);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        databse=new DatabaseHelper(context);
        holder.contact.setText(contactlist.get(position).getContact());
        holder.category.setText(contactlist.get(position).getCategory());
        holder.type.setText(contactlist.get(position).getType());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent=new Intent(context,Contacts.class);
                intent.putExtra("contactid",contactlist.get(position).getId());
                context.startActivity(intent);*/
                editcontact=contactlist.get(position).getId();
                edtcontact();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletecontact=contactlist.get(position).getId();
              /*  try {
                    Intent intent = new Intent(context, ViewandAddContact.class);
                    intent.putExtra("deleteedu",contactlist.get(position).getId());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                delcontact();

            }
        });

    }

    private void delcontact() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete Contact data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Contact data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databse.deletedu(deletecontact);
                                        context.startActivity(new Intent(context,ViewandAddContact.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void edtcontact() {
        ArrayList<String>con=databse.editcontact(editcontact);
        try{
            stid=con.get(0);
            stcontact=con.get(1);
            stcategory=con.get(2);
            ststatus=con.get(3);
            sttype=con.get(4);
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
            View mView = layoutInflaterAndroid.inflate(R.layout.contact_dialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
            alertDialogBuilderUserInput.setView(mView);
            final EditText contact = (EditText) mView.findViewById(R.id.contact);
            final EditText contactstatus = (EditText) mView.findViewById(R.id.contactstatus);
            final MaterialBetterSpinner contacttype = (MaterialBetterSpinner) mView.findViewById(R.id.contacttype);
            final MaterialBetterSpinner contactcategory = (MaterialBetterSpinner) mView.findViewById(R.id.category);
            contact.setText(stcontact);
            contactstatus.setText(ststatus);
            ArrayAdapter<String> contactada = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.contact_array));
            contactada.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            contacttype.setAdapter(contactada);
            if (!sttype.equals(null)) {
                int spinnerPosition = contactada.getPosition(sttype);
                try {
                    if (sttype.trim().length() > 0) {
                        contacttype.setText(contactada.getItem(spinnerPosition).toString());
                        //positionn.setSelection(spinnerPosition);
                    } else {
                        System.out.println("do nothing");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            ArrayAdapter<String> catada = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.contact_category_array));
            catada.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            contactcategory.setAdapter(catada);
            if (!stcategory.equals(null)) {
                int spinnerPosition = catada.getPosition(stcategory);
                try {
                    if (stcategory.trim().length() > 0) {
                        contactcategory.setText(catada.getItem(spinnerPosition).toString());
                        //positionn.setSelection(spinnerPosition);
                    } else {
                        System.out.println("do nothing");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            try{
                                if(databse.update_contact(stid,contact.getText().toString(),contactcategory.getText().toString(),contactstatus.getText().toString(),contacttype.getText().toString())) {
                                    TastyToast.makeText(context, "Contact Edited successfully !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                    Intent intent1 = new Intent(context, ViewandAddContact.class);
                                    context.startActivity(intent1);
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put("contact",contact.getText().toString());
                                    jsonObject.put("category",contactcategory.getText().toString());
                                    jsonObject.put("status",contactstatus.getText().toString());
                                    jsonObject.put("type",contacttype.getText().toString());
                                    databse.update_personbit(stid,jsonObject,"pending","contact_bit");

                                }
                                else {
                                    TastyToast.makeText(context, "Could not save Project!", TastyToast.LENGTH_LONG,
                                            TastyToast.ERROR);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }                    }
                    })

                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogBox, int id) {
                                    dialogBox.cancel();
                                }
                            });

            AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
            alertDialogAndroid.show();




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}



