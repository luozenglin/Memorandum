package com.example.luozenglin.mymemorandum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luozenglin.common.Item;
import com.example.luozenglin.sqlite.SQLiteUtils;

public class MemoEditActivity extends AppCompatActivity {
    private Button backBtn;
    private Button finishBtn;
    private TextView dateView;
    private TextView timeView;
    private EditText editText;
    private SQLiteUtils sqLiteUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    protected void init(){
        sqLiteUtils = new SQLiteUtils(this);
        dateView = (TextView) findViewById(R.id.dateText);
        timeView = (TextView) findViewById(R.id.timeText);
        editText = (EditText) findViewById(R.id.editText);
        backBtn = (Button) findViewById(R.id.back_btn);
        finishBtn = (Button) findViewById(R.id.finish_btn);
        dateView.setText(getIntent().getStringExtra("textDate"));
        timeView.setText(getIntent().getStringExtra("textTime"));
        editText.setText(getIntent().getStringExtra("mainText"));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void backToMainActivity(){
        saveData();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    protected void saveData(){
        if(String.valueOf(editText.getText()).equals("")){
            return;
        }
        Item item = new Item();
        Time time = new Time();
        time.setToNow();
        item.setContent(String.valueOf(editText.getText()));
        item.setDatetime(String.valueOf(time.toMillis(true)));
        sqLiteUtils.insert(item);
    }
}
