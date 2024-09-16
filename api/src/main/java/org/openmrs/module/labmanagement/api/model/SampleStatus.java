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

        public static boolean isArchived(Sample sample){
                return sample != null && ((sample.getStatus() != null && sample.getStatus()  == ARCHIVED) ||
                        (sample.getStorageStatus() != null && sample.getStorageStatus() == StorageStatus.ARCHIVED));
        }

        public static boolean canDeleteSampleWithStatus(SampleStatus sampleStatus){
                return sampleStatus.equals(COLLECTION);
        }

        public static boolean canReleaseAdditionalTestItemsForTesting(SampleStatus sampleStatus) {
                return sampleStatus != null && (sampleStatus.equals(TESTING) || sampleStatus.equals(ARCHIVED) || sampleStatus.equals(DISPOSED));
        }

        public static boolean canReleaseSamplesForTesting(SampleStatus sampleStatus) {
                return sampleStatus != null && (sampleStatus.equals(COLLECTION) || sampleStatus.equals(DISPOSED) || sampleStatus.equals(ARCHIVED));
        }

        public static boolean canReleaseForTesting(SampleDTO sample){
                return sample != null && canReleaseSamplesForTesting(sample.getStatus()) && (sample.getTests() == null ||
                        sample.getTests().isEmpty() || sample.getTests().stream().anyMatch(p-> TestRequestItemStatus.needsReleaseSamplesForTesting(p.getStatus())));
        }

        public static boolean canEdit(SampleDTO sample){
                return sample != null && !DISPOSED.equals(sample.getStatus()) && !ARCHIVED.equals(sample.getStatus());
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

        public static SampleStatus findByName(String name) {
                SampleStatus result = null;
                for (SampleStatus sampleStatus : values()) {
                        if (sampleStatus.name().equalsIgnoreCase(name)) {
                                result = sampleStatus;
                                break;
                        }
                }
                return result;
        }

}
