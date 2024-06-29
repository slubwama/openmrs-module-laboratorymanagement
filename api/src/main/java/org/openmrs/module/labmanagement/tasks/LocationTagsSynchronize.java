package org.openmrs.module.labmanagement.tasks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.Drug;
import org.openmrs.LocationTag;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.labmanagement.LabLocationTags;

import java.util.*;
import java.util.stream.Collectors;

public class LocationTagsSynchronize implements StartupTask {

	private final Log log = LogFactory.getLog(this.getClass());

	private LocationTag ensureMainLaboratory(LocationService locationService) {
		LocationTag mainLaboratory = locationService.getLocationTagByName(LabLocationTags.MAIN_LABORATORY_LOCATION_TAG);
		if (mainLaboratory == null) {
			log.debug("Created main laboratory tag");
			mainLaboratory = new LocationTag();
			mainLaboratory.setUuid("d55e52c2-35eb-11ef-9df0-00155d919f83");
			mainLaboratory.setName(LabLocationTags.MAIN_LABORATORY_LOCATION_TAG);
			mainLaboratory.setDescription("A main area for carrying out patient tests");
			mainLaboratory.setDateCreated(new Date());
			mainLaboratory.setCreator(Context.getAuthenticatedUser());
			mainLaboratory = locationService.saveLocationTag(mainLaboratory);

		}
        Drug drug = Context.getConceptService().getDrug(3);
		return mainLaboratory;
	}

	private LocationTag ensureLaboratory(LocationService locationService) {
		LocationTag laboratory = locationService.getLocationTagByName(LabLocationTags.LABORATORY_LOCATION_TAG);
		if (laboratory == null) {
			log.debug("Created Laboratory tag");
			laboratory = new LocationTag();
			laboratory.setUuid("1e6acc3e-696d-47de-8f74-63ed7bbe6e81");
			laboratory.setName(LabLocationTags.LABORATORY_LOCATION_TAG);
			laboratory.setDescription("A tag to indicate a laboratory");
			laboratory.setDateCreated(new Date());
			laboratory.setCreator(Context.getAuthenticatedUser());
			laboratory = locationService.saveLocationTag(laboratory);
		}
		return laboratory;
	}

	private Location getSuitableParentLocation(List<Location> locations){
        Map<Optional<Location>, List<Location>> parents = locations.stream().collect(Collectors.groupingBy(p -> Optional.ofNullable(p.getParentLocation())));
        if(parents.isEmpty()) return null;
        ArrayList<List<Location>> locationGroups = new ArrayList<>(parents.values());
        locationGroups.sort((x, y) -> Integer.compare(y.size(),x.size()));
        return locationGroups.get(0).get(0).getParentLocation();
    }

	@Override
    public void execute() {
        log.debug("Checking the synchronization status of the location tags for main laboratory, laboratory");
        try {
            LocationService locationService = Context.getLocationService();
            LocationTag mainLaboratoryTag = ensureMainLaboratory(locationService);
            LocationTag laboratoryTag = ensureLaboratory(locationService);

            Location mainLaboratory = null;

            List<Location> locationsWithTag = locationService.getLocationsHavingAllTags(Arrays.asList(mainLaboratoryTag));
            if(!locationsWithTag.isEmpty()){
                Optional<Location> tempLocation = locationsWithTag.stream().filter(p->p.getRetired() == null || !p.getRetired()).findFirst();
                if(tempLocation.isPresent()){
                    mainLaboratory = tempLocation.get();
                }else{
                    mainLaboratory = locationsWithTag.get(0);
                }
            }

            List<Location> locations = locationService.getAllLocations(true);
            for(Location location : locations){
                if(mainLaboratory == null){
                    String locationName =location.getName().toLowerCase();
                    if(locationName.contains("main") && locationName.contains("laboratory")){
                        mainLaboratory = location;
                    }
                }

                if(mainLaboratory != null){
                    break;
                }
            }

            if(mainLaboratory == null){
                mainLaboratory = locationService.getLocationByUuid("ba158c33-dc43-4306-9a4a-b4075751d36c");
            }

            Location parentLocation = null;
            if(mainLaboratory == null){
                log.debug("Creating main laboratory location");
                parentLocation = getSuitableParentLocation(locations);
                mainLaboratory=new Location();
                mainLaboratory.setName("Main Laboratory");
                mainLaboratory.setCreator(Context.getAuthenticatedUser());
                mainLaboratory.setDateCreated(new Date());
                mainLaboratory.setParentLocation(parentLocation);
                mainLaboratory.setUuid("ba158c33-dc43-4306-9a4a-b4075751d36c");
                mainLaboratory = locationService.saveLocation(mainLaboratory);
            }

            Set<LocationTag> locationTags = mainLaboratory.getTags();
            if(locationTags == null || !locationTags.contains(mainLaboratoryTag)){
                log.debug("Taging main laboratory location");
                mainLaboratory.addTag(mainLaboratoryTag);
                locationService.saveLocation(mainLaboratory);
            }

        }catch (Exception exception){
            log.error("Error while synchronizing location and tags", exception);
        }
    }

	@Override
	public int getPriority() {
		return 90;
	}
}
