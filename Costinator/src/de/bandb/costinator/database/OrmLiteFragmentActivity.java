package de.bandb.costinator.database;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * activity class that provides the helper-class for the database
 */

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.support.v4.app.FragmentActivity;

public class OrmLiteFragmentActivity extends FragmentActivity {
    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
