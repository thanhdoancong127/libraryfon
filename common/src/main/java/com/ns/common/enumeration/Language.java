package com.ns.common.enumeration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    ENGLISH("EN"),
    FRENCH("FR"),
    SPANISH("ES"),
    GERMAN("DE"),
    ITALIAN("IT"),
    RUSSIAN("RU"),
    CHINESE("ZH"),
    JAPANESE("JA"),
    KOREAN("KO"),
    ARABIC("AR"),
    HINDI("HI"),
    BENGALI("BN"),
    PORTUGUESE("PT"),
    URDU("UR"),
    TURKISH("TR"),
    TAMIL("TA"),
    PUNJABI("PA"),
    TELEGU("TE"),
    MARATHI("MR"),
    GUJARATI("GU"),
    KANNADA("KN"),
    VIETNAMESE("VI");

    private final String abbreviation;

    public static Language fromAbbreviation(String abbreviation) {
        for (Language language : values()) {
            if (language.getAbbreviation().equals(abbreviation)) {
                return language;
            }
        }
        return null;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
