package idi.francesc.footballleague;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ClassificacioFragment extends Fragment {

    public ClassificacioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String[] from = { EquipsContract.EquipEntry.COLUMN_NAME_NOM,
                EquipsContract.EquipEntry.COLUMN_NAME_VICTORIES, EquipsContract.EquipEntry.COLUMN_NAME_DERROTES,
                EquipsContract.EquipEntry.COLUMN_NAME_EMPATS,  EquipsContract.EquipEntry.COLUMN_NAME_PUNTS};
        final int[] to = {R.id.item_nom, R.id.item_victories, R.id.item_derrotes, R.id.item_empats, R.id.item_punts};
        final View rootview =  inflater.inflate(R.layout.fragment_classificacio, container, false);
        final ListView listView = (ListView) rootview.findViewById(R.id.list_classificacio);
        listView.setEmptyView((LinearLayout) rootview.findViewById(R.id.empty));
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_classif);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(rootview.getContext(), R.layout.equip_row,
                DBHandler.getDbInstance(getContext()).cursorClassificacio(), from, to);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DBHandler handler = DBHandler.getDbInstance(getContext());
                byte[] arr = {1};
                Equip equip = new Equip("F.C. MoltsGols", arr, 45, 2, 20, 10, 12);
                Equip equip2 = new Equip("F.C. PocsGols", arr, 0, 2, 20, 10, 12);
                handler.addEquip(equip);
                handler.addEquip(equip2);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(rootview.getContext(), R.layout.equip_row,
                        handler.cursorClassificacio(), from, to);
                listView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        String size = Integer.toString(DBHandler.getDbInstance(getContext()).getAllEquips().size());
        Toast toast = Toast.makeText(getContext(), size, Toast.LENGTH_LONG);
        toast.show();
        return rootview;
    }
}
