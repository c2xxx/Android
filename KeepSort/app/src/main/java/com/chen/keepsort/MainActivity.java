package com.chen.keepsort;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.chen.keepsort.bean.SportGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_sport_group) {
            Intent intent = new Intent(this, AddSportGroupActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_delete_sport_group) {
            delete();
        } else if (id == R.id.action_change_sport_group) {
            change();
        }
        return super.onOptionsItemSelected(item);
    }

    private void change() {
        execute(1);
    }

    private void delete() {
        execute(-1);
    }

    private void execute(int type) {
        List<SportGroup> list = SportGroupManager.getInstance().loadList();
        if (list == null || list.isEmpty()) {
            Toast.makeText(this, "列表为空，请新建运动组", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] items = new String[list.size()];
        int index = 0;
        for (SportGroup group : list) {
            items[index++] = group.getName();
        }

        new AlertDialog.Builder(this).setCancelable(false)
                .setTitle("请选择")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SportGroup group = list.get(i);
                        if (type == 1) {
                            SportGroupManager.getInstance().setDefaultSportGroup(group);
                        } else if (type == -1) {
                            SportGroupManager.getInstance().delete(group);
                        }
                        restartApp();
                    }
                })
                .show();
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}