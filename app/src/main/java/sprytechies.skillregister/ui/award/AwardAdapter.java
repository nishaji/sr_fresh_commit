package sprytechies.skillregister.ui.award;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
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
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.AwardViewHolder> implements DatePickerDialog.OnDateSetListener {

    private List<AwardInsert> awards;AlertDialog ab;
    @Inject DatabaseHelper databaseHelper;
    Context context;String edit_id; TextView time;
    private Subscription mSubscription;
    @Inject public AwardAdapter() {
        awards=new ArrayList<>();
    }
    public void setAwards(List<AwardInsert> award) {
        awards = award;
    }
    @Override
    public AwardAdapter.AwardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.award_row, parent, false);
        return new AwardViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final AwardAdapter.AwardViewHolder holder, final int position) {
        AwardInsert awardInsert=awards.get(position);
        holder.award_title.setText(awardInsert.award().title());
        holder.award_desc.setText(awardInsert.award().description());
        holder.award_org.setText(awardInsert.award().organisation());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=awards.get(position).award().id();databaseHelper.delete_AWARDS(deleteid);
                context.startActivity( new Intent(AwardAdapter.this.context, AwardActivity.class));}});
                holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=awards.get(position).award().id();edit_award();
            }});
    }

    private void edit_award() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getAwardForUpdate(edit_id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<AwardInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award.");
                    }
                    @Override
                    public void onNext(List<AwardInsert> awards) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.award_dialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText title = (EditText) mView.findViewById(R.id.di_award_title);
                        final EditText description = (EditText) mView.findViewById(R.id.di_award_description);
                        final EditText organisation = (EditText) mView.findViewById(R.id.di_award_organisation);
                        time = (TextView) mView.findViewById(R.id.di_award_duration_text);
                        final ImageView duration = (ImageView) mView.findViewById(R.id.di_award_duration);
                        duration.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar now = Calendar.getInstance();
                                DatePickerDialog dpd = DatePickerDialog.newInstance(
                                        AwardAdapter.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                                );
                                dpd.show(((Activity)AwardAdapter.this.context).getFragmentManager(), "Datepickerdialog");
                            }
                        });
                        title.setText(awards.get(0).award().title());
                        description.setText(awards.get(0).award().description());
                        organisation.setText(awards.get(0).award().organisation());time.setText(awards.get(0).award().duration());
                        alertDialogBuilderUserInput.setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        databaseHelper.edit_awards(Award.builder()
                                                .setTitle(title.getText().toString()).setDescription(description.getText().toString())
                                                .setOrganisation(organisation.getText().toString()).setDuration(time.getText().toString()).setPutflag("0")
                                                .build(),edit_id);
                                         context.startActivity( new Intent(AwardAdapter.this.context, AwardActivity.class));

                                    }})
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        ab = alertDialogBuilderUserInput.create();ab.show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return awards.size();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
          String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
          time.setText(date);
    }

    class AwardViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.view_award_title) TextView award_title;
        @BindView(R.id.view_award_org) TextView award_org;
        @BindView(R.id.view_award_desc) TextView award_desc;
        @BindView(R.id.edit_award)ImageView edit;
        @BindView(R.id.delete_award)ImageView delete;

        public AwardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        ab.dismiss();
    }
}
