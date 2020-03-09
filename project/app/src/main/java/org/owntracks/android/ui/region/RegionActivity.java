package org.owntracks.android.ui.region;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import org.owntracks.android.R;
import org.owntracks.android.databinding.UiRegionBinding;
import org.owntracks.android.databinding.UiRegionReportingModeBinding;
import org.owntracks.android.ui.base.BaseActivity;
import org.owntracks.android.ui.region.dialog.ReportingModeDialogViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

public class RegionActivity extends BaseActivity<UiRegionBinding, RegionMvvm.ViewModel> implements RegionMvvm.View {

    private MenuItem saveButton;
    private ReportingModeDialogViewModel activeDialogViewModel ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasEventBus(false);
        bindAndAttachContentView(R.layout.ui_region, savedInstanceState);
        setSupportToolbar(binding.toolbar);

        Bundle b = navigator.getExtrasBundle(getIntent());
        if (b != null) {
            viewModel.loadWaypoint(b.getLong("waypointId", 0));
        }

        binding.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                conditionallyEnableSaveButton();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_waypoint, menu);
        this.saveButton = menu.findItem(R.id.save);
        conditionallyEnableSaveButton();
        return true;
    }

    @Override
    public void showModeDialog() {

        UiRegionReportingModeBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.ui_region_reporting_mode,  null, false);

        dialogBinding.setVm(viewModel.getModeDialogViewModel());
        activeDialogViewModel = dialogBinding.getVm();


        new AlertDialog.Builder(this)
                .setView(dialogBinding.getRoot())
                .setTitle(R.string.mode_heading)
                .setPositiveButton(R.string.accept, dialogBinding.getVm())
                .setNegativeButton(R.string.cancel, dialogBinding.getVm())
                .show();

        //new MaterialDialog.Builder(this)
        //         .customView(dialogBinding.getRoot(), true)
        //         .title(R.string.mode_heading)
        //         .positiveText(R.string.accept)
        //         .negativeText(R.string.cancel)
        //         .onPositive(dialogBinding.getVm())
        //        .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                viewModel.saveWaypoint();
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void conditionallyEnableSaveButton() {
        if (saveButton != null) {
            saveButton.setEnabled(viewModel.canSaveWaypoint());
            saveButton.getIcon().setAlpha(viewModel.canSaveWaypoint() ? 255 : 130);
        }
    }
}
