package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_BIRTHDAY;
import static ru.bench.dormitoryv2.DBHelper.KEY_COUNTLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_ID;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_MAXLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStudent extends AppCompatActivity {

    public DBHelper dbHelper;
    public ContentValues contentValues;
    SQLiteDatabase database;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;

    public EditText etName;
    public EditText etSurname;
    public EditText etOtch;
    public EditText etPaspSer;
    public EditText etPaspNum;
    public EditText etBirth;
    public EditText etSex;
    public EditText etGroupNumber;
    public EditText etTelephone;
    public Spinner spinnerRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        dbHelper = new DBHelper(getApplicationContext());
        etName = findViewById(R.id.AddNameStudent);
        etSurname = findViewById(R.id.AddSurname);
        etOtch=findViewById(R.id.addOtch);
        etPaspSer=findViewById(R.id.addPaspSer);
        etPaspNum=findViewById(R.id.addPaspNum);
        etBirth=findViewById(R.id.addBirthday);
        etSex=findViewById(R.id.addSex);
        etGroupNumber = findViewById(R.id.addNumberGroup);
        etTelephone = findViewById(R.id.addPhone);
        spinnerRoom=findViewById(R.id.spinner_Room);

        database = dbHelper.getReadableDatabase();
        cursor = database.query(DBHelper.TABLE_ROOMS, null,
                null, null, null, null, null);
        ArrayList<String> rooms= new ArrayList<>();

        //Заполнение спинера
        if (cursor.moveToFirst()) // если курсор не пустой
        {

            int RoomIndex = cursor.getColumnIndex(KEY_NUMBERROOM);
            int MaxLeaverIndex=cursor.getColumnIndex(KEY_MAXLEAVER);
            int CountLeaverIndex=cursor.getColumnIndex(KEY_COUNTLEAVER);


                for (int i = 0; i < cursor.getCount(); i++) {
                    int CountLeaver = Integer.parseInt(cursor.getString(CountLeaverIndex));
                    int MaxLeaver = Integer.parseInt(cursor.getString(MaxLeaverIndex));
                    if (CountLeaver < MaxLeaver) {
                        rooms.add(String.valueOf(cursor.getInt(RoomIndex))); // заполняем
                    }
                    cursor.moveToNext();
            }
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rooms.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(adapter);

    }



    /**
     * Добавление нового студента в БД
     * @param view
     */
    public void NewStudentClick(View view)
    {
        database = dbHelper.getWritableDatabase();



        String name = this.etName.getText().toString();
        String surname = this.etSurname.getText().toString();
        String otch = this.etOtch.getText().toString();
        String PaspSer = this.etPaspSer.getText().toString();
        String PaspNum = this.etPaspNum.getText().toString();
        String Birth = this.etBirth.getText().toString();
        String Sex = this.etSex.getText().toString();
        String groupNumber = this.etGroupNumber.getText().toString();
        String telephone = this.etTelephone.getText().toString();
        String NumberRoom=this.spinnerRoom.getSelectedItem().toString();

        if (name==""| surname=="" | otch=="" | PaspSer=="" | PaspNum=="" | Birth=="" | Sex=="" | groupNumber=="" | telephone=="" | NumberRoom=="")
        {
            Toast.makeText(getApplicationContext(), "Введите данные", Toast.LENGTH_LONG).show();
        }
        else {

            cursor = database.query(DBHelper.TABLE_STUDENT, null,
                    KEY_PASPNUM + " = ?", new String[]{PaspNum}, null, null, null);

            if (cursor.moveToFirst() == false) {
                contentValues = new ContentValues();
                contentValues.put(KEY_NAME, name);
                contentValues.put(KEY_SURNAME, surname);
                contentValues.put(KEY_OTCH, otch);
                contentValues.put(KEY_PASPSER, PaspSer);
                contentValues.put(KEY_PASPNUM, PaspNum);
                contentValues.put(KEY_BIRTHDAY, Birth);
                contentValues.put(KEY_SEX, Sex);
                contentValues.put(KEY_GROUPNUMBER, groupNumber);
                contentValues.put(KEY_PHONE, telephone);
                contentValues.put(KEY_LEAVEROOM, NumberRoom);

                try {
                    long result = database.insertOrThrow(dbHelper.TABLE_STUDENT, null, contentValues);
                    Toast.makeText(getApplicationContext(), "Добавлено " + result, Toast.LENGTH_LONG).show();
                    UpdateRoomTable(NumberRoom);
                    onClearInput();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Такая запись существует", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Обновление таблицы комнат в зависимости от выбранного значения в спинере
     * @param NumberRoom
     */
    public  void UpdateRoomTable(String NumberRoom)
    {
        database = dbHelper.getWritableDatabase();


         cursor = database.query(dbHelper.TABLE_ROOMS,
                null,
                KEY_NUMBERROOM +" = ?",
                new String[] {NumberRoom},
                null,
                null,
                null);



        int idIndex = cursor.getColumnIndex(KEY_id);
        //int LeaverIndex=cursor.getColumnIndex(KEY_COUNTLEAVER);
        //String strLevear=cursor.getString(LeaverIndex);
        //int Leaver= Integer.parseInt(strLevear)+1;
        //String NewLeaver=Integer.toString(Leaver);
        //int _NumberRoom=Integer.parseInt(NumberRoom);

        //cursor=database.rawQuery("Select CountLeaver From Rooms Where NumberRoom="+_NumberRoom,null);
        int newNumber=1;
        String _NewNumberRoom= Integer.toString(newNumber);

        if (cursor.moveToFirst())
        {
            ContentValues updatedValues = new ContentValues();

            updatedValues.put(KEY_COUNTLEAVER, _NewNumberRoom);
            String where = KEY_id + "=" + cursor.getInt(idIndex);

            database.update(dbHelper.TABLE_ROOMS, updatedValues, where, null);
            Toast.makeText(getApplicationContext(), "Обновлено ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
        cursor.close();
}



    /**
     * Очистка ввода
     */
    public void onClearInput() {
        etName.setText("");
        etSurname.setText("");
        etOtch.setText("");
        etPaspSer.setText("");
        etPaspNum.setText("");
        etBirth.setText("");
        etSex.setText("");
        etGroupNumber.setText("");
        etTelephone.setText("");
    }
}