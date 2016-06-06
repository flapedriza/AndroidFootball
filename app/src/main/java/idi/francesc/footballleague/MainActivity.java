package idi.francesc.footballleague;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabedit;
    private FloatingActionButton fabadd;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("firstTime", true)) {
            DBHandler handler = DBHandler.getDbInstance(this);
            handler.dropAll();
            Byte[] esctuts = {0};
            ArrayList<Equip> equipsIni = new ArrayList<>();
            String[] noms = {"F.C. Barcelona","R. Madrid", "R.C.D. Espanyol", "Inventat F.C", "F.C. MoltsGols",
            "F.C. PocsGols", "Perdedor C.F.", "Guanyador F.C.", "Equip F.C.", "AltreEquip F.C."};
            String[] ciutats = {"Barcelona", "Madrid", "Barcelona", "Sevilla", "Lugo", "Paris", "Londres",
            "Lisboa", "Pekin", "Tokyo"};
            for(int i = 0; i<noms.length; ++i) {
                handler.addEquip(new Equip(noms[i], ciutats[i]));
            }
            equipsIni = handler.getAllEquips();
            Collections.shuffle(equipsIni);
            List<Equip> half1 = equipsIni.subList(0,5);
            List<Equip> half2 = equipsIni.subList(5,10);
            for(int i = 0; i< half1.size(); ++i) {
                Random random = new Random();
                int goloc = random.nextInt(5);
                int golvis = random.nextInt(5);
                half1.get(i).incrementGolsFavor(goloc);
                half1.get(i).incrementGolsContra(golvis);
                half2.get(i).incrementGolsFavor(golvis);
                half2.get(i).incrementGolsContra(goloc);
                if(goloc > golvis) {
                    half1.get(i).addVictoria();
                    half2.get(i).addDerrota();
                }else if(golvis > goloc)  {
                    half1.get(i).addDerrota();
                    half2.get(i).addVictoria();
                }else {
                    half1.get(i).addEmpat();
                    half2.get(i).addEmpat();
                }
                handler.updateEquip(half1.get(i));
                handler.updateEquip(half2.get(i));
                handler.addPartit(new Partit(half1.get(i).get_nom(), half2.get(i).get_nom(),
                        new Date(2016,06,05),goloc, golvis));
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();
        }
        setContentView(R.layout.activity_main);
        fabadd = (FloatingActionButton) findViewById(R.id.fabadd);
        fabedit = (FloatingActionButton) findViewById(R.id.fabedit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //showRightFab(position);
                showRightFab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DBHandler.getDbInstance(context).getNombreEquips() >= 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final DatePicker picker = new DatePicker(context);
                    builder.setTitle("Escolliu data")
                            .setView(picker)
                            .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, AddPartitActivity.class);
                                    int monthNum = picker.getMonth() +1;
                                    String month = (monthNum > 9) ? String.valueOf(monthNum) : "0" + monthNum;
                                    String day = (picker.getDayOfMonth() > 9) ? String.valueOf(picker.getDayOfMonth()) :
                                            "0" + picker.getDayOfMonth();
                                    String date = picker.getYear()+"-"+month+"-"+day;
                                    intent.putExtra("date",date);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel·lar", null)
                            .show();
                } else Snackbar.make(view, "Calen com a mínim 2 equips per a disputar un partit", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nequips = DBHandler.getDbInstance(context).getNombreEquips();
                if (nequips < 10) {
                    Intent intent = new Intent(MainActivity.this, AddEditEquipActivity.class);
                    intent.putExtra("edit", false);
                    startActivity(intent);
                }
                else Snackbar.make(view, "No hi pot haver més de 10 equips", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        showRightFab(viewPager.getCurrentItem());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast toast = Toast.makeText(context, "Lliga de Futbol 1.0\n Francesc Lapedriza - 2016", Toast.LENGTH_LONG);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            if(v != null) v.setGravity(Gravity.CENTER);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClassificacioFragment(), "Classificació");
        adapter.addFragment(new PartitsFragment(), "Partits");
        viewPager.setAdapter(adapter);
    }


    public void showRightFab(int tab) {
        switch (tab) {
            case 0:
                fabedit.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fabadd.show();
                    }
                });
                break;
            case 1:
                fabadd.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        fabedit.show();
                    }
                });
                break;
            default:
                fabadd.hide();
                fabedit.hide();
                break;
        }
    }





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTittleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  mFragmentTittleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTittleList.add(title);
        }


    }
}
