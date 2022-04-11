#TeamCS

Feel free to fork this repository and work on it or play around with the code
Let me know if there's any suggestions or errors you spotted
Thanks!

To compile JavaFX on command line(windows):

download javaFX sdk:
https://gluonhq.com/products/javafx/

run the command *change to the proper full path to javafx-sdk\lib*

set PATH_TO_FX="path to \javafx-sdk-17.0.2\lib"

javac --module-path %PATH_TO_FX% --add-modules javafx.controls roomSystem.java

java --module-path %PATH_TO_FX% --add-modules javafx.controls roomSystem
