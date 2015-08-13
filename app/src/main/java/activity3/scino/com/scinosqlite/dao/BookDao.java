package activity3.scino.com.scinosqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import activity3.scino.com.scinosqlite.db.BookTable;
import activity3.scino.com.scinosqlite.db.SCSQLiteHelper;
import activity3.scino.com.scinosqlite.model.Book;

/**
 * Created by Name on 10/08/15.
 */
public class BookDao {

    private SQLiteDatabase mDatabase;
    private SCSQLiteHelper mHelper;

    public BookDao(Context context) {
        mHelper = new SCSQLiteHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public Book createBook(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookTable.COLUMN_NAME, name);
        long bookId = mDatabase.insert(BookTable.TABLE_BOOK, null, contentValues);

        Cursor cursor = mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME},
                BookTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(bookId)}, null, null, null);
        cursor.moveToFirst();
        Book book = cursorToBook(cursor);
        cursor.close();

        return book;
    }

    public void deleteBookById(long id) {
        mDatabase.delete(BookTable.TABLE_BOOK, BookTable.COLUMN_ID + " = " + id, null);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        Cursor cursor = mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();

        return books;
    }

    public Cursor getAllBooksCursor() {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME}, null, null, null, null, null);

    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(cursor.getColumnIndex(BookTable.COLUMN_ID)));
        book.setName(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_NAME)));

        return book;
    }
}
