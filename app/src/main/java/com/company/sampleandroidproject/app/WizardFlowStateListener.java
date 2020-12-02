package com.company.sampleandroidproject.app;

import android.content.Intent;
import android.util.Log;
import javax.crypto.Cipher;
import com.sap.cloud.mobile.flowv2.ext.FlowStateListener;
import com.sap.cloud.mobile.flowv2.model.AppConfig;

import org.jetbrains.annotations.NotNull;

public class WizardFlowStateListener extends FlowStateListener {
    private SAPWizardApplication application;

    public WizardFlowStateListener(@NotNull SAPWizardApplication application) {
        super();
        this.application = application;
    }

    @Override
    public void onAppConfigRetrieved(@NotNull AppConfig appConfig) {
        Log.d(WizardFlowStateListener.class.getSimpleName(), "onAppConfigRetrieved " + appConfig.toString());
        application.initializeServiceManager(appConfig);
        application.setAppConfig(appConfig);
    }

    @Override
    public void onApplicationReset() {
        Log.d(WizardFlowStateListener.class.getSimpleName(), "onApplicationReset executing...");
        this.application.resetApp();
        Intent intent = new Intent(application, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        application.startActivity(intent);
    }

    @Override
    public void onApplicationLocked() {
        super.onApplicationLocked();
        application.isApplicationUnlocked = false;
    }

    @Override
    public void onUnlockWithCipher(@NotNull Cipher cipher) {
        super.onUnlockWithCipher(cipher);
        application.isApplicationUnlocked = true;
    }

    @Override
    public void onUnlockWithPasscode(@NotNull char[] code) {
        super.onUnlockWithPasscode(code);
        application.isApplicationUnlocked = true;
    }

    @Override
    public void onBoarded() {
        super.onBoarded();
        application.isApplicationUnlocked = true;
    }

}
