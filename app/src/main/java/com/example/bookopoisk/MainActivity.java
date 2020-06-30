package com.example.bookopoisk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    FragmentTransaction fragmentTransaction;
    Fragment searchFragment;
    TabLayout tabLayout;
    ViewPager pager;
    Toolbar toolbar;
    SearchFragmentInterface searchFragmentInterface;
    String textSearch;
    Handler sHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchFragment = new SearchFragment();

        // Добавляем тулбар
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Добавляем "бургер"
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Регистрация слушателя в NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Добавление табов
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Связывание TabLayout с ViewPager
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        sHandler = new Handler();
    }

    // Добавление поиска
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_search));

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, searchFragment).commit();

                AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                tabLayout.setVisibility(View.GONE);
                pager.setVisibility(View.GONE);
                p.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);
                toolbar.setLayoutParams(p);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(searchFragment).commit();

                AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                tabLayout.setVisibility(View.VISIBLE);
                pager.setVisibility(View.VISIBLE);
                p.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                toolbar.setLayoutParams(p);
                return true;
            }
        });
        searchView.setOnQueryTextListener(this);
        return true;
    }

    // Выбор одного из пунктов навигационного меню
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.nav_profile:
                intent = new Intent(this, ProfileActivity.class);
                break;
            case R.id.nav_reg_auth:
                intent = new Intent(this, LoginActivity.class);
                break;
        }

        startActivity(intent);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    // Закрытие навигационного меню при нажатии кнопки назад
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Слушатели поиска
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(@NonNull String newText) {
        textSearch = newText;
        sHandler.removeCallbacksAndMessages(null);

        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!textSearch.equals("")) {
                    searchFragmentInterface.onTextChange(textSearch);
                } else {
                    searchFragmentInterface.onTextChange("");
                }
            }
        }, 300);

        return true;
    }

    // Интерфейс поиска
    interface SearchFragmentInterface {
        void onTextChange(String newText);
    }


    // Адаптер страничного компонента фрагментов
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NewFragment();
                case 1:
                    return new RecommendedFragment();
                case 2:
                    return new GenresFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.new_tab);
                case 1:
                    return getResources().getText(R.string.recommended_tab);
                case 2:
                    return getResources().getText(R.string.genres_tab);
            }

            return null;
        }
    }
}
