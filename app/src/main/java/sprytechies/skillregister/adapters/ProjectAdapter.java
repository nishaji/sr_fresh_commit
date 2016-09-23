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
import android.widget.ArrayAdapter;
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
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Project;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 29/7/16.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> {

    private ArrayList<Project> projlist;
    Context context;
    DatabaseHelper databaseHelper;
    String edtid,dlid;
    public ProjectAdapter(ArrayList<Project> mDataSet) {
        this.projlist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView projectname, role, responsibility,id;
        CardView cardView;
        ImageButton edit,delete;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.projcard);
            projectname = (TextView) view.findViewById(R.id.projectname);
            role = (TextView) view.findViewById(R.id.rolename);
            id = (TextView) view.findViewById(R.id.projid);
            responsibility = (TextView) view.findViewById(R.id.responsibilityname);
            edit=(ImageButton) view.findViewById(R.id.editproj);
            delete=(ImageButton)view.findViewById(R.id.deleteproj);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.proj_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        databaseHelper=new DatabaseHelper(context);
        holder.projectname.setText(projlist.get(position).getProjectname());
        holder.role.setText(projlist.get(position).getRole());
        holder.responsibility.setText(projlist.get(position).getResponsibility());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtid=projlist.get(position).getId();
                editproj();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlid=projlist.get(position).getId();
                deleteproj();


            }
        });

    }

    @Override
    public int getItemCount() {
        return projlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void editproj(){

        ArrayList<String> projects = databaseHelper.getallproject(edtid);
        System.out.println("projects" + projects);
        edtid = projects.get(0);
        String projname = projects.get(1);
        String projrole = projects.get(2);
        String respon = projects.get(3);
        String achieve = projects.get(4);
        String du = projects.get(5);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.proj_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText dilodprojname = (EditText) mView.findViewById(R.id.projectname);
        final MaterialBetterSpinner dilodrolename = (MaterialBetterSpinner) mView.findViewById(R.id.role);
        final EditText dilodresname = (EditText) mView.findViewById(R.id.responsibility);
        final EditText dilodacname = (EditText) mView.findViewById(R.id.achievement);
        final TextView duration=(TextView)mView.findViewById(R.id.diprojectdurationtext);
        dilodprojname.setText(projname);
        ArrayAdapter<String> projadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.project_role_array));
        projadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dilodrolename.setAdapter(projadapter);
        if (!projrole.equals(null)) {
            int spinnerPosition = projadapter.getPosition(projrole);
            try {
                if (projrole.trim().length() > 0) {
                    dilodrolename.setText(projadapter.getItem(spinnerPosition).toString());
                    //positionn.setSelection(spinnerPosition);
                } else {
                    System.out.println("do nothing");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        dilodresname.setText(respon);
        dilodacname.setText(achieve);
        duration.setText(du);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            if (databaseHelper.updateeproject(edtid,dilodprojname.getText().toString(), dilodrolename.getText().toString(), dilodresname.getText().toString(), dilodacname.getText().toString(), dilodacname.getText().toString())) {
                                TastyToast.makeText(context, "Project updated successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(context, Projects.class);
                                context.startActivity(intent);
                                String ab=duration.getText().toString();
                                String[] parts = ab.split("To");
                                String from = parts[0]; // 004
                                String  to = parts[1];
                                Date datefrom=new Date(from);
                                Date dateto=new Date(to);
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("project",dilodprojname.getText().toString());
                                jsonObject.put("from",datefrom);
                                jsonObject.put("upto",dateto);
                                jsonObject.put("role",dilodrolename.getText().toString());
                                jsonObject.put("meta",dilodresname.getText().toString());
                                databaseHelper.update_personbit(edtid,jsonObject,"pending","proj_bit");
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
    public void deleteproj(){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete project data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Project data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databaseHelper.deletproj(dlid);
                                        context.startActivity(new Intent(context,Projects.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }
}

