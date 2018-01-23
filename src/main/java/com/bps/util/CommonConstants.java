package com.bps.util;

public final class CommonConstants {
	public enum Page {
		Login, Main
	}
	private CommonConstants() { }
	public static final String ACTION = "action";
	public static final String LOGIN_ACTION_SIGNUP = "signup";
	public static final String LOGIN_ACTION_SIGNIN = "signin";
	public static final String URL_HOME_CONTROLLER = "/home";
	public static final String URL_MAIN_CONTROLLER = "/main";
	public static final String URL_SURVEY_CONTROLLER = "/survey";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String HOME = "Home";
	public static final String SYSTEM = "System";
	public static final String SELF = "Self";
	public static final String UTC = "UTC";
	public static final String IS_LOGIN_FAILED = "isLoginFailed";
	public static final String IS_SIGNUP_FAILED = "isSignUpFailed";
	public static final String IS_PASSWORD_CHANGE_SUCCESSFUL = "isPasswordChangeSuccessful";
	public static final String IS_NAME_CHANGE_SUCCESSFUL = "isNameChangeSuccessful";
	public static final String IS_ACCOUNT_DELETION_SUCCESSFUL = "isAccountDeletionSuccessful";
}
