java -cp %HOMEPATH%\Dropbox\MyProjects\DataStuff\VersionControl\classes; VersionControl %HOMEPATH%\Dropbox\MyProjects\ExpenseAdvisor java

javac -d classes  src\*.java

java -cp classes;libs\sqlite-jdbc-3.7.2.jar ExpenseAdvisor %1 %2 %3 %4 %5 %6 %7 %8 %9