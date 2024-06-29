package org.openmrs.module.labmanagement.web;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.representation.Representation;

public class SampleTestRequestItemRepresentation implements Representation {

    public static SampleTestRequestItemRepresentation Instance = new SampleTestRequestItemRepresentation();

    public SampleTestRequestItemRepresentation() {
    }

    public String getRepresentation() {
        return "strir";
    }
}
