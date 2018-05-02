package com.n8plus.smarthome.Presenter.ProfilePresenter.ChangeInformation;

import java.util.Map;

public interface ChangeInfoPresenterImpl {
    void changeInfo(String username, Map<String, String> headers);
    void changeAvatar(String username, Map<String, String> headers);
}
