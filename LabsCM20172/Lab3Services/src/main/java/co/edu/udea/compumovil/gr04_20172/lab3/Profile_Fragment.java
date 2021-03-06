package co.edu.udea.compumovil.gr04_20172.lab3;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {

    // LINK DEL SERIVDOR
    private final String HOST_CODE = "https://ensuenoservices-jersonlopez.c9users.io";

    // COMPLEMENTOS
    private final String URL_CUSTOMERS_COMPLEMENT = ":8080/api/Customers/";
    private final String URL_CONTAINER_DOWN_COMPLEMENT = ":8080/api/Containers/user/download/";

    private final String URL_CUSTOMERS = HOST_CODE.concat(URL_CUSTOMERS_COMPLEMENT);
    private final String URL_CONTAINER_DOWN = HOST_CODE.concat(URL_CONTAINER_DOWN_COMPLEMENT);

    Context context;
    String email;
    Cursor cursor;
    TextView nameProfile=null, lastnameProfile=null, emailProfile=null, genderProfile=null, phoneProfile=null;
    ImageView photo;
    Bitmap bitmap;
    SwitchPreference sw;


    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getActivity().getIntent().getStringExtra("email");
        getCustomer();
        //Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

    }

    private void addPreferencesFromResource(int settings) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_, container,false);
        nameProfile = v.findViewById(R.id.textViewNameProfile);
        lastnameProfile = v.findViewById(R.id.textViewLastnameProfile);
        emailProfile = v.findViewById(R.id.textViewEmailProfile);
        genderProfile = v.findViewById(R.id.textViewGender);
        phoneProfile = v.findViewById(R.id.textViewNumberphoneProfile);
        photo = v.findViewById(R.id.imageViewProfile);
        //Toast.makeText(getActivity(), "acabe en el perfil", Toast.LENGTH_SHORT).show();
        return v;
    }

    private void getCustomer() {
        String id_Customer = email.toString();
        if ("".equals(id_Customer)){
            Toast.makeText(getActivity(), "Ingrese un Id", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_CUSTOMERS.concat(id_Customer), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Customer customer = new Gson().fromJson(response.toString(), Customer.class);

                        nameProfile.setText(customer.getUsername());
                        lastnameProfile.setText(customer.getUserlastname());
                        emailProfile.setText(customer.getId());
                        genderProfile.setText(customer.getGender());
                        phoneProfile.setText(customer.getNumberphone());

                        Glide.with(Profile_Fragment.this)
                                .load(URL_CONTAINER_DOWN.concat(String.valueOf(customer.getId())).concat(customer.getPhoto()))
                                .into(photo);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error consultando información", Toast.LENGTH_SHORT).show();
                        //Log.d("nada2",error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}
