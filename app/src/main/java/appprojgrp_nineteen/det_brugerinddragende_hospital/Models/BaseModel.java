package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class BaseModel {
    public int id;

    public String tableName() {
        return getClass().getSimpleName().toLowerCase();
    }

    public abstract ContentValues getContentValues();
    public abstract void populateFromCursor(Cursor cursor);
}
