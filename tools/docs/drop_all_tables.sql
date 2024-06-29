DROP TABLE labmgmt_test_request_item_sample;
ALTER TABLE esmugemr.labmgmt_test_approval DROP FOREIGN KEY labmgmt_test_approval_test_result_fk;
ALTER TABLE esmugemr.labmgmt_test_result DROP FOREIGN KEY labmgmt_test_result_test_approval_fk;

DROP TABLE labmgmt_test_approval;
DROP TABLE labmgmt_test_result_document;
DROP TABLE labmgmt_test_result;
DROP TABLE labmgmt_test_config;
DROP TABLE labmgmt_approval_flow;
DROP TABLE labmgmt_approval_config;
DROP TABLE labmgmt_worksheet_item;
DROP TABLE labmgmt_worksheet;
DROP TABLE labmgmt_sample_activity;
#DROP TABLE labmgmt_bio_repository_item;
#DROP TABLE labmgmt_bio_repository_item_move_line;
DROP TABLE labmgmt_test_request_item;
DROP TABLE labmgmt_sample;
DROP TABLE labmgmt_test_request;
DROP TABLE labmgmt_referral_location;
delete from liquibasechangelog where ID like 'labmanagement_%';

