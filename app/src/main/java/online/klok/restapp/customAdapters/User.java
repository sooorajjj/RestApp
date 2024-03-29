package online.klok.restapp.customAdapters;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import online.klok.restapp.models.UserModel;

public class User extends AppCompatActivity {

    private Spinner spinnerFood;
    private ProgressDialog dialog;
    private List<UserModel> userModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_users);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

//        lvUsers = (ListView)findViewById(R.id.lvUsers);
        spinnerFood = (Spinner) findViewById(R.id.spinFood);
        new JSONTask().execute("http://146.185.178.83/resttest/User");
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < userModelList.size(); i++) {
            lables.add(userModelList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerFood.setAdapter(spinnerAdapter);
    }

    public class JSONTask extends AsyncTask<String, String, List<UserModel> > {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<UserModel> doInBackground(String... params) {
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
                JSONArray parentArray = new JSONArray(finalJson);

                // finalBufferData stores all the data as string
//                StringBuffer finalBufferData = new StringBuffer();
                // for loop so it fetch all the json_object in the json_array

//                List<UserModel> userModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    UserModel userModel =gson.fromJson(finalObject.toString(), UserModel.class);
//                    UserModel userModel = new UserModel();
//                    userModel.setId(finalObject.getInt("id"));
//                    userModel.setName(finalObject.getString("name"));
//                    userModel.setUserId(finalObject.getString("userId"));
//                    userModel.setCreated_at(finalObject.getString("created_at"));
//                    userModel.setUpdated_at(finalObject.getString("updated_at"));
                    userModelList.add(userModel);
                }

                return userModelList;

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
        protected void onPostExecute(List<UserModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
//            UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.row_users, result);
//            lvUsers.setAdapter(adapter);
            populateSpinner();

//            TODO need to set the data to the list
        }
    }

//    public class UserAdapter extends ArrayAdapter {
//
//        public List<UserModel> userModelList;
//        private int resource;
//        private LayoutInflater inflater;
//        public UserAdapter(Context context, int resource, List<UserModel> objects) {
//            super(context, resource, objects);
//            userModelList = objects;
//            this.resource = resource;
//            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//
//            if(convertView == null){
//                holder = new ViewHolder();
//                convertView=inflater.inflate(resource, null);
//                holder.tvId = (TextView)convertView.findViewById(R.id.tvId);
//                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
//                holder.tvUserId = (TextView)convertView.findViewById(R.id.tvUserId);
//                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
//                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
//                convertView.setTag(holder);
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//
//
//            holder.tvId.setText("Id: " + userModelList.get(position).getId());
//            holder.tvName.setText("Name: " + userModelList.get(position).getName());
//            holder.tvUserId.setText("user_id: " + userModelList.get(position).getUserId());
//            holder.tvCreated_at.setText("Created_on: " + userModelList.get(position).getCreated_at());
//            holder.tvUpdated_at.setText("Updated_on: " + userModelList.get(position).getUpdated_at());
//
//            return convertView;
//        }
//
//        class ViewHolder{
//            private TextView tvId;
//            private TextView tvName;
//            private TextView tvUserId;
//            private TextView tvCreated_at;
//            private TextView tvUpdated_at;
//        }
//    }
}
