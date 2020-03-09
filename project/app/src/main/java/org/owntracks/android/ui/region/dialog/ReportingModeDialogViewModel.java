package org.owntracks.android.ui.region.dialog;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.databinding.BaseObservable;

import org.owntracks.android.R;
import org.owntracks.android.data.WaypointModel;
import org.owntracks.android.services.LocationProcessor;
import org.owntracks.android.ui.preferences.connection.dialog.ConnectionModeDialogViewModel;

public class ReportingModeDialogViewModel extends BaseObservable implements DialogInterface.OnClickListener {
    private int reportingModeResId;
    private WaypointModel waypointModel;

    public ReportingModeDialogViewModel(WaypointModel waypointModel) {
        super();
        this.waypointModel = waypointModel;
        load();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    public void load() {
        this.reportingModeResId = modeToResId(waypointModel.getReportingMode());
    }

    public void save() {
        waypointModel.setReportingMode(resIdToMode(reportingModeResId));
    }

    public int getMode() {
        return reportingModeResId;
    }

    public void setMode(int mode) {
        this.reportingModeResId = mode;
    }

    private int modeToResId(int mode) {
        switch (mode) {

            case LocationProcessor.MONITORING_MOVE:
                return R.id.radioModeMoveReporting;
            case LocationProcessor.MONITORING_SIGNIFFICANT:
            default:
                return R.id.radioModeSignificantReporting;
        }
    }

    private int resIdToMode(int resId) {
        switch (resId) {
            case R.id.radioModeMoveReporting:
                return LocationProcessor.MONITORING_MOVE;
            case R.id.radioModeSignificantReporting:
                return LocationProcessor.MONITORING_SIGNIFFICANT;
            default:
                return LocationProcessor.MONITORING_MANUAL;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            save();
        } else if(which == DialogInterface.BUTTON_NEGATIVE) {
            dialog.cancel();
        }
    }
}

