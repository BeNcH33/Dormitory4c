package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;


public class ListStudent extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public DBHelper dbHelper;
    SQLiteDatabase database;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView listView;
    public static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);
        dbHelper = new DBHelper(getApplicationContext());

        listView = findViewById(R.id.ListView_Student);
        onRead();


    }

    /**
     * Метод считывает все имеющиеся записи в базе данных
     */
    public void onRead() {
        database = dbHelper.getReadableDatabase();

        cursor = DBHelper.readAllStudent(database);
        cursor=database.query(DBHelper.TABLE_STUDENT, null, null, null, null, null, null);
        String[] from = new String[]{ KEY_NAME, KEY_SURNAME, KEY_GROUPNUMBER, KEY_PHONE,KEY_LEAVEROOM};
        int[] to = new int[]{R.id.tvName_V, R.id.tvSurName_V,R.id.tvNumber_Group_V,R.id.tvTelephone, R.id.tvLeaveRoom};
        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item, cursor, from, to, 0);
        listView.setAdapter(simpleCursorAdapter);
        listView.setOnItemClickListener(this);
    }

    public void NewStudent(View view)
    {
        Intent intent = new Intent(this, AddStudent.class);
        startActivity(intent);
    }

    @Override
    /**
     * Данный метод открвывет детальную информацию о студенте
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        int nameIndex = cursor.getColumnIndex(KEY_NAME);
        int surnameIndex = cursor.getColumnIndex(KEY_SURNAME);
        int groupNumberIndex = cursor.getColumnIndex(KEY_GROUPNUMBER);
        int telephoneIndex = cursor.getColumnIndex(KEY_PHONE);
        int LeaveRoomIndex = cursor.getColumnIndex(KEY_LEAVEROOM);

        cursor.moveToPosition(i);
        String name=cursor.getString(nameIndex);
        String surname=cursor.getString(surnameIndex);
        String groupNumber=cursor.getString(groupNumberIndex);
        String telephone=cursor.getString(telephoneIndex);
        String LeaveRoom=cursor.getString(LeaveRoomIndex);

        Intent intent = new Intent(this, ChangedStudent.class);
        intent.putExtra("name", name);
        intent.putExtra("surname", surname);
        intent.putExtra("groupNumber", groupNumber);
        intent.putExtra("telephone", telephone);
        intent.putExtra("LeaveRoom", LeaveRoom);
        startActivity(intent);

    }
}