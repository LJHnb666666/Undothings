package com.google.samples.apps.sunflower.works;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.samples.apps.sunflower.roomconfig.AppDatabase;
import com.google.samples.apps.sunflower.roombean.UndoBean;
import com.google.samples.apps.sunflower.utilites.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class SeedDatabaseWorker extends Worker {

    private static final String TAG = SeedDatabaseWorker.class.getSimpleName();

    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            InputStream input = getApplicationContext().getAssets().open(Constants.UNDO_DATA_FILENAME);
            JsonReader reader = new JsonReader(new InputStreamReader(input));
            Type undoType = new TypeToken<List<UndoBean>>(){}.getType();
            List<UndoBean> undoList = new Gson().fromJson(reader, undoType);
            input.close();

            AppDatabase database = AppDatabase.getInstance(getApplicationContext());

            database.getUndoDao().insertAll(undoList);

            Log.v("ljh","doWork这里，任务成功");
            return Result.success();  // 本次任务成功

        } catch (IOException e) {
            return Result.failure(); // 本次任务失败
        }
    }
}
