package com.solvd.task.gui.enums;

public enum Category {

    HOME_FURNITURE_DIY("Home, Furniture & DIY"),
    WHOLESALE_JOB_LOTS("Wholesale & Job Lots"),
    CRAFTS("Crafts");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
