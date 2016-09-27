package sprytechies.skillregister.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sprytechies.skillregister.activities.ViewAndAddAwards;
import sprytechies.skillregister.activities.ViewAndAddPublication;
import sprytechies.skillregister.activities.ViewAndAddVolunteers;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Volunteer;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 15/9/16.
 */
public class VolunTeerAdapter extends RecyclerView.Adapter<VolunTeerAdapter.MyViewHolder> {

    private ArrayList<Volunteer> volunlist;
    Context context;
    String edtid,deleteid;
    DatabaseHelper databaseHelper;

    public VolunTeerAdapter(ArrayList<Volunteer> mDataSet) {
        this.volunlist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView role,org,desc;
        ImageButton edit,delete;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            role=(TextView)itemView.findViewById(R.id.volunrole);
            org=(TextView)itemView.findViewById(R.id.volunorg);
            desc=(TextView)itemView.findViewById(R.id.volundesc);
            edit=(ImageButton)itemView.findViewById(R.id.editvolunteer);
            delete=(ImageButton)itemView.findViewById(R.id.deletevolunteer);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.volunteer_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        databaseHelper=new DatabaseHelper(context);
        holder.role.setText(volunlist.get(position).getRole());
        holder.org.setText(volunlist.get(position).getOrganisation());
        holder.desc.setText(volunlist.get(position).getDescription());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtid=volunlist.get(position).getId();
                editproj();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteid=volunlist.get(position).getId();
                deleteproj();


            }
        });

    }

    private void deleteproj() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete Volunteer data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Volunteer data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databaseHelper.deletevolun(deleteid);
                                        context.startActivity(new Intent(context,ViewAndAddVolunteers.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void editproj() {
        ArrayList<String> volunteer = databaseHelper.getallvolunteer(edtid);
        System.out.println("publications" + volunteer);
        edtid = volunteer.get(0);
        String role = volunteer.get(1);
        String roletype = volunteer.get(2);
        String org = volunteer.get(3);
        final String from = volunteer.get(4);
        final String to = volunteer.get(5);
        String finaldate=from.concat("To").concat(to);
        String desc = volunteer.get(6);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.volunteer_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText edtrole = (EditText) mView.findViewById(R.id.volunrole);
        final MaterialBetterSpinner edtroletype=(MaterialBetterSpinner)mView.findViewById(R.id.roletype);
        final EditText edtorg = (EditText) mView.findViewById(R.id.org);
        final EditText edtdesc = (EditText) mView.findViewById(R.id.description);
        final TextView duration=(TextView)mView.findViewById(R.id.volunteertext);
        edtrole.setText(role);
        ArrayAdapter<String> volun = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.project_role_array));
        volun.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        edtroletype.setAdapter(volun);
        if (!roletype.equals(null)) {
            int spinnerPosition = volun.getPosition(roletype);
            try {
                if (roletype.trim().length() > 0) {
                    edtroletype.setText(volun.getItem(spinnerPosition).toString());
                    //positionn.setSelection(spinnerPosition);
                } else {
                    System.out.println("do nothing");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        edtdesc.setText(desc);
        edtorg.setText(org);
        duration.setText(finaldate);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            if (databaseHelper.updatevolunteer(edtid,edtrole.getText().toString(), edtroletype.getText().toString(),edtorg.getText().toString(), from,to, edtdesc.getText().toString())) {
                                TastyToast.makeText(context, "Volunteer updated successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("role",edtrole.getText().toString());
                                jsonObject.put("type",edtroletype.getText().toString());
                                jsonObject.put("organization",edtorg.getText().toString());
                                jsonObject.put("from",from);
                                jsonObject.put("upto",to);
                                jsonObject.put("desc",edtdesc.getText().toString());
                                databaseHelper.update_personbit(edtid,jsonObject,"pending","volunteer_bit");
                                Intent intent = new Intent(context, ViewAndAddVolunteers.class);
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
        return volunlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}



