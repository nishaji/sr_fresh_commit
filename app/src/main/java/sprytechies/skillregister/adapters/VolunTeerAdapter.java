package sprytechies.skillregister.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import sprytechies.skillregister.model.Volunteer;
import sprytechies.skillsregister.R;


/**
 * Created by sprydev5 on 15/9/16.
 */
public class VolunTeerAdapter extends RecyclerView.Adapter<VolunTeerAdapter.MyViewHolder> {

    private ArrayList<Volunteer> volunlist;
    Context context;

    public VolunTeerAdapter(ArrayList<Volunteer> mDataSet) {
        this.volunlist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView role,org,desc;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            role=(TextView)itemView.findViewById(R.id.volunrole);
            org=(TextView)itemView.findViewById(R.id.volunorg);
            desc=(TextView)itemView.findViewById(R.id.volundesc);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.volunteer_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.role.setText(volunlist.get(position).getRole());
        holder.org.setText(volunlist.get(position).getOrganisation());
        holder.desc.setText(volunlist.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return volunlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}



