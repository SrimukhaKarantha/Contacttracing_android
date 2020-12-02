package com.company.sampleandroidproject.app;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import com.company.sampleandroidproject.service.SAPServiceManager;
import com.sap.cloud.mobile.foundation.authentication.AppLifecycleCallbackHandler;
import com.sap.cloud.mobile.flowv2.model.AppConfig;

import com.company.sampleandroidproject.repository.RepositoryFactory;



public class SAPWizardApplication extends Application {

    /**
     * The application configuration information.
     */
    private AppConfig appConfig;
    public boolean isApplicationUnlocked = false;


    /**
     * Manages and provides access to OData stores providing data for the app.
     */
    private SAPServiceManager sapServiceManager;

    /**
     * Application-wide RepositoryFactory
     */
    private RepositoryFactory repositoryFactory;

    /**
     * Returns the application-wide service manager.
     *
     * @return the service manager
     */
    public SAPServiceManager getSAPServiceManager() {
        return sapServiceManager;
    }


    /**
     * Returns the application-wide repository factory
     *
     * @return the repository factory
     */
    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    /**
     * Clears all user-specific data from the application, essentially resetting
     * it to its initial state.
     */
    public void resetApp() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().clear().apply();
        appConfig = null;
        isApplicationUnlocked = false;
        repositoryFactory.reset();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(AppLifecycleCallbackHandler.getInstance());
    }

    /**
     * Initialize service manager with application configuration
     *
     * @param appConfig the application configuration
     */
    public void initializeServiceManager(AppConfig appConfig) {
        sapServiceManager = new SAPServiceManager(appConfig);

        repositoryFactory = new RepositoryFactory(sapServiceManager);
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
