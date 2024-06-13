package com.example.testerrorlog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.error_button).setOnClickListener(v -> {
            // выкинуть ошибку
            throw new RuntimeException(
                    ((EditText) findViewById(R.id.editTextText)).getText().toString()
            );

        });

        findViewById(R.id.get_file_button).setOnClickListener(v -> {

            // загружаем файл из памяти
            File logFile = new File(getApplicationContext().getFilesDir(), "error_log.txt");
            if (!logFile.exists()) {
                Toast.makeText(this, "Логов ошибок нет", Toast.LENGTH_SHORT).show();
                return;
            }

            // получаем путь до файла (https://stackoverflow.com/questions/48117511/exposed-beyond-app-through-clipdata-item-geturi)
            Uri fileUri = FileProvider.getUriForFile(
                    MainActivity.this,
                    "com.example.testerrorlog.provider", //(use your app signature + ".provider" )
                    logFile);

            // диалог отправки файла
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Error log");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(logFile));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Send error log"));

        });

    }
}