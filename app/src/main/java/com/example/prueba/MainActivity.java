package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Nullable private TextView tvBrand;
    @Nullable private TextView tvHeroTitle;
    @Nullable private TextView tvHeroBody;
    @Nullable private TextView tvCompatibility;

    @Nullable private Button btnStartNow;
    @Nullable private Button btnSeeOffers;

    @Nullable private LinearLayout llDynamicBanner;

    @Nullable private ScrollView svContent;
    @Nullable private View gridGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBrand         = findViewById(R.id.tvBrand);
        tvHeroTitle     = findViewById(R.id.tvHeroTitle);
        tvHeroBody      = findViewById(R.id.tvHeroBody);
        tvCompatibility = findViewById(R.id.tvCompatibility);

        btnStartNow     = findViewById(R.id.btnStartNow);
        btnSeeOffers    = findViewById(R.id.btnSeeOffers);

        llDynamicBanner = findViewById(R.id.llDynamicBanner);

        svContent = findViewById(R.id.svContent);
        gridGames = findViewById(R.id.gridGames);

        if (btnStartNow != null) {
            btnStartNow.setOnClickListener(v ->
                    Toast.makeText(this, "Arrancamos", Toast.LENGTH_SHORT).show()
            );
        }

        if (btnSeeOffers != null) {
            btnSeeOffers.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, OfertasActivity.class);
                startActivity(intent);
            });
        }

        addDynamicBanner();
    }

    private void addDynamicBanner() {
        if (llDynamicBanner == null) return;

        TextView banner = new TextView(this);
        banner.setText(" Promo del día: 3 meses -20%");
        banner.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        banner.setTextSize(16f);

        int pad = getResources().getDimensionPixelSize(R.dimen.space_sm);
        banner.setPadding(pad, pad, pad, pad);
        banner.setBackgroundColor(ContextCompat.getColor(this, R.color.card_bg));

        llDynamicBanner.addView(banner);
    }

    private void scrollToGrid() {
        if (svContent == null || gridGames == null) return;

        svContent.post(() -> {
            int y = gridGames.getTop() - dp(12);
            if (y < 0) y = 0;
            svContent.smoothScrollTo(0, y);
        });
    }

    private int dp(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
