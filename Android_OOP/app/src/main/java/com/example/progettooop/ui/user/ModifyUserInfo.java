package com.example.progettooop.ui.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettooop.R;
import com.example.progettooop.ui.mainDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class ModifyUserInfo extends AppCompatActivity implements View.OnClickListener {

    private static final int CHOOSE_IMAGE = 101;
    private static final int PERMISSION_CODE = 102;

    private final static String TAG = "Upload user";
    private ImageView image;
    private EditText username, address, phone;
    private EditText monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    private Button save;
    private ProgressBar loadingBar;

    private final static int mWidth = 30;
    private final static int mLenght = 30;
    private ArrayList<String> pathArray; //verrà salvato il percorso dell'immagine
    private int array_position;

    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    public FirebaseFirestore db;
    public Uri imguri;
    private StorageReference ref;


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
        loadingBar = (ProgressBar) findViewById(R.id.User_progressbar) ;

        monday = findViewById(R.id.modify_monday);
        tuesday = findViewById(R.id.modify_tuesday);
        thursday = findViewById(R.id.modify_thursday);
        wednesday = findViewById(R.id.modify_wednesday);
        friday = findViewById(R.id.modify_friday);
        saturday = findViewById(R.id.modify_saturday);
        sunday = findViewById(R.id.modify_sunday);

        loadingBar.setVisibility(View.GONE);

        pathArray = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("immagini");

        image.setClickable(true);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimetypemap = MimeTypeMap.getSingleton();
        return mimetypemap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!= null && data.getData()!=null) {
            imguri = data.getData();
            image.setImageURI(imguri);
        }
    }



    private void saveUserInfo() {
        String nick = username.getText().toString();
        if (nick.isEmpty()) {
            username.setError("Name Required");
            username.requestFocus();
            return;
        }

        String indirizzo = address.getText().toString();
        if (indirizzo.isEmpty()) {
            address.setError("Address Required");
            address.requestFocus();
            return;
        }

        String numero = phone.getText().toString();
        if (numero.isEmpty()) {
            phone.setError("Phone Number required");
            phone.requestFocus();
            return;
        }

        ;
        // loading the image , if it fails return, else it goes on
        if (imguri != null) {
            loadingBar.setVisibility(View.VISIBLE);
            String datapath = System.currentTimeMillis() + "." + getExtension(imguri);
            ref = mStorageRef.child(datapath);

            UploadTask uploadTask =  ref.putFile(imguri);
            Task<Uri>  uriTask = uploadTask.continueWithTask((task) -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
                    }).addOnCompleteListener((task) -> {
                        if (task.isSuccessful()){
                            imguri = task.getResult();
                        }
            });
            /*ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imguri = uri;
                            Log.wtf(TAG, "download image path : "+uri.toString() );
                            //now you have path to the uploaded file save this path to your database


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                             //TODO
                            return;
                        }
                    });
                    loadingBar.setVisibility(View.GONE);
                    Toast.makeText(ModifyUserInfo.this, "image uploaded correctly", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            loadingBar.setVisibility(View.GONE);
                        }
                    });*/

        Map<String, Object> user = new HashMap<>();
        user.put ("PhotoID",datapath);
        user.put("phone", numero);
        user.put("username", nick);
        user.put("address", indirizzo);
        user.put("monday",monday);
        user.put("tuesday",tuesday);
        user.put("wednesday",wednesday);
        user.put("thursday",thursday);
        user.put("friday",friday);
        user.put("saturday",saturday);
        user.put("sunday",sunday);

        db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser().getUid()))
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "correctly committed data to server", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "DocumentSnapshot");
                        Intent intent = new Intent(ModifyUserInfo.this, mainDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        loadingBar.setVisibility(View.GONE);
                        return;
                    }
                });
    }
        else {
            Toast.makeText(getApplicationContext(), "insert a Profile Image", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @SuppressLint("SetTextI18n")
    private void check_date(){
        if (monday.getText().equals("") || monday.getText() == null){
            monday.setText("Not Available");
        }
        if (tuesday.getText().equals("") || tuesday.getText() == null){
            tuesday.setText("Not Available");
        }
        if (wednesday.getText().equals("") || wednesday.getText() == null){
            wednesday.setText("Not Available");
        }
        if (thursday.getText().equals("") || monday.getText() == null){
            monday.setText("Not Available");
        }
        if (friday.getText().equals("") || friday.getText() == null){
            friday.setText("Not Available");
        }
        if (saturday.getText().equals("") || saturday.getText() == null){
            saturday.setText("Not Available");
        }
        if (sunday.getText().equals("") || sunday.getText() == null){
            sunday.setText("Not Available");
        }
    }

    @Override
    public void onClick(View view) {

    }





}