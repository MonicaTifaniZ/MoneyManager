package com.monicatifanyz.manyom;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class OCRScannerActivity extends AppCompatActivity {


    private static final int RC_OCR_CAPTURE = 9003;

    EditText editTextOCRTotal;
    Button buttonTanggal , buttonSimpan, buttonOCRR;
    Spinner spinner;
    TextView textViewTanggal;

    DatabaseReference databaseReference ;


    private String[] item = {"Makanan dan Minuman","Pendidikan","Transportasi","Kesehatan",
            "Lainnya"};

    String kategori, tanggal, pengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detection);


        editTextOCRTotal = findViewById(R.id.tvQRTotal);
        textViewTanggal = findViewById(R.id.txtTanggal);
        buttonSimpan = findViewById(R.id.btnSimpan);
        buttonTanggal = findViewById(R.id.btnTanggal);
        buttonOCRR = findViewById(R.id.buttonOCR);
        spinner = findViewById(R.id.spKategori);

//
//        spinnerDataList = new ArrayList<>();
//        arrayAdapter = new ArrayAdapter<>(OCRScannerActivity.this, android.R.layout.simple_spinner_dropdown_item, item);

        final Spinner List = findViewById(R.id.spKategori);

        //Inisialiasi Array Adapter dengan memasukkan String Array
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,item);

        //Memasukan Adapter pada Spinner
        List.setAdapter(adapter);

        List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Saya Memesan "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
                kategori = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                simpanData();
            }
        });
        buttonOCRR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OCRScannerActivity.this, OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (resultCode == RC_OCR_CAPTURE){
           if (resultCode == CommonStatusCodes.SUCCESS){
               if (data !=null){
                   String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                   text = text.replaceAll("[^\\d.]", "");
                   //statusMessage.setText(R.string.ocr_success);
                   editTextOCRTotal.setText(text);
                   pengeluaran = editTextOCRTotal.toString();
                   //Log.d(TAG, "Text read: " + text);
               } else {
                   //statusMessage.setText(R.string.ocr_failure);
//                    Log.d(TAG, "No Text captured, intent data is null");
               }
           }

       }
       else {

           super.onActivityResult(requestCode, resultCode, data);
       }
    }


        @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    void simpanData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Pengeluaran");

        HashMap<String, Object> data = new HashMap<>();
        data.put("kategori", kategori);
        data.put("tanggal", tanggal);
        data.put("total_pengeluaran", pengeluaran);

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
}