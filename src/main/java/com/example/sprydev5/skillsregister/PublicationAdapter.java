package com.example.sprydev5.skillsregister;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sprydev5.skillsregister.model.Publication;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by sprydev5 on 16/9/16.
 */
public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.MyViewHolder> {

    private ArrayList<Publication> publist;
    Context context;

    public PublicationAdapter(ArrayList<Publication> mDataSet) {
        this.publist = mDataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,org,desc;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            title=(TextView)itemView.findViewById(R.id.publicationtitle);
            org=(TextView)itemView.findViewById(R.id.publicationorg);
            desc=(TextView)itemView.findViewById(R.id.publicationdesc);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.publication_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.title.setText(publist.get(position).getTitle());
        holder.org.setText(publist.get(position).getOrganisation());
        holder.desc.setText(publist.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return publist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

            }



