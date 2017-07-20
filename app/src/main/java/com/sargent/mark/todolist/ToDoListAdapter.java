package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.sargent.mark.todolist.data.Contract;


/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, String category, long id);

//        click listerner with main activity and checkbox
//        https://stackoverflow.com/questions/21814150/check-if-any-checkbox-is-checked
        void onCheckedChanged(long id, boolean done);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descr;
        TextView due;
        TextView cat;
        CheckBox cb;
        String duedate;
        String description;
        String category;
        int doneValue;
        long id;


        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);

//            creating a textview for category
            cat = (TextView) view.findViewById(R.id.category_textview);
            cb = (CheckBox) view.findViewById(R.id.checkBox_xml);



            //http://www.technotalkative.com/android-checkbox-example/
            //https://developer.android.com/reference/android/widget/CompoundButton.OnCheckedChangeListener.html
            //creating a boolean comparison with int because sql is an integer value
            cb.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean done) {
                            if (done == 1 > 0)
                                doneValue = 1;
                            else
                                doneValue = 0;
                            listener.onCheckedChanged(id, done);
                        }
                    });
            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {

            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);
            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            //binding the cursor to get the spinner
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));

            //binding the cursor for the checkbox
            doneValue = cursor.getInt(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DONE));

            descr.setText(description);
            due.setText(duedate);
            cat.setText(category);

//testing
//            if (doneValue==1)
//                cb.setChecked(!cb.isChecked());

            //if the checkbox is changed to true
            cb.setChecked(doneValue ==1);

            holder.itemView.setTag(id);
        }
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, category, id);
        }
    }

}