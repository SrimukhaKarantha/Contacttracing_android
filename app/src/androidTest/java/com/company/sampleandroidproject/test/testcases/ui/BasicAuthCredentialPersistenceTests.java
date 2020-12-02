package com.company.sampleandroidproject.test.testcases.ui;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.company.sampleandroidproject.app.SAPWizardApplication;
import com.company.sampleandroidproject.app.WelcomeActivity;
import com.company.sampleandroidproject.test.core.BaseTest;
import com.company.sampleandroidproject.test.core.Constants;
import com.company.sampleandroidproject.test.core.Utils;
import com.company.sampleandroidproject.test.core.factory.LoginPageFactory;
import com.company.sampleandroidproject.app.WizardFlowStateListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static com.company.sampleandroidproject.test.core.Constants.APPLICATION_AUTH_TYPE;
import com.company.sampleandroidproject.test.pages.WelcomePage;
import com.sap.cloud.mobile.flowv2.core.Flow;
import com.sap.cloud.mobile.flowv2.core.FlowContext;
import com.sap.cloud.mobile.flowv2.core.FlowContextBuilder;
import com.sap.cloud.mobile.flowv2.model.FlowType;

@RunWith(AndroidJUnit4.class)
public class BasicAuthCredentialPersistenceTests extends BaseTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void basicAuthCredentialsGetReused() throws InterruptedException {
        if (APPLICATION_AUTH_TYPE != Constants.AuthType.BASIC) {
            return;
        }
        Utils.doOnboarding(activityTestRule.getActivity());
        // Clear session cookies so the server will give a basic auth challenge
        Utils.clearSessionCookies();
    }

    @Test
    public void basicAuthCredentialsGetCleared() throws InterruptedException {
        if (APPLICATION_AUTH_TYPE != Constants.AuthType.BASIC) {
            return;
        }
        Utils.doOnboarding(activityTestRule.getActivity());

        // Clear session cookies so the server will give a basic auth challenge
        Utils.clearSessionCookies();

        // Clear basic auth credentials as well
        SAPWizardApplication application = (SAPWizardApplication) activityTestRule.getActivity().getApplication();
        FlowContext flowContext = new FlowContextBuilder()
                .setApplication(application.getAppConfig())
                .setFlowType(FlowType.RESET)
                .setFlowStateListener(new WizardFlowStateListener(application))
                .build();
        Flow.start(activityTestRule.getActivity(), flowContext);
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.clickGetStarted();
        LoginPageFactory.getLoginPage().authenticate();
    }
}
