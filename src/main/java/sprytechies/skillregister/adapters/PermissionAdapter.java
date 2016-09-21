package sprytechies.skillregister.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import sprytechies.skillsregister.R;

/**
 * Created by sprydev5 on 14/9/16.
 */
public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.GmailVH> {
        List<String> dataList;
        String letter;
        Context context;
        ColorGenerator generator = ColorGenerator.MATERIAL;
    String per;

    /*int colors[] = {R.color.red, R.color.pink, R.color.purple, R.color.deep_purple,
            R.color.indigo, R.color.blue, R.color.light_blue, R.color.cyan, R.color.teal, R.color.green,
            R.color.light_green, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.deep_orange};*/

public PermissionAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;

        }

@Override
public GmailVH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.permission_row, viewGroup, false);
        return new GmailVH(view);
        }

@Override
public void onBindViewHolder(final GmailVH gmailVH, int i) {


    gmailVH.title.setText(dataList.get(i));
    letter = String.valueOf(dataList.get(i).charAt(0));
    TextDrawable drawable = TextDrawable.builder()
            .buildRound(letter, generator.getRandomColor());
    gmailVH.letter.setImageDrawable(drawable);
    gmailVH.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // do something, the isChecked will be
            // true if the switch is in the On position
            if(isChecked){
                cretadialog();
               // gmailVH.per.setText(per);
                System.out.println("per"+per);
            }else {
                System.out.println("helllow");

            }

        }
    });
}

    private void cretadialog() {
        final CharSequence[] items = {"Export", "Import", "Display","Public"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("Select your choice");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                per=items[item].toString();
                TastyToast.makeText(context, "You select !"+ per, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
        }

class GmailVH extends RecyclerView.ViewHolder {
    TextView title;
    ImageView letter;
    SwitchCompat switchCompat;

    public GmailVH(View itemView) {
        super(itemView);
        letter = (ImageView) itemView.findViewById(R.id.gmailitem_letter);
        title = (TextView) itemView.findViewById(R.id.gmailitem_title);
        switchCompat=(SwitchCompat)itemView.findViewById(R.id.switche);


    }
}

}


