package org.openmrs.module.labmanagement.api.model;

import org.openmrs.module.labmanagement.api.dto.SampleDTO;

import java.util.Arrays;
import java.util.List;

public enum SampleStatus {
        PENDING(),
        COLLECTION(),
        TESTING(),
        STORAGE(),
        DISPOSED();

        public static List<SampleStatus> getActiveStatuses(){
           return Arrays.asList(COLLECTION, TESTING, STORAGE);
        }
        public static boolean canDeleteSampleWithStatus(SampleStatus sampleStatus){
                return sampleStatus.equals(COLLECTION) || sampleStatus.equals(PENDING);
        }


        public static boolean canReleaseAdditionalTestItemsForTesting(SampleStatus sampleStatus) {
                return sampleStatus != null && (sampleStatus.equals(TESTING));
        }

        public static boolean canReleaseSamplesForTesting(SampleStatus sampleStatus) {
                return sampleStatus != null && (sampleStatus.equals(COLLECTION));
        }

        public static boolean canReleaseForTesting(SampleDTO sample){
                return sample != null && COLLECTION.equals(sample.getStatus());
        }

        public static boolean canEdit(SampleDTO sample){
                return sample != null && !DISPOSED.equals(sample.getStatus());
        }

}
