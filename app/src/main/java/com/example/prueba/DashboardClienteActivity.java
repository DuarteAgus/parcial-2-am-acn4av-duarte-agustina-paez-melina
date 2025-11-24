package com.example.prueba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardClienteActivity extends AppCompatActivity {

    private TextView tvEstadoPlan;
    private TextView tvDetallePlan;

    private EditText etDispositivo;
    private Button btnAgregarDispositivo;
    private Button btnCatalogoOnline;
    private LinearLayout llDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_cliente);

        tvEstadoPlan = findViewById(R.id.tvEstadoPlan);
        tvDetallePlan = findViewById(R.id.tvDetallePlan);

        etDispositivo = findViewById(R.id.etDispositivo);
        btnAgregarDispositivo = findViewById(R.id.btnAgregarDispositivo);
        btnCatalogoOnline = findViewById(R.id.btnCatalogoOnline);
        llDispositivos = findViewById(R.id.llDispositivos);

        cargarPlanDesdePrefs();

        btnAgregarDispositivo.setOnClickListener(v -> agregarDispositivo());
        btnCatalogoOnline.setOnClickListener(v -> abrirCatalogoOnline());
    }

    private void cargarPlanDesdePrefs() {
        SharedPreferences prefs = getSharedPreferences("nucloud_prefs", MODE_PRIVATE);

        String nombre = prefs.getString("plan_nombre", null);
        String precio = prefs.getString("plan_precio", null);
        String metodo = prefs.getString("plan_metodo", null);
        String alias = prefs.getString("plan_alias", null);

        if (nombre == null) {
            tvEstadoPlan.setText("No tenés un plan contratado");
            tvDetallePlan.setText("Contratá un plan desde la sección Ofertas para ver el estado acá.");
        } else {
            tvEstadoPlan.setText("Plan contratado: " + nombre);

            String detalle = "Precio: " + precio;
            if (metodo != null) {
                detalle += "\nMétodo de pago: " + metodo;
            }
            if (alias != null) {
                detalle += "\nAlias / tarjeta: " + alias;
            }

            tvDetallePlan.setText(detalle);
        }
    }

    private void agregarDispositivo() {
        String nombreDispositivo = etDispositivo.getText().toString().trim();
        if (TextUtils.isEmpty(nombreDispositivo)) {
            etDispositivo.setError("Ingresá un dispositivo (ej: TV del living)");
            return;
        }

        TextView tv = new TextView(this);
        tv.setText("• " + nombreDispositivo);
        tv.setTextSize(14f);
        tv.setTextColor(0xFFFFFFFF);
        tv.setPadding(0, 4, 0, 4);

        llDispositivos.addView(tv);
        etDispositivo.setText("");
    }

    private void abrirCatalogoOnline() {
        if (!hayConexion()) {
            Toast.makeText(this, "Sin conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse("https://rawg.io/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private boolean hayConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        Network network = cm.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities caps = cm.getNetworkCapabilities(network);
        if (caps == null) return false;

        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }
}
