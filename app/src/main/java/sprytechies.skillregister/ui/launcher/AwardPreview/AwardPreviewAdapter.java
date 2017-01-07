package sprytechies.skillregister.ui.launcher.AwardPreview;

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
import sprytechies.skillregister.data.model.AwardInsert;

public class AwardPreviewAdapter extends RecyclerView.Adapter<AwardPreviewAdapter.AwardViewHolder> {

    private List<AwardInsert> awards;
    @Inject DatabaseHelper databaseHelper;
    Context context;private Subscription mSubscription;

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
    }
    @Override
    public int getItemCount() {
        return awards.size();
    }

    class AwardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_award_title) TextView award_title;
        @BindView(R.id.view_award_org) TextView award_org;
        @BindView(R.id.view_award_desc) TextView award_desc;
        @BindView(R.id.view_award_date)
        TextView date;

        public AwardViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }

    }
}

