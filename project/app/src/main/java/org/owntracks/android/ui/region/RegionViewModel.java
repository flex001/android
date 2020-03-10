package org.owntracks.android.ui.region;

import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.owntracks.android.data.WaypointModel;
import org.owntracks.android.data.repos.LocationRepo;
import org.owntracks.android.data.repos.WaypointsRepo;
import org.owntracks.android.injection.scopes.PerActivity;
import org.owntracks.android.support.Preferences;
import org.owntracks.android.ui.base.viewmodel.BaseViewModel;
import org.owntracks.android.ui.preferences.connection.dialog.ConnectionModeDialogViewModel;
import org.owntracks.android.ui.region.dialog.ReportingModeDialogViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;

import static org.owntracks.android.App.getContext;

@PerActivity
public class RegionViewModel extends BaseViewModel<RegionMvvm.View> implements RegionMvvm.ViewModel<RegionMvvm.View> {
    private final LocationRepo locationRepo;
    private WaypointsRepo waypointsRepo;

    private WaypointModel waypoint;

    @Inject
    public RegionViewModel(WaypointsRepo waypointsRepo, LocationRepo locationRepo) {
        super();
        this.waypointsRepo = waypointsRepo;
        this.locationRepo = locationRepo; 
    }

    public void attachView(@NonNull RegionMvvm.View view, @Nullable Bundle savedInstanceState) {
        super.attachView(view, savedInstanceState);
    }

    public void loadWaypoint(long id) {
        WaypointModel w = waypointsRepo.get(id);
        if(w == null) {
            w = new WaypointModel();
            if (locationRepo.hasLocation()) {
                w.setGeofenceLatitude(locationRepo.getCurrentLocation().getLatitude());
                w.setGeofenceLongitude(locationRepo.getCurrentLocation().getLongitude());
            } else {
                w.setGeofenceLatitude(0);
                w.setGeofenceLongitude(0);
            }
        }
        setWaypoint(w);
    }

    @Override
    public void onReportModeClick() {
        getView().showModeDialog();
    }

    @Override
    public ReportingModeDialogViewModel getModeDialogViewModel() {
        return new ReportingModeDialogViewModel(waypoint);
    }

    public boolean canSaveWaypoint() {
        return this.waypoint.getDescription().length() > 0;
    }

    public void saveWaypoint() {
        if(canSaveWaypoint()) {
            waypointsRepo.insert(waypoint);
        }
    }

    @Bindable
    public WaypointModel getWaypoint() {
        return waypoint;
    }

    private void setWaypoint(WaypointModel waypoint) {
        this.waypoint = waypoint;
    }
}

