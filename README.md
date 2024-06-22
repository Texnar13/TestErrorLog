
# Демо проект по сохранению логов ошибок в текстовый файл

На экране приложения находятся текстовое поле и две кнопки.

При нажатии на кнопку "Throw Exception", приложение выкидывает ошибку с текстом введенным в поле выше.

При нажатии на кнопку "Get Exception File" из приложения выгружаются текстовый файл содержащий логи ошибок.


В файл собираются только логи ошибок и никаких других дополнительных логов.

### Важные файлы проекта
Основная логика отлова ошибок находится в каталоге
[/main/java/com/example/testerrorlog/App](https://github.com/Texnar13/TestErrorLog/tree/master/app/src/main/java/com/example/testerrorlog/App) 

- В `MyApp.java` используется `setDefaultUncaughtExceptionHandler()`, позволяющий отлавливать все ошибки возникающие в приложении и передавать их в `MyCustomExceptionHandler`

- `MyCustomExceptionHandler.java` - Получает `Throwable` и вызывает у него метод `printStackTrace()` записывая содержимое в текстовый файл

- В файле [AndroidManifest.xml](https://github.com/Texnar13/TestErrorLog/blob/master/app/src/main/AndroidManifest.xml)   подключается `MyApp.java`в качестве базового класса приложения строкой `android:name=".App.MyApp"`

Выгрузка файла

- Также в `AndroidManifest.xml` прописан `FileProvider` для экспорта файла с ошибками
```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false"
    android:grantUriPermissions="true">
    <!-- resource file to create -->
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_path" />
</provider>
```

- Путь для `FileProvider` прописан в [file_path.xml](https://github.com/Texnar13/TestErrorLog/blob/master/app/src/main/res/xml/file_path.xml) с помощью строки `<files-path name="my_internal_folder" path="/"/>`, которая используется для метода `Context.getFilesDir()`

UI

- Вызов ошибки и экспорт файла прописаны в [MainActivity.java](https://github.com/Texnar13/TestErrorLog/blob/master/app/src/main/java/com/example/testerrorlog/MainActivity.java)
 


