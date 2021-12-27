package ru.bench.dormitoryv2;

import static ru.bench.dormitoryv2.DBHelper.KEY_DESKVIOLATION;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAME;
import static ru.bench.dormitoryv2.DBHelper.KEY_NAMEVIOLATION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddViolation extends AppCompatActivity {
    public DBHelper dbHelper;
    public ContentValues contentValues;
    SQLiteDatabase database;

    public EditText NameViolation;
    public EditText Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_violation);
        dbHelper = new DBHelper(getApplicationContext());
        NameViolation = findViewById(R.id.AddNameViolation);
        Description=findViewById(R.id.addDeskrViolation);
    }



    /**
     * Метод добавляет новую запись в БД
     * @param view
     */
    public void NewViolation(View view)
    {
        database = dbHelper.getWritableDatabase();
        String nameViolation = this.NameViolation.getText().toString();
        String description=this.Description.getText().toString();

        contentValues = new ContentValues();
        contentValues.put(KEY_NAMEVIOLATION, nameViolation);
        contentValues.put(KEY_DESKVIOLATION, description);

        try {
            long result=database.insertOrThrow(dbHelper.TABLE_VIOLATIONNAME, null, contentValues);
            Toast.makeText(getApplicationContext(), "Добавлено "+ result, Toast.LENGTH_LONG).show();
            onClearInput();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Произошла ошибка ", Toast.LENGTH_LONG).show();
        }
        //database.close();
    }
    public void onClearInput() {
        NameViolation.setText("");

    }
}