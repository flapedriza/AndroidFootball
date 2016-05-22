package idi.francesc.footballleague;

import android.provider.BaseColumns;

/**
 * Created by franc on 23/05/2016.
 */
public class JugadorsContract {
    public JugadorsContract() {}

    public static abstract class JugadorEntry implements BaseColumns {
        public static final String TABLE_NAME = "jugadors";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_COGNOMS = "cognoms";
        public static final String COLUMN_NAME_EQUIP = "equip";
        public static final String COLUMN_NAME_GOLS = "gols";
    }
}
