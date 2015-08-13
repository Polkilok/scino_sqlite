package activity3.scino.com.scinosqlite;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import activity3.scino.com.scinosqlite.dao.BookDao;

/**
 * Created by Name on 10/08/15.
 */
public class BookCursorLoader extends CursorLoader {

    private BookDao mBookDao;

    public BookCursorLoader(Context context, BookDao dao) {
        super(context);
        mBookDao = dao;
    }

    @Override
    public Cursor loadInBackground() {
        return mBookDao.getAllBooksCursor();
    }
}
