package de.stephanlindauer.criticalmaps.utils;

import android.app.Activity;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import de.stephanlindauer.criticalmaps.R;

public class AlertBuilder {
    public static void show(Activity activity, @StringRes int title, @StringRes int text) {
        new AlertDialog.Builder(activity, R.style.AlertDialogTheme)
                .setTitle(activity.getString(title))
                .setMessage(activity.getString(text))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.ok), null)
                .create()
                .show();
    }
}
