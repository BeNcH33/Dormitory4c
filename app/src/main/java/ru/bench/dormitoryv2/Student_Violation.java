package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAMEVIOLATION;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_SurNameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_VIOLATION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Student_Violation extends AppCompatActivity {

    public DBHelper dbHelper;
    public ContentValues contentValues;
    SQLiteDatabase database;
    Cursor cursor;

    public Spinner spinnerName;
    public Spinner spinnerSurName;
    public Spinner spinnerViolation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_violation);

        dbHelper = new DBHelper(getApplicationContext());

        spinnerName=findViewById(R.id.spinner_NameStudent);
        spinnerSurName=findViewById(R.id.spinner_Surname);
        spinnerViolation=findViewById(R.id.spinner_Violation);

        CreateNameSpiner();
        CreateSurNameSpiner();
        CreateNameViolationSpiner();

    }


    /**
     * Заполение спинера с именем студента
     */
    public  void  CreateNameSpiner()
    {
        database = dbHelper.getReadableDatabase();
        cursor = database.query(DBHelper.TABLE_STUDENT, null,
                null, null, null, null, null);

        ArrayList<String> name= new ArrayList<>();

        if (cursor.moveToFirst()) // если курсор не пустой
        {
            int NameIndex = cursor.getColumnIndex(KEY_NAME);
            int SurNameIndex=cursor.getColumnIndex(KEY_SURNAME);

            for (int i = 0; i < cursor.getCount(); i++)
            {
                name.add(String.valueOf(cursor.getString(NameIndex))); // заполняем
                cursor.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, name.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setAdapter(adapter);
    }


    /**
     * Заполнение спинера с фамилией студента
     */
    public  void  CreateSurNameSpiner()
    {
        database = dbHelper.getReadableDatabase();
        cursor = database.query(DBHelper.TABLE_STUDENT, null,
                null, null, null, null, null);

        ArrayList<String> surname= new ArrayList<>();

        if (cursor.moveToFirst()) // если курсор не пустой
        {

            int SurNameIndex=cursor.getColumnIndex(KEY_SURNAME);

            for (int i = 0; i < cursor.getCount(); i++)
            {
                surname.add(String.valueOf(cursor.getString(SurNameIndex))); // заполняем
                cursor.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, surname.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSurName.setAdapter(adapter);
    }


    /**
     * Заполнение спинера с названием нарушения
     */
    public  void  CreateNameViolationSpiner()
    {
        database = dbHelper.getReadableDatabase();
        cursor = database.query(DBHelper.TABLE_VIOLATIONNAME, null,
                null, null, null, null, null);

        ArrayList<String> nameViolation= new ArrayList<>();

        if (cursor.moveToFirst()) // если курсор не пустой
        {

            int SurNameIndex=cursor.getColumnIndex(KEY_NAMEVIOLATION);

            for (int i = 0; i < cursor.getCount(); i++)
            {
                nameViolation.add(String.valueOf(cursor.getString(SurNameIndex))); // заполняем
                cursor.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nameViolation.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerViolation.setAdapter(adapter);
    }



    /**
     * Метод добавляющий запись в БД
     * @param view
     */
    public void AddViolationStudent(View view)
    {
        database = dbHelper.getWritableDatabase();

        String name=this.spinnerName.getSelectedItem().toString();
        String surname=this.spinnerSurName.getSelectedItem().toString();
        String nameviolation=this.spinnerViolation.getSelectedItem().toString();

        contentValues = new ContentValues();
        contentValues.put(KEY_NameStudent, name);
        contentValues.put(KEY_SurNameStudent, surname);
        contentValues.put(KEY_VIOLATION, nameviolation);

        try {
            long result=database.insertOrThrow(dbHelper.TABLE_VIOLATION, null, contentValues);
            Toast.makeText(getApplicationContext(), "Добавлено "+ result, Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Произошла ошибка ", Toast.LENGTH_LONG).show();
        }
    }
}