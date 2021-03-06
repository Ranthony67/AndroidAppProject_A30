package grp19.det_brugerinddragende_hospital.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import grp19.det_brugerinddragende_hospital.Models.Report;
import grp19.det_brugerinddragende_hospital.R;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>{

    private ArrayList<Report> _reportData = new ArrayList<>();
    private ReportViewHolder reportViewHolder;
    private Context _context;

    public ReportListAdapter(ArrayList<Report> reports, Context context){
        _reportData = reports;
        _context = context;
    }

    public void clear(){
        _reportData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Report> list){
        _reportData.addAll(list);
        notifyDataSetChanged();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView medicineView;
        TextView foodView;
        TextView diaperView;
        TextView commentView;
        TextView nurseView;

        public ReportViewHolder(View itemView) {
            super(itemView);

            this.medicineView = (TextView) itemView.findViewById(R.id.medicineGivenTextView);
            this.foodView = (TextView) itemView.findViewById(R.id.foodGivenTextView);
            this.diaperView = (TextView) itemView.findViewById(R.id.diaperChangedTextView);
            this.commentView = (TextView) itemView.findViewById(R.id.commentTextView);
            this.nurseView = (TextView) itemView.findViewById(R.id.nurseName);
        }
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reportview_item, parent, false);

        reportViewHolder = new ReportViewHolder(view);
        return reportViewHolder;
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        TextView medicineText = holder.medicineView;
        TextView foodText = holder.foodView;
        TextView diaperText = holder.diaperView;
        TextView commentText = holder.commentView;
        TextView nurseText = holder.nurseView;

        final Report report = _reportData.get(position);

        if(report.medicine) medicineText.setText("Der er givet medicin");
        if(report.food) foodText.setText("Der er givet mad");
        if(report.diaper) diaperText.setText("Har fået skiftet ble");

        commentText.setText(report.comment);
        nurseText.setText(report.created_by);
    }

    @Override
    public int getItemCount() {
        return _reportData.size();
    }
}
