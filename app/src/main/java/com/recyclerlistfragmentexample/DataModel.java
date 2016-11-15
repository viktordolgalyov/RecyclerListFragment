package com.recyclerlistfragmentexample;

import android.support.annotation.DrawableRes;

class DataModel {
    @DrawableRes private final int icon;
    private final String title;
    private final String description;

    DataModel(int icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    @DrawableRes
    int getIcon() {
        return icon;
    }

    String getTitle() {
        return title;
    }

    String getDescription() {
        return description;
    }
}
