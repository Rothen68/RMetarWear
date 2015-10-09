package com.rothen.rmetarwear.Exceptions;

import java.sql.SQLException;

/**
 * Created by stéphane on 09/10/2015.
 */
public class SQLiteDeleteException extends SQLException {
    public SQLiteDeleteException() {
        super();
    }

    public SQLiteDeleteException(String error) {
        super(error);
    }
}
