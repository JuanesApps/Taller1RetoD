package icesi.i2t.taller1retod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaylistActivity extends AppCompatActivity {

    private ImageView iv_playlist;
    private TextView tv_nombre_playlist;
    private TextView tv_descripcion;
    private TextView tv_numero_canciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        iv_playlist = findViewById(R.id.iv_playlist);
        tv_nombre_playlist = findViewById(R.id.tv_nombre_playlist);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        tv_numero_canciones = findViewById(R.id.tv_numero_canciones);
    }
}
