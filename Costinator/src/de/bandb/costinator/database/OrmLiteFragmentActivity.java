package de.bandb.costinator.database;

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
