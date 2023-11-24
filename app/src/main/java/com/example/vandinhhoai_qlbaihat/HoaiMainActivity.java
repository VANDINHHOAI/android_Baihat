package com.example.vandinhhoai_qlbaihat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class HoaiMainActivity extends AppCompatActivity {
    String DATABASE_NAME = "VanDinhHoai_QLBaiHat.db";
    SQLiteDatabase database;
    ListView lstDSBH;
    EditText txtSearch;
    Button btnThemBH, btnSearch;
    ArrayList<BaiHat> list;
    AdapterBaiHat adapterBaiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoai_main);

        btnThemBH = (Button) findViewById(R.id.buttonThemBH);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        txtSearch = (EditText) findViewById(R.id.editTextSearch);

        addEvent();
        lstDSBH = (ListView) findViewById(R.id.listViewDSBH);
        list = new ArrayList<>();
        adapterBaiHat = new AdapterBaiHat(HoaiMainActivity.this, list);
        lstDSBH.setAdapter(adapterBaiHat);

        database = Database.initDatabase(HoaiMainActivity.this, DATABASE_NAME);

        Cursor cursor = database.rawQuery("Select * from VanDinhHoai_BaiHat", null);
        list.clear();
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbh = cursor.getInt(0);
            String tenbh = cursor.getString(1);
            String tencs = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new BaiHat(idbh, tenbh, tencs, anh));
        }
        adapterBaiHat.notifyDataSetChanged();
    }

    private void addEvent() {
        btnThemBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaiMainActivity.this, HoaiInsertActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoaiMainActivity.this, HoaiSearchActivity.class);
                intent.putExtra("searchString", txtSearch.getText().toString());
                startActivity(intent);
            }
        });
    }
}