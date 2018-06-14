package com.ingreens.roomorm.activities;

import android.Manifest;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ingreens.roomorm.DataBase.AppDatabase;
import com.ingreens.roomorm.R;
import com.ingreens.roomorm.adapters.MyDataAdapter;
import com.ingreens.roomorm.constants.AppConstants;
import com.ingreens.roomorm.models.MyDataModel;
import com.ingreens.roomorm.utils.BitmapManager;
import com.ingreens.roomorm.utils.ImageCompressor;

import java.io.File;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "ROOM CAMERA";
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    /*private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };*/

    private Uri fileuri;
    private RecyclerView recyclerView;
    private MyDataAdapter myDataAdapter;
    private AppDatabase appDatabase;
    private SharedPreferences preferences;
    private MyDataModel myDataModel;

    private ImageView imgvPlayer;
    private EditText etName;
    private EditText etTeam;
    private EditText etRank;
    private Button btnCapture,btnAddToView,btnNext;
    private Bitmap btmPlayerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        preferences=getSharedPreferences(AppConstants.SP_NAME,MODE_PRIVATE);
        appDatabase= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,AppConstants.DB_NAME)
                .allowMainThreadQueries()
                .build();
        recyclerView=findViewById(R.id.recycleView);
        imgvPlayer=findViewById(R.id.ivPhoto);
        etName=findViewById(R.id.etName);
        etTeam=findViewById(R.id.etTeam);
        etRank=findViewById(R.id.etRank);
        btnCapture=findViewById(R.id.btnCapture);
        btnAddToView=findViewById(R.id.btnAddToView);
        btnNext=findViewById(R.id.btnNext);
        btnCapture.setOnClickListener(this);
        btnAddToView.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        loadList();
    }

    private void loadList() {
       myDataAdapter=new MyDataAdapter(appDatabase.myDataDao().getAll(),this);
       recyclerView.setAdapter(myDataAdapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openCameraForShopImage(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){

            captureImage();

        }else {
            //requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSION);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
            //requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSION);
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileuri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);


        //fileUri=FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() +".com.ingreens.mycameraapp.provider",getOutputMediaFile(MEDIA_TYPE_IMAGE));


        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        System.out.println("@@@@@@@@@@@@@@@@@@@@ captureImage()"+intent+fileuri);
    }

    public Uri getOutputMediaFileUri(int type) {

//        fileUri=FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName() +".provider",getOutputMediaFile(MEDIA_TYPE_IMAGE));
//        fileUri=FileProvider.getUriForFile(this,this.getApplicationContext().getPackageName() +".provider",getOutputMediaFile(MEDIA_TYPE_IMAGE));
        //Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());


        return Uri.fromFile(getOutputMediaFile(type));
//        return fileUri;
    }
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCapture:
                openCameraForShopImage();
                break;

            case R.id.btnAddToView:
                insertDataIntoDb();
                Toast.makeText(this, "button Add Clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnNext:
                Toast.makeText(this, "button next clicked.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StudentActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {
                    // bimatp factory
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    String SelectedFilePath=new ImageCompressor(MainActivity.this).compressImage(fileuri.getPath());
                    String SelectedFileName=SelectedFilePath.substring(SelectedFilePath.lastIndexOf("/")+1);
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    System.out.println("shop image file path==="+SelectedFilePath);
                    System.out.println("shop image file name==="+SelectedFileName);
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    btmPlayerImage= BitmapFactory.decodeFile(SelectedFilePath);
                    imgvPlayer.setImageBitmap(btmPlayerImage);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                       "user canceled to Capture Image.", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void insertDataIntoDb() {

        String pName=etName.getText().toString();
        String pTeam=etTeam.getText().toString();
        int pRank= Integer.parseInt(etRank.getText().toString());
        String photo= BitmapManager.bitmapToBase64(btmPlayerImage);
        byte[] image=BitmapManager.bitmapToByte(btmPlayerImage);
        System.out.println("@@@@@@  BEFORE INSERT FROM FIELD @@@@@");
        System.out.println("player name=="+pName);
        System.out.println("player team=="+pTeam);
        System.out.println("player rank=="+pRank);
        //System.out.println("player photo(B64)=="+photo);
        System.out.println("player image=="+image);
        System.out.println("@@@@@@  BEFORE INSERT FROM FIELD @@@@@");

        myDataModel=new MyDataModel(pName,pTeam,pRank,photo,image);
        appDatabase.myDataDao().insertAll(myDataModel);

        System.out.println("@@@@@@  AFTER INSERT FROM DATABASE @@@@@");
        System.out.println("get all data  using database query"+appDatabase.myDataDao().getAll());
        System.out.println("get all data  using database query (toString)"+appDatabase.myDataDao().getAll().toString());
        System.out.println("@@@@@@  AFTER INSERT FROM DATABASE @@@@@");

        System.out.println("@@@@@@  AFTER INSERT FROM MODEL @@@@@");
        System.out.println("player name=="+myDataModel.getName());
        System.out.println("player team=="+myDataModel.getTeam());
        System.out.println("player rank=="+myDataModel.getRank());
        //System.out.println("player photo(B64)=="+myDataModel.getPhoto());
        System.out.println("player image=="+myDataModel.getImage());
        System.out.println("@@@@@@  AFTER INSERT FROM MODEL @@@@@");


        loadList();
        etName.getText().clear();
        etTeam.getText().clear();
        etRank.getText().clear();
    }

}
