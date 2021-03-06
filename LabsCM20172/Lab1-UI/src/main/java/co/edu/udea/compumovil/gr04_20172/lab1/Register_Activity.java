package co.edu.udea.compumovil.gr04_20172.lab1;


import android.app.DatePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class Register_Activity extends AppCompatActivity implements View.OnClickListener {

    private Uri imageCaptureUri;
    private ImageView myImageView;
    private static final int PICK_FROM_CAMERA=1;
    private static final int PICK_FROM_FILE=2;
    Button btn_choose_image;
    //private int day, month, year;
    //ImageButton imageViewProfile;
    Button btnsave;
    EditText eName;
    EditText eLastname;
    TextView eBorn;
    EditText eDirection;
    EditText eEmail;
    EditText ePassword;
    EditText ePhone;
    EditText eCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);


        btnsave = (Button) findViewById(R.id.btnSave);
        eName = (EditText) findViewById(R.id.editTextNombre);
        eLastname = (EditText) findViewById(R.id.editTextApellido);
        eBorn = (TextView) findViewById(R.id.editTextDate);
        eDirection = (EditText) findViewById(R.id.editTextAddress);
        eEmail = (EditText) findViewById(R.id.editTextEmail);
        ePassword = (EditText) findViewById(R.id.editTextPassword);
        ePhone = (EditText) findViewById(R.id.editTextPhone);
        eCity = (EditText) findViewById(R.id.editTextCity);
        eBorn.setOnClickListener(this);
        btnsave.setOnClickListener(this);



        final String[] items=new String[] {"From Cam", "From SD Card"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Select imagen");
        builder.setAdapter(adapter,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                if (which==0){
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file=new File(Environment.getExternalStorageDirectory(), "tmp_avatar"+String.valueOf(System.currentTimeMillis())+".jpg");
                    imageCaptureUri=Uri.fromFile(file);
                    try{
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageCaptureUri);
                        intent.putExtra("return data", true);

                        startActivityForResult(intent,PICK_FROM_CAMERA);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    dialog.cancel();
                }else{
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Complete_action_using"), PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog=builder.create();
        myImageView=(ImageView)findViewById(R.id.img_show);
        btn_choose_image=(Button)findViewById(R.id.btn_choose_image);
        btn_choose_image.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                    dialog.show();

                }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        Bitmap bitmap=null;
        String path="";
        if (requestCode==PICK_FROM_FILE){
            imageCaptureUri=data.getData();
            path=getRealPathfromURI(imageCaptureUri);
            if (path ==null){
                path= imageCaptureUri.getPath();
            }
            if (path !=null){
                bitmap= BitmapFactory.decodeFile(path);
            }
        }else{
            path=imageCaptureUri.getPath();
            bitmap=BitmapFactory.decodeFile(path);
        }
        myImageView.setImageBitmap(bitmap) ;

    }

    public String getRealPathfromURI(Uri contentUri){
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(contentUri, proj,null,null, null);
        if (cursor==null) return null;
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)


    @Override
    public void onClick(View v) {

        String textName = eName.getText().toString();
        String textLastname = eLastname.getText().toString();
        String textBorn = eBorn.getText().toString();
        String textDirection = eDirection.getText().toString();
        String textEmail = eEmail.getText().toString();
        String textPassword = ePassword.getText().toString();
        String textPhone = ePhone.getText().toString();
        String textCity = eCity.getText().toString();



        switch (v.getId()) {

            case R.id.editTextDate:
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                        eBorn.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
                break;

            /*case R.id.imageViewProfile:

                break;*/

            case R.id.btnSave:

                SharedPreferences sharedP = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedP.edit();
                editor.putString("nombre", textName);
                editor.putString("apellidos", textLastname);
                editor.putString("fechanacimiento", textBorn);
                editor.putString("direccion", textDirection);
                editor.putString("correo", textEmail);
                editor.putString("contraseña", textPassword);
                editor.putString("telefono", textPhone);
                editor.putString("ciudad", textCity);
                editor.apply();
                break;
        }

    }

}
