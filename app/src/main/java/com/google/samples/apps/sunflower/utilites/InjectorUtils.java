package com.google.samples.apps.sunflower.utilites;

import android.content.Context;
import android.util.Log;

import com.google.samples.apps.sunflower.data.AppDatabase;
import com.google.samples.apps.sunflower.dao.MyUndoListDao;
import com.google.samples.apps.sunflower.repository.MyUndoListRepository;
import com.google.samples.apps.sunflower.repository.UndoRepository;
import com.google.samples.apps.sunflower.factory.CommonUndoItemViewModelFactory;
import com.google.samples.apps.sunflower.factory.UndoDetailViewModelFactory;
import com.google.samples.apps.sunflower.factory.AllUndoListViewModelFactory;


public class InjectorUtils {

    private static MyUndoListRepository getMyUndoListRepository(Context context) {
        Log.v("ljh","InjectorUtils的getMyUndoListRepository");

        MyUndoListDao dao = AppDatabase.getInstance(context.getApplicationContext()).getMyUndoListDao();

        return MyUndoListRepository.getInstance(dao);
    }


    public static CommonUndoItemViewModelFactory provideCommonUndoItemViewModelFactory(Context context) {
        Log.v("ljh","InjectorUtils的provideCommonUndoItemViewModelFactory");
        MyUndoListRepository undoListRepository = getMyUndoListRepository(context);

        return new CommonUndoItemViewModelFactory(undoListRepository);
    }

    private static UndoRepository getUndoRepository(Context context) {
        return UndoRepository.getInstance(AppDatabase.getInstance(context.getApplicationContext()).getUndoDao());
    }

    public static AllUndoListViewModelFactory provideAllUndoListViewModelFactory(Context context) {

        UndoRepository undoRepository = getUndoRepository(context);

        return new AllUndoListViewModelFactory(undoRepository);
    }

    public static UndoDetailViewModelFactory providerUndoDetailViewModelFactory(Context context, String undoId) {
        UndoRepository undoRepository = getUndoRepository(context);
        MyUndoListRepository myUndoListRepository = getMyUndoListRepository(context);
        return new UndoDetailViewModelFactory(undoRepository, myUndoListRepository, undoId);
    }
}
