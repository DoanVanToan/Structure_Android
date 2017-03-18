package com.fstyle.structure_android.widget.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.fstyle.structure_android.screen.BaseView;

/**
 * Created by le.quang.dao on 14/03/2017.
 */

public class DialogManagerImpl implements DialogManager {

    private Activity mActivity;
    private MainAlertDialog mDialog;

    public DialogManagerImpl(Activity activity) {
        mActivity = activity;
    }

    public <V extends BaseView> DialogManagerImpl(@NonNull V view) {
        if (view instanceof Activity) {
            mActivity = (Activity) view;
        }
    }

    @Override
    public MainAlertDialog getDialog() {
        return mDialog;
    }

    @Override
    public boolean isShowing() {
        if (mDialog == null) {
            return false;
        }
        return mDialog.isShowing();
    }

    @Override
    public void dismiss() {
        if (mDialog == null) {
            return;
        }
        mDialog.dismiss();
    }

    @Override
    public void dialogMainStyle(String message,
            DialogInterface.OnClickListener positiveButtonListener) {
        if (isShowing()) {
            return;
        }
        mDialog = new MainAlertDialog.Builder(mActivity).setVisibleTitle(false)
                .setMessage(message)
                .setPositiveListener(positiveButtonListener)
                .show();
    }
}
