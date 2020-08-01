package com.example.progettooop.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.progettooop.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.*;


import java.io.IOException;
import java.security.Permissions;
import java.util.UUID;

public class ModifyUserInfo extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_IMAGE = 101;
    private static final int PERMISSION_CODE= 102;
    private EditText username, address, phone;
    private ImageView imageView;
    private Button save;
    private Uri FilePropic; // contains the data of the image
    private String photoStringLink; // contains the download string from the firebase server after the file has been uploaded
    private StorageReference profileImageRef;//contains the reference of the uploaded file into the firebase server
    private FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_information);
        username = findViewById(R.id.modify_username);
        address = findViewById(R.id.modify_address);
        phone = findViewById(R.id.modify_phone);
        imageView = findViewById(R.id.imageView);
        save = findViewById(R.id.save_changes_button);
        authenticator = FirebaseAuth.getInstance();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            saveUserInfo();
            }
        });

    }

    ;

    //image chooser
    private void openImageChooser() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                //permission not granted
                String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSION_CODE);
            }
            else{
                //permission granted
                pickImage();
            }
        }
        else{
            //os older than marshmallow
            pickImage();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length>0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    pickImage();}
                    else{
                        //permission denied
                    Toast.makeText(this,"Permission Denied...",Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_IMAGE);

    }

    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && requestCode == RESULT_OK)
            FilePropic = data.getData();
            imageView.setImageURI(FilePropic);
    }

    private void uploadImageToServer() {
        String uniqueId = UUID.randomUUID().toString();
        profileImageRef = FirebaseStorage.getInstance().getReference("profilepic/" + uniqueId);
          UploadTask uploadTask =  profileImageRef.putFile(FilePropic);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return profileImageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    System.out.println("Upload " + downloadUri);
                    Toast.makeText(getApplicationContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    if (downloadUri != null) {

                        photoStringLink = downloadUri.toString();
                        System.out.println("Upload " + photoStringLink);

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "File not uploaded successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveUserInfo(){
        String nick =  username.getText().toString();
        if(nick.isEmpty())
        {
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        String indirizzo =  address.getText().toString();
        if(nick.isEmpty())
        {
            address.setError("Name Required");
            address.requestFocus();
            return;
        }

        String numero =phone.getText().toString();
        if(numero.isEmpty()){
            phone.setError("Phone Number required");
            phone.requestFocus();
        }

        FirebaseUser user = authenticator.getCurrentUser();
    if (user!=null && photoStringLink!= null) {
        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                .setDisplayName(nick)
                .setPhotoUri(Uri.parse(photoStringLink))
                .build();

        user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "profile updated correctly", Toast.LENGTH_SHORT);
                } else {
                    //set task not successful
                }
            }
        });
    }

    }


    @Override
    public void onClick(View view) {

    }
}