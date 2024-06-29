
--  Auto-generated SQL script #202407040854
INSERT INTO labmgmt_approval_config (approval_title,privilege,pending_status,returned_status,rejected_status,approved_status,creator,date_created,uuid)

VALUES ('Supervisor Test Result Approver','Task: labmanagement.testrequests.approve','Approval Pending','Returned by Approver','Rejected By Approver','Approved',1,now(),uuid());

--  Auto-generated SQL script #202407050030
INSERT INTO esmugemr.labmgmt_approval_flow (name,system_name,level_one,level_one_allow_owner,creator,date_created, uuid)
	VALUES ('Default','Default',1,1,1,now(), uuid());


INSERT INTO esmugemr.privilege (privilege, description, uuid) VALUES('Task: labmanagement.testresults.approve', 'Able to approve lab test results', 'b887639b-35c8-11ef-9df0-00155d919f83');

INSERT INTO esmugemr.labmgmt_approval_config (approval_title,privilege,pending_status,returned_status,rejected_status,approved_status,creator,date_created,changed_by,date_changed,voided,voided_by,date_voided,void_reason,uuid) VALUES
	 ('Supervisor Approval','Task: labmanagement.testresults.approve','Pending Supervisor Approval','Returned By Supervisor','Rejected By Supervisor','Approved By Supervisor',1,'2024-07-10 10:45:44',NULL,NULL,0,NULL,NULL,NULL,'2c89e7a6-484c-483b-b056-f67ffb62f24d');

