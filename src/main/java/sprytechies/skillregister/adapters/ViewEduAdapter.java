package sprytechies.skillregister.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.sdsmdg.tastytoast.TastyToast;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sprytechies.skillregister.activities.ViewandAddeducation;
import sprytechies.skillregister.database.DatabaseHelper;
import sprytechies.skillregister.model.Edu;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 6/8/16.
 */
public class ViewEduAdapter  extends RecyclerView.Adapter<ViewEduAdapter.MyViewHolder> {
    private  Context context;
    private ArrayList<Edu> edulist;
    String editid,deleteid;
    String TAG="ViewEducAdapter";
    DatabaseHelper databaseHelper;
    String school,edtschooltype,edtcoursetype,edutitle,edutype,edtcgpi,edtcgpitype,edtlocationname,edtlocationtype,yearfrom,yearupto,finalyear;

    public ViewEduAdapter(ArrayList<Edu> mDataSet) {
        this.edulist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView school,course,loc,id,mongoid;
        CardView cardView;
        ImageButton edit,delete;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.educard);
            school = (TextView) view.findViewById(R.id.schoolname);
            course = (TextView) view.findViewById(R.id.course);
            loc = (TextView) view.findViewById(R.id.loc);
            id=(TextView)view.findViewById(R.id.id);
            mongoid=(TextView)view.findViewById(R.id.mongoid);
            edit=(ImageButton) view.findViewById(R.id.editedu);
            delete=(ImageButton)view.findViewById(R.id.deleteedu);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_edu_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        databaseHelper=new DatabaseHelper(context);
        holder.school.setText(edulist.get(position).getSchoolname());
        holder.course.setText(edulist.get(position).getCourse());
        holder.loc.setText(edulist.get(position).getLocation());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editid=edulist.get(position).getId();
                editedu();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteid=edulist.get(position).getId();
                deleteedu();

            }
        });

    }

    @Override
    public int getItemCount() {
        return edulist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void deleteedu(){
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to delete education data")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance
                        sDialog.setTitleText("Deleted!")
                                .setContentText("Education data is deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        databaseHelper.deleterow(deleteid);
                                        context.startActivity(new Intent(context,ViewandAddeducation.class));

                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();
    }
   public void editedu(){
       ArrayList<String> edu=databaseHelper.getalledu(editid);
       System.out.println(edu+"educationdta");
       school=edu.get(2);
       edtschooltype=edu.get(3);
       edtcoursetype=edu.get(4);
       edutitle=edu.get(5);
       edutype=edu.get(6);
       edtcgpi=edu.get(7);
       edtcgpitype=edu.get(8);
       edtlocationname=edu.get(9);
       edtlocationtype=edu.get(10);
       yearfrom=edu.get(11);
       yearupto=edu.get(12);
       finalyear=yearfrom.concat("To").concat(yearupto);
       LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
       View mView = layoutInflaterAndroid.inflate(R.layout.edudialog, null);
       AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
       alertDialogBuilderUserInput.setView(mView);
       final EditText schoolname = (EditText) mView.findViewById(R.id.schollname);
       final MaterialBetterSpinner schooltype=(MaterialBetterSpinner)mView.findViewById(R.id.schooltype);
       final MaterialBetterSpinner coursetype = (MaterialBetterSpinner) mView.findViewById(R.id.coursetype);
       final MaterialBetterSpinner educationtitle = (MaterialBetterSpinner) mView.findViewById(R.id.edutitle);
       final MaterialBetterSpinner educationtype = (MaterialBetterSpinner) mView.findViewById(R.id.edutype);
       final EditText cgpi = (EditText) mView.findViewById(R.id.cgpi);
       final MaterialBetterSpinner cgpitype = (MaterialBetterSpinner) mView.findViewById(R.id.cgpitype);
       final EditText locationname = (EditText) mView.findViewById(R.id.locationname);
       final MaterialBetterSpinner locationtype = (MaterialBetterSpinner) mView.findViewById(R.id.locationtype);
       final TextView educationtdurationtext = (TextView) mView.findViewById(R.id.educationtdurationtext);
       final ImageView eduduration=(ImageView)mView.findViewById(R.id.eduduration);
       schoolname.setText(school);
       cgpi.setText(edtcgpi);
       locationname.setText(edtlocationname);
       educationtdurationtext.setText(finalyear);
       ArrayAdapter<String> schooladapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.school_type));
       schooladapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       schooltype.setAdapter(schooladapter);
       if (!edtschooltype.equals(null)) {
           int spinnerPosition = schooladapter.getPosition(edtschooltype);
           try {
               if (edtschooltype.trim().length() > 0) {
                   schooltype.setText(schooladapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       ArrayAdapter<String> coursetypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.course_type_array));
       coursetypeadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       coursetype.setAdapter(coursetypeadapter);
       if (!edtcoursetype.equals(null)) {
           int spinnerPosition = coursetypeadapter.getPosition(edtcoursetype);
           try {
               if (edtcoursetype.trim().length() > 0) {
                   coursetype.setText(coursetypeadapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       ArrayAdapter<String> edutitleadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.education_title_array));
       edutitleadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       educationtitle.setAdapter(edutitleadapter);
       if (!edutitle.equals(null)) {
           int spinnerPosition = edutitleadapter.getPosition(edutitle);
           try {
               if (edutitle.trim().length() > 0) {
                   educationtitle.setText(edutitleadapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       ArrayAdapter<String> edutypeeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.education_type_array));
       edutypeeadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       educationtype.setAdapter(edutypeeadapter);
       if (!edutype.equals(null)) {
           int spinnerPosition = edutypeeadapter.getPosition(edutype);
           try {
               if (edutype.trim().length() > 0) {
                   educationtype.setText(edutypeeadapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       ArrayAdapter<String> cgpitypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.cgpitype_array));
       cgpitypeadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       cgpitype.setAdapter(cgpitypeadapter);
       if (!edtcgpitype.equals(null)) {
           int spinnerPosition = cgpitypeadapter.getPosition(edtcgpitype);
           try {
               if (edtcgpitype.trim().length() > 0) {
                   cgpitype.setText(cgpitypeadapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       ArrayAdapter<String> loctypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.location_type));
       loctypeadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
       locationtype.setAdapter(edutitleadapter);
       if (!edtlocationtype.equals(null)) {
           int spinnerPosition = loctypeadapter.getPosition(edtlocationtype);
           try {
               if (edtlocationtype.trim().length() > 0) {
                   locationtype.setText(loctypeadapter.getItem(spinnerPosition).toString());
                   //positionn.setSelection(spinnerPosition);
               } else {
                   System.out.println("do nothing");
               }
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
       final FragmentManager manager = ((Activity) context).getFragmentManager();
       eduduration.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                       SmoothDateRangePickerFragment
                               .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                                   @Override
                                   public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                              int yearStart, int monthStart,
                                                              int dayStart, int yearEnd,
                                                              int monthEnd, int dayEnd) {
                                       String date = dayStart + "/" + (++monthStart)
                                               + "/" + yearStart + " To " + dayEnd + "/"
                                               + (++monthEnd) + "/" + yearEnd;
                                       educationtdurationtext.setText(date);

                                   }
                               });
               smoothDateRangePickerFragment.show(manager, "Datepickerdialog");

           }
       });

       alertDialogBuilderUserInput
               .setCancelable(false)
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialogBox, int id) {
                       String ab=educationtdurationtext.getText().toString();
                       String[] parts = ab.split("To");
                      String from = parts[0]; // 004
                      String  to = parts[1];
                       try{
                           if(databaseHelper.updateeducation(editid,schoolname.getText().toString(),schooltype.getText().toString(),coursetype.getText().toString(),educationtitle.getText().toString(),cgpi.getText().toString(),cgpitype.getText().toString(),locationname.getText().toString(),locationtype.getText().toString(),from,to)){
                               TastyToast.makeText(context, "Education data Updated successfully !", TastyToast.LENGTH_LONG,
                                       TastyToast.SUCCESS);
                               Intent intent = new Intent(context, ViewandAddeducation.class);
                               context.startActivity(intent);
                               String school=schoolname.getText().toString().trim();
                               String schooltypee=schooltype.getText().toString().trim();
                               String type=coursetype.getText().toString().trim();
                               String title=educationtitle.getText().toString().trim();
                               String edutype=educationtype.getText().toString().trim();
                               String cgpii=cgpi.getText().toString();
                               String cgtype=cgpitype.getText().toString();
                               String loc=locationname.getText().toString();
                               String loctype=locationtype.getText().toString();
                               JSONObject jsonObject=new JSONObject();
                               jsonObject.put("name",loc);
                               jsonObject.put("type",loctype);
                               Date date=new Date(from);
                               Date date1=new Date(to);
                               JSONObject hashmap=new JSONObject();
                               hashmap.put("school",school);
                               hashmap.put("from",date.toString());
                               hashmap.put("upto",date1.toString());
                               hashmap.put("course",type);
                               hashmap.put("type",schooltypee);
                               hashmap.put("title",title);
                               hashmap.put("edutype",edutype);
                               hashmap.put("cgpi",cgpii);
                               hashmap.put("cgpitype",cgtype);
                               hashmap.put("location",jsonObject);
                               databaseHelper.update_personbit(editid,hashmap,"pending","edu_bit");

                              // apiData.updateedudata(school,date,date,schooltypee,type,title,edutype,cgpii,cgtype,loc);
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

