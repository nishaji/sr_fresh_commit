package sprytechies.skillregister.ui.launcher.CertificatePreview;

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
import sprytechies.skillregister.data.model.CertificateInsert;

public class CertificatePreviewAdapter extends RecyclerView.Adapter<CertificatePreviewAdapter.CertificateViewHolder> {
    private List<CertificateInsert> certificateInsertList;
    @Inject DatabaseHelper databaseHelper;
    Context context;private Subscription mSubscription;

    @Inject
    public CertificatePreviewAdapter(){
        certificateInsertList=new ArrayList<>();

    }
    public void setCertificates(List<CertificateInsert> certificates) {
        certificateInsertList = certificates;
    }
    @Override
    public CertificatePreviewAdapter.CertificateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cert_preview_row, parent, false);
        return new CertificateViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CertificatePreviewAdapter.CertificateViewHolder holder,final int position) {
        CertificateInsert certificateInsert=certificateInsertList.get(position);
        holder.certificate_name.setText(certificateInsert.certificate().name());
        holder.certificate_authority.setText(certificateInsert.certificate().authority());
        holder.certificate_type.setText(certificateInsert.certificate().type());
        holder.certificate_rank.setText(certificateInsert.certificate().rank());
        holder.certificate_date.setText(certificateInsert.certificate().date());
    }
    @Override
    public int getItemCount() {
        return certificateInsertList.size();
    }
    class CertificateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_certificate_name) TextView certificate_name;
        @BindView(R.id.view_certificate_authority) TextView certificate_authority;
        @BindView(R.id.view_certificate_date) TextView certificate_date;
        @BindView(R.id.view_certificate_rank) TextView certificate_rank;
        @BindView(R.id.view_certificate_type) TextView certificate_type;
        public CertificateViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}


