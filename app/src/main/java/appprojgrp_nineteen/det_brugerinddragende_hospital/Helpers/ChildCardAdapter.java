package appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Constants.Constants;
import appprojgrp_nineteen.det_brugerinddragende_hospital.HistoryActivity;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.R;
import appprojgrp_nineteen.det_brugerinddragende_hospital.ReportActivity;

public class ChildCardAdapter extends RecyclerView.Adapter<ChildCardAdapter.ChildViewHolder> {
    private ArrayList<Child> _childrenData = new ArrayList<>();
    private ChildViewHolder childViewHolder;
    private Context passedContext;

    public ChildCardAdapter(ArrayList<Child> children, Context context) {
        this._childrenData = children;
        this.passedContext = context;
    }

    public void clear() {
        _childrenData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Child> list) {
        _childrenData.addAll(list);
        notifyDataSetChanged();
    }

    /*
    * Gracefully learned at
    * http://www.journaldev.com/10024/android-recyclerview-and-cardview-example-tutorial
    * */
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

        final Child child = _childrenData.get(position);

        textViewName.setText(child.getName());
        textViewVersion.setText(child.getDepartment());

        holder.newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("newReport", "onClick");

                Intent intent = new Intent(v.getContext(), ReportActivity.class);
                intent.putExtra("child-id", child.id);
                intent.putExtra("child-name", child.name);
                ((Activity)passedContext).startActivityForResult(intent, Constants.NEW_REPORT_RESULT);
            }
        });

        holder.history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("history", "onClick");

                Intent intent = new Intent(v.getContext(), HistoryActivity.class);
                intent.putExtra("child-id", child.id);
                intent.putExtra("child-name", child.name);
                ((Activity)passedContext).startActivityForResult(intent, Constants.NEW_REPORT_RESULT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _childrenData.size();
    }
}
