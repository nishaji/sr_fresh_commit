package sprytechies.skillregister.ui.launcher.EducationPresenter;

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
import sprytechies.skillregister.data.model.EducationInsert;

public class EducationPreviewAdapter extends RecyclerView.Adapter<EducationPreviewAdapter.EducationViewHolder> {

    private List<EducationInsert> education;
    @Inject DatabaseHelper databaseHelper;
    private Subscription mSubscription;Context context;
    @Inject
    public EducationPreviewAdapter(){
        education=new ArrayList<>();
    }

    public void setEducation(List<EducationInsert> educations) {
        education = educations;
    }
    @Override
    public EducationPreviewAdapter.EducationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_edu_row, parent, false);
        return new EducationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EducationPreviewAdapter.EducationViewHolder holder, final int position) {
        EducationInsert educations=education.get(position);
        holder.school_name.setText(educations.education().school());
        holder.course_name.setText(educations.education().course());
        holder.location_name.setText(educations.education().location().name);
        holder.from.setText(educations.education().from());
        holder.upto.setText(educations.education().upto());
        holder.type.setText(educations.education().schooltype());
        holder.title.setText(educations.education().title());
        holder.edutype.setText(educations.education().edutype());
        holder.cgpi.setText(educations.education().cgpi());
        holder.cgpi_type.setText(educations.education().cgpitype());

    }
    @Override
    public int getItemCount() {
        return education.size();
    }

    class EducationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_school_name) TextView school_name;
        @BindView(R.id.view_course) TextView course_name;
        @BindView(R.id.view_location) TextView location_name;
        @BindView(R.id.view_from) TextView from;
        @BindView(R.id.view_upto) TextView upto;
        @BindView(R.id.view_type) TextView type;
        @BindView(R.id.view_title) TextView title;
        @BindView(R.id.view_edu_type) TextView edutype;
        @BindView(R.id.view_cgpi_type) TextView cgpi_type;
        @BindView(R.id.view_cgpi) TextView cgpi;
        public EducationViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }

}


