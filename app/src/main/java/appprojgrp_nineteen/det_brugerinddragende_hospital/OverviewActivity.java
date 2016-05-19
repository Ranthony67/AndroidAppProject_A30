package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers.ChildCardAdapter;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Helpers.ChildOnClickListener;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;

public class OverviewActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Child> childrenData = new ArrayList<>();
    public static View.OnClickListener childOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        dbHelper = new DatabaseHelper(this);
        childOnClickListener = new ChildOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Child child = new Child();
        child.name = "Rasmus Anthony";
        child.department = "Intensiv";

        dbHelper.insertModel(child);

        try {
            childrenData = dbHelper.getAll(Child.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ChildCardAdapter(childrenData);
        recyclerView.setAdapter(adapter);
    }
}
