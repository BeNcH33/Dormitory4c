package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_BIRTHDAY;
import static ru.bench.dormitoryv2.DBHelper.KEY_COUNTLEAVER;
import static ru.bench.dormitoryv2.DBHelper.KEY_GROUPNUMBER;
import static ru.bench.dormitoryv2.DBHelper.KEY_ID;
import static ru.bench.dormitoryv2.DBHelper.KEY_LEAVEROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAMEVIOLATION;
import static ru.bench.dormitoryv2.DBHelper.KEY_NUMBERROOM;
import static ru.bench.dormitoryv2.DBHelper.KEY_NameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_OTCH;
import static ru.bench.dormitoryv2.DBHelper.KEY_PASPNUM;
import static ru.bench.dormitoryv2.DBHelper.KEY_PASPSER;
import static ru.bench.dormitoryv2.DBHelper.KEY_PHONE;
import static ru.bench.dormitoryv2.DBHelper.KEY_SEX;
import static ru.bench.dormitoryv2.DBHelper.KEY_SURNAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_SurNameStudent;
import static ru.bench.dormitoryv2.DBHelper.KEY_VIOLATION;
import static ru.bench.dormitoryv2.DBHelper.KEY_id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChangedStudent extends AppCompatActivity {

    public DBHelper dbHelper;
    SQLiteDatabase database;
    public static Cursor cursor;
    public Spinner spinnerViolation;
    public ContentValues contentValues;

    EditText ETVname,ETVsurname,ETVlastname,ETVPaspSer,ETVPaspNum, ETVBirth, ETVnumberGroup, ETVphonenumber, ETVleaveroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_student);

        ETVname=findViewById(R.id.Edittext_ETVname);
        ETVsurname=findViewById(R.id.edittext_TVsurname);
        ETVnumberGroup=findViewById(R.id.edittext_TVnumberGroup);
        ETVphonenumber=findViewById(R.id.edittext_TVphonenumber);
        ETVleaveroom=findViewById(R.id.edittext_TVleaveroom);
        ETVlastname=findViewById(R.id.edittext_TVlastname);
        ETVPaspSer=findViewById(R.id.edittext_TVPaspSer);
        ETVPaspNum=findViewById(R.id.edittext_TVPaspNum);
        ETVBirth=findViewById(R.id.edittext_TVBirthday);

        spinnerViolation=findViewById(R.id.spinner_violationch);

        dbHelper = new DBHelper(getApplicationContext());


        String name = " Name ";
        String surname = " Surname ";
        String groupNumber = " Number ";
        String telephone = " Number ";
        String LeaveRoom = " Room ";

        name = getIntent().getExtras().getString("name");
        surname = getIntent().getExtras().getString("surname");
        groupNumber = getIntent().getExtras().getString("groupNumber");
        telephone = getIntent().getExtras().getString("telephone");
        LeaveRoom = getIntent().getExtras().getString("LeaveRoom");
        Search(name,surname,groupNumber,telephone);
        CreateNameViolationSpiner();
    }

    /**
     * Данный метод выполняет поиск данных в БД по заданным параметрам
     * @param name
     * @param surname
     * @param groupNumber
     * @param telephone
     */
    public void  Search(String name, String surname,String groupNumber,String telephone){


         database = dbHelper.getWritableDatabase();

        cursor = database.query(dbHelper.TABLE_STUDENT,
                null,
                KEY_NAME + " LIKE ? AND " +
                        KEY_SURNAME + " LIKE ? AND " +
                        KEY_GROUPNUMBER + " LIKE ? AND " +
                        KEY_PHONE + " LIKE ? ",
                new String[] {
                        name + "%",
                        surname + "%",
                        groupNumber + "%",
                        telephone + "%"
                },
                null,
                null,
                null);

        if(cursor.moveToFirst() == true) {
            //Получение индексов
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nameIndex = cursor.getColumnIndex(KEY_NAME);
            int surnameIndex = cursor.getColumnIndex(KEY_SURNAME);
            int lastnameIndex=cursor.getColumnIndex(KEY_OTCH);
            int paspserIndex=cursor.getColumnIndex(KEY_PASPSER);
            int paspnumIndex=cursor.getColumnIndex(KEY_PASPNUM);
            int birthIndex=cursor.getColumnIndex(KEY_BIRTHDAY);
            int sexIndex=cursor.getColumnIndex(KEY_SEX);
            int groupNumberIndex = cursor.getColumnIndex(KEY_GROUPNUMBER);
            int telephoneIndex = cursor.getColumnIndex(KEY_PHONE);
            int LeaveRoomIndex = cursor.getColumnIndex(KEY_LEAVEROOM);

            do {
                //Здесь вывод данных на текстбоксы
                String nameStr=cursor.getString(nameIndex);
                String surnameStr=cursor.getString(surnameIndex);
                String lastname=cursor.getString(lastnameIndex);
                String paspser=cursor.getString(paspserIndex);
                String paspnum=cursor.getString(paspnumIndex);
                String birth=cursor.getString(birthIndex);
                String sex=cursor.getString(sexIndex);
                String numberroomStr=cursor.getString(groupNumberIndex);
                String phoneStr=cursor.getString(telephoneIndex);
                String leaveroomStr=cursor.getString(LeaveRoomIndex);


                ETVname.setText(nameStr);
                ETVname.setFocusable(false);
                ETVname.setLongClickable(false);

                ETVsurname.setText(surnameStr);
                ETVsurname.setFocusable(false);
                ETVsurname.setLongClickable(false);

                ETVlastname.setText(lastname);
                ETVlastname.setFocusable(false);
                ETVlastname.setLongClickable(false);

                ETVPaspSer.setText(paspser);
                ETVPaspSer.setFocusable(false);
                ETVPaspSer.setLongClickable(false);

                ETVPaspNum.setText(paspnum);
                ETVPaspNum.setFocusable(false);
                ETVPaspNum.setLongClickable(false);

                ETVBirth.setText(birth);
                ETVBirth.setFocusable(false);
                ETVBirth.setLongClickable(false);

                ETVnumberGroup.setText(numberroomStr);
                ETVnumberGroup.setFocusable(false);
                ETVnumberGroup.setLongClickable(false);

                ETVphonenumber.setText(phoneStr);
                ETVphonenumber.setFocusable(false);
                ETVphonenumber.setLongClickable(false);

                ETVleaveroom.setText(leaveroomStr);
                ETVleaveroom.setFocusable(false);
                ETVleaveroom.setLongClickable(false);

            }while (cursor.moveToNext() == true);
        }


    }


    /**
     * Выполняется заполнение спинера
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
     * Добавление нарушения студенту
     * @param view
     */
    public void btn_addnewviolation(View view)
    {
        database = dbHelper.getWritableDatabase();

        String name=ETVname.getText().toString();
        String surname=ETVsurname.getText().toString();
        String nameviolation=this.spinnerViolation.getSelectedItem().toString();

        contentValues = new ContentValues();
        contentValues.put(KEY_NameStudent, name);
        contentValues.put(KEY_SurNameStudent, surname);
        contentValues.put(KEY_VIOLATION, nameviolation);

        try {
            long result=database.insertOrThrow(dbHelper.TABLE_VIOLATION, null, contentValues);
            Toast.makeText(getApplicationContext(), "Добавлено "+ result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Произошла ошибка ", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Метод удаления студента
     * @param view
     */
    public void DeleteStudent(View view)
    {
        database = dbHelper.getWritableDatabase();
        String name = ETVname.getText().toString();
        String surname = ETVsurname.getText().toString();
        String numberroom=ETVleaveroom.getText().toString();

        cursor = database.query(dbHelper.TABLE_STUDENT,
                null,
                KEY_NAME +" = ? AND "+ KEY_SURNAME +" = ?",
                new String[] {name, surname},
                null,
                null,
                null);

        int idIndex = cursor.getColumnIndex(KEY_ID);

        if (cursor.moveToFirst() == true) {

            long result = database.delete(dbHelper.TABLE_STUDENT, KEY_ID +" = ?", new String[] {cursor.getString(idIndex)});
            Toast.makeText(getApplicationContext(), "Удалено " + result, Toast.LENGTH_LONG).show();
            UpdateRoomTable(numberroom);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Удаление по заданным данным невозможно", Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }



    /**
     * Обновление количества проживающих в комнате
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
        int newNumber=0;
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
}