package icesi.i2t.taller1retod;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        long idTrackRecived = 0;
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            idTrackRecived = bundle.getLong("track");
        }

        tv_nombre = findViewById(R.id.tv_nombre);
        tv_artista = findViewById(R.id.tv_artista);
        tv_album = findViewById(R.id.tv_album);
        tv_duracion = findViewById(R.id.tv_duracion);
        btn_escuchar = findViewById(R.id.btn_escuchar);

        String aplicationID = "301624";
        final DeezerConnect deezerConnect = new DeezerConnect(this, aplicationID);

        // the request listener
        RequestListener jsonListener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                Track track = (Track) result;

                // Llenar las variables
                tv_nombre.setText("Nombre: " + track.getTitle());
                tv_artista.setText("Artista: " + track.getArtist());
                tv_album.setText("Album:" + track.getAlbum());
                tv_duracion.setText("Duración: " + track.getDuration() + "");
            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        // create the request
        DeezerRequest request = DeezerRequestFactory.requestPlaylist(idTrackRecived);
        // set a requestId, that will be passed on the listener's callback methods
        request.setId("myRequest");

        // launch the request asynchronously
        //deezerConnect.requestAsync(request, jsonListener);


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
                    trackPlayer.stop();
                    trackPlayer.release();
                } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
                    tooManyPlayersExceptions.printStackTrace();
                } catch (DeezerError deezerError) {
                    deezerError.printStackTrace();
                }
            }
        });
    }
}
