package com.example.project167.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project167.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class activity_predict extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final String API_URL = "https://731c-125-164-23-108.ngrok-free.app//prediksi";

    private ProgressBar progressBar;
    private ImageView imageView;
    private Button uploadButton;
    private Button selectButton;
    private Button downloadButton;
    private String selectedImageFilename;
    private Bitmap selectedImageBitmap;
    private String selectedAnnotatedImageUrl;
    private TextView title;
    private TextView subtitle;
    private String sublabel;
    private String modifiedSublabel;
    private String label;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String> selectImageLauncher;

    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        imageView = findViewById(R.id.imageView);
        selectButton = findViewById(R.id.selectButton);
        uploadButton = findViewById(R.id.predictButton);
        title = findViewById(R.id.txtConf);
        subtitle = findViewById(R.id.txtLabel);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                            Toast.makeText(activity_predict.this, "Image selected", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(activity_predict.this, "Failed to select image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Listener untuk tombol pilih gambar
        selectButton.setOnClickListener(v -> selectImageFromGallery());
    }

    private void selectImageFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                // Tampilkan dialog penjelasan...
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            } else {
                // Minta izin tanpa penjelasan...
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            }
        } else {
            // Izin telah diberikan, lanjutkan untuk membuka image picker...
            selectImageLauncher.launch("image/*");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, launch the image picker launcher...
            selectImageLauncher.launch("image/*");
        } else {
            // Tampilkan pesan bahwa izin diperlukan...
            Toast.makeText(this, "Permission is required to access the gallery.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    // Load the selected image into ImageView
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imageView.setImageBitmap(bitmap);

                    selectedImageFilename = getFilenameFromUri(imageUri);

                    // Show toast notification
                    Toast.makeText(activity_predict.this, "Image selected", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFilenameFromUri(Uri uri) {
        String filename = null;
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                filename = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filename;
    }

    private void uploadImage() {
        startTime = System.currentTimeMillis();
        // Get the selected image from ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Convert image to file
        File imageFile = bitmapToFile(bitmap);

        // Create HTTP client with timeouts
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // Timeout untuk membangun koneksi awal
                .writeTimeout(60, TimeUnit.SECONDS)    // Timeout untuk menulis data ke koneksi (penting untuk pengunggahan file)
                .readTimeout(60, TimeUnit.SECONDS)     // Timeout untuk membaca data dari koneksi
                .build();

        // Create multipart request body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                .build();

        // Create HTTP request
        Request request = new Request.Builder()
                .url("https://731c-125-164-23-108.ngrok-free.app/prediksi")
                .post(requestBody)
                .build();


        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Execute the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();  // Mendapatkan pesan error
                Log.e("Upload Error", errorMessage);  // Mencetak pesan error ke log dengan tag "Upload Error"
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Hide progress bar
                        progressBar.setVisibility(View.GONE);
                        // Menampilkan pesan error ke pengguna menggunakan Toast
                        Toast.makeText(activity_predict.this, "Failed to upload image: " + errorMessage, Toast.LENGTH_LONG).show();
                        // Calculate the time taken
                        long endTime = System.currentTimeMillis();
                        long totalTime = endTime - startTime;
                        // Display the time taken in a toast
                        Toast.makeText(activity_predict.this, "Process completed in " + totalTime/1000 + " seconds", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.d("Response", json);

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        final String annotatedImageUrl = jsonObject.optString("annotatedImageUrl");
                        label = jsonObject.optString("Conf");
                        sublabel = jsonObject.optString("Label");

                        // Show toast notification
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Hide progress bar
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(activity_predict.this, "Response received", Toast.LENGTH_SHORT).show();
                                // Show label
                                title.setText("Conf: " + label);
                                subtitle.setText("Label: " + sublabel);
                                // Calculate the time taken
                                long endTime = System.currentTimeMillis();
                                long totalTime = endTime - startTime;
                                // Display the time taken in a toast
                                Toast.makeText(activity_predict.this, "Process completed in " + totalTime/1000 + " seconds", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Enable the download button
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                downloadButton.setEnabled(true);
//                            }
//                        });

                        // Store the annotated image URL for downloading
                        selectedAnnotatedImageUrl = annotatedImageUrl;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Hide progress bar
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(activity_predict.this, "Failed to receive response", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private File bitmapToFile(Bitmap bitmap) {
        // Create a file in the cache directory
        File file = new File(getCacheDir(), "image.jpg");
        try {
            // Write the bitmap to the file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}