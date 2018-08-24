package br.edu.infnet.pedrolopes.tp2uploadfirebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class FotoAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> fotos;

    public FotoAdapter(Context context, List<Bitmap> fotos){
        this.context = context;
        this.fotos = fotos;
    }

    @Override
    public int getCount() {
        return this.fotos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.fotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap foto = (Bitmap) getItem(position);
        ImageView imgFoto = new ImageView(context);
        imgFoto.setPadding(10, 10, 10, 10);
        imgFoto.setImageBitmap(foto);
        return imgFoto;
    }
}
