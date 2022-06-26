package com.google.samples.apps.sunflower.utilites;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 *TODO 全局线程池任务， 给整个项目 用 网络线程池，磁盘线程池，主线程 都很方便 的使用此类即可
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor diskIO;  // 磁盘任务IO

    private final Executor networkIO; // 网络任务IO

    private final Executor mainThread; // 主线程任务

    // 只是配合单例模式用的 Holder 而已
    private static final class InstanceHolder {
        private static AppExecutors instance = new AppExecutors(new DiskIOThreadExecutor(),  // 磁盘任务IO 异步的

                Executors.newFixedThreadPool(THREAD_COUNT),  // 网络任务IO 异步的

                new MainThreadExecutor() // 主线程

        );
    }

    // 单例模式
    public static AppExecutors getInstance() {
        return InstanceHolder.instance;
    }

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    // TODO 真正对外暴漏出去被调用的函数 （磁盘线程池 异步）
    public Executor diskIO() {
        return diskIO;
    }

    // TODO 真正对外暴漏出去被调用的函数 （网络线程池 异步）
    public Executor networkIO() {
        return networkIO;
    }

    // TODO 真正对外暴漏出去被调用的函数 （主线程 非异步）
    public Executor mainThread() {
        return mainThread;
    }

    // 主线程任务
    private static class MainThreadExecutor implements Executor {
        // 从异步线程 到 UI线程  Looper.getMainLooper()
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    // 磁盘任务IO
    static class DiskIOThreadExecutor implements Executor {

        private final Executor mDiskIO;

        public DiskIOThreadExecutor() {
            mDiskIO = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mDiskIO.execute(command);
        }
    }

}
