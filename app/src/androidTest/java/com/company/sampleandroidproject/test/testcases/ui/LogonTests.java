package com.company.sampleandroidproject.test.testcases.ui;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.company.sampleandroidproject.app.WelcomeActivity;
import com.company.sampleandroidproject.test.core.ClientPolicyManager;
import com.company.sampleandroidproject.test.core.BaseTest;
import com.company.sampleandroidproject.test.core.UIElements;
import com.company.sampleandroidproject.test.core.Utils;
import com.company.sampleandroidproject.test.core.Credentials;
import com.company.sampleandroidproject.test.core.WizardDevice;
import com.company.sampleandroidproject.test.pages.DetailPage;
import com.company.sampleandroidproject.test.pages.PasscodePage;
import com.company.sampleandroidproject.test.pages.EntityListPage;
import com.company.sampleandroidproject.test.pages.MasterPage;
import com.company.sampleandroidproject.test.pages.SettingsListPage;
import com.company.sampleandroidproject.test.pages.WelcomePage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.company.sampleandroidproject.test.core.UIElements.EntityListScreen.entityList;

@RunWith(AndroidJUnit4.class)
public class LogonTests extends BaseTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void testLogonFlow() {

        // Take care of welcome screen, authentication, and passcode flow.
        Utils.doOnboarding(activityTestRule.getActivity());

        // Actions on the entitylist Page
        EntityListPage entityListPage = new EntityListPage(entityList);
         entityListPage.clickFirstElement();
         entityListPage.leavePage();

        // Actions on the master Page
        MasterPage masterPage = new MasterPage(UIElements.MasterScreen.refreshButton);
        masterPage.clickFirstElement();
        masterPage.leavePage();

        DetailPage detailPage = new DetailPage();
        detailPage.clickBack();
        detailPage.leavePage();

        masterPage = new MasterPage(UIElements.MasterScreen.refreshButton);
        masterPage.clickBack();
        masterPage.leavePage();

        entityListPage = new EntityListPage(entityList);
        entityListPage.clickSettings();
        entityListPage.leavePage();

        SettingsListPage settingsListPage = new SettingsListPage();
        settingsListPage.clickResetApp();

        settingsListPage.checkConfirmationDialog();

        settingsListPage.clickYes();
    }


    @Test
    public void logonFlowPutAppIntoBackground() {
        // Take care of welcome screen, authentication, and passcode flow.
        Utils.doOnboarding(activityTestRule.getActivity());

        EntityListPage entityListPage = new EntityListPage(entityList);
         entityListPage.clickFirstElement();
         entityListPage.leavePage();

        MasterPage masterPage = new MasterPage(UIElements.MasterScreen.refreshButton);
        masterPage.clickFirstElement();
        masterPage.leavePage();

        // Get the lockTimeOut (in seconds) from the SecureStoreManager
        int lockTimeOut = ClientPolicyManager.getInstance().getPasscodeLockTimeout();

        // Put the app into background and immediately start again
        WizardDevice.putApplicationBackground(0, activityTestRule);
        WizardDevice.reopenApplication();

        if (lockTimeOut == 0) {
            PasscodePage.EnterPasscodePage enterPasscodePage = new PasscodePage().new EnterPasscodePage();
            enterPasscodePage.enterPasscode(Credentials.PASSCODE);
            enterPasscodePage.clickSignIn();
            enterPasscodePage.leavePage();
        }

        DetailPage mDetailPage = new DetailPage(UIElements.DetailScreen.deleteButton);
        mDetailPage.clickBack();
        mDetailPage.leavePage();

        masterPage = new MasterPage(UIElements.MasterScreen.refreshButton);
        masterPage.clickBack();
        masterPage.leavePage();

        entityListPage = new EntityListPage(entityList);
        entityListPage.clickSettings();
        entityListPage.leavePage();

        SettingsListPage settingsListPage = new SettingsListPage();
        settingsListPage.clickResetApp();

        settingsListPage.checkConfirmationDialog();

        settingsListPage.clickYes();
    }
    @Test
    public void LogonFlowBack () {
        Utils.checkCredentials();
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.clickGetStarted();
        welcomePage.waitForCredentials();
        Utils.pressBack();
        Utils.doOnboarding(activityTestRule.getActivity());
    }
}
