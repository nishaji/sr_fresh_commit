package sprytechies.skillregister.ui.launcher.PublicationPreview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.PublicationInsert;

public class PublicatiobPreviewAdapter extends RecyclerView.Adapter<PublicatiobPreviewAdapter.PublicationViewHolder> {
    List<PublicationInsert> publications;
    Context context;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;

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
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    class PublicationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publication_title)TextView title;
        @BindView(R.id.publication_org)TextView org;
        @BindView(R.id.publication_desc)TextView desc;
        public PublicationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }
    }
}





