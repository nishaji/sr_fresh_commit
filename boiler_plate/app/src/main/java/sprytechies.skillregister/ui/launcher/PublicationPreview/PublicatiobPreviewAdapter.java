package skillregister.ui.launcher.PublicationPreview;

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
import sprytechies.skillregister.data.model.PublicationInsert;
import sprytechies.skillregister.ui.publication.ActivityPublication;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

/**
 * Created by sprydev5 on 17/11/16.
 */

public class PublicatiobPreviewAdapter extends RecyclerView.Adapter<PublicatiobPreviewAdapter.PublicationViewHolder> {
    List<PublicationInsert> publications;
    Context context;
    @Inject
    DatabaseHelper databaseHelper;
    private Subscription mSubscription;
    String edit_id;
    @Inject
    PublicatiobPreviewAdapter(){
        publications=new ArrayList<>();
    }
    public void setPublication(List<PublicationInsert> publication){
        publications=publication;
    }
    @Override
    public PublicatiobPreviewAdapter.PublicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.publication_row, parent, false);
        return new PublicationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PublicatiobPreviewAdapter.PublicationViewHolder holder, final int position) {
        PublicationInsert publicationInsert=publications.get(position);
        holder.title.setText(publicationInsert.publication().title());
        holder.desc.setText(publicationInsert.publication().description());
        holder.org.setText(publicationInsert.publication().authors());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=publications.get(position).publication().id();
                edit_publication();

            }});
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=publications.get(position).publication().id();
                databaseHelper.delete_publication(deleteid);
                context.startActivity( new Intent(context, ActivityPublication.class));

            }});

    }

    private void edit_publication() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getPublicationForUpdate(edit_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PublicationInsert>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the publication.");

                    }

                    @Override
                    public void onNext(List<PublicationInsert> volunteer) {
                        System.out.println(volunteer);

                    }
                });
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.publication_dialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText title=(EditText)mView.findViewById(R.id.di_publication_title);
        final EditText author=(EditText)mView.findViewById(R.id.di_publication_author);
        final EditText org=(EditText)mView.findViewById(R.id.di_publication_org);
        final EditText desc=(EditText)mView.findViewById(R.id.di_publication_desc);
        final EditText url=(EditText)mView.findViewById(R.id.di_publication_url);
        final TextView time_text=(TextView)mView.findViewById(R.id.di_publication_text);
        title.setText(publications.get(0).publication().title());
        author.setText(publications.get(0).publication().authors());
        org.setText(publications.get(0).publication().authors());
        desc.setText(publications.get(0).publication().description());
        url.setText(publications.get(0).publication().url());
        time_text.setText(publications.get(0).publication().date());



        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }}).setNegativeButton("Cancel",
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
        return publications.size();
    }

    class PublicationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publication_title)TextView title;
        @BindView(R.id.publication_org)TextView org;
        @BindView(R.id.publication_desc)TextView desc;
        @BindView(R.id.edit_publication)ImageView edit;
        @BindView(R.id.delete_publication)ImageView delete;
        public PublicationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
}





