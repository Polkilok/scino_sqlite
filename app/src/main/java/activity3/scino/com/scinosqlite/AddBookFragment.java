package activity3.scino.com.scinosqlite;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Name on 10/08/15.
 */
public class AddBookFragment extends DialogFragment {

    private static final String TAG = AddBookFragment.class.toString();

    private EditText mBookName;
    private Button mButtonOk;
    private Button mButtonCancel;

    private OnBookAddedListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnBookAddedListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        mBookName = (EditText) view.findViewById(R.id.book_name_edit_text);

        mButtonOk = (Button) view.findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = mBookName.getText().toString();

                if (isValidBookName(bookName)) {
                    if (mListener != null) {
                        mListener.onBookAdded(bookName);
                        AddBookFragment.this.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.error_to_add_book_name), Toast.LENGTH_LONG).show();
                }
            }
        });

        mButtonCancel = (Button) view.findViewById(R.id.button_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookFragment.this.dismiss();
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setTitle(getString(R.string.add_book_fragment_title));

        return dialog;
    }

    private boolean isValidBookName(String bookName) {
        return !TextUtils.isEmpty(bookName);
    }
}
