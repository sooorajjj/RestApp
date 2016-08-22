package online.klok.restapp.customAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import online.klok.restapp.models.SubcategoryModel;

public class SubCategories extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        lvUsers = (ListView) findViewById(R.id.lvUsers);
        new JSONTask().execute("http://146.185.178.83/resttest/SubCategories");
    }

    public class JSONTask extends AsyncTask<String, String, List<SubcategoryModel> > {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<SubcategoryModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line ="";
                while ((line=reader.readLine()) !=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("movies");
                JSONArray parentArray = new JSONArray(finalJson);

                // finalBufferData stores all the data as string
//                StringBuffer finalBufferData = new StringBuffer();
                // for loop so it fetch all the json_object in the json_array

                List<SubcategoryModel> subcategoryModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    SubcategoryModel subcategoryModel = gson.fromJson(finalObject.toString(), SubcategoryModel.class);
                    subcategoryModelList.add(subcategoryModel);
                }
                return subcategoryModelList;

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection !=null) {
                    connection.disconnect();
                }
                try {
                    if (reader !=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<SubcategoryModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            SubcategoryAdapter adapter = new SubcategoryAdapter(getApplicationContext(), R.layout.row_subcategory, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class SubcategoryAdapter extends ArrayAdapter {

        public List<SubcategoryModel> subcategoryModelList;
        private int resource;
        private LayoutInflater inflater;
        public SubcategoryAdapter(Context context, int resource, List<SubcategoryModel> objects) {
            super(context, resource, objects);
            subcategoryModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView=inflater.inflate(resource, null);
                holder.tvId = (TextView)convertView.findViewById(R.id.tvId);
                holder.tvCategoryId = (TextView)convertView.findViewById(R.id.tvCategoryId);
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvShortName = (TextView)convertView.findViewById(R.id.tvShortName);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id : " + subcategoryModelList.get(position).getId());
            holder.tvCategoryId.setText("Category Id : " + subcategoryModelList.get(position).getCategoryId());
            holder.tvName.setText("Name: "+ subcategoryModelList.get(position).getName());
            holder.tvShortName.setText("Short Name: "+subcategoryModelList.get(position).getShortName());
            holder.tvCreated_at.setText("Created On: "+ subcategoryModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: "+subcategoryModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvCategoryId;
            private TextView tvName;
            private TextView tvShortName;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }
}
