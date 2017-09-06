package com.thelaundrychute.user.common.database;

public class AppDbSchema {
    public static final class AppTable {
        public static final String NAME = "appSettings";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String STATUS = "status";
        }
    }
}
