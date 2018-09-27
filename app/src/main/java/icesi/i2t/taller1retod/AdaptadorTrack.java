package icesi.i2t.taller1retod;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorTrack extends BaseAdapter {

    private Activity activity;
    private ArrayList<Track> arrayTracks;

    public AdaptadorTrack(Activity activity){
        this.activity = activity;
        arrayTracks = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrayTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewPlay = inflater.inflate(R.layout.view_track, null, false);
        // Inicializo componentes del view
        TextView tv_nombre_cancion = viewPlay.findViewById(R.id.tv_nombre_cancion);
        TextView tv_artista_cancion = viewPlay.findViewById(R.id.tv_artista_cancion);
        TextView tv_ano_lanzamiento = viewPlay.findViewById(R.id.tv_ano_lanzamiento);
        ImageView iv_image = viewPlay.findViewById(R.id.iv_image);
        // Lleno los componentes del view
        tv_nombre_cancion.setText("Nombre Canción: "+arrayTracks.get(position).getTitle());
        tv_artista_cancion.setText("Artista Canción: "+arrayTracks.get(position).getArtist());
        tv_ano_lanzamiento.setText("Año Lanzamiento: "+arrayTracks.get(position).getDiscNumber());
        Picasso.get().load(arrayTracks.get(position).getPreviewUrl()).into(iv_image);

        return viewPlay;
    }

    public void agregarTrack(Track track){
        arrayTracks.add(track);
        notifyDataSetChanged();
    }

    public ArrayList<Track> getArrayTracks() {
        return arrayTracks;
    }

    public void setArrayTracks(ArrayList<Track> arrayTracks) {
        this.arrayTracks = arrayTracks;
    }
}
