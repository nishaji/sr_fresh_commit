package sprytechies.skillregister.ui.launcher.ExperiencePreview;

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
import sprytechies.skillregister.data.model.Experience;
import sprytechies.skillregister.data.model.ExperienceInsert;
import sprytechies.skillregister.data.model.Location;
import sprytechies.skillregister.ui.experience.ExperienceActivity;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


public class ExperiencePreviewAdapter extends RecyclerView.Adapter<ExperiencePreviewAdapter.ExperienceViewHolder> {

    private List<ExperienceInsert> experience;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;Context context;
    @Inject
    public ExperiencePreviewAdapter(){
        experience = new ArrayList<>();
    }
    public void setExperience(List<ExperienceInsert> experiences) {
        experience = experiences;
    }
    @Override
    public ExperiencePreviewAdapter.ExperienceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_work_exp_row, parent, false);
        return new ExperienceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExperiencePreviewAdapter.ExperienceViewHolder holder, final int position) {
        ExperienceInsert experienceInsert=experience.get(position);
        holder.company_name.setText(experienceInsert.experience().company());
        holder.company_title.setText(experienceInsert.experience().title());
        holder.company_location.setText(experienceInsert.experience().location().name);
        holder.type.setText(experienceInsert.experience().type());
        holder.from.setText(experienceInsert.experience().from());
        holder.upto.setText(experienceInsert.experience().upto());
        holder.status.setText(experienceInsert.experience().status());
        holder.job.setText(experienceInsert.experience().job());
    }
    @Override
    public int getItemCount() {
        return experience.size();
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_company_name) TextView company_name;
        @BindView(R.id.view_company_title) TextView company_title;
        @BindView(R.id.view_exp_location) TextView company_location;
        @BindView(R.id.view_exp_type) TextView type;
        @BindView(R.id.view_exp_from) TextView from;
        @BindView(R.id.view_exp_upto) TextView upto;
        @BindView(R.id.view_exp_status) TextView status;
        @BindView(R.id.view_exp_job) TextView job;
        public ExperienceViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}



