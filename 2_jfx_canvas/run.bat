set JDK=C:\java\jdk-17.0.2
set JFX=C:\java\javafx-sdk-17.0.2
set OPT=--module-path %JFX%\lib --add-modules javafx.controls,javafx.fxml
start %JDK%\bin\java -cp bin %OPT% example1
start %JDK%\bin\java -cp bin %OPT% example2
start %JDK%\bin\java -cp bin %OPT% example3
start %JDK%\bin\java -cp bin %OPT% example4
