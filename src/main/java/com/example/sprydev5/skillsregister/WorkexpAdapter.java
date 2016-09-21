package com.example.sprydev5.skillsregister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sprydev5.skillsregister.data.ApiData;
import com.example.sprydev5.skillsregister.model.Workexperience;
import com.sdsmdg.tastytoast.TastyToast;


import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
/**
 * Created by sprydev5 on 28/7/16.
 */
public class WorkexpAdapter extends RecyclerView.Adapter<WorkexpAdapter.MyViewHolder> {

    private ArrayList<Workexperience> explist;
    private Context context;
    String editid,deleteid,edtid,expfrom,expto;
    DatabaseHelper databaseHelper;
    public WorkexpAdapter(ArrayList<Workexperience> mDataSet) {
        this.explist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView companyname,comapnytitle,location;
        CardView cardView;
        ImageButton edit,delete;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.expcard);
            companyname = (TextView) view.findViewById(R.id.companyname);
            comapnytitle = (TextView) view.findViewById(R.id.companytitle);
            location=(TextView) view.findViewById(R.id.exploc);
            edit=(ImageButton) view.findViewById(R.id.editexp);
            delete=(ImageButton)view.findViewById(R.id.deleteexp);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_exp_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        databaseHelper=new DatabaseHelper(context);
        holder.companyname.setText(explist.get(position).getCompanyname());
        holder.comapnytitle.setText(explist.get(position).getCompanytitle());
        holder.location.setText(explist.get(position).getLocation());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(context,AddWorkExp.class);
                intent.putExtra("exp",explist.get(position).getId());
                context.startActivity(intent);*/
                editid=explist.get(position).getId();
                System.out.println(editid+"idddddddddddddddddddddddddddd");
                editexp();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* try {
                    Intent intent = new Intent(context, WorkExperience.class);
                    intent.putExtra("deleteexp",explist.get(position).getId());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
                  deleteid=explist.get(position).getId();
                  deleteexp();
            }
        });
    }
    @Override
    public int getItemCount() {
        return explist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void editexp()
    {
        ArrayList<String> exp = databaseHelper.getallexp(editid);
        System.out.println(exp+"experienceinadapter");
          edtid = exp.get(0);
        String edtcompnyname = exp.get(1);
        String edtcompnytitle = exp.get(2);
        String edtjobtitle = exp.get(3);
        String edtjobtype = exp.get(4);
        String expstatus=exp.get(5);
        String jbloc=exp.get(6);
         expfrom=exp.get(7);
         expto=exp.get(8);
        String finaldate=expfrom.concat("To").concat(expto);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.expdialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText companyname = (EditText) mView.findViewById(R.id.companyname);
        final MaterialBetterSpinner companytitle = (MaterialBetterSpinner) mView.findViewById(R.id.companytitle);
        final EditText job = (EditText) mView.findViewById(R.id.jobtitle);
        final MaterialBetterSpinner jobtype = (MaterialBetterSpinner) mView.findViewById(R.id.jobtype);
        final EditText status = (EditText) mView.findViewById(R.id.status);
        final EditText location = (EditText) mView.findViewById(R.id.explocation);
        final TextView expduration = (TextView) mView.findViewById(R.id.expdurationtext);
        companyname.setText(edtcompnyname);
        ArrayAdapter<String> comtitleadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.industry_array));
        comtitleadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        companytitle.setAdapter(comtitleadapter);
        if (!edtcompnytitle.equals(null)) {
            int spinnerPosition = comtitleadapter.getPosition(edtcompnytitle);
            try {
                if (edtcompnytitle.trim().length() > 0) {
                    companytitle.setText(comtitleadapter.getItem(spinnerPosition).toString());
                    //positionn.setSelection(spinnerPosition);
                } else {
                    System.out.println("do nothing");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        job.setText(edtjobtitle);
        ArrayAdapter<String> sjobtypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.job_type_array));
        sjobtypeadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jobtype.setAdapter(sjobtypeadapter);
        if (!edtjobtype.equals(null)) {
            int spinnerPosition = sjobtypeadapter.getPosition(edtjobtype);
            try {
                if (edtjobtype.trim().length() > 0) {
                    jobtype.setText(sjobtypeadapter.getItem(spinnerPosition).toString());
                    //positionn.setSelection(spinnerPosition);
                } else {
                    System.out.println("do nothing");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            status.setText(expstatus);
            location.setText(jbloc);
           expduration.setText(finaldate);
            alertDialogBuilderUserInput
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogBox, int id) {
                            try{
                                if(databaseHelper.updateexp(edtid,expfrom,expto,status.getText().toString(),companytitle.getText().toString(),companyname.getText().toString(),job.getText().toString(),jobtype.getText().toString(),location.getText().toString())){
                                    TastyToast.makeText(context, "Education data Updated successfully !", TastyToast.LENGTH_LONG,
                                            TastyToast.SUCCESS);
                                    Intent intent = new Intent(context, WorkExperience.class);
                                    context.startActivity(intent);
                                    String ab=expduration.getText().toString();
                                    String[] parts = ab.split("To");
                                    String from = parts[0]; // 004
                                    String to = parts[1];
                                    Date date=new Date(from);
                                    Date date1=new Date(to);
                                    JSONObject hashMap=new JSONObject();
                                    hashMap.put("from",date.toString());
                                    hashMap.put("upto",date1.toString());
                                    hashMap.put("status",status.getText().toString());
                                    hashMap.put("type",jobtype.getText().toString());
                                    hashMap.put("title",companytitle.getText().toString());
                                    hashMap.put("company",companyname.getText().toString());
                                    hashMap.put("job",job.getText().toString());
                                    hashMap.put("loctation",location.getText().toString());
                                    databaseHelper.update_personbit(edtid,hashMap,"pending","exp_bit");
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


        }
    }


    public void deleteexp(){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete experience data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Experience data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databaseHelper.deleteexp(deleteid);
                                        context.startActivity(new Intent(context,WorkExperience.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

}
