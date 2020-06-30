package id.ac.stiki.doleno.mayom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import id.ac.stiki.doleno.mayom.R;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    Intent m = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivityForResult(m,0);
                }
            }
        };

        timer.start();

    }
}
