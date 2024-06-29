package org.openmrs.module.labmanagement.api.dto;

import org.openmrs.api.ConceptNameType;
import org.openmrs.util.LocaleUtility;
import org.springframework.util.ObjectUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;

public class ConceptNameDTO {

	private Integer conceptId;

	private String name;

	private Locale locale;

	private Boolean localePreferred = false;

	@Enumerated(EnumType.STRING)
	public ConceptNameType conceptNameType;

	public Integer getConceptId() {
		return conceptId;
	}

	public void setConceptId(Integer conceptId) {
		this.conceptId = conceptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConceptNameType getConceptNameType() {
		return conceptNameType;
	}

	public void setConceptNameType(ConceptNameType conceptNameType) {
		this.conceptNameType = conceptNameType;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Boolean getLocalePreferred() {
		return localePreferred;
	}

	public void setLocalePreferred(Boolean localePreferred) {
		this.localePreferred = localePreferred;
	}



	private static Collection<ConceptNameDTO> getNames(List<ConceptNameDTO> conceptNames, Locale locale) {
		Collection<ConceptNameDTO> localeNames = new Vector<>();

        for (ConceptNameDTO possibleName : conceptNames) {
            if (possibleName.getLocale().equals(locale)) {
                localeNames.add(possibleName);
            }
        }

		return localeNames;
	}

	private static ConceptNameDTO getPreferredName( List<ConceptNameDTO> conceptNames, Locale forLocale) {
		if (conceptNames.isEmpty()) {
			return null;
		} else if (forLocale == null) {
			return null;
		} else {
			Iterator<ConceptNameDTO> i$ = getNames(conceptNames, forLocale).iterator();
			ConceptNameDTO nameInLocale;
			do {
				if (!i$.hasNext()) {
					return null;
				}
				nameInLocale = (ConceptNameDTO)i$.next();
			} while(!ObjectUtils.nullSafeEquals(nameInLocale.getLocalePreferred(), true));

			return nameInLocale;
		}
	}

	public static ConceptNameDTO getPreferredConceptName(List<ConceptNameDTO> conceptNames){
			if (conceptNames.isEmpty()) {
				return null;
			} else {
                for (Locale currentLocale : LocaleUtility.getLocalesInOrder()) {
                    ConceptNameDTO preferredName = getPreferredName(conceptNames, currentLocale);
                    if (preferredName != null) {
                        return preferredName;
                    }
                }
				return conceptNames.get(0);
			}


	}
}
