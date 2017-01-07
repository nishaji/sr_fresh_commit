package sprytechies.skillregister.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import sprytechies.skillregister.injection.ApplicationContext;


@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "skill.db";
    public static final int DATABASE_VERSION = 2;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        //Uncomment line below if you want to enable foreign keys
        //db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(Db.RibotProfileTable.CREATE_LIVE_SYNC_STATUS);
            db.execSQL(Db.RibotProfileTable.CREATE_AWARDS);
            db.execSQL(Db.RibotProfileTable.CREATE_CERTIFICATE);
            db.execSQL(Db.RibotProfileTable.CREATE_CONTACT);
            db.execSQL(Db.RibotProfileTable.CREATE_EDUCATION);
            db.execSQL(Db.RibotProfileTable.CREATE_EXP);
            db.execSQL(Db.RibotProfileTable.CREATE_PROJECT);
            db.execSQL(Db.RibotProfileTable.CREATE_PUBLICATION);
            db.execSQL(Db.RibotProfileTable.CREATE_SIGUP);
            db.execSQL(Db.RibotProfileTable.CREATE_VOLUNTEER);
            db.execSQL(Db.RibotProfileTable.CREATE_SAVE_PROFILE);
            //Add other tables here
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
