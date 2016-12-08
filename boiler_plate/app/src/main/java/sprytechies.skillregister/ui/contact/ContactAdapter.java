package sprytechies.skillregister.ui.contact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
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
import sprytechies.skillregister.data.model.Contact;
import sprytechies.skillregister.data.model.ContactInsert;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<ContactInsert> contacts;
    @Inject DatabaseHelper databaseHelper;
    Context context;String edit_id;
    private Subscription mSubscription;
    @Inject
    public ContactAdapter() {
        contacts = new ArrayList<>();
    }
    public void setContacts(List<ContactInsert>contact){
        contacts=contact;

    }
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ContactViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder,final int position) {
      ContactInsert contactInsert=contacts.get(position);
        holder.contact.setText(contactInsert.contact().contact());
        holder.cateory.setText(contactInsert.contact().category());
        holder.type.setText(contactInsert.contact().type());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=contacts.get(position).contact().id();
                databaseHelper.delete_contact(deleteid);
                context.startActivity( new Intent(context, ConatctActivity.class));
            }});
                holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=contacts.get(position).contact().id();edit_contact();
            }});
    }

    private void edit_contact() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getContactForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Subscriber<List<ContactInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the contact.");
                    }
                    @Override
                    public void onNext(List<ContactInsert> contactlist) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
                        View mView = layoutInflaterAndroid.inflate(R.layout.contact_dialog, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
                        alertDialogBuilderUserInput.setView(mView);
                        final EditText contact=(EditText)mView.findViewById(R.id.di_contact);
                        final MaterialBetterSpinner contact_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_contact_type);
                        final MaterialBetterSpinner category=(MaterialBetterSpinner)mView.findViewById(R.id.di_category);
                        contact.setText(contactlist.get(0).contact().contact());
                        ArrayAdapter<String> contact_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.contact_array));
                        contact_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        contact_type.setAdapter(contact_type_adapter);
                        System.out.println(contactlist.get(0).contact().type());
                        if (!contactlist.get(0).contact().type().equals(null)) {
                            int spinnerPosition = contact_type_adapter.getPosition(contactlist.get(0).contact().type());
                            System.out.println(spinnerPosition+"position");
                            try {
                                if (contactlist.get(0).contact().type().trim().length() > 0) {
                                    contact_type.setText(contact_type_adapter.getItem(spinnerPosition));
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.contact_category_array));
                        category_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        category.setAdapter(category_adapter);
                        if (!contactlist.get(0).contact().category().equals(null)) {
                            int spinnerPosition = category_adapter.getPosition(contactlist.get(0).contact().category());
                            System.out.println(spinnerPosition+"position");
                            try {
                                if (contactlist.get(0).contact().category().trim().length() > 0) {
                                    category.setText(category_adapter.getItem(spinnerPosition).toString());
                                } else {
                                    System.out.println("do nothing");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        databaseHelper.edit_contact(Contact.builder()
                                                .setContact(contact.getText().toString()).setCategory(category.getText().toString())
                                                .setStatus("pending").setType(contact_type.getText().toString()).setPutflag("0")
                                                .build(),edit_id);
                                        context.startActivity( new Intent(context, ConatctActivity.class));
                                    }})
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();alertDialogAndroid.show();
                    }});

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_contact)TextView contact;
        @BindView(R.id.row_category)TextView cateory;
        @BindView(R.id.row_contact_type)TextView type;
        @BindView(R.id.contact_edit)ImageView edit;
        @BindView(R.id.contact_delete)ImageView delete;
        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}


