package com.pddstudio.simplethemeengine.sample.misc;

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
public class AboutDialog {

    private static AboutDialog aboutDialog;
    private MaterialDialog dialog;

    private AboutDialog(AppCompatActivity context) {
        dialog = new MaterialDialog.Builder(context)
                .title(R.string.about_dialog_title)
                .titleColor(ThemeEngine.Color.primary())
                .content(Html.fromHtml(context.getString(R.string.about_dialog_content)))
                .contentLineSpacing(1.6f)
                .positiveText(R.string.dialog_text_ok)
                .positiveColor(ThemeEngine.Color.accent())
                .build();
    }

    public static void show(AppCompatActivity context) {
        aboutDialog = new AboutDialog(context);
        aboutDialog.dialog.show();
    }

}
