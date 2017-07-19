package com.sargent.mark.todolist.data;

import android.provider.BaseColumns;

/**
 * Created by mark on 7/4/17.
 */

public class Contract {

    public static class TABLE_TODO implements BaseColumns{
        public static final String TABLE_NAME = "todoitems";

        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";

        //0 is FALSE 1 is TRUE
        public static final String COLUMN_NAME_ISCHECKED = "isChecked" ;

        //CREATING CATEGORY STRING
        public static final String COLUMN_NAME_CATEGORY = "category" ;


        //CREATING A TESTING COLUMN
        public static final String COLUMN_NAME_DESCRIPTION2 = "description2";

    }
}
