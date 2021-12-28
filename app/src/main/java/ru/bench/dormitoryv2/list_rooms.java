package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_COUNTLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_FURNITURE;
import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_MAXLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERFLOOR;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class list_rooms extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public DBHelper dbHelper;
    SQLiteDatabase database;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView listView;
    public static Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rooms);
        dbHelper = new DBHelper(getApplicationContext());
        listView = findViewById(R.id.listview_rooms);
        onRead();
    }

    /**
     * Метод считывает все имеющиеся записи в базе данных
     */
    public void onRead() {
        database = dbHelper.getReadableDatabase();

        cursor=database.query(DBHelper.TABLE_ROOMS, null, null, null, null, null, null);
        String[] from = new String[]{ KEY_NUMBERROOM, KEY_NUMBERFLOOR, KEY_FURNITURE, KEY_MAXLEAVER,KEY_COUNTLEAVER};
        int[] to = new int[]{R.id.TV_Number, R.id.TV_Floor,R.id.TV_furniture,R.id.TV_maxleaver, R.id.TV_leaver};
        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_room, cursor, from, to, 0);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(this);
    }

    public void AddRoom(View view)
    {
        Intent intent = new Intent(this, AddRoom.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        int numberIndex = cursor.getColumnIndex(KEY_NUMBERROOM);
        int floorIndex = cursor.getColumnIndex(KEY_NUMBERFLOOR);

        cursor.moveToPosition(i);

        String numberStr=cursor.getString(numberIndex);
        String floorStr=cursor.getString(floorIndex);


        Intent intent = new Intent(this, ChangedRoom.class);
        intent.putExtra("number", numberStr);
        intent.putExtra("floor", floorStr);

        startActivity(intent);
    }
}