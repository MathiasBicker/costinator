package de.bandb.costinator.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME 			= "costinator.db";
	private static final int	DATABASE_VERSION 		= 1;
	private static final String TAG 					= DatabaseHelper.class.getName();
	
	
	public DatabaseHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, TCostgroup.class);
			TableUtils.createTable(connectionSource, TCostelement.class);
		}catch(SQLException e) {
			Log.e(TAG, "Can't create database", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource arg1, int oldVersion, int newVersion) {
		try {
			Log.i(TAG, "onUpgrade");
			TableUtils.dropTable(connectionSource, TCostgroup.class, true);
			TableUtils.dropTable(connectionSource, TCostelement.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		}catch (SQLException e) {
			Log.e(TAG, "Can't drop databases", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
