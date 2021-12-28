package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_BIRTHDAY;
import static ru.bench.dormitoryv2.DBHelper.KEY_COUNTLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_FURNITURE;
import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_ID;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_MAXLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERFLOOR;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_OTCH;
import static ru.bench.dormitoryv2.DBHelper.KEY_PASPNUM;
import static ru.bench.dormitoryv2.DBHelper.KEY_PASPSER;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SEX;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangedRoom extends AppCompatActivity {

    public DBHelper dbHelper;
    SQLiteDatabase database;
    public static Cursor cursor;
    public ContentValues contentValues;

    EditText et_number, et_floor, et_furniture, et_max, et_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_room);
        et_number=findViewById(R.id.et_Number);
        et_floor=findViewById(R.id.et_floor);
        et_furniture=findViewById(R.id.et_furniture);
        et_max=findViewById(R.id.et_max);
        et_count=findViewById(R.id.et_count);

        dbHelper = new DBHelper(getApplicationContext());

        String number = " Name ";
        String floor = " Surname ";
        number = getIntent().getExtras().getString("number");
        floor = getIntent().getExtras().getString("floor");
        Search(number,floor);
    }

    public void ClickDelete(View view)
    {
        database = dbHelper.getWritableDatabase();

        String number = et_number.getText().toString();
        String floor = et_floor.getText().toString();


        cursor = database.query(dbHelper.TABLE_ROOMS,
                null,
                KEY_NUMBERROOM +" = ? AND "+ KEY_NUMBERFLOOR +" = ?",
                new String[] {number, floor},
                null,
                null,
                null);

        int idIndex = cursor.getColumnIndex(KEY_id);

        if (cursor.moveToFirst() == true) {

            long result = database.delete(dbHelper.TABLE_ROOMS, KEY_id +" = ?", new String[] {cursor.getString(idIndex)});
            Toast.makeText(getApplicationContext(), "Удалено " + result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(getApplicationContext(), "Удаление по заданным данным невозможно", Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    public void  Search(String number, String floor){


        database = dbHelper.getWritableDatabase();

        cursor = database.query(dbHelper.TABLE_ROOMS,
                null,
                KEY_NUMBERROOM + " LIKE ? AND " +
                        KEY_NUMBERFLOOR + " LIKE ? ",
                new String[] {
                        number + "%",
                        floor + "%"
                },
                null,
                null,
                null);

        if(cursor.moveToFirst() == true) {
            //Получение индексов
            int idIndex = cursor.getColumnIndex(KEY_id);
            int numberIndex = cursor.getColumnIndex(KEY_NUMBERROOM);
            int floorIndex = cursor.getColumnIndex(KEY_NUMBERFLOOR);
            int furnitureIndex=cursor.getColumnIndex(KEY_FURNITURE);
            int maxIndex=cursor.getColumnIndex(KEY_MAXLEAVER);
            int countIndex=cursor.getColumnIndex(KEY_COUNTLEAVER);

            do {
                //Здесь вывод данных на текстбоксы
                String numberStr=cursor.getString(numberIndex);
                String floorStr=cursor.getString(floorIndex);
                String furniturename=cursor.getString(furnitureIndex);
                String max=cursor.getString(maxIndex);
                String count=cursor.getString(countIndex);


                et_number.setText(numberStr);
                et_number.setFocusable(false);
                et_number.setLongClickable(false);

                et_floor.setText(floorStr);
                et_floor.setFocusable(false);
                et_floor.setLongClickable(false);

                et_furniture.setText(furniturename);
                et_furniture.setFocusable(false);
                et_furniture.setLongClickable(false);

                et_max.setText(max);
                et_max.setFocusable(false);
                et_max.setLongClickable(false);

                et_count.setText(count);
                et_count.setFocusable(false);
                et_count.setLongClickable(false);

            }while (cursor.moveToNext() == true);
        }


    }
}