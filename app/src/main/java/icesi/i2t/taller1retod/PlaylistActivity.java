package icesi.i2t.taller1retod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private ImageView iv_playlist;
    private TextView tv_nombre_playlist;
    private TextView tv_descripcion;
    private TextView tv_numero_canciones;
    private ListView lv_canciones;
    private AdaptadorTrack adaptadorTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Bundle bundle = getIntent().getExtras();
        long idPlaylistRecived = 0;
        if(bundle!=null){
            idPlaylistRecived = bundle.getLong("playlist");
        }

        iv_playlist = findViewById(R.id.iv_playlist);
        tv_nombre_playlist = findViewById(R.id.tv_nombre_playlist);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        tv_numero_canciones = findViewById(R.id.tv_numero_canciones);
        lv_canciones = findViewById(R.id.lv_canciones);
        adaptadorTrack = new AdaptadorTrack(this);

        lv_canciones.setAdapter(adaptadorTrack);

        String aplicationID = "301624";
        final DeezerConnect deezerConnect = new DeezerConnect(this, aplicationID);

        // the request listener
        RequestListener jsonListener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                Playlist playlist = (Playlist) result;
                // Llenar las variables
                Picasso.get().load(playlist.getPictureUrl()).into(iv_playlist);
                tv_nombre_playlist.setText("Nombre: " + playlist.getTitle());
                tv_descripcion.setText("Descripción: " + playlist.getDescription());
                tv_numero_canciones.setText("#Canciones: " + playlist.getTracks().size());
                // do something with the albums
                for (int i = 0; i < playlist.getTracks().size(); i++)
                    adaptadorTrack.agregarTrack(playlist.getTracks().get(i));

            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        // create the request
        DeezerRequest request = DeezerRequestFactory.requestPlaylist(idPlaylistRecived);
        // set a requestId, that will be passed on the listener's callback methods
        request.setId("myRequest");

        // launch the request asynchronously
        deezerConnect.requestAsync(request, jsonListener);


        lv_canciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), TrackActivity.class);
                long idTrack = Long.parseLong(adaptadorTrack.getArrayTracks().get(position).getId() + "");
                i.putExtra("track", idTrack);
                startActivity(i);
                finish();
            }
        });
    }
}
