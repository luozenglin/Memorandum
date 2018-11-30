package com.example.luozenglin.mymemorandum;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.luozenglin.common.Item;
import com.example.luozenglin.common.MemoAdapter;
import com.example.luozenglin.sqlite.SQLiteUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemList;
    ListView listView;
    SQLiteUtils sqLiteUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteUtils = new SQLiteUtils(this);
        listView = (ListView) findViewById(R.id.list);
        refreshLayout();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editItem(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClick(position);
                return true;
            }
        });
    }

    protected void refreshLayout() {
        itemList = sqLiteUtils.query();
        MemoAdapter adapter = new MemoAdapter(MainActivity.this,
                R.layout.memo_item, itemList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_item, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","start onResume()");
        refreshLayout();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                addItem();
                break;
            default:
        }
        return true;
    }

    protected void addItem() {
        Intent intent = new Intent(this,MemoEditActivity.class);
        Time time = new Time();
        time.setToNow();
        int position = itemList.size();
        intent.putExtra("position",position);
        intent.putExtra("textDate",Item.getDate(String.valueOf(time.toMillis(true))));
        intent.putExtra("textTime",Item.getTime(String.valueOf(time.toMillis(true))));
        intent.putExtra("mainText","");
        startActivityForResult(intent,position);
    }

    protected void editItem(int position){
        sqLiteUtils.delete(itemList.get(position).getDatetime());
        Intent intent = new Intent(this,MemoEditActivity.class);
        Item item = itemList.get(position);
        intent.putExtra("position",position);
        intent.putExtra("textDate",Item.getDate(item.getDatetime()));
        intent.putExtra("textTime",Item.getTime(item.getDatetime()));
        intent.putExtra("mainText",item.getContent());
        startActivityForResult(intent,position);
    }

    protected void longClick(final int position) {
        final View popupView = getLayoutInflater().inflate(R.layout.delete_item, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(popupView, Gravity.BOTTOM, 0, 0);

        Button deleteButton = (Button) popupView.findViewById(R.id.deleteButton);
        Button shareButton = (Button) popupView.findViewById(R.id.shareButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
                popupWindow.dismiss();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareItem(position);
                popupWindow.dismiss();
            }
        });
    }

    protected void deleteItem(int position) {
        String datetime = itemList.get(position).getDatetime();
        sqLiteUtils.delete(datetime);
        refreshLayout();
    }

    private void shareItem(int position) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "hi,我是luozenglin，来自备忘录分享："+itemList.get(position).getContent());
        startActivity(Intent.createChooser(intent, "分享到"));
    }

}