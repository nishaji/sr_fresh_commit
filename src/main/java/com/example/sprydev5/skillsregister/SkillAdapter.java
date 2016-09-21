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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sprydev5.skillsregister.model.Skill;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sprydev5 on 29/7/16.
 */
public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.MyViewHolder> {

    private ArrayList<Skill> skilllist;
    Context context;
    DatabaseHelper databasehelper;
    String editskill, deleteskill,idd;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public SkillAdapter(ArrayList<Skill> mDataSet) {
        this.skilllist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        CardView cardView;
        ImageButton editskill, deleteskill;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.skill_card);
            title = (TextView) view.findViewById(R.id.skii_title);
            description = (TextView) view.findViewById(R.id.certstatus);
            editskill = (ImageButton) view.findViewById(R.id.skiiedit);
            deleteskill = (ImageButton) view.findViewById(R.id.skiidelete);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        databasehelper = new DatabaseHelper(context);
        holder.title.setText(skilllist.get(position).getTitle());
        holder.description.setText(skilllist.get(position).getDescription());
        holder.editskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editskill = skilllist.get(position).getId();
                System.out.println(editskill + "idddddddddddddddddddddddddddd");
                editexp();

            }
        });
        holder.deleteskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteskill = skilllist.get(position).getId();
                deleteexp();
            }
        });

    }

    private void deleteexp() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete skill data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Skill data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databasehelper.deletskill(deleteskill);
                                        context.startActivity(new Intent(context, Skills.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void editexp() {
        ArrayList<String> skiii = databasehelper.getallskill(editskill);
         idd=skiii.get(0);
        String skii = skiii.get(1);
        String des = skiii.get(2);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.skill_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText skititle = (EditText) mView.findViewById(R.id.ski_title);
        final EditText skidesc = (EditText) mView.findViewById(R.id.ski_description);
        skititle.setText(skii);
        skidesc.setText(des);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            if (databasehelper.update_skill(idd, skititle.getText().toString(), skidesc.getText().toString())) {
                                TastyToast.makeText(context, "Education data Updated successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(context, Skills.class);
                                context.startActivity(intent);
                                JSONObject hashmap = new JSONObject();
                                hashmap.put("type", skititle);
                                hashmap.put("level", skidesc);
                                databasehelper.update_personbit(idd,hashmap,"pending","skill_bit");
                            } else {
                                TastyToast.makeText(context, "Could not inserted sucessfully!", TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
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



    @Override
    public int getItemCount() {
        return skilllist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

