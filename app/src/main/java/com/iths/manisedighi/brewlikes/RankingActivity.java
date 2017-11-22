package com.iths.manisedighi.brewlikes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RankingActivity extends AppCompatActivity {
    private ImageView beerImage;
    private TextView tasteText;
    private RatingBar tasteRate;
    private TextView priceText;
    private RatingBar priceRate;
    private EditText beerComment;
    private Button rankingButton;
    private TextView awfulText;
    private TextView perfectText;
    private TextView expensiveText;
    private TextView cheapText;
    private ScrollView categoryScroll;
    private EditText beerName;


    //private Bitmap bitMap;

    static final int REQUEST_TAKE_PHOTO = 1337;
    static final int RESULT_LOAD_IMAGE = 80085;
    static final int REQUEST_CODE = 7175;

    private static final String TAG = "RankingActivity";

    //CameraActivity cam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ranking);
        findViews();
        cameraLauncher();

    }

    /**
     * A method to find the views.
     */
    private void findViews(){
        beerImage = findViewById(R.id.beerImage);
        tasteText = findViewById(R.id.tasteText);
        tasteRate = findViewById(R.id.tasteRate);
        priceText = findViewById(R.id.priceText);
        priceRate = findViewById(R.id.priceRate);
        beerComment = findViewById(R.id.beerComment);
        rankingButton = findViewById(R.id.rankingButton);
        awfulText = findViewById(R.id.awfulText);
        perfectText = findViewById(R.id.perfectText);
        expensiveText = findViewById(R.id.expensiveText);
        cheapText = findViewById(R.id.cheapText);
        categoryScroll = findViewById(R.id.categoryScroll);
        beerName = findViewById(R.id.beerName);
    }

    /**
     * The method that does all the work with saving the rankings and put them into the database/infoviews.
     * @param view
     */
    private void onRankingButtonClick(View view){

        float taste = saveTasteRate(view);
        float price = savePriceRate(view);
        String name = saveBeerName(view);
        String comment = saveBeerComment(view);

    }

    /**
     * Saving the ranking-number of the taste.
     * @param view
     * @return a float for the number of stars filled in.
     */
    private float saveTasteRate(View view){
        return tasteRate.getRating();
    }

    /**
     * Saving the ranking-number of the price.
     * @param view
     * @return a float for the number of stars filled in.
     */
    private float savePriceRate(View view){
        return priceRate.getRating();
    }

    /**
     * Saving the name of the beer.
     * @param view
     * @return a String for the name of the beer.
     */
    private String saveBeerName(View view){
        return beerName.getText().toString();
    }
    /**
     * Saving the comment for the beer.
     * @param view
     * @return a String for the comment to the beer.
     */
    private String saveBeerComment(View view){
        return beerComment.getText().toString();
    }

    public void cameraLauncher() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RankingActivity.this);
        builder.setMessage("Please choose an alternative").setCancelable(false)
                .setPositiveButton("Take photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                    }
                })
                .setNeutralButton("Upload image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        /*
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image");
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        */
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Time to brew...");
        alert.show();

    }


    /**
     * To create and invoke the Intent for the picture. First, ensure that there's a camera activity to handle the intent.
     */

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // To create a file to put the picture in


        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ioException) {
                //Error occured while creating the File
                makeToast("Cannot create file");
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.iths.manisedighi.brewlikes.FileProvider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Log.d(TAG, "dispatchTakePictureIntent: this works");
            }
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap beerPhoto = (Bitmap) extras.get("data");
            beerImage.setImageBitmap(beerPhoto);

        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            scalePicture();
            addPictureToGallery();
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            //TODO find path to gallery

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.beerImage);

            Bitmap bmp = null;

            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            imageView.setImageBitmap(bmp);


        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    /**
     * Creates a collision-resistant name for the image file
     * @return the image with the new name
     * @throws IOException - if something goes wrong
     */


    String mCurrentPhotoPath;

    public File createImageFile() throws IOException {
        // Create a name for the image file
        String dateStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + dateStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //within parentheses: prefix, suffix, directory
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        //Save the file, path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "createImageFile: this works");
        return image;
    }


    /**
     * Adds the picture to the gallery
     */


    public void addPictureToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Log.d(TAG, "addPictureToGallery: this works");
    }


    /**
     * TODO give mImageView the same name as the shown ImageView
     * To scale down the picture and then decode it.
     */


    public void scalePicture() {
        //The dimensions of the View
        int targetW = beerImage.getWidth();
        int targetH = beerImage.getHeight();

        //The dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Decides how much to scale down the picture
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        //Decodes the image into Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        beerImage.setImageBitmap(bitmap);

        //return bitmap;
    }




    public void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
