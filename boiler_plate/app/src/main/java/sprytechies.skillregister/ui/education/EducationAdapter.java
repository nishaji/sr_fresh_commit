package sprytechies.skillregister.ui.education;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Education;
import sprytechies.skillregister.data.model.EducationInsert;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

    private List<EducationInsert> education;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String edit_id;Context context;AlertDialog ab;
    @Inject EducationAdapter(){
        education=new ArrayList<>();
    }
    public void setEducation(List<EducationInsert> educations) {
        education = educations;
    }
    @Override
    public EducationAdapter.EducationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_edu_row, parent, false);
        return new EducationViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(EducationAdapter.EducationViewHolder holder, final int position) {
        EducationInsert educations=education.get(position);
        holder.school_name.setText(educations.education().school());
        holder.course_name.setText(educations.education().course());
        holder.location_name.setText(educations.education().location().name);
        holder.id.setText(educations.education().id());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String deleteid=education.get(position).education().id();
                databaseHelper.delete_education(deleteid);
                context.startActivity( new Intent(context, EducationActivity.class));

            }});
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=education.get(position).education().id();edit_education();
            }});

    }
    @Override
    public int getItemCount() {
        return education.size();
    }
    class EducationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_school_name) TextView school_name;
        @BindView(R.id.view_course) TextView course_name;
        @BindView(R.id.view_location) TextView location_name;
        @BindView(R.id.education_id) TextView id;
        @BindView(R.id.edit_edu) ImageView edit;
        @BindView(R.id.delete_edu) ImageView delete;

        public EducationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
    private void edit_education() {
                 RxUtil.unsubscribe(mSubscription);
                 mSubscription = databaseHelper.getEducationForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<EducationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                    }
                    @Override
                    public void onNext(List<EducationInsert> education) {
                        System.out.println(education);
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.edudialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText schoolname=(EditText)mView.findViewById(R.id.di_school_name);
                        final MaterialBetterSpinner school_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_school_type);
                        final MaterialBetterSpinner education_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_edu_type);
                        final EditText cgpi=(EditText)mView.findViewById(R.id.di_cgpi);
                        final MaterialBetterSpinner cgpi_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_cgpi_type);
                        final EditText course=(EditText)mView.findViewById(R.id.di_course_type);
                        final EditText location=(EditText)mView.findViewById(R.id.di_location_name);
                        final MaterialBetterSpinner location_type=(MaterialBetterSpinner) mView.findViewById(R.id.di_location_type);
                        final EditText title=(EditText)mView.findViewById(R.id.di_edu_title);
                        final TextView duration=(TextView)mView.findViewById(R.id.di_edu_duration_text);
                        final ImageView duration_clock=(ImageView)mView.findViewById(R.id.di_edu_duration);
                        String du=education.get(0).education().from().concat("To").concat(education.get(0).education().upto());
                        duration.setText(du);
                        schoolname.setText(education.get(0).education().school());
                        ArrayAdapter<String> school_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.school_type));
                        school_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        school_type.setAdapter(school_type_adapter);
                        if (!education.get(0).education().schooltype().equals(null)) {
                            int spinnerPosition = school_type_adapter.getPosition(education.get(0).education().schooltype());
                            try {
                                if (education.get(0).education().schooltype().trim().length() > 0) {
                                    school_type.setText(school_type_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        ArrayAdapter<String> education_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.education_type_array));
                        education_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        education_type.setAdapter(education_type_adapter);
                        if (!education.get(0).education().edutype().equals(null)) {
                            int spinnerPosition = education_type_adapter.getPosition(education.get(0).education().edutype());
                            try {
                                if (education.get(0).education().edutype().trim().length() > 0) {
                                    education_type.setText(education_type_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        ArrayAdapter<String> location_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.location_type));
                        location_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        location_type.setAdapter(location_type_adapter);
                        if (!education.get(0).education().location().getType().equals(null)) {
                            int spinnerPosition = location_type_adapter.getPosition(education.get(0).education().location().getType());
                            try {
                                if (education.get(0).education().location().getType().trim().length() > 0) {
                                    location_type.setText(location_type_adapter.getItem(spinnerPosition));
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        ArrayAdapter<String> cgpi_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.cgpitype_array));
                        cgpi_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        cgpi_type.setAdapter(cgpi_type_adapter);
                        if (!education.get(0).education().cgpitype().equals(null)) {
                            int spinnerPosition = cgpi_type_adapter.getPosition(education.get(0).education().cgpitype());
                            try {
                                if (education.get(0).education().cgpitype().trim().length() > 0) {
                                    cgpi_type.setText(cgpi_type_adapter.getItem(spinnerPosition));
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        cgpi.setText(education.get(0).education().cgpi());
                        course.setText(education.get(0).education().course());
                        location.setText(education.get(0).education().location().getName());
                        title.setText(education.get(0).education().title());
                        final FragmentManager manager = ((Activity) context).getFragmentManager();
                        duration_clock.setOnClickListener(new View.OnClickListener() {
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
                                                        String date = dayStart + "/" + (++monthStart) + "/" + yearStart + " To " + dayEnd + "/"
                                                                + (++monthEnd) + "/" + yearEnd;duration.setText(date);
                                                    }
                                                });
                                smoothDateRangePickerFragment.show(manager, "Datepickerdialog");

                            }
                        });

                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        String[] parts = duration.getText().toString().split("To");
                                        final String from = parts[0];final String to = parts[1];
                                        databaseHelper.edit_education
                                                (Education.builder()
                                                        .setCgpi(cgpi.getText().toString()).setCgpitype(cgpi_type.getText().toString())
                                                        .setCourse(course.getText().toString()).setEdutype(education_type.getText().toString())
                                                        .setFrom(from).setUpto(to).setPutflag("0")
                                                        .setLocation(new Location(location.getText().toString(),location_type.getText().toString()))
                                                        .setSchool(schoolname.getText().toString())
                                                        .setSchooltype(school_type.getText().toString()).setTitle(title.getText().toString())
                                                        .build(),edit_id);
                                        context.startActivity( new Intent(context, EducationActivity.class));
                                    }}
                                ).setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        ab = alertDialogBuilderUserInput.create();ab.show();
                    }
                });

    }
}

