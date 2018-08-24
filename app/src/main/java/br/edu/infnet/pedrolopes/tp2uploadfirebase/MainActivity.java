package br.edu.infnet.pedrolopes.tp2uploadfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button mBtnSelect, mBtnUpload;
    private ImageView mImgUpload;
    private byte[] mImageByteArr;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mImgUpload = findViewById(R.id.img_upload);
        mBtnSelect = findViewById(R.id.btn_select);
        mBtnUpload = findViewById(R.id.btn_upload);

        mBtnSelect.setOnClickListener(selectImage);
        mBtnUpload.setOnClickListener(uploadImage);

    }

    //Recebe o resultado da activity com a imagem selecionada pelo usuário e exibe em uma ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                try{
                    mImageByteArr = data.getByteArrayExtra("image");
                    Bitmap image = BitmapFactory.decodeByteArray(mImageByteArr, 0, mImageByteArr.length);

                    mImgUpload.setImageBitmap(image);
                } catch (NullPointerException ex){
                    Log.e("RESULT_CODE", ex.getMessage());
                }
            }
        }
    }

    //Inicia a activity que irá lidar com a exibição e seleção da imagem na galeria
    private View.OnClickListener selectImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            startActivityForResult(new Intent(getApplicationContext(), GaleryActivity.class), 1);

        }
    };

    //Realiza o processo de upload da imagem convertida em um Array de bytes para o Storage do Firebase
    private View.OnClickListener uploadImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nome = String.valueOf(new Date().getTime());
            UploadTask uploadTask = mStorageRef.child(nome).putBytes(mImageByteArr);

            //Verifica o retorno do Storage após a tentativa de envio
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "Houve um erro no envio", Toast.LENGTH_SHORT).show();
                    Log.e("FIREBASE_UPLOAD_TASK", exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "Envio efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    Log.i("FIREBASE_UPLOAD_TASK", taskSnapshot.getMetadata().getName() + " Enviado");
                }
            });

        }
    };
}
