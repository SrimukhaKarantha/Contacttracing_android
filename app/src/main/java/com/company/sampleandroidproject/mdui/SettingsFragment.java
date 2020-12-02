package com.company.sampleandroidproject.mdui;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.annotation.Nullable;

import com.company.sampleandroidproject.R;
import com.company.sampleandroidproject.app.SAPWizardApplication;

import com.company.sampleandroidproject.app.WizardFlowStateListener;
import com.sap.cloud.mobile.flowv2.core.Flow;
import com.sap.cloud.mobile.flowv2.core.FlowContext;
import com.sap.cloud.mobile.flowv2.core.FlowContextBuilder;
import com.sap.cloud.mobile.flowv2.model.FlowConstants;
import com.sap.cloud.mobile.flowv2.model.FlowType;
import com.sap.cloud.mobile.flowv2.core.DialogHelper;






import kotlin.Unit;

/** This fragment represents the settings screen. */
public class SettingsFragment extends PreferenceFragmentCompat {

    private Preference changePasscodePreference;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        SAPWizardApplication application =
            (SAPWizardApplication) getActivity().getApplication();
        addPreferencesFromResource(R.xml.preferences);

        changePasscodePreference = findPreference(getString(R.string.manage_passcode));
        changePasscodePreference.setOnPreferenceClickListener(preference -> {
            changePasscodePreference.setEnabled(false);
            FlowContext flowContext = new FlowContextBuilder()
                    .setApplication(application.getAppConfig())
                    .setFlowType(FlowType.CHANGEPASSCODE)
                    .setFlowStateListener(new WizardFlowStateListener(application))
                    .build();
            Flow.start(this, flowContext);
            return false;
        });


        // Reset App
        Preference resetAppPreference = findPreference(getString(R.string.reset_app));
        resetAppPreference.setOnPreferenceClickListener(preference -> {
            new DialogHelper(getContext(), R.style.OnboardingDefaultTheme_Dialog_Alert)
                    .showDialogWithCancelAction(
                            getActivity().getSupportFragmentManager(),
                            requireContext().getString(R.string.reset_app_confirmation),
                            (() -> {
                                return Unit.INSTANCE;
                            }),
                            getString(R.string.confirm_no),
                            getString(R.string.reset_app),
                            getString(R.string.confirm_yes),
                            (() -> {
                                FlowContext flowContext = new FlowContextBuilder()
                                        .setApplication(application.getAppConfig())
                                        .setFlowStateListener(new WizardFlowStateListener(application))
                                        .setFlowType(FlowType.RESET)
                                        .build();
                                Flow.start(this, flowContext);
                                return Unit.INSTANCE;
                            })
                    );
            return false;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlowConstants.FLOW_ACTIVITY_REQUEST_CODE) {
            changePasscodePreference.setEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
