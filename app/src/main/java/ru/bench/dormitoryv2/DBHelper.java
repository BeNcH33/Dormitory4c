package ru.bench.dormitoryv2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Dormitory";

    //Таблица студентов

    public static final String TABLE_STUDENT = "Student";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_OTCH = "otch";
    public static final String KEY_PASPSER = "serial";
    public static final String KEY_PASPNUM = "number";
    public static final String KEY_BIRTHDAY = "date";
    public static final String KEY_SEX = "sex";
    public static final String KEY_GROUPNUMBER = "group_number";
    public static final String KEY_PHONE = "telephone";
    public static final String KEY_LEAVEROOM="NumberRoom";

    //Таблица комнат
    public static final String TABLE_ROOMS="Rooms";
    public static final String KEY_id = "_id";
    public static final String KEY_NUMBERROOM="NumberRoom";
    public static final String KEY_NUMBERFLOOR="NumberFloor";
    public static final String KEY_FURNITURE = "furniture";
    public static final String KEY_MAXLEAVER="MaxLeaver";
    public static final String KEY_COUNTLEAVER="CountLeaver";

    //Таблица с возможными нарушениями
    public static final String TABLE_VIOLATIONNAME="ViolationName";
    public static final String KEY_idVIONM = "_id";
    public static final String KEY_NAMEVIOLATION = "NameViolation";
    public static final String KEY_DESKVIOLATION = "deskViolation";

    //Таблица совершенных нарушений
    public static final String TABLE_VIOLATION="Violation";
    public static final String KEY_idVIO = "_id";
    public static final String KEY_NameStudent = "name";
    public static final String KEY_SurNameStudent = "surname";
    public static final String KEY_VIOLATION = "Violation";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /**
     * Создание таблиц базы данных
     */
    public void onCreate(@NonNull SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_STUDENT+ "("
                + KEY_ID + " integer primary key,"
                + KEY_NAME + ","
                + KEY_SURNAME + ","
                + KEY_OTCH + ","
                + KEY_PASPSER + ","
                + KEY_PASPNUM + ","
                + KEY_BIRTHDAY + ","
                + KEY_SEX + ","
                + KEY_GROUPNUMBER + ","
                + KEY_PHONE + ","
                + KEY_LEAVEROOM + ""
                + ")"
                + "");

        db.execSQL("create table " + TABLE_ROOMS
                + "("
                + KEY_id + " integer primary key,"
                + KEY_NUMBERROOM + ","
                + KEY_NUMBERFLOOR + ","
                + KEY_FURNITURE + ","
                + KEY_MAXLEAVER + ","
                + KEY_COUNTLEAVER + ""
                + ")"
                + "");
        db.execSQL("create table " + TABLE_VIOLATIONNAME+ "("
                + KEY_idVIONM + " integer primary key,"
                + KEY_NAMEVIOLATION + ","
                + KEY_DESKVIOLATION + ""
                + ")"
                + "");
        db.execSQL("create table " + TABLE_VIOLATION+ "("
                + KEY_idVIO + " integer primary key,"
                + KEY_NameStudent + ","
                + KEY_SurNameStudent + ","
                + KEY_VIOLATION + ""
                + ")"
                + "");
    }



    /**
     * Метод возвращающий содержание таблицы со студентами
     * @param db
     * @return
     */
    public static Cursor readAllStudent(SQLiteDatabase db){
        if (db != null)
            return db.query(DBHelper.TABLE_STUDENT, null, null, null, null, null, null);
        return null;
    }
    public static Cursor readAllNumberRoom(SQLiteDatabase db){
        if (db != null)
            return db.query(DBHelper.TABLE_ROOMS, null, KEY_NUMBERROOM, null, null, null, null);
        return null;
    }

    public static Cursor readAllRooms(SQLiteDatabase db){
        if (db != null)
            return db.query(DBHelper.TABLE_ROOMS, null, null, null, null, null, null);
        return null;
    }

    /**
     * Метод возвращающий нарушения студентов
     * @param db
     * @return
     */
    public static Cursor readAllStudentViolation(SQLiteDatabase db){
        if (db != null)
            return db.query(DBHelper.TABLE_VIOLATION, null, null, null, null, null, null);
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_STUDENT);
        db.execSQL("drop table if exists " + TABLE_ROOMS);
        onCreate(db);
    }
}
