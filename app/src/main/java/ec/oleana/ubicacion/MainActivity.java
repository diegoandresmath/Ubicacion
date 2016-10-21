package ec.oleana.ubicacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLanzarMapas();
    }

    public void btnLanzarMapas() {
        Log.d("DLC", "MainActivity.btnLanzarDialogo");
        btnMapa = (Button) findViewById(R.id.btnMapas);
        btnMapa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "MainActivity.btnLanzarMapas.onClick -> antes");
                        //startActivity(new Intent(MainActivity.this,MapasActivity.class));
                        Intent intent = new Intent(getApplicationContext(), MapasActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "MainActivity.btnLanzarMapas.onClick -> despues");
                    }
                }
        );
    }
}
