package idi.francesc.footballleague;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class ClassificacioFragment extends Fragment {

    public ClassificacioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] from = {/*EquipsContract.EquipEntry.COLUMN_NAME_ESCUT,*/ EquipsContract.EquipEntry.COLUMN_NAME_NOM,
                EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, EquipsContract.EquipEntry.COLUMN_NAME_DERROTES,
                EquipsContract.EquipEntry.COLUMN_NAME_EMPATS,  EquipsContract.EquipEntry.COLUMN_NAME_PUNTS};
        int[] to = {R.id.item_nom, R.id.item_victories, R.id.item_derrotes, R.id.item_empats, R.id.item_punts};
        View rootview =  inflater.inflate(R.layout.fragment_classificacio, container, false);
        ListView listView = (ListView) rootview.findViewById(R.id.classificacio);
        listView.invalidateViews();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(rootview.getContext(), R.layout.equip_row,
                DBHandler.getDbInstance(getContext()).cursorClassificacio(), from, to);
        listView.setAdapter(adapter);
        String size = Integer.toString(DBHandler.getDbInstance(getContext()).getAllEquips().size());
        Toast toast = Toast.makeText(getContext(), size, Toast.LENGTH_LONG);
        toast.show();
        return rootview;
    }
}
