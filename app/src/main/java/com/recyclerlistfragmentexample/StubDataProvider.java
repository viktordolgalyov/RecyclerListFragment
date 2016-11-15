package com.recyclerlistfragmentexample;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class StubDataProvider {
    private static final int STUB_DATA_PACKET_SIZE = 20;

    static List<DataModel> createStubItems(int offset) {
        List<DataModel> models = new ArrayList<>(STUB_DATA_PACKET_SIZE);
        for (int i = 0; i < STUB_DATA_PACKET_SIZE; ++i) {
            DataModel model = new DataModel(android.R.drawable.ic_menu_call, String.format(Locale.getDefault(), "Item %d", (offset + i)), "Description");
            models.add(model);
        }
        return models;
    }
}
