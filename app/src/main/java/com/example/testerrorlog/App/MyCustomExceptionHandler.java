package com.example.testerrorlog.App;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class MyCustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Thread.UncaughtExceptionHandler defaultUEH;
    private final File logFile;

    // конструктор
    public MyCustomExceptionHandler(File logFile) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.logFile = logFile;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable exception) {
        Log.e("testLog", "Отлов пошёл");
        try {
            // Запись логов в файл
            PrintWriter writer = new PrintWriter(new FileWriter(logFile, true));

            // Записываем в файл текущую дату
            writer.printf("\n\n ------------- %1$td-%1$tm %1$tH:%1$tM ------------- \n", new Date());
            // %tB for Locale-specific full month name, e.g. "January", "February".
            // %<td d for Day of month, formatted as two digits with leading zeros as necessary, < for reuse the last parameter.

            // запись стека ошибки
            exception.printStackTrace(writer);

            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Передача ошибки дальше стандартному обработчику
        if (defaultUEH != null) {
            defaultUEH.uncaughtException(thread, exception);
        }
    }

}
