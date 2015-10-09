package com.rothen.rmetarwear.Exceptions;

import android.database.sqlite.SQLiteException;

/**
 * Created by stéphane on 09/10/2015.
 */
public class SQLiteInsertException extends SQLiteException {
    public SQLiteInsertException() {
        super();
    }

    public SQLiteInsertException(String error) {
        super(error);
    }
}
