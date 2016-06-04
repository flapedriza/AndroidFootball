package idi.francesc.footballleague;

import android.provider.BaseColumns;

/**
 * Created by franc on 23/05/2016.
 */
public class EquipsContract {
    public EquipsContract() {}

    public static abstract class EquipEntry implements BaseColumns {
        public static final String TABLE_NAME = "equips";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUM_NAME_CIUTAT= "ciutat";
        public static final String COLUMN_NAME_ESCUT = "escut";
        public static final String COLUMN_NAME_GOLS_FAV = "gfavor";
        public static final String COLUMN_NAME_GOLS_CONTRA = "gcontra";
        public static final String COLUMN_NAME_VICTORIES = "victories";
        public static final String COLUMN_NAME_DERROTES = "derrotes";
        public static final String COLUMN_NAME_EMPATS = "empats";
        public static final String COLUMN_NAME_PUNTS = "punts";

    }
}
