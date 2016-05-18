package appprojgrp_nineteen.det_brugerinddragende_hospital.Models;

import android.content.ContentValues;

public class BaseModel {
    public int id;

    public String tableName() {
        return getClass().getSimpleName().toString().toLowerCase();
    }

    public ContentValues getContentValues() {
        return new ContentValues();
    }
}
