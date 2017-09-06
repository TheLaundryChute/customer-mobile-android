package com.inMotion.core.objects.lists;

import com.google.gson.JsonObject;

/**
 * Created by sfbechtold on 12/9/15.
 */
public class Pager {
    private Path page = null;
    private Path records = null;

    public Pager(Path page, Path records) {
        this.page = page;
        this.records = records;
    }

    public Path getPage() {
        return page;
    }

    public Path getRecords() {
        return records;
    }

    public static Pager parse(JsonObject value) {
        Path page = new Path(value.get("page").getAsInt(), value.get("size").getAsInt());
        Path records = new Path(value.get("index").getAsInt(), value.get("count").getAsInt());

        return  new Pager(page, records);
    }
}
