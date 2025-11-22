package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OfertasActivity extends AppCompatActivity {

    public static final String EXTRA_PLAN_ID = "PLAN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        Button btnPlanNebula = findViewById(R.id.btnPlanNebula);
        Button btnPlanQuantum = findViewById(R.id.btnPlanQuantum);
        Button btnPlanEclipse = findViewById(R.id.btnPlanEclipse);

        btnPlanNebula.setOnClickListener(v -> openOrder("nebula"));
        btnPlanQuantum.setOnClickListener(v -> openOrder("quantum"));
        btnPlanEclipse.setOnClickListener(v -> openOrder("eclipse"));
    }

    private void openOrder(String planId) {
        Intent intent = new Intent(this, OrdenActivity.class);
        intent.putExtra(EXTRA_PLAN_ID, planId);
        startActivity(intent);
    }
}
