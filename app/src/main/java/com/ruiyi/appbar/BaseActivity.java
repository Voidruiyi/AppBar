package com.ruiyi.appbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import widget.Appbar;

/**
 * Created by ruiyi on 2017/9/19.
 */

public abstract class BaseActivity extends AppCompatActivity implements Appbar.OnAppbarClickListener {

    protected Appbar appbar;

    protected abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        appbar = (Appbar) findViewById(R.id.appbar);

        if (appbar != null) {
            appbar.setOnAppbarClickListener(this);
        }
    }

    @Override
    public void onAppbarClick(View view, int postion) {
        if (postion == POS_LEFT_IMAGE) {
//            onBackPressed();
            Toast.makeText(this, "ss", Toast.LENGTH_LONG).show();

        }
    }

    protected Appbar getAppbar() {
        return appbar;
    }
}
