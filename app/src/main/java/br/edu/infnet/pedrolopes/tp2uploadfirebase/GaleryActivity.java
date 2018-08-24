package br.edu.infnet.pedrolopes.tp2uploadfirebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GaleryActivity extends AppCompatActivity {

    private static final int RREQUEST_READ_PERMISSIONS_CODE = 100;

    private List<Bitmap> mLista;
    private GridView mGrdFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        mGrdFotos = findViewById(R.id.grid_local_img);
        mGrdFotos.setOnItemClickListener(selecionar);
        mLista = new ArrayList<>();

        carregarArquivos();

    }

    //Busca e exibe as imagens contidas na pasta de imagens do dispositivo
    private void carregarArquivos(){
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                    RREQUEST_READ_PERMISSIONS_CODE);
            return;
        }

        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        for(File image : diretorio.listFiles()){
            mLista.add(BitmapFactory.decodeFile(image.getPath()));
        }

        mGrdFotos.setAdapter(new FotoAdapter(this, mLista));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case RREQUEST_READ_PERMISSIONS_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    carregarArquivos();
                }
        }
    }

    //Ao clicar em uma imagem, envia os dados da imagem como resultado da requisição da MainActivity
    //e finaliza a activity atual
    private AdapterView.OnItemClickListener selecionar = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Bitmap imagem = mLista.get(position);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            Intent intent = new Intent();
            intent.putExtra("image", data);
            setResult(RESULT_OK, intent);
            finish();

        }
    };
}
