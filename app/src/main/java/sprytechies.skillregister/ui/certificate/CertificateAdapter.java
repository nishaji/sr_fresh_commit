package sprytechies.skillregister.ui.certificate;

import android.app.Activity;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
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
import sprytechies.skillregister.data.model.Certificate;
import sprytechies.skillregister.data.model.CertificateInsert;
import sprytechies.skillregister.util.RxUtil;
import timber.log.Timber;


/**
 * Created by sprydev5 on 4/10/16.
 */

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder> implements DatePickerDialog.OnDateSetListener {
    private List<CertificateInsert>certificateInsertList;
    @Inject DatabaseHelper databaseHelper;TextView time;
    String edit_id;Context context;AlertDialog ab;
    private Subscription mSubscription;

    @Inject
    CertificateAdapter(){
        certificateInsertList=new ArrayList<>();
    }
    public void setCertificates(List<CertificateInsert> certificates) {
        certificateInsertList = certificates;
    }
    @Override
    public CertificateAdapter.CertificateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cert_row, parent, false);
        return new CertificateViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CertificateAdapter.CertificateViewHolder holder,final int position) {
        CertificateInsert certificateInsert=certificateInsertList.get(position);
        holder.certificate_name.setText(certificateInsert.certificate().name());
        holder.certificate_authority.setText(certificateInsert.certificate().authority());
        holder.certificate_date.setText(certificateInsert.certificate().type());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteid=certificateInsertList.get(position).certificate().id();
                databaseHelper.delete_certificate(deleteid);
                context.startActivity( new Intent(context, CertificateActivity.class));
            }});
                holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_id=certificateInsertList.get(position).certificate().id();edit_certificate();
            }});

    }

    private void edit_certificate() {
        RxUtil.unsubscribe(mSubscription);
        mSubscription = databaseHelper.getCertificateForUpdate(edit_id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<CertificateInsert>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the award.");
                    }
                    @Override
                    public void onNext(List<CertificateInsert> education) {
                        System.out.println(education);}});
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.certdialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mView);
        final EditText cert_name=(EditText)mView.findViewById(R.id.di_certificate_name);
        final MaterialBetterSpinner cert_type=(MaterialBetterSpinner)mView.findViewById(R.id.di_certificate_type);
        final EditText rank=(EditText)mView.findViewById(R.id.di_rank);
        final EditText authority=(EditText)mView.findViewById(R.id.di_authority);
        time=(TextView)mView.findViewById(R.id.di_cert_duration_text);
        final ImageView duration = (ImageView) mView.findViewById(R.id.di_cert_duration);
        duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CertificateAdapter.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(((Activity)CertificateAdapter.this.context).getFragmentManager(), "Datepickerdialog");
            }
        });
        cert_name.setText(certificateInsertList.get(0).certificate().name());
        rank.setText(certificateInsertList.get(0).certificate().rank());
        authority.setText(certificateInsertList.get(0).certificate().authority());
        time.setText(certificateInsertList.get(0).certificate().certdate());
        ArrayAdapter<String> cert_type_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, context.getResources().getStringArray(R.array.certificate_array));
        cert_type_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        cert_type.setAdapter(cert_type_adapter);
        if (!certificateInsertList.get(0).certificate().type().equals(null)) {
            int spinnerPosition = cert_type_adapter.getPosition(certificateInsertList.get(0).certificate().type());
            try {
                if (certificateInsertList.get(0).certificate().type().trim().length() > 0) {
                    cert_type.setText(cert_type_adapter.getItem(spinnerPosition).toString());
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
                        databaseHelper.edit_certificate(Certificate.builder()
                                .setAuthority(authority.getText().toString()).setCertdate(time.getText().toString())
                                .setName(cert_name.getText().toString()).setRank(rank.getText().toString())
                                 .setType(cert_type.getText().toString())
                                .build(),edit_id);
                        context.startActivity( new Intent(context, CertificateActivity.class));
                    }})
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        ab = alertDialogBuilderUserInput.create();ab.show();

    }

    @Override
    public int getItemCount() {
        return certificateInsertList.size();
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;time.setText(date);
    }

    class CertificateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_certificate_name) TextView certificate_name;
        @BindView(R.id.view_certificate_authority) TextView certificate_authority;
        @BindView(R.id.view_certificate_date) TextView certificate_date;
        @BindView(R.id.cert_edit)ImageView edit;
        @BindView(R.id.cert_delete)ImageView delete;
        public CertificateViewHolder(View itemView) {
            super(itemView);ButterKnife.bind(this, itemView);context = itemView.getContext();
        }
    }
}

