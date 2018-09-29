package icesi.i2t.taller1retod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.squareup.picasso.Picasso;

public class TrackActivity extends AppCompatActivity {

    private ImageView iv_flecha_to_playlist;
    private ImageView iv_cancion;
    private TextView tv_nombre;
    private TextView tv_artista;
    private TextView tv_album;
    private TextView tv_duracion;
    private Button btn_escuchar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        long idTrackRecived = 0;
        long idPlaylistReceived = 0;
        String idAdaptadorPlaylistRecived = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idTrackRecived = bundle.getLong("track");
            idPlaylistReceived = bundle.getLong("playlist");
            idAdaptadorPlaylistRecived = bundle.getString("adaptadorPlaylist");
        }
        final long aux = idPlaylistReceived;
        final String aux2 = idAdaptadorPlaylistRecived;

        iv_flecha_to_playlist = findViewById(R.id.iv_flecha_to_playlist);
        iv_cancion = findViewById(R.id.iv_cancion);
        tv_nombre = findViewById(R.id.tv_nombre);
        tv_artista = findViewById(R.id.tv_artista);
        tv_album = findViewById(R.id.tv_album);
        tv_duracion = findViewById(R.id.tv_duracion);
        btn_escuchar = findViewById(R.id.btn_escuchar);

        final DeezerConnect deezerConnect = new DeezerConnect(this, MainActivity.AplicationID);

        // the request listener
        RequestListener jsonListener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                Track track = (Track) result;

                // Llenar las variables
                Picasso.get().load(track.getArtist().getSmallImageUrl()).into(iv_cancion);
                tv_nombre.setText("Nombre: " + track.getTitle());
                tv_artista.setText("Artista: " + track.getArtist().getName());
                tv_album.setText("Album:" + track.getAlbum().getTitle());
                int durationMinutes = 0;
                int durationSeconds = 0;
                int i = 0;
                int total = track.getDuration();
                while (total >= 60) {
                    total -= 60;
                    i++;
                }
                durationMinutes = i;
                durationSeconds = track.getDuration() - (60 * i);
                if (durationMinutes < 10) {
                    tv_duracion.setText("Duración: 0" + durationMinutes + ":" + durationSeconds);
                } else if (durationSeconds < 10) {
                    tv_duracion.setText("Duración: " + durationMinutes + ":0" + durationSeconds);
                } else if (durationMinutes < 10 && durationSeconds < 10) {
                    tv_duracion.setText("Duración: 0" + durationMinutes + ":0" + durationSeconds);
                } else {
                    tv_duracion.setText("Duración: " + durationMinutes + ":" + durationSeconds);

                }
            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        // create the request
        DeezerRequest request = DeezerRequestFactory.requestTrack(idTrackRecived);
        // set a requestId, that will be passed on the listener's callback methods
        request.setId("myRequest");
        // launch the request asynchronously
        deezerConnect.requestAsync(request, jsonListener);

        final long finalIdTrackRecived = idTrackRecived;
        btn_escuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the player |application
                try {
                    TrackPlayer trackPlayer = new TrackPlayer(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
                    // start playing music
                    trackPlayer.playTrack(finalIdTrackRecived);
                    // ...
                    // to make sure the player is stopped (for instance when the activity is closed)
                    //trackPlayer.stop();
                    //trackPlayer.release();
                } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
                    tooManyPlayersExceptions.printStackTrace();
                } catch (DeezerError deezerError) {
                    deezerError.printStackTrace();
                }
            }
        });

        iv_flecha_to_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
                i.putExtra("idFromTrack", aux);
                i.putExtra("adaptadorPlaylist", aux2);
                startActivity(i);
                finish();
            }
        });
    }
}
