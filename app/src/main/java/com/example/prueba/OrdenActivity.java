package com.example.prueba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrdenActivity extends AppCompatActivity {

    private TextView tvResumenPlan;
    private EditText etAlias;
    private RadioGroup rgMetodoPago;
    private Button btnConfirmar;

    private PlanData planSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);

        tvResumenPlan = findViewById(R.id.tvResumenPlan);
        etAlias = findViewById(R.id.etAlias);
        rgMetodoPago = findViewById(R.id.rgMetodoPago);
        btnConfirmar = findViewById(R.id.btnConfirmarPago);

        String planId = getIntent().getStringExtra(OfertasActivity.EXTRA_PLAN_ID);
        planSeleccionado = getPlanById(planId);

        if (planSeleccionado == null) {
            Toast.makeText(this, "Plan no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String resumen = "Estás contratando: " + planSeleccionado.nombre +
                "\nPrecio: " + planSeleccionado.precio +
                "\n\n" + planSeleccionado.descripcion;
        tvResumenPlan.setText(resumen);

        btnConfirmar.setOnClickListener(v -> confirmarPago());
    }

    private void confirmarPago() {
        int checkedId = rgMetodoPago.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, "Seleccioná un método de pago", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(checkedId);
        String metodo = rb.getText().toString();

        String alias = etAlias.getText().toString().trim();
        if (TextUtils.isEmpty(alias)) {
            etAlias.setError("Ingresá el alias o número de tarjeta");
            return;
        }

        SharedPreferences prefs = getSharedPreferences("nucloud_prefs", MODE_PRIVATE);
        prefs.edit()
                .putString("plan_nombre", planSeleccionado.nombre)
                .putString("plan_precio", planSeleccionado.precio)
                .putString("plan_metodo", metodo)
                .putString("plan_alias", alias)
                .apply();

        Toast.makeText(this, "¡Plan contratado con éxito!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, DashboardClienteActivity.class);
        startActivity(intent);
        finish();
    }

    private PlanData getPlanById(String id) {
        if (id == null) return null;

        switch (id) {
            case "nebula":
                return new PlanData(
                        "Plan Nebula",
                        "$5.999 / mes",
                        "1080p, 60 FPS, hasta 2 dispositivos."
                );
            case "quantum":
                return new PlanData(
                        "Plan Quantum",
                        "$8.999 / mes",
                        "1440p, 120 FPS, hasta 3 dispositivos."
                );
            case "eclipse":
                return new PlanData(
                        "Plan Eclipse",
                        "$11.999 / mes",
                        "4K HDR, 144 FPS, hasta 4 dispositivos."
                );
        }
        return null;
    }

    private static class PlanData {
        final String nombre;
        final String precio;
        final String descripcion;

        PlanData(String nombre, String precio, String descripcion) {
            this.nombre = nombre;
            this.precio = precio;
            this.descripcion = descripcion;
        }
    }
}
