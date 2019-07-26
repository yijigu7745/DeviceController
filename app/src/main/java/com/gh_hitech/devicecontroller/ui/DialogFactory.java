package com.gh_hitech.devicecontroller.ui;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

/**
 * @author yijigu
 */
public class DialogFactory {


    /**
     * 创建提示对话框
     *
     * @param context
     * @param text
     * @return
     */
    public static ConfirmationDialog createAlertDialog(Context context, String text, ConfirmationDialog.OnButtonClickListener listener) {
        return createAlertDialog(context, Color.BLACK, text, ConfirmationDialog.ConfirmationType.TwoButton, listener);
    }

    public static ConfirmationDialog createAlertDialog(Context context, int colorText, String text, ConfirmationDialog.ConfirmationType mode, ConfirmationDialog.OnButtonClickListener listener) {
        ConfirmationDialog dialog = new ConfirmationDialog(context);
        dialog.setType(mode);
        dialog.setContentText(colorText, text);
        dialog.isShowTitle(false);
        if (listener != null) {
            dialog.setOnButtonClickListener(listener);
        }
        return dialog;
    }

    /**
     * 创建提示对话框
     *
     * @param context
     * @param text
     * @param mode
     * @return
     */
    public static ConfirmationDialog createAlertDialog(Context context, String title, int colorText, String text, ConfirmationDialog.ConfirmationType mode, ConfirmationDialog.OnButtonClickListener listener) {
        if (TextUtils.isEmpty(title)) {
            return createAlertDialog(context, colorText, text, mode, listener);
        } else {
            ConfirmationDialog dialog = new ConfirmationDialog(context);
            dialog.setType(mode);
            dialog.setContentText(colorText, text);
            dialog.setTitle(title);
            if (listener != null) {
                dialog.setOnButtonClickListener(listener);
            }
            return dialog;
        }
    }

    public static TimeDialog createTimeSelectDialog(Context context, TimeDialog.OnTimeSelectListener listener) {
        TimeDialog dialog = new TimeDialog(context);
        if (listener != null) {
            dialog.setOnTimeSelectListener(listener);
        }
        return dialog;
    }

    public static SheetPopUpWindow createSheetPopUpWindow(Context context, List<String> data,
                                                          SheetPopUpWindow.OnItemSelectListener listener) {
        SheetPopUpWindow popUpWindow = SheetPopUpWindow.createSheetPopUpWindow(context, data, listener);
        return popUpWindow;
    }


    public static ConfirmationDialog createListDialog(Context context, View view, ConfirmationDialog.ConfirmationType mode, ConfirmationDialog.OnButtonClickListener listener) {
        ConfirmationDialog dialog = new ConfirmationDialog(context);
        dialog.setContentView(view);
        dialog.setType(mode);
        dialog.isShowTitle(false);
        if (listener != null) {
            dialog.setOnButtonClickListener(listener);
        }
        return dialog;
    }


}
