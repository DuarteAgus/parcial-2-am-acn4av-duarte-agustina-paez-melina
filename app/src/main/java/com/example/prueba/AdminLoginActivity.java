package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etAdminUser;
    private EditText etAdminPass;
    private Button btnAdminLogin;

    private FirebaseAuth mAuth;

    private static final String ADMIN_EMAIL = "admin@nucloud.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminUser   = findViewById(R.id.etAdminUser);
        etAdminPass   = findViewById(R.id.etAdminPass);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        mAuth = FirebaseAuth.getInstance();

        btnAdminLogin.setOnClickListener(v -> intentarLoginAdmin());
    }

    private void intentarLoginAdmin() {
        String user = etAdminUser.getText().toString().trim();
        String pass = etAdminPass.getText().toString().trim();

        if (TextUtils.isEmpty(user)) {
            etAdminUser.setError("Ingresá el email admin");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            etAdminPass.setError("Ingresá la contraseña");
            return;
        }

        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        if (!user.equalsIgnoreCase(ADMIN_EMAIL)) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(this, "Este usuario no es administrador", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(this, "Bienvenida administradora", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, AdminDashboardActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        String msg = (task.getException() != null)
                                ? task.getException().getMessage()
                                : "Credenciales incorrectas";
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
