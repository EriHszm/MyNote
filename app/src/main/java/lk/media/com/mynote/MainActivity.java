package lk.media.com.mynote;

import android.database.Cursor;

import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    EditText editTextMemo;
    Button buttonSave;

    ListView listViewMemos;
    ArrayAdapter<String> adapter;
    ArrayList<String> memoList;
    MemoDatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContent = findViewById(R.id.editTextContent);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMemo = findViewById(R.id.editTextMemo);
        buttonSave = findViewById(R.id.buttonSave);
        listViewMemos = findViewById(R.id.listViewMemos);

        // FloatingActionButtonのリファレンスを取得
        com.google.android.material.floatingactionbutton.FloatingActionButton fabAddMemo = findViewById(R.id.fabAddMemo);

        // クリックリスナーを設定
        fabAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ここにメモの新規作成に関連するアクションを書く
                // 例：新しいアクティビティに移動する、ダイアログを表示するなど
            }
        });

        mDatabaseHelper = new MemoDatabaseHelper(this);

        memoList = mDatabaseHelper.getAllMemos(); // mDatabaseHelperを使用する

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memoList);
        listViewMemos.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemo();
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

private void saveMemo() {
    String title = editTextTitle.getText().toString();
    String content = editTextContent.getText().toString();
    if (!title.isEmpty() && !content.isEmpty()) {
        // AddData(title, content);  // このメソッドの中でmDatabaseHelperを使っている場合、それも変更が必要です。
        mDatabaseHelper.addData(title, content);  // この行は仮定に基づいています。正確なメソッド名やパラメータに注意してください。

        editTextTitle.setText("");
        editTextContent.setText("");
    } else {
        toastMessage("タイトルと内容を入力してください");
    }

    Cursor data = mDatabaseHelper.getData();  // この行を変更しました
    ArrayList<String> listData = new ArrayList<>();
    while (data.moveToNext()) {
        listData.add(data.getString(1));
    }

    String memo = editTextMemo.getText().toString();
    if (!memo.isEmpty()) {
        mDatabaseHelper.addMemo(memo);  // この行を変更しました
        memoList.add(memo);
        adapter.notifyDataSetChanged();
        editTextMemo.setText("");
        Toast.makeText(this, "メモを保存しました", Toast.LENGTH_SHORT).show();
    }
}



/***
    private void saveMemo() {

        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();
        if (!title.isEmpty() && !content.isEmpty()) {
            AddData(title, content);
            editTextTitle.setText("");
            editTextContent.setText("");
        } else {
            toastMessage("タイトルと内容を入力してください");
        }

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1));  // タイトルのカラムのデータのみ追加
        }



        String memo = editTextMemo.getText().toString();
        if (!memo.isEmpty()) {
            databaseHelper.addMemo(memo);
            memoList.add(memo);
            adapter.notifyDataSetChanged();
            editTextMemo.setText("");
            Toast.makeText(this, "メモを保存しました", Toast.LENGTH_SHORT).show();
        }
    }
 ***/

    /** 追加２
    public void AddData(String title, String content) {
        boolean isInserted = mDatabaseHelper.addData(title, content);
        if (isInserted) {
            toastMessage("メモが保存されました");
        } else {
            toastMessage("メモの保存に失敗しました");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** 追加２ ここまで **/

}


/***
    private void loadMemo() {
        String memo = getSharedPreferences("memo_app", MODE_PRIVATE).getString("memo_key", "");
        editTextMemo.setText(memo);
    }
} ***/

