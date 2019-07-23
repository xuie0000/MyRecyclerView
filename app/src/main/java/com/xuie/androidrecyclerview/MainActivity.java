package com.xuie.androidrecyclerview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFab();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, SimpleRecyclerViewFragment.newInstance(0)).commit();
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.defaultF) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, SimpleRecyclerViewFragment.newInstance(0)).commit();
        } else if (id == R.id.example1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, SimpleRecyclerViewFragment.newInstance(1)).commit();
        } else if (id == R.id.example2) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, SimpleRecyclerViewFragment.newInstance(2)).commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
