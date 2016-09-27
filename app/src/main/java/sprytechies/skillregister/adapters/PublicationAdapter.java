package sprytechies.skillregister.adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.ArrayList;


import cn.pedant.SweetAlert.SweetAlertDialog;
import sprytechies.skillregister.activities.Projects;
import sprytechies.skillregister.activities.ViewAndAddAwards;
import sprytechies.skillregister.activities.ViewAndAddPublication;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Publication;
import sprytechies.skillsregister.R;

/**
 * Created by sprydev5 on 16/9/16.
 */
public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.MyViewHolder> {

    private ArrayList<Publication> publist;
    Context context;
    String edtid,deleteid;
    DatabaseHelper databaseHelper;

    public PublicationAdapter(ArrayList<Publication> mDataSet) {
        this.publist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,org,desc;
        ImageButton edit,delete;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            title=(TextView)itemView.findViewById(R.id.publicationtitle);
            org=(TextView)itemView.findViewById(R.id.publicationorg);
            desc=(TextView)itemView.findViewById(R.id.publicationdesc);
            edit=(ImageButton)itemView.findViewById(R.id.editpublication);
            delete=(ImageButton)itemView.findViewById(R.id.deletepublication);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.publication_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        databaseHelper=new DatabaseHelper(context);
        holder.title.setText(publist.get(position).getTitle());
        holder.org.setText(publist.get(position).getOrganisation());
        holder.desc.setText(publist.get(position).getDescription());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtid=publist.get(position).getId();
                editproj();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteid=publist.get(position).getId();
                deleteproj();


            }
        });

    }

    private void deleteproj() {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete publication data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Publication data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databaseHelper.deletepub(deleteid);
                                        context.startActivity(new Intent(context,ViewAndAddPublication.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }

    private void editproj() {
        ArrayList<String> publication = databaseHelper.getallpublication(edtid);
        System.out.println("publications" + publication);
        edtid = publication.get(0);
        String title = publication.get(1);
        String organisation = publication.get(2);
        String date = publication.get(3);
        String desc = publication.get(4);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.publication_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText edttitle = (EditText) mView.findViewById(R.id.publicationtitle);
        final EditText edtorg = (EditText) mView.findViewById(R.id.publicationorganisation);
        final EditText edtdesc = (EditText) mView.findViewById(R.id.publicationdescription);
        final TextView duration=(TextView)mView.findViewById(R.id.publicationtext);
        edttitle.setText(title);
        edtorg.setText(organisation);
        edtdesc.setText(desc);
        duration.setText(date);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        try {
                            if (databaseHelper.updatepub(edtid,edttitle.getText().toString(), edtorg.getText().toString(), duration.getText().toString(), edtdesc.getText().toString())) {
                                TastyToast.makeText(context, "Publication updated successfully !", TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                Intent intent = new Intent(context, ViewAndAddPublication.class);
                                context.startActivity(intent);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("title",edttitle.getText().toString());
                                jsonObject.put("organization",edtorg.getText().toString());
                                jsonObject.put("date",duration.getText().toString());
                                jsonObject.put("desc",edtdesc.getText().toString());
                                databaseHelper.update_personbit(edtid,jsonObject,"pending","publication_bit");
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
        return publist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

            }



