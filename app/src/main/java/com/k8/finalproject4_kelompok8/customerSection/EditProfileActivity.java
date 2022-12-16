package com.k8.finalproject4_kelompok8.customerSection;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.k8.finalproject4_kelompok8.R;
import com.k8.finalproject4_kelompok8.api.ApiClient;
import com.k8.finalproject4_kelompok8.utils.CompressingFile;
import com.k8.finalproject4_kelompok8.utils.Constants;
import com.k8.finalproject4_kelompok8.utils.SharedPreferenceUtils;
import com.k8.finalproject4_kelompok8.utils.UriHelper;
import com.k8.finalproject4_kelompok8.utils.responses.LoginResponse;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    CircleImageView profilePic;
    TextView backArrow;
    EditText editName, editEmail, editContact;
    Button updateInfoButton;

    private static final int PICK_FILE = 0;
    private static final String TAG = "Permissions Request: ";
    private static final int REQ_CODE = 324;
    private String cacheDir;

    File finalFile = null;
    Uri selectedUri = null;
    String iconsStoragePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        profilePic=findViewById(R.id.profilePic);
        editName=findViewById(R.id.editName);
        editEmail=findViewById(R.id.editEmail);

        editContact=findViewById(R.id.editContact);
        backArrow=findViewById(R.id.backArrow);
        updateInfoButton=findViewById(R.id.updateInfoBtn);

        setEditForm();

        iconsStoragePath = this.getExternalFilesDir(null).getAbsolutePath() + "/Pictures/WeTree";
        cacheDir = this.getExternalFilesDir(null).getAbsolutePath() + "/Pictures/WeTree/cache";


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions())
                    openFiles();
            }
        });

        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedUri == null && editName.getText().toString().isEmpty() && editEmail.getText().toString().isEmpty()
                        && editContact.getText().toString().isEmpty()) {
                    // Toast.makeText(EditProfileActivity.this, "There is nothing to be changed", Toast.LENGTH_LONG).show();
                    Snackbar.make(view,"There is nothing to be changed!",Snackbar.LENGTH_LONG).show();
                } else {
                    if (selectedUri == null) {

                        uploadFile(null, null);
                    } else if (finalFile == null) {
                        String path = UriHelper.getPath(EditProfileActivity.this, selectedUri);
                        uploadFile(new File(path), MediaType.parse(getContentResolver().getType(selectedUri)));
                    } else {
                        uploadFile(finalFile, MediaType.parse(getContentResolver().getType(selectedUri)));
                    }
                }
            }
        });

    }

    private void setEditForm() {
        editName.setText(SharedPreferenceUtils.getStringPreference(this, Constants.NAME));
        editContact.setText(SharedPreferenceUtils.getStringPreference(this,Constants.CONTACT));
        editEmail.setText(SharedPreferenceUtils.getStringPreference(this,Constants.EMAIL));
        String imageURL=SharedPreferenceUtils.getStringPreference(this,Constants.PROFILE_PIC);
        Picasso.get().load(Constants.BASE_URL+"/bus_ticket_booking/"+imageURL).into(profilePic);
    }


    private boolean validate() {
        String fullNameValue = editName.getText().toString();
        String emailIdValue = editEmail.getText().toString();
        String phoneNoValue = editContact.getText().toString();
        if (fullNameValue.isEmpty() || emailIdValue.isEmpty() || phoneNoValue.isEmpty() ) {
            Toast.makeText(EditProfileActivity.this, "Fields cannot be left empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (phoneNoValue.length() < 10) {
                editContact.setError("The phone number must be greater than 9 digits");
                return false;
            }

        }
        return true;
    }

    void uploadFile(File file, MediaType mimType) {
        if(validate()){
            String fullName, email, phnNumber, add ;
            fullName=editName.getText().toString();
            email=editEmail.getText().toString();
            phnNumber=editContact.getText().toString();

            RequestBody fullNameBody = RequestBody.create(MediaType.parse("text/plain"), fullName);
            RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody phnBody = RequestBody.create(MediaType.parse("text/plain"), phnNumber);
            RequestBody oldProfileLinkBody = RequestBody.create(MediaType.parse("text/plain"), SharedPreferenceUtils.getStringPreference(this,Constants.PROFILE_PIC));
            MultipartBody.Part body = null;

            if (file != null) {
                RequestBody reqFile = RequestBody.create(mimType, new CompressingFile().saveBitmapToFile(file));
                body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
            }

            String apiKey = SharedPreferenceUtils.getStringPreference(this, Constants.API_KEY);
            if (apiKey != null) {
                Call<LoginResponse> editProfileResponseCall = ApiClient.getApiService().edit_profile(apiKey, emailBody,fullNameBody, phnBody,  oldProfileLinkBody, body);
                editProfileResponseCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                SharedPreferenceUtils.setStringPreference(EditProfileActivity.this, Constants.NAME, response.body().getFullName());
                                SharedPreferenceUtils.setStringPreference(EditProfileActivity.this, Constants.PROFILE_PIC, response.body().getProfilePic());
                                SharedPreferenceUtils.setStringPreference(EditProfileActivity.this, Constants.EMAIL, response.body().getEmail());
                                SharedPreferenceUtils.setStringPreference(EditProfileActivity.this, Constants.CONTACT, response.body().getContactNo());
                                Toast.makeText(EditProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        }

    }

    private void openFiles() {
        Intent chooseFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(chooseFile, PICK_FILE);
    }

    Boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission is granted");
                return true;
            } else {

                Log.d(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_CODE);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG, "Permission is granted");
            return true;
        }

    }

    void setImageToImageView(Uri uri) {
        profilePic.setImageURI(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                setImageToImageView(uri);
                selectedUri = uri;
            }
        }
    }
}