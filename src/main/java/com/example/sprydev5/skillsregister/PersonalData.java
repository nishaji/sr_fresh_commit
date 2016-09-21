package com.example.sprydev5.skillsregister;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.sdsmdg.tastytoast.TastyToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class PersonalData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private PersonalData mActivity;
    private static final String TAG = "MainActivity";

    String[] location = {"India ", "Australia", "Japan", "China"};
    String[] position = {"Manager ", "Executive", "HR", "CEO"};
    EditText name, phone, email;
    TextView edtdob;
    MaterialBetterSpinner positionn,locationn;
    private DatabaseHelper dbHelper;
    String loc, candidate, phonee, emaill, dobb;
    Toolbar toolbar;
    FloatingActionButton selectimg;
    ImageView viewimg;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Uri selectedImageUri;
    Bitmap bitmap;
   ImageView img;
    byte[] byteArray;
    byte[] inputData = new byte[0];
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldata);
        toolbar = (Toolbar) findViewById(R.id.personaltool);
        toolbar.setTitle("  Personal Details");
        toolbar.setTitleTextColor(0xffffffff);
        toolbar.setLogo(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(toolbar);
        //dbHelper = new DatabaseHelper(PersonalData.this);
       /* dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        name = (EditText) findViewById(R.id.name);
        viewimg = (ImageView) findViewById(R.id.viewimage);
        toolbar = (Toolbar) findViewById(R.id.personal_tool);
        locationn = (MaterialBetterSpinner) findViewById(R.id.location);
        positionn = (MaterialBetterSpinner) findViewById(R.id.position);
        edtdob = (TextView) findViewById(R.id.dobtxt);
        edtdob.setInputType(InputType.TYPE_NULL);
        try{
            ArrayList<String>personalfulldetail=dbHelper.getAllpeople();
            System.out.println("fulldetail"+personalfulldetail);
            if(personalfulldetail.isEmpty()){
                ArrayList<String>people=dbHelper.getuserdata();
                System.out.println(people+"from sign up");
                String f_name=people.get(0);
                String l_name=people.get(1);
                String fullname=f_name.concat(" ").concat(l_name);
                String usremail=people.get(2);
                name.setText(fullname);
                email.setText(usremail);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_dropdown_item_1line, location);
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_dropdown_item_1line, position);
                locationn.setAdapter(arrayAdapter);
                positionn.setAdapter(arrayAdapter1);
            }else {
                byteArray =dbHelper.retreiveImageFromDB();
                System.out.println(byteArray+"image byte from db");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String full_name=personalfulldetail.get(2);
                String userphone=personalfulldetail.get(3);
                String usremail=personalfulldetail.get(5);
                String usrlocation=personalfulldetail.get(1);
                String usrposition=personalfulldetail.get(4);
                String dob=personalfulldetail.get(6);

                System.out.println(personalfulldetail+"personal detail form personal table");
                name.setText(full_name);
                email.setText(usremail);
                phone.setText(userphone);
                edtdob.setText(dob);
                viewimg.setImageBitmap(bmp);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                inputData = bytes.toByteArray();
                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, position);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                positionn.setAdapter(arrayAdapter1);
                if (!usrposition.equals(null)) {
                    int spinnerPosition = arrayAdapter1.getPosition(usrposition);
                    try {
                        if (usrposition.trim().length() > 0) {
                            positionn.setText(arrayAdapter1.getItem(spinnerPosition).toString());
                            //positionn.setSelection(spinnerPosition);
                        } else {
                            System.out.println("do nothing");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, location);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                locationn.setAdapter(arrayAdapter);
                if (!usrlocation.equals(null)) {
                    int spinnerPosition = arrayAdapter.getPosition(usrlocation);
                    try {
                        if (usrlocation.trim().length() > 0) {
                            locationn.setText(arrayAdapter.getItem(spinnerPosition).toString());
                        } else {
                            System.out.println("do nothing");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }
        img = (ImageView) findViewById(R.id.picture);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        toolbar.setTitle("  Personal Details");
        toolbar.setLogo(R.drawable.arrowlleft);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalData.this, LanucherActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
      ImageView img=(ImageView)findViewById(R.id.dob);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PersonalData.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(PersonalData.this.getFragmentManager(), "Datepickerdialog");
            }
        });

*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();

         /*   try{

                String _id = "1";
                if (locationn.getText().toString().length() == 0 || name.getText().toString().length() == 0 || positionn.getText().toString().length() == 0 || email.getText().toString().length() == 0 || phone.getText().toString().length() == 0 || edtdob.getText().toString().length() == 0) {
                    TastyToast.makeText(getApplicationContext(), "Searching for required fields", TastyToast.LENGTH_LONG,
                            TastyToast.INFO);
                } else {

                    try {
                        if (null != selectedImageUri) {
                            InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
                            inputData = Utils.getBytes(iStream);
                        } else {
                            System.out.println("image is null");
                        }
                    } catch (IOException ioe) {
                        Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
                    }
                    if (dbHelper.isExist(_id)) {
                        System.out.println("exist");
                        if (dbHelper.updatepersonaldetail(_id,locationn.getText().toString(), name.getText().toString(),positionn.getText().toString(), email.getText().toString(), phone.getText().toString(),edtdob.getText().toString(),inputData)) {
                            //  new PersonalDetail().execute("HIGH");
                            TastyToast.makeText(getApplicationContext(), "Personal data Updated successfully !", TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS);
                            Intent intent = new Intent(PersonalData.this, ViewProfile.class);
                            startActivity(intent);
                        } else {
                            TastyToast.makeText(getApplicationContext(), "Could not inserted sucessfully!", TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR);
                        }

                    } else {
                        System.out.println("not exist");
                        if (dbHelper.insert_personal_info(locationn.getText().toString(), name.getText().toString(), positionn.getText().toString(), email.getText().toString(), phone.getText().toString(), edtdob.getText().toString(), inputData)) {
                            //  new PersonalDetail().execute("HIGH");
                            TastyToast.makeText(getApplicationContext(), "Personal data saved successfully !", TastyToast.LENGTH_LONG,
                                    TastyToast.SUCCESS);
                            Intent intent = new Intent(PersonalData.this, ViewProfile.class);
                            intent.putExtra("location", locationn.getText().toString());
                            intent.putExtra("name", name.getText().toString());
                            intent.putExtra("position", positionn.getText().toString());
                            intent.putExtra("email", email.getText().toString());
                            intent.putExtra("phone", phone.getText().toString());
                            intent.putExtra("dob", edtdob.getText().toString());
                            intent.putExtra("img", inputData);
                            startActivity(intent);
                        } else {
                            TastyToast.makeText(getApplicationContext(), "Could not inserted sucessfully!", TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR);
                        }

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }*/


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =" "+dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        edtdob.setText(date);
    }

   /* private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalData.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(PersonalData.this);
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(result)
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image*//*");
                    if(result)
                    startActivityForResult(Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            selectedImageUri = data.getData();
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    onSelectFromGalleryResult(data);
                } else if (requestCode == REQUEST_CAMERA) {
                    onCaptureImageResult(data);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        inputData = bytes.toByteArray();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewimg.setImageBitmap(bitmap);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
        viewimg.setImageBitmap(bitmap);
    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*String et1,et2,et3,et4,et5,et6;
        et1=locationn.getText().toString();
        et2=name.getText().toString();
        et3=positionn.getText().toString();
        et4= email.getText().toString();
        et5=phone.getText().toString();
        et6=edtdob.getText().toString();

        if (!et1.isEmpty()||et2.isEmpty()||et3.isEmpty()||et4.isEmpty()||et5.isEmpty()||et6.isEmpty()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("you want to go back without saving your data ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(PersonalData.this,LanucherActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setOnCancelListener(null);
            builder.show();

         }
         else{
            startActivity(new Intent(PersonalData.this,LanucherActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            System.out.println("do nothing");
         }
*/
    }
}






