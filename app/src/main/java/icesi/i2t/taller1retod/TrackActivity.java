package icesi.i2t.taller1retod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TrackActivity extends AppCompatActivity {

    private TextView tv_nombre;
    private TextView tv_artista;
    private TextView tv_album;
    private TextView tv_duracion;
    private Button btn_escuchar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        tv_nombre = findViewById(R.id.tv_nombre);
        tv_artista = findViewById(R.id.tv_artista);
        tv_album = findViewById(R.id.tv_album);
        tv_duracion = findViewById(R.id.tv_duracion);
        btn_escuchar = findViewById(R.id.btn_escuchar);

        btn_escuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
