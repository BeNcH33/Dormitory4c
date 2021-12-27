package ru.bench.dormitoryv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNewStudent(View view)
    {
        Intent intent = new Intent(this, ListStudent.class);
        startActivity(intent);
    }

    public void OnClickAddRoom(View view)
    {
        Intent intent = new Intent(this, AddRoom.class);
        startActivity(intent);
    }

    public void NewViolation(View view)
    {
        Intent intent = new Intent(this, AddViolation.class);
        startActivity(intent);
    }

    public void StudentViolation(View view)
    {
        Intent intent = new Intent(this, list_violation.class);
        startActivity(intent);
    }
}