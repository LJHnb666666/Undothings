package com.google.samples.apps.sunflower.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.samples.apps.sunflower.bean.MyUndoListBean;
import com.google.samples.apps.sunflower.bean.UndoBean;
import com.google.samples.apps.sunflower.dao.MyUndoListDao;
import com.google.samples.apps.sunflower.dao.UndoDao;
import com.google.samples.apps.sunflower.utilites.Constants;
import com.google.samples.apps.sunflower.works.SeedDatabaseWorker;

/**
 * 数据中心， 注意：整个APP 数据源获取的 唯一入口，就是此类
 *
 * The Room database for this app
 * 此应用程序的房间数据库
 */
@Database(entities = {MyUndoListBean.class, UndoBean.class}, version = 1, exportSchema = false)
// 解决此错误：Cannot figure out how to save this field into database. You can consider adding a type
// 必须导入自己的 Converters
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MyUndoListDao getMyUndoListDao();

    public abstract UndoDao getUndoDao();

    private static volatile AppDatabase instance;

    // 单例模式
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if(instance == null){
                    Log.v("ljh","AppDatabase的getInstance"+context);
                    instance = buildDatabase(context); // 一次
                }
            }
        }
        return instance;
    }

    // Create and pre-populate the database. See this article for more details:
    // 创建并预填充数据库。
    // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
    private static AppDatabase buildDatabase(Context context) {
        Log.v("ljh","buildDatabase");
        return Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.v("ljh","buildDatabase，下面是导入数据了");
                        // 此任务没有任何约束条件， 马上执行

                        WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(SeedDatabaseWorker.class));
                    }
                })

                // 到这里之后：就已经去插入数据到数据库了

                .build();
    }
}
