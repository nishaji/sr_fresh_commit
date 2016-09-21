package sprytechies.skillregister.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import sprytechies.skillregister.model.Award;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 16/9/16.
 */
public class AwardAdapter extends RecyclerView.Adapter<AwardAdapter.MyViewHolder> {

private ArrayList<Award> awardlist;
        Context context;

public AwardAdapter(ArrayList<Award> mDataSet) {
        this.awardlist = mDataSet;
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, org, desc;
    CardView cardView;
    ImageButton edit,delete;
    public MyViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        cardView = (CardView) itemView.findViewById(R.id.awardcard);
        title = (TextView) view.findViewById(R.id.awardtitle);
        org = (TextView) view.findViewById(R.id.awardorg);
        desc = (TextView) view.findViewById(R.id.awarddescription);

    }
}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.award_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.title.setText(awardlist.get(position).getTitle());
        holder.org.setText(awardlist.get(position).getOrganisation());
        holder.desc.setText(awardlist.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return awardlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}




