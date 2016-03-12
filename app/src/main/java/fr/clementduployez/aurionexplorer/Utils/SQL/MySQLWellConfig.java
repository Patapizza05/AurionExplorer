package fr.clementduployez.aurionexplorer.Utils.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yarolegovich.wellsql.DefaultWellConfig;
import com.yarolegovich.wellsql.WellTableManager;
import com.yarolegovich.wellsql.mapper.SQLiteMapper;

import java.util.Map;

import fr.clementduployez.aurionexplorer.MesNotes.GradesInfo;

/**
 * Created by cdupl on 3/12/2016.
 */
public class MySQLWellConfig extends DefaultWellConfig {

    private static final String DB_NAME = "AurionDatabase";
    private static final int DB_VERSION = 1;

    public MySQLWellConfig(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, WellTableManager helper) {
        helper.createTable(GradesInfo.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, WellTableManager helper, int i, int i1) {
        helper.dropTable(GradesInfo.class);
        onCreate(sqLiteDatabase, helper);
    }

    @Override
    public int getDbVersion() {
        return DB_VERSION;
    }

    @Override
    public String getDbName() {
        return DB_NAME;
    }
}
