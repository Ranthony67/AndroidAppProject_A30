package appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.OverviewActivity;
import appprojgrp_nineteen.det_brugerinddragende_hospital.R;

/**
 * Created by Rasmus on 19/05/2016.
 */
public class ChildCardAdapter extends RecyclerView.Adapter<ChildCardAdapter.ChildViewHolder> {

    private ArrayList<Child> _childrenData = new ArrayList<>();
    private ChildViewHolder childViewHolder;

    public ChildCardAdapter(ArrayList<Child> children) {
        this._childrenData = children;
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        TextView departmentView;

        public ChildViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.nameText);
            this.departmentView = (TextView) itemView.findViewById(R.id.departmentText);
        }
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);

        view.setOnClickListener(OverviewActivity.childOnClickListener);

        childViewHolder = new ChildViewHolder(view);
        return childViewHolder;
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        TextView textViewName = holder.nameView;
        TextView textViewVersion = holder.departmentView;

        textViewName.setText(_childrenData.get(position).getName());
        textViewVersion.setText(_childrenData.get(position).getDepartment());
    }

    @Override
    public int getItemCount() {
        return _childrenData.size();
    }
}
