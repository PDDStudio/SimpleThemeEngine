package com.pddstudio.simplethemeengine.sample.misc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pddstudio.simplethemeengine.R;
import com.pddstudio.themeengine.ThemeEngine;

/**
 * This Class was created by Patrick J
 * on 08.10.15. For more Details and Licensing
 * have a look at the README.md
 */
public class LibDialog {

    private static LibDialog libDialog;
    private MaterialDialog dialog;

    private LibDialog(AppCompatActivity context) {
        dialog = new MaterialDialog.Builder(context)
                .title(R.string.lib_dialog_title)
                .titleColor(ThemeEngine.Color.primary())
                .content(Html.fromHtml(context.getString(R.string.lib_dialog_content)))
                .contentLineSpacing(1.6f)
                .positiveColor(ThemeEngine.Color.accent())
                .positiveText(R.string.dialog_text_ok)
                .build();
    }

    public static void show(AppCompatActivity context) {
        libDialog = new LibDialog(context);
        libDialog.dialog.show();
    }

}
