package com.example.jayden.jayden1_fueltrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ListView logView;
    private FuelLogList entries = new FuelLogList();
    private ArrayAdapter<FuelLogEntry> adapter;
    private int indexOf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newButton = (Button) findViewById(R.id.buttonNew);
        logView = (ListView) findViewById(R.id.listView);

        newButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent pop = new Intent(MainActivity.this, FuelTrackPopUp.class);

                startActivityForResult(pop, 1);

                setResult(RESULT_OK);
                //When button is clicked do something
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //Receive result from activity
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                FuelLogEntry i;
                i = (FuelLogEntry)data.getSerializableExtra("entry");
                entries.addLog(i);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        FuelLogEntry entry = new FuelLogEntry("Esso", "Normal", 10.02f, 100.500f, 1.20f);
        entries.addLog(entry);

        adapter = new ArrayAdapter<>(this, R.layout.list_item, entries.getList());
        logView.setAdapter(adapter);


        // Contextual Action Mode
        logView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        logView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                //When there is an item selected run this
                mode.setTitle("Log Entry Selected");
                indexOf = position;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //When the context menu is created run this
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.submenu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                //When
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                //When context buttons are clicked run this.

                if (item.getItemId()==R.id.id_delete){
                    entries.deleteLog(entries.getLog(indexOf));
                    adapter.notifyDataSetChanged();
                    mode.finish();
                }

                if (item.getItemId()==R.id.id_edit){

                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                //When the context menu is closed run this.
                indexOf = 0; //Reset index
            }
        });
    }

}