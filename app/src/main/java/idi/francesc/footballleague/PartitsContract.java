package idi.francesc.footballleague;

import android.provider.BaseColumns;

/**
 * Created by Francesc Lapedriza.
 */
public class PartitsContract {

    public PartitsContract() {}

    public static abstract class PartitsEntry implements BaseColumns {
        public static final String TABLE_NAME = "partits";
        public static final String COLUMN_NAME_LOCAL = "local";
        public static final String COLUMN_NAME_VISITANT = "visitant";
        public static final String COLUMN_NAME_DATA = "data";
        public static final String COLUMN_NAME_GOLS_LOCAL = "golslocal";
        public static final String COLUMN_NAME_GOLS_VISITANT = "golsvisitant";

    }
}
