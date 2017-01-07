package sprytechies.skillregister.ui.publication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sprytechies.skillregister.R;
import sprytechies.skillregister.data.SyncService;
import sprytechies.skillregister.data.local.DatabaseHelper;
import sprytechies.skillregister.data.model.Publication;
import sprytechies.skillregister.data.remote.postservice.CertificatePost;
import sprytechies.skillregister.data.remote.postservice.PublicationPost;
import sprytechies.skillregister.ui.base.BaseActivity;
import sprytechies.skillregister.util.AndroidComponentUtil;
import sprytechies.skillregister.util.NetworkUtil;
import timber.log.Timber;


public class AddPublication extends BaseActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.add_publication_tool) Toolbar toolbar;
    @BindView(R.id.publication_title) EditText pub_title;
    @BindView(R.id.publisher) EditText publisher;
    @BindView(R.id.author) EditText author;
    @BindView(R.id.url) EditText url;
    @BindView(R.id.publication_desc) EditText pub_desc;
    @BindView(R.id.publication_duration) ImageView duration;
    @BindView(R.id.publication_duration_text) TextView duration_text;
    @BindView(R.id.add_publication) Button add_publication;
    @Inject DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_publications);
        ButterKnife.bind(this);
        setupToolbar();
    }
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.mipmap.arrowlleft);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPublication.this, ActivityPublication.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
            }
        });
    }

    @OnClick(R.id.add_publication)
    void onFabClick() {
        if(author.getText().toString().length()==0||pub_desc.getText().toString().length()==0||pub_title.getText().toString().length()==0||publisher.getText().toString().length()==0||url.getText().toString().length()==0){
            Toast.makeText(AddPublication.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else if(duration_text.getText().toString().length()==0){
            Toast.makeText(AddPublication.this, "Please pick date", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseHelper.setPublication(Publication.builder()
                    .setAuthors(author.getText().toString()).setDate(duration_text.getText().toString())
                    .setDescription(pub_desc.getText().toString()).setPublishers(publisher.getText().toString())
                    .setTitle(pub_title.getText().toString()).setUrl(url.getText().toString()).setPostflag("0").setPutflag("1").setCreateflag("1").setUpdateflag("1").build());
            startActivity(new Intent(AddPublication.this,ActivityPublication.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            if (!NetworkUtil.isNetworkConnected(this)) {
                Timber.i("Publication Sync canceled, connection not available");
                AndroidComponentUtil.toggleComponent(this, SyncService.SyncOnConnectionAvailable.class, true);
            }else{
                startService(PublicationPost.getStartIntent(this));
            }
        }
    }
    @OnClick(R.id.publication_duration)
    void onClockClick() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddPublication.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(AddPublication.this.getFragmentManager(), "Datepickerdialog");
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;duration_text.setText(date);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddPublication.this, ActivityPublication.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);finish();
    }
}
