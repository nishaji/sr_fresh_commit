package sprytechies.skillregister.ui.project;

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
import sprytechies.skillregister.data.model.Project;
import sprytechies.skillregister.data.model.ProjectInsert;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    List<ProjectInsert> projects;
    @Inject
    DatabaseHelper databaseHelper;
    Context context;
    String edit_id;
    private Subscription mSubscription;

    @Inject
    ProjectAdapter() {
        projects = new ArrayList<>();
    }

    public void setProjects(List<ProjectInsert> project) {
        projects = project;
    }

    @Override
    public ProjectAdapter.ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.proj_row, parent, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ProjectViewHolder holder, final int position) {
        ProjectInsert projectInsert = projects.get(position);
        holder.project_name.setText(projectInsert.project().project());
        holder.role_name.setText(projectInsert.project().role());
        holder.res_name.setText(projectInsert.project().meta());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid = projects.get(position).project().id();
                databaseHelper.delete_project(deleteid);
                context.startActivity(new Intent(context, ProjectActivity.class));
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id = projects.get(position).project().id();edit_project();
            }
        });

    }

    private void edit_project() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getProjectForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ProjectInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the project.");
                    }
                    @Override
                    public void onNext(List<ProjectInsert> projects) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.proj_dialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText project_name = (EditText) mView.findViewById(R.id.di_project_name);
                        final EditText responsibility = (EditText) mView.findViewById(R.id.di_responsibility);
                        final TextView duration = (TextView) mView.findViewById(R.id.di_project_duration_text);
                        final ImageView duration_clock = (ImageView) mView.findViewById(R.id.di_project_duration);
                        final MaterialBetterSpinner role = (MaterialBetterSpinner) mView.findViewById(R.id.di_role);
                        project_name.setText(projects.get(0).project().project());
                        responsibility.setText(projects.get(0).project().meta());
                        String du = projects.get(0).project().from().concat(projects.get(0).project().upto());
                        duration.setText(du);
                        ArrayAdapter<String> role_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.project_role_array));
                        role_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        role.setAdapter(role_adapter);
                        if (!projects.get(0).project().role().equals(null)) {
                            int spinnerPosition = role_adapter.getPosition(projects.get(0).project().role());
                            try {
                                if (projects.get(0).project().role().trim().length() > 0) {
                                    role.setText(role_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
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
                                        final String from = parts[0];
                                        final String to = parts[1];
                                        databaseHelper.edit_project(Project.builder()
                                                .setProject(project_name.getText().toString()).setMeta(responsibility.getText().toString())
                                                .setRole(role.getText().toString()).setFrom(from).setUpto(to).setPutflag("0").build(), edit_id);
                                        context.startActivity(new Intent(context, ProjectActivity.class));
                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();alertDialogAndroid.show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.project_name) TextView project_name;
        @BindView(R.id.role_name) TextView role_name;
        @BindView(R.id.responsibility_name) TextView res_name;
        @BindView(R.id.delete_project) ImageView delete;
        @BindView(R.id.edit_project) ImageView edit;

        public ProjectViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();

        }
    }
}




