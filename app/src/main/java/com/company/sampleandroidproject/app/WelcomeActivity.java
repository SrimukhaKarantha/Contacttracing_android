package com.company.sampleandroidproject.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.company.sampleandroidproject.R;
import com.sap.cloud.mobile.fiori.onboarding.LaunchScreen;
import com.sap.cloud.mobile.fiori.onboarding.ext.LaunchScreenSettings;
import com.sap.cloud.mobile.flowv2.core.Flow;
import com.sap.cloud.mobile.flowv2.model.FlowType;
import com.sap.cloud.mobile.flowv2.model.AppConfig;
import com.sap.cloud.mobile.flowv2.model.FlowConstants;
import com.sap.cloud.mobile.flowv2.core.FlowContext;
import com.sap.cloud.mobile.flowv2.core.FlowContextBuilder;
import com.sap.cloud.mobile.flowv2.model.BasicAuth;
import java.net.URL;
import java.net.MalformedURLException;


import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.JvmClassMappingKt;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private boolean isFlowStarted = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LaunchScreen welcome = new LaunchScreen(this);
        welcome.initialize(new LaunchScreenSettings.Builder()
                .setDemoButtonVisible(false)
                .setHeaderLineLabel(getString(R.string.welcome_screen_headline_label))
                .setPrimaryButtonText(getString(R.string.welcome_screen_primary_button_label))
                .setFooterVisible(true)
                .setUrlTermsOfService("http://www.sap.com")
                .setUrlPrivacy("http://www.sap.com")
                .addInfoViewSettings(
                        new LaunchScreenSettings.LaunchScreenInfoViewSettings(
                                R.drawable.ic_android_white_circle_24dp,
                                getString(R.string.application_name),
                                getString(R.string.welcome_screen_detail_label)
                        )
                )
                .build());
        welcome.setPrimaryButtonOnClickListener(v -> {
            if (!isFlowStarted) {
                startFlow(this, FlowType.ONBOARDING);
                isFlowStarted = true;
            }
        });
        setContentView(welcome);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FlowConstants.FLOW_ACTIVITY_REQUEST_CODE) {
            isFlowStarted = false;
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(this, MainBusinessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    public static void startFlow(Activity context, FlowType flowType) {
        try {
            FlowContext flowContext = new FlowContextBuilder()
                    .setApplication(prepareAppConfig())
                    .setMobileServices(getServices())
                    .setFlowStateListener(new WizardFlowStateListener(
                            (SAPWizardApplication) context.getApplication()))
                    .build();
            Flow.start(context, flowContext);
        } catch (MalformedURLException ex) {
            //Do nothing, should not happen since wizard will check the URL format
        }
    }

    private static List getServices() {
        List services = new ArrayList<>();
        return services;
    }

    private static AppConfig prepareAppConfig() throws MalformedURLException {

        return new AppConfig.Builder()
                .applicationId("com.sap.android.wizard.sample")
                .host(new URL("https://d0f5535atrial-dev-com-sap-android-wizard-sample.cfapps.eu10.hana.ondemand.com/").getHost())
                .addAuth(new BasicAuth())
                .build();
        }
}