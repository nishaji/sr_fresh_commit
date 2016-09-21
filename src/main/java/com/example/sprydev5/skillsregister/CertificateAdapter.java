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

import com.example.sprydev5.skillsregister.model.Certificate;
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
public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.MyViewHolder> {

    private ArrayList<Certificate> certlist;
    String certdelete,certedit,updateid;
    Context context;
    DatabaseHelper database;
    public CertificateAdapter(ArrayList<Certificate> mDataSet) {
        this.certlist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView certname, certstatus, certtype;
        CardView cardView;
        ImageButton edit,delete;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.projcard);
            certname = (TextView) view.findViewById(R.id.certname);
            certstatus = (TextView) view.findViewById(R.id.certstatus);
            certtype = (TextView) view.findViewById(R.id.certtype);
            edit=(ImageButton) view.findViewById(R.id.certedit);
            delete=(ImageButton)view.findViewById(R.id.certdelete);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cert_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        database=new DatabaseHelper(context);
        holder.certname.setText(certlist.get(position).getCertname());
        holder.certstatus.setText(certlist.get(position).getCertstatus());
        holder.certtype.setText(certlist.get(position).getCerttype());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(context,Certificates.class);
                intent.putExtra("certtid",certlist.get(position).getId());
                context.startActivity(intent);*/
                certedit=certlist.get(position).getId();
                editecert();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* try {
                    Intent intent = new Intent(context, ViewandEditCertificate.class);
                    intent.putExtra("deletecert",certlist.get(position).getId());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                certdelete=certlist.get(position).getId();
                deletecert();

            }
        });

    }

    private void editecert() {
        try{
            ArrayList<String> cert=database.editcert(certedit);
             updateid=cert.get(0);
           String stcertname=cert.get(1);
            String stcertstatus=cert.get(2);
            String stcerttype=cert.get(3);
            String stcertdate=cert.get(4);
            String stauthority=cert.get(5);
            String strank=cert.get(6);
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
            View mView = layoutInflaterAndroid.inflate(R.layout.certdialog, null);
            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
            alertDialogBuilderUserInput.setView(mView);
            final EditText certname = (EditText) mView.findViewById(R.id.certificatename);
            final MaterialBetterSpinner certtype = (MaterialBetterSpinner) mView.findViewById(R.id.certificatetype);
            final EditText rank = (EditText) mView.findViewById(R.id.rank);
            final EditText auth = (EditText) mView.findViewById(R.id.authority);
            final EditText status = (EditText) mView.findViewById(R.id.certstatus);
            final TextView certdate = (TextView) mView.findViewById(R.id.certdate);
            certname.setText(stcertname);
            rank.setText(strank);
            auth.setText(stauthority);
            status.setText(stcertstatus);
            certdate.setText(stcertdate);
            ArrayAdapter<String> certada = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.certificate_array));
            certada.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            certtype.setAdapter(certada);
            if (!stcerttype.equals(null)) {
                int spinnerPosition = certada.getPosition(stcerttype);
                try {
                    if (stcerttype.trim().length() > 0) {
                        certtype.setText(certada.getItem(spinnerPosition).toString());
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
                                if(database.updatecert(updateid,certname.getText().toString(),status.getText().toString(),certtype.getText().toString(),certdate.getText().toString(),auth.getText().toString(),status.getText().toString())){
                                    TastyToast.makeText(context, "Education data Updated successfully !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                    Intent intent = new Intent(context, ViewandEditCertificate.class);
                                    context.startActivity(intent);
                                    Date date1=new Date(certdate.getText().toString());
                                    JSONObject jsonObject=new JSONObject();
                                    jsonObject.put("name",certname.getText().toString());
                                    jsonObject.put("status",status.getText().toString());
                                    jsonObject.put("type",certtype.getText().toString());
                                    jsonObject.put("certdate",date1);
                                    jsonObject.put("authority",auth.getText().toString());
                                    jsonObject.put("rank",rank.getText().toString());
                                    database.update_personbit(updateid,jsonObject,"pending","cert_bit");
                                }else{
                                    TastyToast.makeText(context, "Could not inserted sucessfully!", TastyToast.LENGTH_LONG,
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

    private void deletecert() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete certification data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Certificate data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        database.deletcert(certdelete);
                                        context.startActivity(new Intent(context,ViewandEditCertificate.class));
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return certlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}




