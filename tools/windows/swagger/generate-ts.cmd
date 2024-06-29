curl http://localhost:44305/openmrs/module/webservices/rest/swagger.json -o ../../../../openmrs-typescript/openmrs.json 
mkdir ../../../../../openmrs-typescript/codegen
java -Dmodels -jar ./swagger-codegen-cli-3.0.41.jar generate -i ../../../../openmrs-typescript/openmrs.json  -o ../../../../openmrs-typescript/codegen -l typescript-axios
