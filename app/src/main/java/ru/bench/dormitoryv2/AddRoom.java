package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_COUNTLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_FURNITURE;
import static ru.bench.dormitoryv2.DBHelper.KEY_MAXLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERFLOOR;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_PASPNUM;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddRoom extends AppCompatActivity {

    public DBHelper dbHelper;
    public ContentValues contentValues;
    SQLiteDatabase database;
    public static Cursor cursor;

    public EditText addNumberRoom;
    public EditText addNumberFloor;
    public EditText addMaxCountLeaver;
    public EditText addCountLeaver;
    public EditText addFurniture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        dbHelper = new DBHelper(getApplicationContext());

        addNumberRoom = findViewById(R.id.AddNumberRoom);
        addNumberFloor = findViewById(R.id.AddFloor);
        addMaxCountLeaver = findViewById(R.id.AddMaxCountLeaver);
        addCountLeaver = findViewById(R.id.addCountLeaver);
        addFurniture=findViewById(R.id.addFurniture);
    }


    /**
     * Метод добавляет новую запись в базу данных
     * @param view
     */
    public void OnAddRoomClick(View view) {
        database = dbHelper.getWritableDatabase();

        String NumberRoom = this.addNumberRoom.getText().toString();
        String Floor = this.addNumberFloor.getText().toString();
        String MaxCount = this.addMaxCountLeaver.getText().toString();
        String Count = this.addCountLeaver.getText().toString();
        String furniture=this.addFurniture.getText().toString();


        if (NumberRoom==""| Floor=="" | MaxCount=="" | Count=="" | furniture=="")
        {
            Toast.makeText(getApplicationContext(), "Введите данные", Toast.LENGTH_LONG).show();
        }
        else {
            cursor = database.query(DBHelper.TABLE_ROOMS, null,
                    KEY_NUMBERROOM + " = ?", new String[]{NumberRoom}, null, null, null);

            if (cursor.moveToFirst() == false)
            {
                contentValues = new ContentValues();
                contentValues.put(KEY_NUMBERROOM, NumberRoom);
                contentValues.put(KEY_NUMBERFLOOR, Floor);
                contentValues.put(KEY_FURNITURE, furniture);
                contentValues.put(KEY_MAXLEAVER, MaxCount);
                contentValues.put(KEY_COUNTLEAVER, Count);
                try
                {
                    long result = database.insert(dbHelper.TABLE_ROOMS, null, contentValues);
                    Toast.makeText(getApplicationContext(), "Добавлено " + result, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка ", Toast.LENGTH_LONG).show();
                }
            }
            }
        }
    }









