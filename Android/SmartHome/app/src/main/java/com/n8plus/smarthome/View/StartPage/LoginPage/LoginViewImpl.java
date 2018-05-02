package com.n8plus.smarthome.View.StartPage.LoginPage;

import com.n8plus.smarthome.Model.User;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public interface LoginViewImpl {
    void loginSuccess(User user);
    void loginFailure(String message);
}
