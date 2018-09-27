package icesi.i2t.taller1retod;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPlaylist extends BaseAdapter {

    private Activity activity;
    private ArrayList<Playlist> arrayPlaylist;

    public AdaptadorPlaylist(Activity activity){
        this.activity = activity;
        arrayPlaylist = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrayPlaylist.size();
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
        View viewPlay = inflater.inflate(R.layout.view_play, null, false);
        // Inicializo componentes del view
        TextView tv_nombre_lista = viewPlay.findViewById(R.id.tv_nombre_lista);
        TextView tv_nombre_creador = viewPlay.findViewById(R.id.tv_nombre_creador);
        TextView tv_numero_items = viewPlay.findViewById(R.id.tv_numero_items);
        ImageView iv_image = viewPlay.findViewById(R.id.iv_image);
        // Lleno los componentes del view
        tv_nombre_lista.setText("Nombre Lista: "+arrayPlaylist.get(position).getTitle());
        tv_nombre_creador.setText("Nombre Creador: "+arrayPlaylist.get(position).getCreator());
        tv_numero_items.setText("Numero Items: "+arrayPlaylist.get(position).getTracks().size());
        Picasso.get().load(arrayPlaylist.get(position).getSmallImageUrl()).into(iv_image);

        return viewPlay;
    }

    public void agregarPlaylist(Playlist playlist){
        arrayPlaylist.add(playlist);
        notifyDataSetChanged();
    }

    public ArrayList<Playlist> getArrayPlaylist() {
        return arrayPlaylist;
    }

    public void setArrayPlaylist(ArrayList<Playlist> arrayPlaylist) {
        this.arrayPlaylist = arrayPlaylist;
    }
}
