package appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers;

import android.content.Context;
import android.view.View;

import appprojgrp_nineteen.det_brugerinddragende_hospital.OverviewActivity;

/**
 * Created by Rasmus on 19/05/2016.
 */
public class ChildOnClickListener implements View.OnClickListener {

    private Context _context = null;

    public ChildOnClickListener(Context context) {
        this._context = context;
    }

    @Override
    public void onClick(View v) {
        // TODO: Load Report view for child
    }
}
