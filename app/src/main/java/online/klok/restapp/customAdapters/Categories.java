package online.klok.restapp.customAdapters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import online.klok.restapp.R;
import online.klok.restapp.models.CategoriesModel;

public class Categories extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerFood;
    private Button okButton;
    private ProgressDialog dialog;
    private List<CategoriesModel> categoriesModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sub);
        setContentView(R.layout.row_categories);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

//        lvUsers = (ListView)findViewById(R.id.lvUsers);
        spinnerFood = (Spinner) findViewById(R.id.spinFood);
        okButton = (Button) findViewById(R.id.bOk);
        // spinner item select listener
        spinnerFood.setOnItemSelectedListener(this);
        new JSONTask().execute("http://146.185.178.83/resttest/categories");
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < categoriesModelList.size(); i++) {
            lables.add(categoriesModelList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerFood.setAdapter(spinnerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CategoriesModel categoriesModel = categoriesModelList.get(position);
        displayCategoriesInformation(categoriesModel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void displayCategoriesInformation(CategoriesModel categoriesModel) {


        //get references to your views
        TextView tvCategoryId = (TextView) findViewById(R.id.tvCategoryId);

        final int categoryId = categoriesModel.getId();


        //set values from your categoriesModel java object to textView
        tvCategoryId.setText("Id :  " + categoriesModel.getId());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Categories.this, SubCategories.class);
                intent.putExtra("parameter_name", categoryId);
                startActivity(intent);
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, List<CategoriesModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<CategoriesModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("movies");
                JSONArray parentArray = new JSONArray(finalJson);

                // finalBufferData stores all the data as string
//                StringBuffer finalBufferData = new StringBuffer();
                // for loop so it fetch all the json_object in the json_array


                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CategoriesModel categoriesModel = gson.fromJson(finalObject.toString(), CategoriesModel.class);
                    categoriesModelList.add(categoriesModel);
                }
                return categoriesModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CategoriesModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
//            CategoriesAdapter adapter = new CategoriesAdapter(getApplicationContext(), R.layout.row_categories, result);
//            lvUsers.setAdapter(adapter);
            populateSpinner();
//            TODO need to set the data to the list
        }
    }
}
