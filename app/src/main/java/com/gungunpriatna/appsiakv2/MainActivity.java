package com.gungunpriatna.appsiakv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gungunpriatna.appsiakv2.adapter.MahasiswaAdapter;
import com.gungunpriatna.appsiakv2.mahasiswa.CreateActivity;
import com.gungunpriatna.appsiakv2.mahasiswa.UpdateActivity;
import com.gungunpriatna.appsiakv2.model.Mahasiswa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private Button btnAdd;

    private MahasiswaAdapter adapter;
    private ArrayList<Mahasiswa> mahasiswaList;
    DatabaseReference dbMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbMahasiswa = FirebaseDatabase.getInstance().getReference("mahasiswa");

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        mahasiswaList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_MAHASISWA, mahasiswaList.get(i));

                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        dbMahasiswa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mahasiswaList.clear();

                for (DataSnapshot mahasiswaSnapshot : dataSnapshot.getChildren()) {
                    Mahasiswa mahasiswa = mahasiswaSnapshot.getValue(Mahasiswa.class);
                    mahasiswaList.add(mahasiswa);
                }

                MahasiswaAdapter adapter = new MahasiswaAdapter(MainActivity.this);
                adapter.setMahasiswaList(mahasiswaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
