package sprytechies.skillregister.ui.publication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.ui.award.AwardAdapter;
import sprytechies.skillregister.ui.education.EducationAdapter;
import sprytechies.skillregister.util.RxUtil;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder> implements DatePickerDialog.OnDateSetListener {
    List<PublicationInsert> publications;
    Context context;String edit_id;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    AlertDialog ab;TextView time;

    @Inject
    PublicationAdapter() {
        publications = new ArrayList<>();
    }

    public void setPublication(List<PublicationInsert> publication) {
        publications = publication;
    }

    @Override
    public PublicationAdapter.PublicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.publication_row, parent, false);
        return new PublicationViewHolder(itemView);
    }

    public void onViewDetachedFromWindow(PublicationViewHolder holder) {
        if (ab != null) {
            ab.dismiss();
            ab = null;
        }
    }
    @Override
    public void onBindViewHolder(PublicationAdapter.PublicationViewHolder holder, final int position) {
        PublicationInsert publicationInsert = publications.get(position);
        holder.title.setText(publicationInsert.publication().title());
        holder.desc.setText(publicationInsert.publication().description());
        holder.org.setText(publicationInsert.publication().authors());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id = publications.get(position).publication().id();
                edit_publication();

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid = publications.get(position).publication().id();
                databaseHelper.delete_publication(deleteid);

            }
        });

    }

    private void edit_publication() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getPublicationForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<PublicationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(final List<PublicationInsert> publications) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.publication_dialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText title = (EditText) mView.findViewById(R.id.di_publication_title);
                        final EditText author = (EditText) mView.findViewById(R.id.di_publication_author);
                        final EditText pub = (EditText) mView.findViewById(R.id.di_publication_publishers);
                        final EditText desc = (EditText) mView.findViewById(R.id.di_publication_desc);
                        final EditText url = (EditText) mView.findViewById(R.id.di_publication_url);
                        time = (TextView) mView.findViewById(R.id.di_publication_text);
                        final ImageView duration=(ImageView)mView.findViewById(R.id.di_publication_duration);
                        title.setText(publications.get(0).publication().title());
                        author.setText(publications.get(0).publication().authors());
                        pub.setText(publications.get(0).publication().publisher());
                        desc.setText(publications.get(0).publication().description());
                        url.setText(publications.get(0).publication().url());
                        time.setText(publications.get(0).publication().date());
                        duration.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar now = Calendar.getInstance();
                                DatePickerDialog dpd = DatePickerDialog.newInstance(
                                        PublicationAdapter.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                                );
                                dpd.show(((Activity)PublicationAdapter.this.context).getFragmentManager(), "Datepickerdialog");
                            }
                        });
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        databaseHelper.edit_publication(Publication.builder()
                                                .setTitle(title.getText().toString())
                                                .setAuthors(author.getText().toString())
                                                .setDate(time.getText().toString())
                                                .setDescription(desc.getText().toString())
                                                .setPublisher(pub.getText().toString())
                                                .setUrl(url.getText().toString())
                                                .build(),edit_id);
                                    }
                                }).setNegativeButton("Cancel",
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
        return publications.size();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        time.setText(date);
    }

    class PublicationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publication_title) TextView title;
        @BindView(R.id.publication_org) TextView org;
        @BindView(R.id.publication_desc) TextView desc;
        @BindView(R.id.edit_publication) ImageView edit;
        @BindView(R.id.delete_publication) ImageView delete;

        public PublicationViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}




