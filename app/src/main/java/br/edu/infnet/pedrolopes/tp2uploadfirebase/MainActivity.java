package br.edu.infnet.pedrolopes.tp2uploadfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private Button mBtnSelect, mBtnUpload;
    private ImageView mImgUpload;

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

    private View.OnClickListener selectImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener uploadImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "UPLOAD", Toast.LENGTH_SHORT).show();
        }
    };
}
