package tmnt.example.loaderdemo;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoaderManager mLoaderManager;
    private List<String> mData;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list_view);

        mData = new ArrayList<>();

        //获取LoaderManager
        mLoaderManager = getLoaderManager();

        //初始化Loader


        mLoaderManager.initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            /**
             * 创建Loader
             *
             * @param id
             * @param args
             * @return
             */
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(MainActivity.this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        , null, null, null, null);
            }

            /**
             * 获取数据 在主线程调用
             * @param loader
             * @param data
             */
            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                while (data.moveToNext()) {
                    String name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String contact = name + " : " + phone;
                    mData.add(contact);
                }

                list.setAdapter(new ListViewAdapter(MainActivity.this, mData));

            }

            /**
             * 没有数据加载是调用
             * @param loader
             */
            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        });

    }
}
