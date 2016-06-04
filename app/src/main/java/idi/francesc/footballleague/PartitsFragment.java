package idi.francesc.footballleague;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;


public class PartitsFragment extends Fragment {

    ListView listView;
    SimpleCursorAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;


    public PartitsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String[] from = {EquipsContract.EquipEntry._ID, PartitsContract.PartitsEntry.COLUMN_NAME_LOCAL,
                PartitsContract.PartitsEntry.COLUMN_NAME_VISITANT, PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_LOCAL,
                PartitsContract.PartitsEntry.COLUMN_NAME_GOLS_VISITANT};
        final int[] to = {R.id.partit_item_identificador, R.id.partit_item_local, R.id.partit_item_visitant,
        R.id.partit_item_gols_local, R.id.partit_item_visitant};
        final View rootView = inflater.inflate(R.layout.fragment_partits, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_partits);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_partits);
        adapter = new SimpleCursorAdapter(rootView.getContext(), R.layout.partit_row,
                DBHandler.getDbInstance(getContext()).cursorPartits(),from, to );
        //listView.invalidateViews();
        listView.setAdapter(adapter);

        return rootView;
    }
}
