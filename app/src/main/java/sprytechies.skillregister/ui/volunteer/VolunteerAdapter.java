package sprytechies.skillregister.ui.volunteer;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;
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
import sprytechies.skillregister.data.model.Volunteer;
import sprytechies.skillregister.data.model.volunteerInsert;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


/**
 * Created by sprydev5 on 4/10/16.
 */

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder> {
    List<volunteerInsert> volunteer;
    Context context;
    @Inject
    DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String edit_id;

    @Inject
    VolunteerAdapter() {
        volunteer = new ArrayList<>();
    }
    public void setVolunteer(List<volunteerInsert>volunteers){
        volunteer=volunteers;
    }

    @Override
    public VolunteerAdapter.VolunteerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_row, parent, false);
        return new VolunteerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VolunteerAdapter.VolunteerViewHolder holder,final int position) {
        volunteerInsert volunteerInsert=volunteer.get(position);
        holder.role.setText(volunteerInsert.volunteer().role());
        holder.desc.setText(volunteerInsert.volunteer().description());
        holder.org.setText(volunteerInsert.volunteer().organisation());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=volunteer.get(position).volunteer().id();
                databaseHelper.delete_volunteer(deleteid);
                context.startActivity( new Intent(context, VolunteerActivity.class));

            }});
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=volunteer.get(position).volunteer().id();
                edit_volunteer();
            }});

    }

    private void edit_volunteer() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getVolunteerForUpdate(edit_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<volunteerInsert>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the volunteer.");

                    }

                    @Override
                    public void onNext(List<volunteerInsert> volunteer) {
                        System.out.println(volunteer);
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.volunteer_dialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText role=(EditText)mView.findViewById(R.id.di_volunteer_role);
                        final EditText org=(EditText)mView.findViewById(R.id.di_volunteer_org);
                        final EditText desc=(EditText)mView.findViewById(R.id.di_volunteer_desc);
                        final EditText type=(EditText)mView.findViewById(R.id.di_volunteer_role_type);
                        final ImageView duration=(ImageView)mView.findViewById(R.id.di_volunteer_duration);
                        final TextView duration_text=(TextView)mView.findViewById(R.id.di_volunteer_text);

                        role.setText(volunteer.get(0).volunteer().role());
                        org.setText(volunteer.get(0).volunteer().organisation());
                        desc.setText(volunteer.get(0).volunteer().description());
                        type.setText(volunteer.get(0).volunteer().type());
                        duration_text.setText(volunteer.get(0).volunteer().from().concat("TO").concat(volunteer.get(0).volunteer().upto()));

                        final FragmentManager manager = ((Activity) context).getFragmentManager();
                        duration.setOnClickListener(new View.OnClickListener() {
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
                                                        duration_text.setText(date);

                                                    }
                                                });
                                smoothDateRangePickerFragment.show(manager, "Datepickerdialog");

                            }
                        });
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        String[] parts = duration_text.getText().toString().split("To");
                                        final String from = parts[0];
                                        final String to = parts[1];
                                        databaseHelper.edit_volunteer(Volunteer.builder()
                                                .setDescription(desc.getText().toString()).setOrganisation(org.getText().toString())
                                                .setRole(role.getText().toString()).setType(type.getText().toString())
                                                .setFrom(from).setUpto(to).setPutflag("0").build(),edit_id);
                                        context.startActivity( new Intent(context, VolunteerActivity.class));

                                    }}).setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                        alertDialogAndroid.show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return volunteer.size();
    }

    class VolunteerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_volunteer_role)TextView role;
        @BindView(R.id.view_volunteer_desc)TextView desc;
        @BindView(R.id.view_volunteer_org)TextView org;
        @BindView(R.id.delete_volunteer)ImageView delete;
        @BindView(R.id.edit_volunteer)ImageView edit;

        public VolunteerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
}

