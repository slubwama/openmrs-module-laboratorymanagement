rem liquibase --driver=com.mysql.jdbc.Driver --changeLogFile=./structure.xml --url="jdbc:mysql://mysql.mysite.com" --username=<myuser> --password=<mypass> generateChangeLog
rem D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://172.18.86.78:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_approval_config, table:labmgmt_approval_flow, table:labmgmt_bio_repository_item, table:labmgmt_bio_repository_item_move_line, table:labmgmt_sample, table:labmgmt_test_approval, table:labmgmt_test_config, table:labmgmt_test_request_item, table:labmgmt_test_result, table:labmgmt_test_result_document, table:labmgmt_worksheet, table:labmgmt_worksheet_item"  generateChangeLog 

rem D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://172.18.86.78:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_test_request_item"  generateChangeLog 

rem --url="jdbc:mysql://mysql.mysite.com/database_name_here"

rem F:\software\liquibase-1.9.5\liquibase.bat  --driver=com.mysql.jdbc.Driver --changeLogFile=liquibase.xml --url="jdbc:mysql://127.0.0.1:3306/capture?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs generateChangeLog 


rem table:labmgmt_approval_config, table:labmgmt_approval_flow, table:labmgmt_bio_repository_item, table:labmgmt_bio_repository_item_move_line, table:labmgmt_sample, table:labmgmt_test_approval, table:labmgmt_test_config, table:labmgmt_test_request_item, table:labmgmt_test_result, table:labmgmt_test_result_document, table:labmgmt_worksheet, table:labmgmt_worksheet_item

del D:\RnD\os\src\liquidbase\liquibase2.xml

REM D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_approval_config, table:labmgmt_approval_flow, table:labmgmt_sample_activity, table:labmgmt_sample, table:labmgmt_test_approval, table:labmgmt_test_config, table:labmgmt_test_request, table:labmgmt_test_request_item, table:labmgmt_test_request_item_sample, table:labmgmt_test_result, table:labmgmt_test_result_document, table:labmgmt_worksheet, table:labmgmt_worksheet_item"  generateChangeLog 

rem org.openmrs.module.labmanagement.api.model

rem D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_referral_location"  generateChangeLog 

REM D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_batch_job, table:labmgmt_batch_job_owner"  generateChangeLog 



REM D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --diff-types=data --include-objects="table:labmgmt_approval_config, table:labmgmt_approval_flow"  generateChangeLog 


REM D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemr?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_storage, table:labmgmt_storage_unit"  generateChangeLog 

D:\RnD\os\tools\liquibase-4.28.0\liquibase.bat --log-level INFO  --driver=com.mysql.cj.jdbc.Driver --changeLogFile=D:\RnD\os\src\liquidbase\liquibase2.xml --url="jdbc:mysql://127.0.0.1:3306/esmugemrdocs?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine=InnoDB" --username=openmrs --password=openmrs --include-objects="table:labmgmt_document"  generateChangeLog 



pause