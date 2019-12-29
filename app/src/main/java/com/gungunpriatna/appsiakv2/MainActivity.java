package com.gungunpriatna.appsiakv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.gungunpriatna.appsiakv2.mahasiswa.CreateActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
            startActivity(intent);
        }
    }
}
