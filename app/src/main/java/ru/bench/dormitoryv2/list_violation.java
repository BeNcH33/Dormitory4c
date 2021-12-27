package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_SurNameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_VIOLATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class list_violation extends AppCompatActivity {
    public DBHelper dbHelper;
    SQLiteDatabase database;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView listView;
    public static Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_violation);

        dbHelper = new DBHelper(getApplicationContext());

        listView = findViewById(R.id.listviewviolation);
        database = dbHelper.getReadableDatabase();

        cursor = DBHelper.readAllStudentViolation(database);
        cursor=database.query(DBHelper.TABLE_VIOLATION, null, null, null, null, null, null);
        String[] from = new String[]{ KEY_NameStudent, KEY_SurNameStudent, KEY_VIOLATION};
        int[] to = new int[]{R.id.tvName_V, R.id.tvSurName_V,R.id.tvNumber_Group_V};
        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_violation, cursor, from, to, 0);
        listView.setAdapter(simpleCursorAdapter);
    }

    /**
     * Метод открывает новую активити
     * @param view
     */
    public void NewViolation(View view)
    {
        Intent intent = new Intent(this, Student_Violation.class);
        startActivity(intent);
    }
}