package com.monicatifanyz.manyom;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {
    ImageView imageView;
    TextView stikiLink, monic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        imageView = findViewById(R.id.image_mon);
        stikiLink = findViewById(R.id.stiki_link);
        monic = findViewById(R.id.mon_link);

        stikiLink.setMovementMethod(LinkMovementMethod.getInstance());
        monic.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
