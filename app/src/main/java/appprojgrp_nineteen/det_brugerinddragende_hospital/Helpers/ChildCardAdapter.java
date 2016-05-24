package appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.MainApplication;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.R;

public class ChildCardAdapter extends RecyclerView.Adapter<ChildCardAdapter.ChildViewHolder> {
    private ArrayList<Child> _childrenData = new ArrayList<>();
    private ChildViewHolder childViewHolder;

    public ChildCardAdapter(ArrayList<Child> children) {
        this._childrenData = children;
    }

    public void clear() {
        _childrenData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Child> list) {
        _childrenData.addAll(list);
        notifyDataSetChanged();
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView departmentView;
        Button newReport;
        Button history;

        public ChildViewHolder(View itemView) {
            super(itemView);
            this.nameView = (TextView) itemView.findViewById(R.id.nameText);
            this.departmentView = (TextView) itemView.findViewById(R.id.departmentText);
            this.newReport = (Button) itemView.findViewById(R.id.newReport);
            this.history = (Button) itemView.findViewById(R.id.history);
        }
    }

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ChildCard", "onClick");
            }
        });

        childViewHolder = new ChildViewHolder(view);
        return childViewHolder;
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, int position) {
        TextView textViewName = holder.nameView;
        TextView textViewVersion = holder.departmentView;

        textViewName.setText(_childrenData.get(position).getName());
        textViewVersion.setText(_childrenData.get(position).getDepartment());

        holder.newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("newReport", "onClick");
            }
        });

        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("history", "onClick");
            }
        });
    }

    @Override
    public int getItemCount() {
        return _childrenData.size();
    }
}
