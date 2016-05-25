package appprojgrp_nineteen.det_brugerinddragende_hospital;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import appprojgrp_nineteen.det_brugerinddragende_hospital.Api.LoginService;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Database.DatabaseHelper;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Child;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Models.Report;
import appprojgrp_nineteen.det_brugerinddragende_hospital.Services.SyncService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    Button btnSave;
    Button btnCancel;

    Button btnMedicine;
    Button btnFood;
    Button btnDiaper;
    EditText txtComment;

    Report tempReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        txtComment = (EditText) findViewById(R.id.txtComment);

        btnMedicine = (Button) findViewById(R.id.btnMedicine);
        btnFood = (Button) findViewById(R.id.btnFood);
        btnDiaper = (Button) findViewById(R.id.btnDiaper);

        tempReport = new Report();

        Intent passedIntent = getIntent();
        if (passedIntent != null) {
            int childId = passedIntent.getIntExtra("child-id", -1);
            String childName = passedIntent.getStringExtra("child-name");

            tempReport.child_id = childId;
            setTitle(childName);

            Log.v("ReportActivity", "Intent, child-id: " + passedIntent.getIntExtra("child-id", 0));
        }

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        btnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCheckmark(btnMedicine, tempReport.medicine);
                tempReport.medicine = !tempReport.medicine;
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCheckmark(btnFood, tempReport.food);
                tempReport.food = !tempReport.food;
            }
        });

        btnDiaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCheckmark(btnDiaper, tempReport.diaper);
                tempReport.diaper = !tempReport.diaper;
            }
        });

        txtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tempReport.comment = txtComment.getText().toString();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnSave();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnCancel();
            }
        });
    }

    private void handleOnCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void handleOnSave() {
        tempReport.save();
        Toast.makeText(ReportActivity.this, "The report was saved successfully.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, SyncService.class);
        intent.putExtra(SyncService.PARAMS_REPORT_ID, tempReport.id);
        startService(intent);

        setResult(RESULT_OK);
        finish();
    }

    private void addCheckmark(Button btn, Boolean isChecked) {
        String checkMark = getString(R.string.report_activity_on_text);

        if (!isChecked) {
            btn.setText(checkMark + btn.getText());
        } else {
            String buttonText = btn.getText().toString();
            String newText = buttonText.subSequence(checkMark.length(), buttonText.length()).toString();
            btn.setText(newText);
        }
    }
}
