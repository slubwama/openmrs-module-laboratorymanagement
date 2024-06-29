rem liquibase --driver=com.mysql.jdbc.Driver --changeLogFile=./structure.xml --url="jdbc:mysql://mysql.mysite.com" --username=<myuser> --password=<mypass> generateChangeLog
rem F:\software\liquibase-4.14.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/capture?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs generateChangeLog 
rem --url="jdbc:mysql://mysql.mysite.com/database_name_here"

rem F:\software\liquibase-1.9.5\liquibase.bat  --driver=com.mysql.jdbc.Driver --changeLogFile=hibernateliquibase.xml --url="jdbc:mysql://127.0.0.1:3306/kemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs generateChangeLog



D:\RnD\os\tools\liquibase-4.14.0\liquibase.bat  --driver=com.mysql.jdbc.Driver --changeLogFile=hibernateliquibase.xml --url="jdbc:mysql://127.0.0.1:3306/yaka?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=kyazze generateChangeLog

D:\RnD\os\tools\liquibase-4.14.0\liquibase.bat --diffTypes=data  --driver=com.mysql.jdbc.Driver --changeLogFile=hibernateliquibase.xml --url="jdbc:mysql://127.0.0.1:3306/yaka?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=kyazze generateChangeLog 