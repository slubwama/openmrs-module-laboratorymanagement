package org.openmrs.module.labmanagement.api.impl;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.api.Privileges;
import org.openmrs.module.labmanagement.api.dto.TestRequestDTO;
import org.openmrs.module.labmanagement.api.dto.TestRequestItemDTO;
import org.openmrs.module.labmanagement.api.model.ApprovalResult;
import org.openmrs.module.labmanagement.api.model.TestRequestItem;
import org.openmrs.module.labmanagement.api.model.TestRequestItemStatus;
import org.openmrs.module.labmanagement.api.model.TestRequestStatus;
import org.openmrs.module.labmanagement.api.utils.GlobalProperties;
import org.openmrs.module.labmanagement.api.utils.Pair;

public class ApprovalUtils {
    public static boolean canApproveRequest(TestRequestDTO testRequest, ApprovalResult action){
        boolean canApproveRequest = testRequest != null && !testRequest.getVoided() &&
                TestRequestStatus.isRequestApproveable(testRequest.getStatus()) &&
                testRequest.getTests() != null && testRequest.getTests().stream().
                anyMatch(p-> ApprovalUtils.canApproveRequest(p, action));

        if(canApproveRequest){
            User authenticatedUser =  Context.getAuthenticatedUser();
            if(!GlobalProperties.getOwnerCanApproveTestRequests()){
                canApproveRequest = !authenticatedUser.getUserId().equals(testRequest.getCreator());
            }
            if(canApproveRequest) {
                canApproveRequest = authenticatedUser.hasPrivilege(Privileges.TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE);
            }
        }
        return canApproveRequest;
    }

    public static boolean canViewClinicalNote(TestRequestDTO testRequest){
        User authenticatedUser = Context.getAuthenticatedUser();
        if(authenticatedUser == null) return false;
        if(authenticatedUser.getUserId().equals(testRequest.getCreator()) ||
                authenticatedUser.getUserId().equals(testRequest.getProviderId()))
            return true;
        return authenticatedUser.hasPrivilege(Privileges.APP_LABMANAGEMENT_TESTRESULTS);
    }

    private static boolean canApproveRequest(TestRequestItemDTO testRequestItem, ApprovalResult action) {
        return testRequestItem != null && !testRequestItem.getVoided() &&
                TestRequestItemStatus.isRequestApproveable(testRequestItem.getStatus(), action);
    }
    public static Pair<Boolean,String> canApproveRequest(TestRequestItem testRequestItem, ApprovalResult action){
        String remarks = null;
        boolean canApproveRequest = testRequestItem != null && !testRequestItem.getVoided() &&
                TestRequestItemStatus.isRequestApproveable(testRequestItem.getStatus(), action);
        if(canApproveRequest){
            User authenticatedUser =  Context.getAuthenticatedUser();
            if(!GlobalProperties.getOwnerCanApproveTestRequests()){
                canApproveRequest = !authenticatedUser.getUserId().equals(testRequestItem.getCreator().getUserId());
                if(!canApproveRequest){
                    remarks="Can not approve request created by self";
                }
            }
            if(canApproveRequest) {
                canApproveRequest = authenticatedUser.hasPrivilege(Privileges.TASK_LABMANAGEMENT_TESTREQUESTS_APPROVE);
            }
        }
        return new Pair<Boolean,String>(canApproveRequest,remarks);

    }
}
