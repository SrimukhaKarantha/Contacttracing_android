package com.company.sampleandroidproject.test.core;

public class Constants {

    public enum AuthType {
        BASIC, OAUTH, SAML, NOAUTH
    }

    public enum OnboardingType {
        DISCOVERY_SERVICE, STANDARD
    }

   public enum EulaScreen{
        DENY, ALLOW
    }

    public final static AuthType APPLICATION_AUTH_TYPE = AuthType.BASIC;
    public final static int NETWORK_REQUEST_TIMEOUT = 5000;

    public static OnboardingType ONBOARDING_TYPE = OnboardingType.STANDARD;
 public static EulaScreen EULASCREEN = EulaScreen.ALLOW;

}
