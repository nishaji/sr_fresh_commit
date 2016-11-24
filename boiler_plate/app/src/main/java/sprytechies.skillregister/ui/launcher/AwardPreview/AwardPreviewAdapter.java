package sprytechies.skillregister.ui.launcher.AwardPreview;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import sprytechies.skillregister.data.model.Award;
import sprytechies.skillregister.data.model.AwardInsert;
import sprytechies.skillregister.ui.award.AwardActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 17/11/16.
 */

public class AwardPreviewAdapter extends RecyclerView.Adapter<AwardPreviewAdapter.AwardViewHolder> {

    private List<AwardInsert> awards;
    @Inject
    DatabaseHelper databaseHelper;
    Context context;
    String edit_id;
    private Subscription mSubscription;

    @Inject
    public AwardPreviewAdapter() {
        awards = new ArrayList<>();
    }
    public void setAwards(List<AwardInsert> award) {
        awards = award;
    }

    @Override
    public AwardPreviewAdapter.AwardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_preview_row, parent, false);
        return new AwardViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AwardPreviewAdapter.AwardViewHolder holder, final int position) {
        AwardInsert awardInsert = awards.get(position);
        holder.award_title.setText(awardInsert.award().title());
        holder.award_desc.setText(awardInsert.award().description());
        holder.award_org.setText(awardInsert.award().organisation());
        holder.date.setText(awardInsert.award().date());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid = awards.get(position).award().id();
                databaseHelper.delete_AWARDS(deleteid);
                context.startActivity(new Intent(context, AwardActivity.class));
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id = awards.get(position).award().id();
                edit_award();
            }
        });
    }

    private void edit_award() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getAwardForUpdate(edit_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award.");
                    }

                    @Override
                    public void onNext(List<AwardInsert> education) {
                        System.out.println(education);
                    }
                });

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.award_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText title = (EditText) mView.findViewById(R.id.di_award_title);
        final EditText description = (EditText) mView.findViewById(R.id.di_award_description);
        final EditText organisation = (EditText) mView.findViewById(R.id.di_award_organisation);
        final TextView time = (TextView) mView.findViewById(R.id.di_award_duration_text);
        final ImageView duration = (ImageView) mView.findViewById(R.id.di_award_duration);
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
                                        time.setText(date);

                                    }
                                });
                smoothDateRangePickerFragment.show(manager, "Datepickerdialog");

            }
        });


        title.setText(awards.get(0).award().title());
        description.setText(awards.get(0).award().description());
        organisation.setText(awards.get(0).award().organisation());
        time.setText(awards.get(0).award().duration());
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        databaseHelper.edit_awards(Award.builder()
                                .setTitle(title.getText().toString())
                                .setDescription(description.getText().toString())
                                .setOrganisation(organisation.getText().toString())
                                .setDuration(time.getText().toString())
                                .setId("")
                                .build(), edit_id);
                       /* databaseHelper.update_award_flag(Award.builder()
                                .setPutflag()
                                .build(),edit_id);*/
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
        return awards.size();
    }

    class AwardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_award_title)
        TextView award_title;
        @BindView(R.id.view_award_org)
        TextView award_org;
        @BindView(R.id.view_award_desc)
        TextView award_desc;
        @BindView(R.id.edit_award)
        ImageView edit;
        @BindView(R.id.delete_award)
        ImageView delete;
        @BindView(R.id.view_award_date)
        TextView date;

        public AwardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

    }
}

