package com.company.sampleandroidproject.test.pages;


import androidx.test.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.company.sampleandroidproject.test.core.AbstractLoginPage;
import com.company.sampleandroidproject.test.core.Credentials;
import com.company.sampleandroidproject.test.core.UIElements;
import com.company.sampleandroidproject.test.core.WizardDevice;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import com.company.sampleandroidproject.test.core.matcher.SimpleInputCellMatcher;
import static androidx.test.espresso.action.ViewActions.typeText;

public class LoginPage {

    public static class BasicAuthPage extends AbstractLoginPage {

        public BasicAuthPage() {
            uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        }

        @Override
        public void authenticate() {

           // Click to the input field
           onView(new SimpleInputCellMatcher(UIElements.LoginScreen.BasicAuthScreen.usernameText)).perform(typeText(Credentials.USERNAME));
           onView(new SimpleInputCellMatcher(UIElements.LoginScreen.BasicAuthScreen.passwordText)).perform(typeText(Credentials.PASSWORD));
           // Click Login on the dialog
           onView(withId(UIElements.LoginScreen.BasicAuthScreen.okButton)).perform(click());
        }

        public void useWrongCredentials() {

            UiObject usernameField = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.BasicAuthScreen.usernameID));
            usernameField.waitForExists(WAIT_TIMEOUT);

            // Click to the input field
            WizardDevice.fillInputField(UIElements.LoginScreen.BasicAuthScreen.usernameText, Credentials.WRONGUSERNAME);
            WizardDevice.fillInputField(UIElements.LoginScreen.BasicAuthScreen.passwordText, Credentials.WRONGPASSWORD);

            // Click Login on the dialog
            onView(withId(UIElements.LoginScreen.BasicAuthScreen.okButton)).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        }
    }

    public static class WebviewPage extends AbstractLoginPage {

        public WebviewPage() {
            uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        }

        @Override
        public void authenticate() {
            fillCredentials();
            // Check whether it's oauth or not
            UiObject authButton = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthAuthorizeButton));
            if (authButton.waitForExists(WAIT_TIMEOUT)) {
                // Oauth case
                clickAuthorizeButton();
            }
        }

        private void fillCredentials() {

            UiObject usernameField = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthUsernameText));

            usernameField.waitForExists(WAIT_TIMEOUT);

            UiObject passwordField = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthPasswordText));

            UiObject logonButton = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthLogonButton));


            try {
                usernameField.clearTextField();
                usernameField.setText(Credentials.USERNAME);
                passwordField.clearTextField();
                passwordField.setText(Credentials.PASSWORD);
                logonButton.click();
            } catch (UiObjectNotFoundException e) {
                // TODO error handling
            }

        }

        private void useWrongCredentials() {

            UiObject usernameField = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthUsernameText));

            usernameField.waitForExists(WAIT_TIMEOUT);

            UiObject passwordField = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthPasswordText));

            UiObject logonButton = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthLogonButton));


            try {
                usernameField.clearTextField();
                usernameField.setText(Credentials.WRONGUSERNAME);
                passwordField.clearTextField();
                passwordField.setText(Credentials.WRONGPASSWORD);
                logonButton.click();
            } catch (UiObjectNotFoundException e) {
                // TODO error handling
            }

        }

        private void clickAuthorizeButton() {
            UiObject authButton = uiDevice.findObject(new UiSelector()
                    .resourceId(UIElements.LoginScreen.OauthScreen.oauthAuthorizeButton));
            try {
                authButton.clickAndWaitForNewWindow();
            } catch (UiObjectNotFoundException e) {
                // TODO error handling
            }
        }

    }

    public static class NoAuthPage extends AbstractLoginPage {

        @Override
        public void authenticate() {
            // in no-auth case we don't need to authenticate
        }
    }


}
