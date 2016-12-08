package sprytechies.skillregister.ui.launcher.ContactPreview;

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
import sprytechies.skillregister.data.model.ContactInsert;

public class ContactPreviewAdapter extends RecyclerView.Adapter<ContactPreviewAdapter.ContactViewHolder> {
    private List<ContactInsert> contacts;
    @Inject DatabaseHelper databaseHelper;
    Context context;private Subscription mSubscription;
    @Inject
    public ContactPreviewAdapter() {
        contacts = new ArrayList<>();
    }
    public void setContacts(List<ContactInsert>contact){
        contacts=contact;

    }
    @Override
    public ContactPreviewAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_preview_row, parent, false);
        return new ContactViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ContactPreviewAdapter.ContactViewHolder holder,final int position) {
        ContactInsert contactInsert=contacts.get(position);
        holder.contact.setText(contactInsert.contact().contact());
        holder.cateory.setText(contactInsert.contact().category());
        holder.type.setText(contactInsert.contact().type());

    }
    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_contact)TextView contact;
        @BindView(R.id.row_category)TextView cateory;
        @BindView(R.id.row_contact_type)TextView type;
        public ContactViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();

        }
    }
}



