package com.example.mysqlphp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    String urlAddress="http://validation.appetizers.in/newone/1/app.php";
    EditText nameTxt,posTxt,teamTxt;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //INITIALIZE UI FIELDS
        nameTxt= (EditText) findViewById(R.id.nameEditTxt);
        saveBtn= (Button) findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //START ASYNC TASK
                Sender s=new Sender(MainActivity.this,urlAddress,nameTxt);
                s.execute();
            }
        });

    }
}
