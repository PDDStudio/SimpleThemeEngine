package com.pddstudio.simplethemeengine.sample;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.pddstudio.simplethemeengine.R;
import com.pddstudio.simplethemeengine.sample.misc.AboutDialog;
import com.pddstudio.simplethemeengine.sample.misc.LibDialog;
import com.pddstudio.themeengine.OnThemeChangedListener;
import com.pddstudio.themeengine.ThemeEngine;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.CallAction;
import com.pddstudio.themeengine.types.ThemeFontColor;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback{

    //Define the OnThemeChangedListener you want to use in your Application
    private final OnThemeChangedListener onThemeChangedListener = new OnThemeChangedListener() {
        @Override
        public void onPrimaryColorChanged() {
            if(toolbar != null) toolbar.setBackgroundColor(ThemeEngine.Color.primary());
            if(primaryColorButton != null) primaryColorButton.setTextColor(ThemeEngine.Color.primary());
        }

        @Override
        public void onPrimaryColorDarkChanged() {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ThemeEngine.Color.primaryDark());
                getWindow().setNavigationBarColor(ThemeEngine.Color.primaryDark());
            }
            if(primaryDarkColorButton != null) primaryDarkColorButton.setTextColor(ThemeEngine.Color.primaryDark());
        }

        @Override
        public void onAccentColorChanged() {
            if(fab != null) fab.getBackground().setColorFilter(ThemeEngine.Color.accent(), PorterDuff.Mode.SRC_ATOP);
            if(headerText != null) headerText.setTextColor(ThemeEngine.Color.accent());
            if(accentColorButton != null) accentColorButton.setTextColor(ThemeEngine.Color.accent());
        }

        @Override
        public void onFontColorChanged(ThemeFontColor themeFontColor) {

        }

        @Override
        public void onApplicationStyleChanged(ApplicationTheme applicationTheme) {

        }
    };


    @ColorInt private int fallbackPrimary = Color.parseColor("#0D63A5");
    @ColorInt private int fallbackPrimaryDark = Color.parseColor("#083358");
    @ColorInt private int fallbackAccent = Color.parseColor("#FFD717");

    public static final int COLOR_PRIMARY = 0;
    public static final int COLOR_PRIMARY_DARK = 1;
    public static final int COLOR_ACCENT = 2;

    private int newCol;

    Toolbar toolbar;

    RelativeLayout bgLayout;
    CardView cardView;

    FloatingActionButton fab;
    TextView headerText;

    Button primaryColorButton;
    Button primaryDarkColorButton;
    Button accentColorButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //load the application
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Using Paper as storage
        Paper.init(this);

        //initialize the SimpleThemeEngine
        ThemeEngine.initThemeEngine(this);
        //set the fallback colors
        ThemeEngine.setFallbackColors(fallbackPrimary, fallbackPrimaryDark, fallbackAccent, ApplicationTheme.THEME_LIGHT, ThemeFontColor.LIGHT_FONT_COLOR);
        /*
        Optional:
        Set a backend notification interface - in case you want to save the colors
        somewhere and restore them on the application's startup via:
        */

        ThemeEngine.setBackendNotificationInterface(BackendSample.BACKEND_NOTIFICATION_INTERFACE);
        ThemeEngine.loadSettingsThemeObject();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadLayoutStuff();


        //Finally, register the class and it's OnThemeChanged interface
        ThemeEngine.registerThemeListener(this, onThemeChangedListener);

    }

    private void loadLayoutStuff() {
        bgLayout = (RelativeLayout) findViewById(R.id.bg_layout);
        cardView = (CardView) findViewById(R.id.card_layout);

        headerText = (TextView) findViewById(R.id.card_header_text);

        primaryColorButton = (Button) findViewById(R.id.button_primary);
        primaryColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog(COLOR_PRIMARY);
            }
        });

        primaryDarkColorButton = (Button) findViewById(R.id.button_primary_dark);
        primaryDarkColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog(COLOR_PRIMARY_DARK);
            }
        });

        accentColorButton = (Button) findViewById(R.id.button_accent);
        accentColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog(COLOR_ACCENT);
            }
        });

    }

    private void showColorDialog(int forColorType) {
        newCol = forColorType;
        new ColorChooserDialog.Builder(this, R.string.color_picker_str)
                .accentMode(false)
                .customColors(ColorPalette.PRIMARY_COLORS, ColorPalette.PRIMARY_COLORS_SUB)
                .dynamicButtonColor(true)
                .show();
    }

    @Override
    public void onColorSelection(ColorChooserDialog colorChooserDialog, @ColorInt int color) {
        switch (newCol) {
            case COLOR_PRIMARY:
                ThemeEngine.colorChanged(CallAction.PRIMARY_COLOR, color);
                ThemeEngine.refreshColorListeners(CallAction.PRIMARY_COLOR);
                break;
            case COLOR_PRIMARY_DARK:
                ThemeEngine.colorChanged(CallAction.PRIMARY_COLOR_DARK, color);
                ThemeEngine.refreshColorListeners(CallAction.PRIMARY_COLOR_DARK);
                break;
            case COLOR_ACCENT:
                ThemeEngine.colorChanged(CallAction.ACCENT_COLOR, color);
                ThemeEngine.refreshColorListeners(CallAction.ACCENT_COLOR);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title(R.string.exit_dialog_title)
                .titleColor(ThemeEngine.Color.primary())
                .content(R.string.exit_dialog_content)
                .positiveText(R.string.dialog_text_yes)
                .positiveColor(ThemeEngine.Color.accent())
                .negativeText(R.string.dialog_text_no)
                .negativeColor(ThemeEngine.Color.accent())
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        System.exit(0);
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                AboutDialog.show(this);
                break;
            case R.id.menu_libs:
                LibDialog.show(this);
                break;
        }
        return true;
    }


}
