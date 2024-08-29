SET @start = 51;
update labmgmt_test_request_item  set initial_sample_id =null , referral_out_sample_id =null, final_result_id =null where test_request_item_id  > @start;
delete FROM labmgmt_test_result tr where tr.test_request_item_sample_id in (select ltris.test_request_item_sample_id  from labmgmt_test_request_item_sample ltris where  ltris.test_request_item_id > @start);
delete from labmgmt_test_request_item_sample ltris where  ltris.test_request_item_id > @start;
delete from labmgmt_test_request_item where test_request_item_id  > @start;
delete from labmgmt_sample ls where ls.test_request_id  in (select tr.test_request_id  from labmgmt_test_request tr where (select count(*) from labmgmt_test_request_item tri where tri.test_request_id = tr.test_request_id) = 0);
delete from labmgmt_test_request tr where (select count(*) from labmgmt_test_request_item tri where tri.test_request_id = tr.test_request_id) = 0;