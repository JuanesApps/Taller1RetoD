package icesi.i2t.taller1retod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;

public class MainActivity extends AppCompatActivity {

    private EditText et_buscar_lista;
    private ImageButton ib_buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_buscar_lista = findViewById(R.id.et_buscar_lista);
        ib_buscar = findViewById(R.id.ib_buscar);

        String aplicationID = "301624";
        final DeezerConnect deezerConnect = new DeezerConnect(this, aplicationID);

        // The set of Deezer Permissions needed by the app
        String[] permissions = new String[]{
                Permissions.BASIC_ACCESS,
                Permissions.MANAGE_LIBRARY,
                Permissions.LISTENING_HISTORY};

        // The listener for authentication events
        DialogListener listener = new DialogListener() {

            public void onComplete(Bundle values) {
                // store the current authentication info
                SessionStore sessionStore = new SessionStore();
                sessionStore.save(deezerConnect, null);
            }

            public void onCancel() {
            }

            public void onException(Exception e) {
            }
        };

        // Launches the authentication process
        deezerConnect.authorize(this, permissions, listener);

    }
}
