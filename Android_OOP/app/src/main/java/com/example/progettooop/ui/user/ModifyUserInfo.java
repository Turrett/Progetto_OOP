package com.example.progettooop.ui.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.progettooop.R;

import java.io.IOException;

public class ModifyUserInfo extends AppCompatActivity implements View.OnClickListener{
    private static final int CHOOSE_IMAGE = 101;
    private EditText username , address , phone;
    private ImageView imageView;
    private Button save;
    private Uri uriprofileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_information);
        username = findViewById(R.id.modify_username);
        address = findViewById(R.id.modify_address);
        phone = findViewById(R.id.modify_phone);
        imageView = findViewById(R.id.imageView);
        save = findViewById(R.id.save_changes_button);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    };
 //image chooser
    private void openImageChooser() {
        Intent intent = new Intent() ;
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"select profile image"),CHOOSE_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHOOSE_IMAGE&&requestCode==RESULT_OK &&data!=null&&data.getData()!=null)
    uriprofileImage=data.getData();

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriprofileImage);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

    }
}