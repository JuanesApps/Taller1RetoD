package icesi.i2t.taller1retod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_buscar_lista;
    private ImageButton ib_buscar;
    private ListView lv_listas;
    private AdaptadorPlaylist adaptadorPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_buscar_lista = findViewById(R.id.et_buscar_lista);
        ib_buscar = findViewById(R.id.ib_buscar);
        lv_listas = findViewById(R.id.lv_listas);
        adaptadorPlaylist = new AdaptadorPlaylist(this);

        lv_listas.setAdapter(adaptadorPlaylist);

        String aplicationID = "301624";
        final DeezerConnect deezerConnect = new DeezerConnect(this, aplicationID);

        ib_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the request listener
                RequestListener listener = new JsonRequestListener() {

                    public void onResult(Object result, Object requestId) {
                        List<Playlist> ListPlay = (List<Playlist>) result;
                        // do something with the albums
                        for (int i = 0; i < ListPlay.size(); i++)
                            adaptadorPlaylist.agregarPlaylist(ListPlay.get(i));
                    }

                    public void onUnparsedResult(String requestResponse, Object requestId) {
                    }

                    public void onException(Exception e, Object requestId) {
                    }
                };
                // create the request
                //long artistId = 11472;
                //DeezerRequest request = DeezerRequestFactory.requestArtistAlbums(artistId);
                DeezerRequest request = DeezerRequestFactory.requestSearchPlaylists(et_buscar_lista.getText().toString());
                // set a requestId, that will be passed on the listener's callback methods
                request.setId("myRequest");

                // launch the request asynchronously
                deezerConnect.requestAsync(request, listener);
            }
        });

        lv_listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
                long idPlaylist = Long.parseLong(adaptadorPlaylist.getArrayPlaylist().get(position).getId() + "");
                i.putExtra("playlist", idPlaylist);
                startActivity(i);
                finish();
            }
        });

    }
}
