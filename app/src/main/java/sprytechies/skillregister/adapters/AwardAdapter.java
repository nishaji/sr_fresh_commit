package sprytechies.skillregister.adapters;

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
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sprytechies.skillregister.activities.Projects;
import sprytechies.skillregister.activities.ViewAndAddAwards;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Award;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 16/9/16.
 */
public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.MyViewHolder> {

private ArrayList<Award> awardlist;
        Context context;
String edtid,deleteid;
    DatabaseHelper database;
public AwardAdapter(ArrayList<Award> mDataSet) {
        this.awardlist = mDataSet;
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, org, desc;
    CardView cardView;
    ImageButton edit,delete;
    public MyViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        cardView = (CardView) itemView.findViewById(R.id.awardcard);
        title = (TextView) view.findViewById(R.id.awardtitle);
        org = (TextView) view.findViewById(R.id.awardorg);
        desc = (TextView) view.findViewById(R.id.awarddescription);
        edit=(ImageButton)view.findViewById(R.id.editaward);
        delete=(ImageButton)view.findViewById(R.id.deleteaward);

    }
}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.award_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        database=new DatabaseHelper(context);
        holder.title.setText(awardlist.get(position).getTitle());
        holder.org.setText(awardlist.get(position).getOrganisation());
        holder.desc.setText(awardlist.get(position).getDescription());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtid=awardlist.get(position).getId();
                editproj();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteid=awardlist.get(position).getId();
                deleteproj();


            }
        });
    }

    private void deleteproj() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete award data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Award data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        database.deleteaward(deleteid);
                                        context.startActivity(new Intent(context,ViewAndAddAwards.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void editproj() {
        ArrayList<String> awards = database.getallaward(edtid);
        System.out.println("awards" + awards);
        edtid = awards.get(0);
        String title = awards.get(1);
        String organisation = awards.get(2);
        String date = awards.get(3);
        String desc = awards.get(4);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.award_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText edttitle = (EditText) mView.findViewById(R.id.awardtitle);
        final EditText edtorg = (EditText) mView.findViewById(R.id.awardorganisation);
        final EditText edtdesc = (EditText) mView.findViewById(R.id.awardescription);
        final TextView duration=(TextView)mView.findViewById(R.id.awarddurationtext);
        edttitle.setText(title);
        edtorg.setText(organisation);
        edtdesc.setText(desc);
        duration.setText(date);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            if (database.updateeaward(edtid,edttitle.getText().toString(), edtorg.getText().toString(), duration.getText().toString(), edtdesc.getText().toString())) {
                                TastyToast.makeText(context, "Project updated successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("title",edttitle.getText().toString());
                                jsonObject.put("organization",edtorg.getText().toString());
                                jsonObject.put("date",duration.getText().toString());
                                jsonObject.put("desc",edtdesc.getText().toString());
                                database.update_personbit(edtid,jsonObject,"pending","award_bit");
                                Intent intent = new Intent(context, ViewAndAddAwards.class);
                                context.startActivity(intent);
                            } else {
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
    }

    @Override
    public int getItemCount() {
        return awardlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}




