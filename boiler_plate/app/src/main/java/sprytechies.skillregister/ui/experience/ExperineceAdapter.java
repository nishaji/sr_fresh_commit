package sprytechies.skillregister.ui.experience;

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
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;



/**
 * Created by sprydev5 on 4/10/16.
 */
public class ExperineceAdapter extends RecyclerView.Adapter<ExperineceAdapter.ExperienceViewHolder> {

    private List<ExperienceInsert> experience;@Inject
    DatabaseHelper databaseHelper;
    String edit_id;private Subscription mSubscription;
    Context context;
    @Inject
    public ExperineceAdapter(){
        experience = new ArrayList<>();
    }
    public void setExperience(List<ExperienceInsert> experiences) {
        experience = experiences;
    }
    @Override
    public ExperineceAdapter.ExperienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_exp_row, parent, false);
        return new ExperienceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExperineceAdapter.ExperienceViewHolder holder, final int position) {
        ExperienceInsert experienceInsert=experience.get(position);
        holder.company_name.setText(experienceInsert.experience().company());
        holder.company_title.setText(experienceInsert.experience().title());
        holder.company_location.setText(experienceInsert.experience().type());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=experience.get(position).experience().id();
                databaseHelper.delete_xperience(deleteid);
                context.startActivity( new Intent(context, ExperienceActivity.class));

            }});
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=experience.get(position).experience().id();edit_experience();

            }
        });

    }
    private void edit_experience() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getExperienceForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ExperienceInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the education.");
                    }
                    @Override
                    public void onNext(List<ExperienceInsert> experience) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.expdialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText company_name=(EditText)mView.findViewById(R.id.di_company_name);
                        final MaterialBetterSpinner company_title=(MaterialBetterSpinner)mView.findViewById(R.id.di_company_title);
                        final MaterialBetterSpinner job_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_job_type);
                        final MaterialBetterSpinner location_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_exp_location_type);
                        final EditText status=(EditText)mView.findViewById(R.id.di_status);
                        final EditText job_title=(EditText)mView.findViewById(R.id.di_job_title);
                        final EditText location=(EditText)mView.findViewById(R.id.di_exp_location);
                        final TextView duration=(TextView)mView.findViewById(R.id.di_exp_duration_text);
                        final ImageView duration_clock=(ImageView)mView.findViewById(R.id.di_exp_duration);
                        company_name.setText(experience.get(0).experience().company());
                        status.setText(experience.get(0).experience().status());
                        job_title.setText(experience.get(0).experience().job());
                        location.setText(experience.get(0).experience().location().getName());
                        String du=experience.get(0).experience().from().concat(experience.get(0).experience().upto());
                        duration.setText(du);
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
                        ArrayAdapter<String> company_title_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.industry_array));
                        company_title_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        company_title.setAdapter(company_title_adapter);
                        if (!experience.get(0).experience().title().equals(null)) {
                            int spinnerPosition = company_title_adapter.getPosition(experience.get(0).experience().title());
                            try {
                                if (experience.get(0).experience().title().trim().length() > 0) {
                                    company_title.setText(company_title_adapter.getItem(spinnerPosition).toString());
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
                        if (!experience.get(0).experience().location().getType().equals(null)) {
                            int spinnerPosition = location_type_adapter.getPosition(experience.get(0).experience().location().getType());
                            try {
                                if (experience.get(0).experience().location().getType().trim().length() > 0) {
                                    location_type.setText(location_type_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> job_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.job_type_array));
                        job_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        job_type.setAdapter(job_type_adapter);
                        if (!experience.get(0).experience().type().equals(null)) {
                            int spinnerPosition = job_type_adapter.getPosition(experience.get(0).experience().type());
                            try {
                                if (experience.get(0).experience().type().trim().length() > 0) {
                                    job_type.setText(job_type_adapter.getItem(spinnerPosition).toString());
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
                                        String[] parts = duration.getText().toString().split("To");
                                        String from = parts[0];String to = parts[1];
                                        databaseHelper.edit_experience(Experience.builder()
                                                .setCompany(company_name.getText().toString()).setUpto(from).setFrom(to)
                                                .setStatus(status.getText().toString()).setJob(job_title.getText().toString())
                                                .setType(job_type.getText().toString()).setTitle(company_title.getText().toString())
                                                .setLocation(new Location(location.getText().toString(),location_type.getText().toString()))
                                                .setId(job_title.getText().toString()).setPutflag("0").build(),edit_id);
                                                 context.startActivity( new Intent(context, ExperienceActivity.class));

                                    }}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();alertDialogAndroid.show();

                    }});


    }

    @Override
    public int getItemCount() {
        return experience.size();
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_company_name) TextView company_name;
        @BindView(R.id.view_company_title) TextView company_title;
        @BindView(R.id.view_exp_location) TextView company_location;
        @BindView(R.id.delete_exp)ImageView delete;
        @BindView(R.id.edit_exp)ImageView edit;
        public ExperienceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}


