package com.example.progettooop.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.progettooop.R;

import com.example.progettooop.ui.mainDashboard;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;


import com.google.firebase.storage.*;
import com.karumi.dexter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static android.content.ContentValues.TAG;

public class ModifyUserInfo extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_IMAGE = 101;
    private static final int PERMISSION_CODE = 102;

    private final static String TAG = "Upload user";
    private ImageView image;
    private EditText username, address, phone;
    private Button save;
    private final static int mWidth = 30;
    private final static int mLenght = 30;

    private ArrayList<String> pathArray; //verrà salvato il percorso dell'immagine
    private int array_position;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    public FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_information);
        username = (EditText) findViewById(R.id.modify_username);
        address = (EditText) findViewById(R.id.modify_address);
        phone =  (EditText) findViewById(R.id.modify_phone);
        image = (ImageView) findViewById(R.id.imageView);
        save = (Button) findViewById(R.id.save_changes_button);
        pathArray = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        checkFilePermissions();




    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = ModifyUserInfo.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += ModifyUserInfo.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }





























    //TODO

   /* private void openImageChooser() {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(getApplicationContext(), "yep", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(), "nope", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {}
                }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.e("Dexter", "There was an error: " + error.toString());
            }
        }).check();

    }*/


    /*private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_IMAGE);

    }*/

  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && requestCode == RESULT_OK)
            FilePropic = data.getData();
        imageView.setImageURI(FilePropic);
    }

    private void uploadImageToServer() {
        String uniqueId = UUID.randomUUID().toString();
        profileImageRef = FirebaseStorage.getInstance().getReference("profilepic/" + uniqueId);
        UploadTask uploadTask = profileImageRef.putFile(FilePropic);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
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

    }*/

    private void saveUserInfo() {
        String nick = username.getText().toString();
        if (nick.isEmpty()) {
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        String indirizzo = address.getText().toString();
        if (indirizzo.isEmpty()) {
            address.setError("Name Required");
            address.requestFocus();
            return;
        }

        String numero = phone.getText().toString();
        if (numero.isEmpty()) {
            phone.setError("Phone Number required");
            phone.requestFocus();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("telefono", numero);
        user.put("username",nick);
        user.put("indirizzo",indirizzo);


        db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser().getUid()))
                .set(user,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"correctly committed data to server",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "DocumentSnapshot" );
                Intent intent =new Intent(ModifyUserInfo.this, mainDashboard.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }



    @Override
    public void onClick(View view) {

    }
}