package com.monicatifanyz.manyom;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRCodeActivity extends Activity {

    TextView  textViewTanggal;
    EditText edittextQRTotal;
    Button buttonTanggal , buttonSimpan, buttonScan;
    Spinner spinner;

    //qr code scanner object
    private  IntentIntegrator intentIntegrator;
    private Pattern regex;
    private Matcher matcher;

    DatabaseReference databaseReference;


    private String[] item = {"Makanan dan Minuman","Pendidikan","Transportasi","Kesehatan",
            "Lainnya"};

    String kategori, tanggal, pengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        scannerView = new ZXingScannerView(this);
        setContentView(R.layout.activity_q_r_code);

        regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        edittextQRTotal = findViewById(R.id.editTextQRTotal);
        textViewTanggal = findViewById(R.id.txtTanggal);
        buttonSimpan = findViewById(R.id.btnSimpan);
        buttonTanggal = findViewById(R.id.btnTanggal);
        buttonScan = findViewById(R.id.buttonScanQR);
        spinner = findViewById(R.id.spKategori);


        final Spinner list = findViewById(R.id.spKategori);

        //Inisialiasi Array Adapter dengan memasukkan String Array
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,item);

        //Memasukan Adapter pada Spinner
        list.setAdapter(adapter);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kategori = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartScan();
            }
        });

        buttonTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilTanggal();
            }
        });

        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanData();
            }
        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            matcher = regex.matcher(result.getContents());
            if (matcher.find()) {
                edittextQRTotal.setText(String.valueOf(result.getContents()));
                pengeluaran = result.getContents();

            }else {
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
                // jika qrcode berisi data
//                try {
//                    // converting the data json]
//                    JSONObject object = new JSONObject(result.getContents());
//
//                    // atur nilai ke textviews
//                    textViewTanggal.setText(object.getString("nama"));
//                     textViewQRTotal.setText(String.valueOf(object.getDouble(toString())));
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    // jika format encoded tidak sesuai maka hasil
//                    // ditampilkan ke toast
//                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
//                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void StartScan(){
        // inisialisasi IntentIntegrator(scanQR)
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }



    public void AmbilTanggal(){
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
                String dateString  = year + " " + month + " " + date;
                textViewTanggal.setText(dateString);

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR , year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);

                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                textViewTanggal.setText(dateText);
                tanggal = dateText;


            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    void SimpanData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pengeluaran");

        HashMap<String, Object> data = new HashMap<>();
        data.put("Kategori", kategori);
        data.put("Tanggal", tanggal);
        data.put("Pengeluaran", pengeluaran);

        reference.push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
