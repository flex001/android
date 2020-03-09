package org.owntracks.android.ui.region;

import org.owntracks.android.data.WaypointModel;
import org.owntracks.android.ui.base.view.MvvmView;
import org.owntracks.android.ui.base.viewmodel.MvvmViewModel;
import org.owntracks.android.ui.preferences.connection.dialog.ConnectionModeDialogViewModel;
import org.owntracks.android.ui.region.dialog.ReportingModeDialogViewModel;

import androidx.databinding.Bindable;

public interface RegionMvvm {

    interface View extends MvvmView {
        void showModeDialog();
    }

    interface ViewModel<V extends MvvmView> extends MvvmViewModel<V> {
        void loadWaypoint(long waypointId);

        void onReportModeClick();

        @Bindable WaypointModel getWaypoint();

        ReportingModeDialogViewModel getModeDialogViewModel();

        boolean canSaveWaypoint();
        void saveWaypoint();
    }
}
