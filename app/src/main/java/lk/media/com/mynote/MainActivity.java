package lk.media.com.mynote;

import android.database.Cursor;

import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private EditText editTextMemo;
    private Button buttonSave;
    private ListView listViewMemos;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> memoList;
    private MemoDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // この行は条件分岐の外に出して、activity_mainを常にセットします


        mDatabaseHelper = new MemoDatabaseHelper(this);
        memoList = mDatabaseHelper.getAllMemos();

        if (memoList.size() > 0) {
            setContentView(R.layout.activity_main);


            editTextTitle = findViewById(R.id.editTextNewMemoTitle);
            editTextContent = findViewById(R.id.editTextNewMemoContent);
            buttonSave = findViewById(R.id.buttonCreateNewMemo);
            listViewMemos = findViewById(R.id.listViewMemos);  // activity_main.xmlにこのIDを追加する必要があります

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memoList);
            listViewMemos.setAdapter(adapter);

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveMemo();
                }
            });

        } else {
            setContentView(R.layout.activity_new_memo);
            editTextMemo = findViewById(R.id.editTextNewMemoContent);
        }

        com.google.android.material.floatingactionbutton.FloatingActionButton fabAddMemo = findViewById(R.id.fabAddMemo);
        fabAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 新規メモ作成処理
            }
        });

        refreshListView();
    }

    private void refreshListView() {
        memoList = mDatabaseHelper.getAllMemos();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memoList);
        listViewMemos.setAdapter(adapter);
    }

    private void saveMemo() {
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        if (!title.isEmpty() && !content.isEmpty()) {
            mDatabaseHelper.addData(title, content);
            editTextTitle.setText("");
            editTextContent.setText("");
            memoList.add(title);
            adapter.notifyDataSetChanged();
        } else {
            toastMessage("タイトルと内容を入力してください");
        }
        refreshListView();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
