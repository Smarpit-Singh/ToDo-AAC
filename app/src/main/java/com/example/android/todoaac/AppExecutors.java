package com.example.android.todoaac;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutors {

    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThred;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor mainThred, Executor networkIO){
        this.diskIO = diskIO;
        this.mainThred = mainThred;
        this.networkIO = networkIO;
    }


    public static AppExecutors getsInstance(){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutors());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThred() {
        return mainThred;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutors implements Executor {

        private Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
        mainHandler.post(command);
        }
    }
}
