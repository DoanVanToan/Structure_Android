package com.fstyle.structure_android.screen.main;

import android.text.TextUtils;
import com.fstyle.structure_android.data.model.User;
import com.fstyle.structure_android.data.source.UserRepository;
import com.fstyle.structure_android.utils.Constant;
import com.fstyle.structure_android.utils.rx.CustomCompositeSubscription;
import com.fstyle.structure_android.utils.validator.Validator;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();

    private MainContract.View mMainView;
    private UserRepository mUserRepository;
    private final CustomCompositeSubscription mCompositeSubscription;
    private Validator mValidator;

    MainPresenter(UserRepository userRepository, Validator validator,
            CustomCompositeSubscription subscription) {
        mUserRepository = userRepository;
        mValidator = validator;
        mValidator.initNGWordPattern();
        mCompositeSubscription = subscription;
    }

    private boolean validateDataInput(int limit, String keyWord) {
        String errorMsg = mValidator.validateValueRangeFrom0to100(limit);
        mMainView.onInvalidLimitNumber(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        errorMsg = mValidator.validateNGWord(keyWord);
        errorMsg += (TextUtils.isEmpty(errorMsg) ? "" : Constant.BREAK_LINE)
                + mValidator.validateValueNonEmpty(keyWord);
        mMainView.onInvalidKeyWord(TextUtils.isEmpty(errorMsg) ? null : errorMsg);

        try {
            return mValidator.validateAll();
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    @Override
    public void setView(MainContract.View view) {
        mMainView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.clear();
    }

    @Override
    public void searchUsers(int limit, String keyWord) {
        if (!validateDataInput(limit, keyWord)) {
            return;
        }
        Subscription subscription = mUserRepository.searchUsers(limit, keyWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) {
                        mMainView.onSearchUsersSuccess(users);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mMainView.onSearchError(throwable);
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
