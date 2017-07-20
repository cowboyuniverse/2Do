package com.sargent.mark.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
//import android.widget.CheckBox;


import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.DBHelper;

public class MainActivity extends AppCompatActivity implements AddToDoFragment.OnDialogCloseListener, UpdateToDoFragment.OnUpdateDialogCloseListener{
    private RecyclerView rv;
    private FloatingActionButton button;
    private DBHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;
    ToDoListAdapter adapter;
    private final String TAG = "mainactivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "oncreate called in main activity");
        button = (FloatingActionButton) findViewById(R.id.addToDo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddToDoFragment frag = new AddToDoFragment();
                frag.show(fm, "addtodofragment");
            }
        });
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    // https://developer.android.com/guide/topics/resources/menu-resource.html
    //inspiration from the api
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


//    inspired from android api https://developer.android.com/guide/topics/ui/menus.html
//    taken from the id of menu.xml, getting the user's input and running sql script
//    idea dn use of swapcursor() is inspired by android api also the code retreiving data is in api
//    https://developer.android.com/guide/components/loaders.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.all:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                    cursor = getAllItems(db);
                    adapter.swapCursor(cursor);
                }
                return true;
            case R.id.personal:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                    cursor = getCategory(db, "Personal");
                    adapter.swapCursor(cursor);
                }
                return true;
            case R.id.work:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                    cursor = getCategory(db, "Work");
                    adapter.swapCursor(cursor);
                }
                return true;
            case R.id.school:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                    cursor = getCategory(db, "School");
                    adapter.swapCursor(cursor);

                }
                return true;
            case R.id.uncategorized:
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                else {
                    item.setChecked(true);
                    cursor = getCategory(db, "Uncategorized");
                    adapter.swapCursor(cursor);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//  http://hmkcode.com/android-simple-sqlite-database-tutorial/
//  referencing how to use the cursor with the sql db

//       Cursor cursor = db.query(TABLE_BOOKS, // a. table
//                                    COLUMNS, // b. column names
//                                  " id = ?", // c. selections
//        new String[] { String.valueOf(id) }, // d. selections args
//                                       null, // e. group by
//                                       null, // f. having
//                                       null, // g. order by
//                                      null); // h. limit

    private Cursor getAllItems(SQLiteDatabase db) {
        return db.query(
                Contract.TABLE_TODO.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE
        );
    }

// Getting query using cusor adapter for the switch statement.
// http://hmkcode.com/android-simple-sqlite-database-tutorial/
    private Cursor getCategory(SQLiteDatabase db, String category) {
        String selections = Contract.TABLE_TODO.COLUMN_NAME_CATEGORY  + "=?";
        String [] selections_args = new String[] { category };
        return  db.query(
                Contract.TABLE_TODO.TABLE_NAME,
                null,
                selections,
                selections_args,
                null,
                null,
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (db != null) db.close();
        if (cursor != null) cursor.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        cursor = getAllItems(db);
        adapter = new ToDoListAdapter(cursor, new ToDoListAdapter.ItemClickListener() {
//     added category onto the paremeter and therfore needed to change many other paramters functions
//     category is automatically changed within database when it's chosen within the spinner of the fragment once its created
            @Override
            public void onItemClick(int pos, String description, String duedate,String category, long id) {
                Log.d(TAG, "item click id: " + id);
                String[] dateInfo = duedate.split("-");
                int year = Integer.parseInt(dateInfo[0].replaceAll("\\s",""));
                int month = Integer.parseInt(dateInfo[1].replaceAll("\\s",""));
                int day = Integer.parseInt(dateInfo[2].replaceAll("\\s",""));

                FragmentManager fm = getSupportFragmentManager();

                UpdateToDoFragment frag = UpdateToDoFragment.newInstance(year, month, day, description, category,id);
                frag.show(fm, "updatetodofragment");
            }

//   With OnCheckedChangeListener you receive an event whenever the checked status changes, even when done in code by using .setChecked().
//   https://stackoverflow.com/questions/22564113/oncheckedchangelistener-or-onclicklistener-with-if-statement-for-checkboxs-whi
//   placing value if check its true and its 1, if it's unchecked it's false and 0 which is autogenerated as zero in sql
            @Override
            public void onCheckedChanged(long id, boolean done) {
                int doneValue;
                if (done == 1 > 0)
                    doneValue = 1;
                else
                    doneValue = 0;
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_TODO.COLUMN_NAME_DONE, doneValue);
                db.update(Contract.TABLE_TODO.TABLE_NAME, cv, Contract.TABLE_TODO._ID + "=" + id, null);
            }
        });
        rv.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long id = (long) viewHolder.itemView.getTag();
                Log.d(TAG, "passing id: " + id);
                removeToDo(db, id);
                adapter.swapCursor(getAllItems(db));
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public void closeDialog(int year, int month, int day, String description,String category) {
        addToDo(db, description, formatDate(year, month, day), category);
        cursor = getAllItems(db);
        adapter.swapCursor(cursor);
    }

    public String formatDate(int year, int month, int day) {
        return String.format("%04d-%02d-%02d", year, month +1, day);
    }

    //added category to use values for updating the databse
    private long addToDo(SQLiteDatabase db, String description, String duedate,String category) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION, description);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE, duedate);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY, category);
        return db.insert(Contract.TABLE_TODO.TABLE_NAME, null, cv);
    }
    private boolean removeToDo(SQLiteDatabase db, long id) {
        Log.d(TAG, "deleting id: " + id);
        return db.delete(Contract.TABLE_TODO.TABLE_NAME, Contract.TABLE_TODO._ID + "=" + id, null) > 0;
    }

    //added category to use values for updating the databse
    private int updateToDo(SQLiteDatabase db, int year, int month, int day, String description, String category,long id){
        String duedate = formatDate(year, month - 1, day);
        ContentValues cv = new ContentValues();
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION, description);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE, duedate);
        cv.put(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY, category);
        return db.update(Contract.TABLE_TODO.TABLE_NAME, cv, Contract.TABLE_TODO._ID + "=" + id, null);
    }
    @Override
    public void closeUpdateDialog(int year, int month, int day, String description,String category, long id) {
        updateToDo(db, year, month, day, description,category, id);
        adapter.swapCursor(getAllItems(db));
    }
}


