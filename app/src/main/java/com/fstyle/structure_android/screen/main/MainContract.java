package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseViewModel;
import java.util.List;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface MainContract {

    /**
     * View
     */
    interface ViewModel extends BaseViewModel {
        void searchError(Throwable throwable);

        void searchUsersSuccess(List<User> users);

        void invalidKeyWord(String errorMsg);

        void invalidLimitNumber(String errorMsg);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void searchUsers(int limit, String keyWord);
    }
}
