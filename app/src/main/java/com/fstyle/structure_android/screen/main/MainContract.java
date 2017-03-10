package com.fstyle.structure_android.screen.main;

import com.fstyle.structure_android.screen.BasePresenter;
import com.fstyle.structure_android.screen.BaseView;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
