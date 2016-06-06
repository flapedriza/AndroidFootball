package idi.francesc.footballleague;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


public class ClassificacioFragment extends Fragment {

    ListView listView;
    SimpleCursorAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    public ClassificacioFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String[] from = {EquipsContract.EquipEntry._ID,EquipsContract.EquipEntry.COLUMN_NAME_NOM,
                EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, EquipsContract.EquipEntry.COLUMN_NAME_DERROTES,
                EquipsContract.EquipEntry.COLUMN_NAME_EMPATS,  EquipsContract.EquipEntry.COLUMN_NAME_PUNTS};
        final int[] to = {R.id.item_identificador,R.id.item_nom, R.id.item_victories, R.id.item_derrotes, R.id.item_empats, R.id.item_punts};
        final View rootview =  inflater.inflate(R.layout.fragment_classificacio, container, false);
        listView = (ListView) rootview.findViewById(R.id.list_classificacio);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_classif);
        adapter = new SimpleCursorAdapter(rootview.getContext(), R.layout.equip_row,
                DBHandler.getDbInstance(getContext()).cursorClassificacio(), from, to);
        listView.setAdapter(adapter);
        listView.setEmptyView(rootview.findViewById(R.id.empty));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DBHandler handler = DBHandler.getDbInstance(getContext());
                /*byte[] arr = {1};
                Equip equip = new Equip("F.C. MoltsGols", "Barcelona", arr, 45, 2, 20, 10, 12);
                Equip equip2 = new Equip("F.C. PocsGols", "Barcelona", arr, 0, 2, 20, 10, 12);
                handler.addEquip(equip);
                handler.addEquip(equip2);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(rootview.getContext(), R.layout.equip_row,
                        handler.cursorClassificacio(), from, to);*/
                adapter.changeCursor(handler.cursorClassificacio());
                listView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int ident = Integer.parseInt(((TextView) view.findViewById(R.id.item_identificador)).getText().toString());
                Intent intent = new Intent(getContext(), AddEditEquipActivity.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", ident);
                startActivity(intent);
            }
        });
        return rootview;
    }

    @Override
    public void onResume() {
        listView.invalidateViews();
        adapter.changeCursor(DBHandler.getDbInstance(getContext()).cursorClassificacio());
        listView.setAdapter(adapter);
        super.onResume();

    }
}
