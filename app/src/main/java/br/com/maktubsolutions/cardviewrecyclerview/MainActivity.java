package br.com.maktubsolutions.cardviewrecyclerview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import br.com.maktubsolutions.cardviewrecyclerview.adapter.RecyclerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList items = new ArrayList<>();
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialog;
    private EditText edt_item;
    private View view;
    private boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Items");

        initViews();
        initDialog();
    }

    private void initViews() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_novaLista);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(items);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_newitem, null);
        alertDialog.setView(view);
        edt_item = (EditText) view.findViewById(R.id.edt_itemName);

        alertDialog.setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (add) {
                            add = false;
                            adapter.addItem(edt_item.getText().toString());
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_novaLista:
                removeView();
                add = true;
                alertDialog.setTitle("Enter a name for your list");
                edt_item.setText("");
                alertDialog.show();
                break;
        }
    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}