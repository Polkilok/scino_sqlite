package activity3.scino.com.scinosqlite;
;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import activity3.scino.com.scinosqlite.adapters.BookAdapter;
import activity3.scino.com.scinosqlite.dao.BookDao;


public class MainActivity extends AppCompatActivity implements OnBookAddedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BOOK_LOADER_ID = 1;

    private ListView mListView;
    private BookAdapter mAdapter;

    private BookDao mBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookDao = new BookDao(this);
        mBookDao.open();

        mListView = (ListView) findViewById(R.id.book_list);
        mAdapter = new BookAdapter(this, null, true);
        mListView.setAdapter(mAdapter);

        Button createBookButton = (Button) findViewById(R.id.add_new_book);
        createBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookFragment fragment = new AddBookFragment();
                fragment.show(getFragmentManager(), fragment.getClass().toString());
            }
        });

        getLoaderManager().initLoader(BOOK_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBookDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBookDao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBookAdded(String name) {
        mBookDao.createBook(name);
//        mAdapter.changeCursor(mBookDao.getAllBooksCursor());
        getLoaderManager().getLoader(BOOK_LOADER_ID).onContentChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (BOOK_LOADER_ID == id) {
            return new BookCursorLoader(this, mBookDao);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
