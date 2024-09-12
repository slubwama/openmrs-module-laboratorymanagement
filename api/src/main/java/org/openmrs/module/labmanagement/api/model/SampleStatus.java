package org.openmrs.module.labmanagement.api.model;

import org.openmrs.module.labmanagement.api.dto.SampleDTO;

import java.util.Arrays;
import java.util.List;

public enum SampleStatus {
        PENDING(),
        COLLECTION(),
        TESTING(),
        ARCHIVED(),
        DISPOSED();

        public static List<SampleStatus> getActiveStatuses(){
           return Arrays.asList(COLLECTION, TESTING, ARCHIVED);
        }

        public static boolean canDeleteSampleWithStatus(SampleStatus sampleStatus){
                return sampleStatus.equals(COLLECTION);
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

        public static boolean canDispose(SampleDTO sample){
                return sample != null && (sample.getStorageStatus() == null ? !DISPOSED.equals(sample.getStatus()) :
                        (  sample.getStorageStatus() != null && !StorageStatus.DISPOSED.equals(sample.getStorageStatus())));
        }

        public static boolean canDispose(Sample sample){
                return sample != null && (sample.getStorageStatus() == null ? !DISPOSED.equals(sample.getStatus()) :
                        ( sample.getStorageStatus() != null && !StorageStatus.DISPOSED.equals(sample.getStorageStatus())));
        }

        public static boolean canArchive(SampleDTO sample){
                return sample != null && ((sample.getStorageStatus() == null && !DISPOSED.equals(sample.getStatus()) ) ||( sample.getStorageStatus() != null && !StorageStatus.DISPOSED.equals(sample.getStorageStatus()) &&
                        !StorageStatus.ARCHIVED.equals(sample.getStorageStatus())));
        }

        public static boolean canArchive(Sample sample){
                return sample != null && ((sample.getStorageStatus() == null && !DISPOSED.equals(sample.getStatus()) ) ||(  sample.getStorageStatus() != null &&!StorageStatus.DISPOSED.equals(sample.getStorageStatus()) &&
                        !StorageStatus.ARCHIVED.equals(sample.getStorageStatus())));
        }

        public static boolean canCheckOut(SampleDTO sample){
                return sample != null && StorageStatus.ARCHIVED.equals(sample.getStorageStatus());
        }

        public static boolean canCheckOut(Sample sample){
                return sample != null && StorageStatus.ARCHIVED.equals(sample.getStorageStatus());
        }

}
