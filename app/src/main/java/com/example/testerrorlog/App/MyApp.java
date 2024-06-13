package com.example.testerrorlog.App;

import android.app.Application;

import java.io.File;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // установка обработчика отлавливающего ошибки
        Thread.setDefaultUncaughtExceptionHandler(new MyCustomExceptionHandler(
            new File(this.getFilesDir(), "error_log.txt")
        ));

    }
}
