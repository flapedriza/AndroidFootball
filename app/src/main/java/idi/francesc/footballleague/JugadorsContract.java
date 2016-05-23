package idi.francesc.footballleague;

import android.provider.BaseColumns;

/**
 * Created by Francesc.
 */
public class JugadorsContract {
    public JugadorsContract() {}

    public static abstract class JugadorEntry implements BaseColumns {
        public static final String TABLE_NAME = "jugadors";
        public static final String COLUMN_NAME_JUGADOR_ID = "jugadorid";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_COGNOMS = "cognoms";
        public static final String COLUMN_NAME_EQUIP = "equip";
        public static final String COLUMN_NAME_GOLS = "gols";
    }
}
