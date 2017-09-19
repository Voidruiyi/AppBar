package com.ruiyi.appbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onAppbarClick(View view, int position) {
        if (position == POS_RIGHT_TEXT) {
            Toast.makeText(this, "ss2", Toast.LENGTH_LONG).show();
        }
        super.onAppbarClick(view, position);
    }
}
