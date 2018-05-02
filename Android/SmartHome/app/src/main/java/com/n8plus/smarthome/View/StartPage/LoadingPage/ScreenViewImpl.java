package com.n8plus.smarthome.View.StartPage.LoadingPage;

import com.n8plus.smarthome.Model.User;

public interface ScreenViewImpl {
    void loginSuccess(User user);

    void loginFailure(String message);
}
