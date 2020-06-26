package com.monicatifanyz.manyom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

public class MainActivity extends Activity {

    CardView cvText, cvQRCode, cvPetunjuk, cvTentang, cvLaporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvText = findViewById(R.id.cvTextOCR);
        cvQRCode = findViewById(R.id.cvQR);
        cvPetunjuk = findViewById(R.id.cvHelp);
        cvTentang = findViewById(R.id.cvAbout);
        cvLaporan = findViewById(R.id.cvReport);
        

        cvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OCRScannerActivity.class);
                startActivity(i);
            }
        });

        cvQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, QRCodeActivity.class);
                startActivity(j);
            }
        });
        cvPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, Help.class);
                startActivity(k);
            }
        });

        cvTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(l);
            }
        });

        cvLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent laporan = new Intent(MainActivity.this, ListData.class);
                startActivity(laporan);
            }
        });





    }
}
