mvn org.openmrs.maven.plugins:openmrs-sdk-maven-plugin:setup-sdk
REM I linked my f:\workspace settings file to c:\users\jssenya
mvn openmrs-sdk:setup-sdk -U
6 -Ddistro=org.openmrs.module:ugandaemr:LATEST-SNAPSHOT
mvn openmrs-sdk:run -DserverId=openmrs

REM in module folder
mvn openmrs-sdk:watch -DserverId=openmrs

enable debug info on startup
UPDATE global_property
SET property_value = 'org.openmrs.api:info, org.springframework.context:debug'
WHERE property = 'log.level';


REM  

jdbc:mysql://127.0.0.1:3306/openmrs
jdbc:mysql://127.0.0.1:3306/kemr
jdbc:mysql://127.0.0.1:3306/ugandaemr?autoReconnect\=true&useUnicode\=true&characterEncoding\=UTF-8&sessionVariables\=default_storage_engine%3DInnoDB

REM made links from  users folder to f:\ for the following
REM mklink /D C:\Users\jssenyan\openmrs F:\workspace\openmrs
REM mklink F:\workspace\settings.xml C:\Users\jssenyan\.m2\settings.xml 


REM cd into openmrs module ugandaemr folder after cloning it and run below
REM mvn openmrs-sdk:watch -DserverId=openmrs

REM Error: Module UgandaEMR cannot be started because it requires the following module(s): patientflags Please install and start these modules first.



mvn  openmrs-sdk:run -DserverId=kemr

org.openmrs.distro:referenceapplication-package:2.3.1


mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:LATEST-SNAPSHOT
mvn openmrs-sdk:run -DserverId=ugandaemr

mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:3.3.9-20220705.091254-3

To setup Ugandaemr

mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:3.4.2-SNAPSHOT -DserverId=ugandaemr


Commands to watch and unwatch folders for stockmanagement and ugandaemr/org****

mvn openmrs-sdk:watch -DserverId=ugandaemr -DskipTests

mvn openmrs-sdk:unwatch -DserverId=ugandaemr -DskipTests 

Watching allows to build the project and deploy it to the server when you run the server


mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:3.4.2-SNAPSHOT -DserverId=ugemr

mvn openmrs-sdk:deploy -Ddistro=org.openmrs.module:ugandaemr:3.4.2-SNAPSHOT 

mvn openmrs-sdk:run -DserverId=ugemr -DskipTests  -Dfork=false
mvn -o openmrs-sdk:run -DserverId=ugemr -DskipTests  -Dfork=false

Commands to watch and unwatch folders for stockmanagement and ugemr/org****

mvn openmrs-sdk:watch -DserverId=ugemr -DskipTests

mvn openmrs-sdk:unwatch -DserverId=ugemr -DskipTests 

# get repos and do a mvn install
# run inside uemr mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:3.4.2-SNAPSHOT -DserverId=ugemr
# run inside plugin mvn -o openmrs-sdk:run -DserverId=ugemr -DskipTests -Dfork=false
# use the -o parameter such that mvn does not keep checking latest


jdbc:mysql://127.0.0.1:3306/ugemr
jdbc:mysql://127.0.0.1:3306/ugemr?autoReconnect\=true&useUnicode\=true&characterEncoding\=UTF-8&sessionVariables\=default_storage_engine%3DInnoDB
jdbc:mysql://root:kyazze@127.0.0.1:3306/ugemr?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine%3DInnoDB
jdbc:mysql://openmrs:kyazze@127.0.0.1:3306/ugemr?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&sessionVariables=default_storage_engine%3DInnoDB


used high ports 44305 for http and 44304 for debugging
faced problem connecting to database had to use an account with username and password

mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:3.4.1 -DserverId=ugemr

mvn openmrs-sdk:deploy -Ddistro=org.openmrs.module:ugandaemr:3.4.1

mvn openmrs-sdk:run -DserverId=ugemr -DskipTests  -Dfork=false


mvn openmrs-sdk:deploy -Ddistro=org.openmrs.module:stockmanagement:1.0.2-SNAPSHOT

The 3.x branch is using the openmrs 4.0.0 SNAPSHOT below


mvn openmrs-sdk:setup -Ddistro=org.openmrs.module:ugandaemr:4.0.0-SNAPSHOT -DserverId=esmugemr

mvn openmrs-sdk:run -DserverId=esmugemr -DskipTests  -Dfork=false

ALTER TABLE `esmugemr`.`stockmgmt_stock_item` 
ADD COLUMN `stock_reference_code` VARCHAR(25) NULL DEFAULT NULL,
ADD COLUMN `stock_source_id` INT(11) NULL DEFAULT NULL;


mysql to disable ONLY_FULL_GROUP_BY
SET PERSIST sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));


--====
rem you need  a -U parameter
mvn openmrs-sdk:setup -U -Ddistro=org.openmrs.module:ugandaemr:4.0.0-SNAPSHOT -DserverId=esmugemr

Linux ports 44305 for http and 44304 for debugging
Windows ports 44308 for http and 44307 for44 debugging


jdbc:mysql://127.0.0.1:3306/esmugemr?autoReconnect\=true&useUnicode\=true&characterEncoding\=UTF-8&sessionVariables\=default_storage_engine%3DInnoDB

rem run the run command
mvn openmrs-sdk:run -DserverId=esmugemr -DskipTests  -Dfork=false

access the browser and it fails
run the concept dictionary

rrestart 
mvn -o openmrs-sdk:run -DserverId=esmugemr -DskipTests  -Dfork=false


rem when sam updates the 4.0.0-SNAPSHOT you can apply the latest changes using the deploy command
mvn openmrs-sdk:deploy -U -Ddistro=org.openmrs.module:ugandaemr:4.0.0-SNAPSHOT -DserverId=esmugemr

run again using 
mvn openmrs-sdk:run -DserverId=esmugemr -DskipTests  -Dfork=false

re-run the concept dictionary when you receive errors related to duplicate concepts keys


ERROR - SqlExceptionHelper.logExceptions(142) |2024-04-09T17:30:07,200| Unknown column 'this_.token_expiry_date' in 'field list'


delete the change log entry
delete from liquibasechangelog l  where ID  like '%ohri-mamba-setup00032%';
delete from liquibasechangelog l  where ID  like '%ugandaemrsync-20230302-0940%';

the latest snapshop failed so I return back to the last one that worked before 13th April

openmrsconfig.zip
Try unzipping that in the runtime  directory of  any server you are running.  Then run the ./build.sh file

rem does not work 44305mvn openmrs-sdk:setup -U -Ddistro=org.openmrs.module:ugandaemr:4.0.0-20240612.103412-167 -DserverId=esmugemr