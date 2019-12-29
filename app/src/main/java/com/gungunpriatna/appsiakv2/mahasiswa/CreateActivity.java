package com.gungunpriatna.appsiakv2.mahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gungunpriatna.appsiakv2.R;
import com.gungunpriatna.appsiakv2.model.Mahasiswa;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNim, edtNama;
    private Button btnSave;

    private Mahasiswa mahasiswa;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNama = findViewById(R.id.edt_nama);
        edtNim = findViewById(R.id.edt_nim);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);

        mahasiswa = new Mahasiswa();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            saveMahasiswa();
        }
    }

    public void saveMahasiswa()
    {
        String nama = edtNama.getText().toString().trim();
        String nim = edtNim.getText().toString().trim();

        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");

        }

        if (TextUtils.isEmpty(nim)) {
            isEmptyFields = true;
            edtNim.setError("Field ini tidak boleh kosong");
        }

        if (! isEmptyFields) {
            Toast.makeText(CreateActivity.this, "Saving Data...", Toast.LENGTH_SHORT).show();

            DatabaseReference dbMahasiswa = mDatabase.child("mahasiswa");

            String id = dbMahasiswa.push().getKey();
            mahasiswa.setId(id);
            mahasiswa.setNim(nim);
            mahasiswa.setNama(nama);
            mahasiswa.setPhoto("");

            dbMahasiswa.child(id).setValue(mahasiswa);

            finish();
        }
    }
}
