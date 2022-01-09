package com.example.smproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class ServiceActivity extends AppCompatActivity {

    public static TextView tvShakeService;

    public Bundle bundle = new Bundle();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Intent intent = new Intent(this, ShakeService.class);
        //Start Service
        startService(intent);

        tvShakeService = findViewById(R.id.tvShakeService);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceActivity.this, MapsActivity.class);
                bundle.putString("latLng", (String) tvShakeService.getText());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}