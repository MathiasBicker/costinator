package de.bandb.costinator.database;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * 
 * Helper-class that manages the communication between the entity-classes
 * and the databasetables
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME 		= "costinator.db";
	private static final int	DATABASE_VERSION 	= 1;
	private static final String TAG 				= DatabaseHelper.class.getName();
	private static final String GROUPDELETED		= "deleted group: ";
	private static final String ELEMENTDELETED 		= "deleted element: ";
	private static final String GROUPUPDATED		= "updated group: ";
	private static final String ELEMENTUPDATED		= "updated element: ";
	private static final String GROUPCREATED		= "created group: ";
	private static final String ELEMENTCREATED 		= "created element: ";
	
	//exception error messages
	private static final String QUERYALLCOSTGROUP		= "failed database access querying costgroups";
	private static final String QUERYALLCOSTELEMENT 	= "failed database access querying costelements";
	private static final String QUERYCOSTGROUP			= "failed database access querying costgroup";
	private static final String QUERYCOSTELEMENT 		= "failed database access querying costelement";
	private static final String UPDATEGROUP 			= "failed database access updating costgroups";
	private static final String UPDATEELEMENT 			= "failed database access updating costelements";
	private static final String CREATEGROUP 			= "failed database access creating costgroups";
	private static final String CREATEELEMENT 			= "failed database access creating costelements";
	private static final String DELETEGROUP 			= "failed database access deleting costgroups";
	private static final String DELETEELEMENT 			= "failed database access deleting costelements";
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, TCostgroup.class);
			TableUtils.createTable(connectionSource, TCostelement.class);
			TCostgroup car = new TCostgroup();
			car.setName("Auto");
			car.setDescription("BMW 118d, weiss");
			car.setMonthlyCost(0.0);
			create(car);
			TCostgroup flat = new TCostgroup();
			car.setName("Wohnung");
			car.setDescription("Mainzer Str. 197, 66121");
			car.setMonthlyCost(0.0);
			create(flat);
			TCostgroup master = new TCostgroup();
			car.setName("Master-Studium");
			car.setDescription("Lebensunterhalt");
			car.setMonthlyCost(0.0);
			create(master);
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
	
	public List<TCostgroup> queryAllCostgroups() {
		List<TCostgroup> list = null;
		try {
			Dao<TCostgroup, Integer> dao = getDao(TCostgroup.class);
			list = dao.queryForAll();
		}catch (SQLException e) {
			Log.e(TAG, QUERYALLCOSTGROUP);
		}
		return list;
	}
	
	public List<TCostelement> queryAllCostelements(TCostgroup group) {
		List<TCostelement> list = null;
		try {
			Dao<TCostelement, Integer> dao = getDao(TCostelement.class);
			list = dao.queryForAll();
			dao.queryBuilder().where().eq(TCostelement.COSTGROUP, group.getId()).query();
		}catch (SQLException e) {
			Log.e(TAG, QUERYALLCOSTELEMENT);
		}
		return list;
	}
	
	public void update(TCostgroup group) {
		try {
			Dao<TCostgroup, Integer> dao = getDao(TCostgroup.class);
			dao.update(group);
			Log.i(TAG, GROUPUPDATED + group);
		}catch (SQLException e) {
			Log.e(TAG, UPDATEGROUP);
		}
	}
	
	public void update(TCostelement element) {
		try {
			Dao<TCostelement, Integer> dao = getDao(TCostelement.class);
			dao.update(element);
			Log.i(TAG, ELEMENTUPDATED + element);
		}catch (SQLException e) {
			Log.e(TAG, UPDATEELEMENT);
		}
	}
	
	public void create(TCostgroup group) {
		try {
			Dao<TCostgroup, Integer> dao = getDao(TCostgroup.class);
			dao.create(group);
			Log.i(TAG, GROUPCREATED + group);
		}catch (SQLException e) {
			Log.e(TAG, CREATEGROUP);
		}
	}
	
	public void create(TCostelement element) {
		try {
			Dao<TCostelement, Integer> dao = getDao(TCostelement.class);
			dao.create(element);
			Log.i(TAG, ELEMENTCREATED + element);
		}catch (SQLException e) {
			Log.e(TAG, CREATEELEMENT);
		}
	}
	
	public void delete(TCostgroup group) {
		try {
			Dao<TCostgroup, Integer> dao = getDao(TCostgroup.class);
			List<TCostelement> elements = queryAllCostelements(group);
			for(TCostelement c : elements)
				delete(c);
			dao.delete(group);
			Log.i(TAG, GROUPDELETED + group);
		}catch (SQLException e) {
			Log.e(TAG, DELETEGROUP);
		}
	}
	
	public void delete(TCostelement element) {
		try {
			Dao<TCostelement, Integer> dao = getDao(TCostelement.class);
			dao.delete(element);
			Log.i(TAG, ELEMENTDELETED + element);
		}catch (SQLException e) {
			Log.e(TAG, DELETEELEMENT);
		}
	}
	
	public TCostgroup queryCostgroup(int id) {
		TCostgroup group = null;
		try {
			Dao<TCostgroup, Integer> dao = getDao(TCostgroup.class);
			group = dao.queryForId(id);
		}catch (SQLException e) {
			Log.e(TAG, QUERYCOSTGROUP);
		}
		return group;
	}
	
	public TCostelement queryCostelement(int id) {
		TCostelement element = null;
		try {
			Dao<TCostelement, Integer> dao = getDao(TCostelement.class);
			element = dao.queryForId(id);
		}catch (SQLException e) {
			Log.e(TAG, QUERYCOSTELEMENT);
		}
		return element;
	}
}
