package online.klok.restapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import online.klok.restapp.models.CardBalanceModel;
import online.klok.restapp.models.CardDetailsModel;
import online.klok.restapp.models.CategoriesModel;
import online.klok.restapp.models.ExtrasModel;
import online.klok.restapp.models.FloorModel;
import online.klok.restapp.models.ItemModel;
import online.klok.restapp.models.LocationModel;
import online.klok.restapp.models.MasterModel;
import online.klok.restapp.models.ModifiersModel;
import online.klok.restapp.models.OrderItemDetailsModel;
import online.klok.restapp.models.OrderModel;
import online.klok.restapp.models.OrderPlaceModel;
import online.klok.restapp.models.SubcategoryModel;
import online.klok.restapp.models.UserModel;

public class MainActivity extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        lvUsers = (ListView)findViewById(R.id.lvUsers);

//                where there is only 1 json_object in a jason_array
//                new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");
    }

    public class JSONTask extends AsyncTask<String, String, List<UserModel> >{

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

                List<UserModel> userModelList = new ArrayList<>();

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
            UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.row_users, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }

    public class UserAdapter extends ArrayAdapter{

        public List<UserModel> userModelList;
        private int resource;
        private LayoutInflater inflater;
        public UserAdapter(Context context, int resource, List<UserModel> objects) {
            super(context, resource, objects);
            userModelList = objects;
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
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvUserId = (TextView)convertView.findViewById(R.id.tvUserId);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }



            holder.tvId.setText("Id: " + userModelList.get(position).getId());
            holder.tvName.setText("Name: " + userModelList.get(position).getName());
            holder.tvUserId.setText("user_id: " + userModelList.get(position).getUserId());
            holder.tvCreated_at.setText("Created_on: " + userModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated_on: " + userModelList.get(position).getUpdated_at());

            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvName;
            private TextView tvUserId;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;
        }
    }

    public class JSONTask1 extends AsyncTask<String, String, List<SubcategoryModel> >{

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
    public class SubcategoryAdapter extends ArrayAdapter{

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

    public class JSONTask2 extends AsyncTask<String, String, List<FloorModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<FloorModel> doInBackground(String... params) {
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

                List<FloorModel> floorModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    FloorModel floorModel = gson.fromJson(finalObject.toString(), FloorModel.class);
                    floorModelList.add(floorModel);
                }
                return floorModelList;

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
        protected void onPostExecute(List<FloorModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            FloorAdapter adapter = new FloorAdapter(getApplicationContext(), R.layout.row_floor, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class FloorAdapter extends ArrayAdapter{

        public List<FloorModel> floorModelList;
        private int resource;
        private LayoutInflater inflater;
        public FloorAdapter(Context context, int resource, List<FloorModel> objects) {
            super(context, resource, objects);
            floorModelList = objects;
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
                holder.tvFloorId = (TextView)convertView.findViewById(R.id.tvFloorId);
                holder.tvFloorName = (TextView)convertView.findViewById(R.id.tvFloorName);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id : " + floorModelList.get(position).getId());
            holder.tvFloorId.setText("Floor Id : " + floorModelList.get(position).getFloorId());
            holder.tvFloorName.setText("Floor Name: " + floorModelList.get(position).getFloorName());
            holder.tvCreated_at.setText("Created On: " + floorModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + floorModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvFloorId;
            private TextView tvFloorName;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask3 extends AsyncTask<String, String, List<CardBalanceModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<CardBalanceModel> doInBackground(String... params) {
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

                List<CardBalanceModel> cardBalanceModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CardBalanceModel cardBalanceModel = gson.fromJson(finalObject.toString(), CardBalanceModel.class);
                    cardBalanceModelList.add(cardBalanceModel);
                }
                return cardBalanceModelList;

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
        protected void onPostExecute(List<CardBalanceModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            CardBalanceAdapter adapter = new CardBalanceAdapter(getApplicationContext(), R.layout.row_cardbalance, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class CardBalanceAdapter extends ArrayAdapter{

        public List<CardBalanceModel> cardBalanceModelList;
        private int resource;
        private LayoutInflater inflater;
        public CardBalanceAdapter(Context context, int resource, List<CardBalanceModel> objects) {
            super(context, resource, objects);
            cardBalanceModelList = objects;
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
                holder.tvCardId = (TextView)convertView.findViewById(R.id.tvCardId);
                holder.tvMainBalance = (TextView)convertView.findViewById(R.id.tvMainBalance);
                holder.tvBonusBalance = (TextView)convertView.findViewById(R.id.tvBonusBalance);
                holder.tvBonusPoints = (TextView)convertView.findViewById(R.id.tvBonusPoints);
                holder.tvActivationDate = (TextView)convertView.findViewById(R.id.tvActivationDate);
                holder.tvLastTransactionDate = (TextView)convertView.findViewById(R.id.tvLastTransactionDate);
                holder.tvExpiryDate = (TextView)convertView.findViewById(R.id.tvExpiryDate);
                holder.tvActiveStatus = (TextView)convertView.findViewById(R.id.tvActiveStatus);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + cardBalanceModelList.get(position).getId());
            holder.tvCardId.setText("Card Id: " + cardBalanceModelList.get(position).getCardId());
            holder.tvMainBalance.setText("Main Balance: " + cardBalanceModelList.get(position).getMainBalance());
            holder.tvBonusBalance.setText("Bonus Balance: " + cardBalanceModelList.get(position).getBonusBalance());
            holder.tvBonusPoints.setText("Bonus Points: " + cardBalanceModelList.get(position).getBonusPoints());
            holder.tvActivationDate.setText("Activation Date: " + cardBalanceModelList.get(position).getActivationDate());
            holder.tvLastTransactionDate.setText("Last Transaction Date: " + cardBalanceModelList.get(position).getLastTransactionDate());
            holder.tvExpiryDate.setText("Expiry Date: " + cardBalanceModelList.get(position).getExpiryDate());
            holder.tvActiveStatus.setText("Active Status: " + cardBalanceModelList.get(position).getActiveStatus());
            holder.tvCreated_at.setText("Created On: " + cardBalanceModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + cardBalanceModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvCardId;
            private TextView tvMainBalance;
            private TextView tvBonusBalance;
            private TextView tvBonusPoints;
            private TextView tvActivationDate;
            private TextView tvLastTransactionDate;
            private TextView tvExpiryDate;
            private TextView tvActiveStatus;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask4 extends AsyncTask<String, String, List<CardDetailsModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<CardDetailsModel> doInBackground(String... params) {
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

                List<CardDetailsModel> cardDetailsModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CardDetailsModel cardDetailsModel = gson.fromJson(finalObject.toString(), CardDetailsModel.class);
                    cardDetailsModelList.add(cardDetailsModel);
                }
                return cardDetailsModelList;

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
        protected void onPostExecute(List<CardDetailsModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            CardDetailsAdapter adapter = new CardDetailsAdapter(getApplicationContext(), R.layout.row_carddetails, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class CardDetailsAdapter extends ArrayAdapter{

        public List<CardDetailsModel> cardDetailsModelList;
        private int resource;
        private LayoutInflater inflater;
        public CardDetailsAdapter(Context context, int resource, List<CardDetailsModel> objects) {
            super(context, resource, objects);
            cardDetailsModelList = objects;
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
                holder.tvCardId = (TextView)convertView.findViewById(R.id.tvCardId);
                holder.tvCardHolderName = (TextView)convertView.findViewById(R.id.tvCardHolderName);
                holder.tvContactNo = (TextView)convertView.findViewById(R.id.tvContactNo);
                holder.tvAddress = (TextView)convertView.findViewById(R.id.tvAddress);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + cardDetailsModelList.get(position).getId());
            holder.tvCardId.setText("Card Id: " + cardDetailsModelList.get(position).getCardId());
            holder.tvCardHolderName.setText("Card Name: " + cardDetailsModelList.get(position).getCardHolderName());
            holder.tvContactNo.setText("Contact No: " + cardDetailsModelList.get(position).getContactNo());
            holder.tvAddress.setText("Address: " + cardDetailsModelList.get(position).getAddress());
            holder.tvCreated_at.setText("Created On: " + cardDetailsModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + cardDetailsModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvCardId;
            private TextView tvCardHolderName;
            private TextView tvContactNo;
            private TextView tvAddress;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask5 extends AsyncTask<String, String, List<CategoriesModel> >{

        @Override
        protected void onPreExecute(){
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

                List<CategoriesModel> categoriesModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CategoriesModel categoriesModel = gson.fromJson(finalObject.toString(), CategoriesModel.class);
                    categoriesModelList.add(categoriesModel);
                }
                return categoriesModelList;

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
        protected void onPostExecute(List<CategoriesModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            CategoriesAdapter adapter = new CategoriesAdapter(getApplicationContext(), R.layout.row_categories, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class CategoriesAdapter extends ArrayAdapter{

        public List<CategoriesModel> categoriesModelList;
        private int resource;
        private LayoutInflater inflater;
        public CategoriesAdapter(Context context, int resource, List<CategoriesModel> objects) {
            super(context, resource, objects);
            categoriesModelList = objects;
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

            holder.tvId.setText("Id: " + categoriesModelList.get(position).getId());
            holder.tvCategoryId.setText("Category Id: " + categoriesModelList.get(position).getCategoryId());
            holder.tvName.setText("Name: " + categoriesModelList.get(position).getName());
            holder.tvShortName.setText("Short Name: " + categoriesModelList.get(position).getShortName());
            holder.tvCreated_at.setText("Created On: " + categoriesModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + categoriesModelList.get(position).getUpdated_at());
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

    public class JSONTask6 extends AsyncTask<String, String, List<ExtrasModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<ExtrasModel> doInBackground(String... params) {
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

                List<ExtrasModel> extrasModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ExtrasModel extrasModel = gson.fromJson(finalObject.toString(), ExtrasModel.class);
                    extrasModelList.add(extrasModel);
                }
                return extrasModelList;

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
        protected void onPostExecute(List<ExtrasModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ExtrasAdapter adapter = new ExtrasAdapter(getApplicationContext(), R.layout.row_extras, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class ExtrasAdapter extends ArrayAdapter{

        public List<ExtrasModel> extrasModelList;
        private int resource;
        private LayoutInflater inflater;
        public ExtrasAdapter(Context context, int resource, List<ExtrasModel> objects) {
            super(context, resource, objects);
            extrasModelList = objects;
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
                holder.tvExtrasId = (TextView)convertView.findViewById(R.id.tvExtrasId);
                holder.tvItem = (TextView)convertView.findViewById(R.id.tvItem);
                holder.tvExtrasName = (TextView)convertView.findViewById(R.id.tvExtrasName);
                holder.tvShortName = (TextView)convertView.findViewById(R.id.tvShortName);
                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);
                holder.tvSalesRate = (TextView)convertView.findViewById(R.id.tvSalesRate);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + extrasModelList.get(position).getId());
            holder.tvExtrasId.setText("Extras Id: " + extrasModelList.get(position).getExtrasId());
            holder.tvItem.setText("Item: " + extrasModelList.get(position).getItem());
            holder.tvExtrasName.setText("Extras Name: " + extrasModelList.get(position).getExtrasName());
            holder.tvShortName.setText("Short Name: " + extrasModelList.get(position).getShortName());
            holder.tvDescription.setText("Description: " + extrasModelList.get(position).getDescription());
            holder.tvSalesRate.setText("Sales Rate: " + extrasModelList.get(position).getSalesRate());
            holder.tvCreated_at.setText("Created On: " + extrasModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + extrasModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvExtrasId;
            private TextView tvItem;
            private TextView tvExtrasName;
            private TextView tvShortName;
            private TextView tvDescription;
            private TextView tvSalesRate;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask7 extends AsyncTask<String, String, List<ItemModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<ItemModel> doInBackground(String... params) {
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

                List<ItemModel> itemModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ItemModel itemModel = gson.fromJson(finalObject.toString(), ItemModel.class);
                    itemModelList.add(itemModel);
                }
                return itemModelList;

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
        protected void onPostExecute(List<ItemModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.row_item, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class ItemAdapter extends ArrayAdapter{

        public List<ItemModel> itemModelList;
        private int resource;
        private LayoutInflater inflater;
        public ItemAdapter(Context context, int resource, List<ItemModel> objects) {
            super(context, resource, objects);
            itemModelList = objects;
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
                holder.tvItemId = (TextView)convertView.findViewById(R.id.tvItemId);
                holder.tvItemName = (TextView)convertView.findViewById(R.id.tvItemName);
                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);
                holder.tvSalesRate = (TextView)convertView.findViewById(R.id.tvSalesRate);
                holder.tvOtherLang = (TextView)convertView.findViewById(R.id.tvOtherLang);
                holder.tvCategoryId = (TextView)convertView.findViewById(R.id.tvCategoryId);
                holder.tvSubCategoryId = (TextView)convertView.findViewById(R.id.tvSubCategoryId);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + itemModelList.get(position).getId());
            holder.tvItemId.setText("Item Id: " + itemModelList.get(position).getItemId());
            holder.tvItemName.setText("Item Name: " + itemModelList.get(position).getItemName());
            holder.tvDescription.setText("Description: " + itemModelList.get(position).getDescription());
            holder.tvSalesRate.setText("Sales Rate: " + itemModelList.get(position).getSalesRate());
            holder.tvOtherLang.setText("Other Lang: " + itemModelList.get(position).getOtherLang());
            holder.tvCategoryId.setText("Category Id: " + itemModelList.get(position).getCategoryId());
            holder.tvSubCategoryId.setText("SubCategory Id: " + itemModelList.get(position).getSubCategoryId());
            holder.tvCreated_at.setText("Created On: " + itemModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + itemModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvItemId;
            private TextView tvItemName;
            private TextView tvDescription;
            private TextView tvSalesRate;
            private TextView tvOtherLang;
            private TextView tvCategoryId;
            private TextView tvSubCategoryId;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask8 extends AsyncTask<String, String, List<ModifiersModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<ModifiersModel> doInBackground(String... params) {
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

                List<ModifiersModel> modifiersModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ModifiersModel modifiersModel = gson.fromJson(finalObject.toString(), ModifiersModel.class);
                    modifiersModelList.add(modifiersModel);
                }
                return modifiersModelList;

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
        protected void onPostExecute(List<ModifiersModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ModifiersAdapter adapter = new ModifiersAdapter(getApplicationContext(), R.layout.row_modifiers, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class ModifiersAdapter extends ArrayAdapter{

        public List<ModifiersModel> modifiersModelList;
        private int resource;
        private LayoutInflater inflater;
        public ModifiersAdapter(Context context, int resource, List<ModifiersModel> objects) {
            super(context, resource, objects);
            modifiersModelList = objects;
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
                holder.tvModifierId = (TextView)convertView.findViewById(R.id.tvModifierId);
                holder.tvItem = (TextView)convertView.findViewById(R.id.tvItem);
                holder.tvModifierName = (TextView)convertView.findViewById(R.id.tvModifierName);
                holder.tvShortName = (TextView)convertView.findViewById(R.id.tvShortName);
                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription);
                holder.tvSalesRate = (TextView)convertView.findViewById(R.id.tvSalesRate);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + modifiersModelList.get(position).getId());
            holder.tvModifierId.setText("Modifier Id: " + modifiersModelList.get(position).getModifierId());
            holder.tvItem.setText("Item: " + modifiersModelList.get(position).getItem());
            holder.tvModifierName.setText("Modifier Name: " + modifiersModelList.get(position).getModifierName());
            holder.tvShortName.setText("Short Name: " + modifiersModelList.get(position).getShortName());
            holder.tvDescription.setText("Description: " + modifiersModelList.get(position).getDescription());
            holder.tvSalesRate.setText("Sales Rate: " + modifiersModelList.get(position).getSalesRate());
            holder.tvCreated_at.setText("Created On: " + modifiersModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + modifiersModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvModifierId;
            private TextView tvItem;
            private TextView tvModifierName;
            private TextView tvShortName;
            private TextView tvDescription;
            private TextView tvSalesRate;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask9 extends AsyncTask<String, String, List<OrderModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<OrderModel> doInBackground(String... params) {
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

                List<OrderModel> orderModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    OrderModel orderModel = gson.fromJson(finalObject.toString(), OrderModel.class);
                    orderModelList.add(orderModel);
                }
                return orderModelList;

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
        protected void onPostExecute(List<OrderModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            OrderAdapter adapter = new OrderAdapter(getApplicationContext(), R.layout.row_order, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class OrderAdapter extends ArrayAdapter{

        public List<OrderModel> orderModelList;
        private int resource;
        private LayoutInflater inflater;
        public OrderAdapter(Context context, int resource, List<OrderModel> objects) {
            super(context, resource, objects);
            orderModelList = objects;
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
                holder.tvOrderId = (TextView)convertView.findViewById(R.id.tvOrderId);
                holder.tvOrderTotal = (TextView)convertView.findViewById(R.id.tvOrderTotal);
                holder.tvCardNo = (TextView)convertView.findViewById(R.id.tvCardNo);
                holder.tvTotalItemsCount= (TextView)convertView.findViewById(R.id.tvTotalItemsCount);
                holder.tvPrimaryItem = (TextView)convertView.findViewById(R.id.tvPrimaryItem);
                holder.tvExtraItem = (TextView)convertView.findViewById(R.id.tvExtraItem);
                holder.tvModifierItem = (TextView)convertView.findViewById(R.id.tvModifierItem);
                holder.tvLocationId = (TextView)convertView.findViewById(R.id.tvLocationId);
                holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
                holder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + orderModelList.get(position).getId());
            holder.tvOrderId.setText("Order Id: " + orderModelList.get(position).getOrderId());
            holder.tvOrderTotal.setText("Order Total: " + orderModelList.get(position).getOrderTotal());
            holder.tvCardNo.setText("Card No: " + orderModelList.get(position).getCardNo());
            holder.tvTotalItemsCount.setText("TotalItemsCount: " + orderModelList.get(position).getTotalItemsCount());
            holder.tvPrimaryItem.setText("Primary Item: " + orderModelList.get(position).getPrimaryItem());
            holder.tvExtraItem.setText("Extra Item: " + orderModelList.get(position).getExtraItem());
            holder.tvModifierItem.setText("Modifier Item: " + orderModelList.get(position).getModifierItem());
            holder.tvLocationId.setText("Location Id: " + orderModelList.get(position).getLocationId());
            holder.tvTime.setText("Time: " + orderModelList.get(position).getTime());
            holder.tvDate.setText("Date: " + orderModelList.get(position).getDate());
            holder.tvCreated_at.setText("Created On: " + orderModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + orderModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvOrderId;
            private TextView tvOrderTotal;
            private TextView tvCardNo;
            private TextView tvTotalItemsCount;
            private TextView tvPrimaryItem;
            private TextView tvExtraItem;
            private TextView tvModifierItem;
            private TextView tvTime;
            private TextView tvDate;
            private TextView tvLocationId;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask10 extends AsyncTask<String, String, List<OrderItemDetailsModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<OrderItemDetailsModel> doInBackground(String... params) {
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

                List<OrderItemDetailsModel> orderItemDetailsModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    OrderItemDetailsModel orderItemDetailsModel = gson.fromJson(finalObject.toString(), OrderItemDetailsModel.class);
                    orderItemDetailsModelList.add(orderItemDetailsModel);
                }
                return orderItemDetailsModelList;

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
        protected void onPostExecute(List<OrderItemDetailsModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            OrderItemDetailsAdapter adapter = new OrderItemDetailsAdapter(getApplicationContext(), R.layout.row_orderitemdetails, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class OrderItemDetailsAdapter extends ArrayAdapter{

        public List<OrderItemDetailsModel> orderItemDetailsModelList;
        private int resource;
        private LayoutInflater inflater;
        public OrderItemDetailsAdapter(Context context, int resource, List<OrderItemDetailsModel> objects) {
            super(context, resource, objects);
            orderItemDetailsModelList = objects;
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
                holder.tvOrderId = (TextView)convertView.findViewById(R.id.tvOrderId);
                holder.tvItemId = (TextView)convertView.findViewById(R.id.tvItemId);
                holder.tvItemType = (TextView)convertView.findViewById(R.id.tvItemType);
                holder.tvQuantity = (TextView)convertView.findViewById(R.id.tvQuantity);
                holder.tvItemTotal = (TextView)convertView.findViewById(R.id.tvItemTotal);
                holder.tvSalesRate = (TextView)convertView.findViewById(R.id.tvSalesRate);
                holder.tvItemCount = (TextView)convertView.findViewById(R.id.tvItemCount);
                holder.tvPayment = (TextView)convertView.findViewById(R.id.tvPayment);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + orderItemDetailsModelList.get(position).getId());
            holder.tvOrderId.setText("Order Id: " + orderItemDetailsModelList.get(position).getOrderId());
            holder.tvItemId.setText("Item Id: " + orderItemDetailsModelList.get(position).getItemId());
            holder.tvItemType.setText("Item Typ: " + orderItemDetailsModelList.get(position).getItemType());
            holder.tvQuantity.setText("Quantity: " + orderItemDetailsModelList.get(position).getQuantity());
            holder.tvItemTotal.setText("Item Total: " + orderItemDetailsModelList.get(position).getItemTotal());
            holder.tvSalesRate.setText("Sales Rate: " + orderItemDetailsModelList.get(position).getSalesRate());
            holder.tvItemCount.setText("Item Count: " + orderItemDetailsModelList.get(position).getItemCount());
            holder.tvPayment.setText("Payment: " + orderItemDetailsModelList.get(position).getPayment());
            holder.tvCreated_at.setText("Created On: " + orderItemDetailsModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + orderItemDetailsModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvOrderId;
            private TextView tvItemId;
            private TextView tvItemType;
            private TextView tvQuantity;
            private TextView tvItemTotal;
            private TextView tvSalesRate;
            private TextView tvItemCount;
            private TextView tvPayment;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask11 extends AsyncTask<String, String, List<OrderPlaceModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }
        @Override
        protected List<OrderPlaceModel> doInBackground(String... params) {
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

                List<OrderPlaceModel> orderPlaceModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    OrderPlaceModel orderPlaceModel =gson.fromJson(finalObject.toString(), OrderPlaceModel.class);
                    orderPlaceModelList.add(orderPlaceModel);
                }

                return orderPlaceModelList;

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
        protected void onPostExecute(List<OrderPlaceModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            OrderPlaceAdapter adapter = new OrderPlaceAdapter(getApplicationContext(), R.layout.row_orderplace, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }

    public class OrderPlaceAdapter extends ArrayAdapter{

        public List<OrderPlaceModel> orderPlaceModelList;
        private int resource;
        private LayoutInflater inflater;
        public OrderPlaceAdapter(Context context, int resource, List<OrderPlaceModel> objects) {
            super(context, resource, objects);
            orderPlaceModelList = objects;
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
                holder.tvUserId = (TextView)convertView.findViewById(R.id.tvUserId);
                holder.tvCardId = (TextView)convertView.findViewById(R.id.tvCardId);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }



            holder.tvId.setText("Id: " + orderPlaceModelList.get(position).getId());
            holder.tvUserId.setText("user Id: " + orderPlaceModelList.get(position).getUserId());
            holder.tvCardId.setText("Card Id: " + orderPlaceModelList.get(position).getCardId());
            holder.tvCreated_at.setText("Created_on: " + orderPlaceModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated_on: " + orderPlaceModelList.get(position).getUpdated_at());

            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvUserId;
            private TextView tvCardId;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;
        }
    }

    public class JSONTask12 extends AsyncTask<String, String, List<MasterModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<MasterModel> doInBackground(String... params) {
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

                List<MasterModel> masterModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    MasterModel masterModel = gson.fromJson(finalObject.toString(), MasterModel.class);
                    masterModelList.add(masterModel);
                }
                return masterModelList;

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
        protected void onPostExecute(List<MasterModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            MasterAdapter adapter = new MasterAdapter(getApplicationContext(), R.layout.row_master, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class MasterAdapter extends ArrayAdapter{

        public List<MasterModel> masterModelList;
        private int resource;
        private LayoutInflater inflater;
        public MasterAdapter(Context context, int resource, List<MasterModel> objects) {
            super(context, resource, objects);
            masterModelList = objects;
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
                holder.tvMasterId = (TextView)convertView.findViewById(R.id.tvMasterId);
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvDetails = (TextView)convertView.findViewById(R.id.tvDetails);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + masterModelList.get(position).getId());
            holder.tvMasterId.setText("Master Id: " + masterModelList.get(position).getMasterId());
            holder.tvName.setText("Name: " + masterModelList.get(position).getName());
            holder.tvDetails.setText("Details: " + masterModelList.get(position).getDetails());
            holder.tvCreated_at.setText("Created On: " + masterModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + masterModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvMasterId;
            private TextView tvName;
            private TextView tvDetails;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }

    public class JSONTask13 extends AsyncTask<String, String, List<LocationModel> >{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<LocationModel> doInBackground(String... params) {
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

                List<LocationModel> locationModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    LocationModel locationModel = gson.fromJson(finalObject.toString(), LocationModel.class);
                    locationModelList.add(locationModel);
                }
                return locationModelList;

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
        protected void onPostExecute(List<LocationModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            LocationAdapter adapter = new LocationAdapter(getApplicationContext(), R.layout.row_location, result);
            lvUsers.setAdapter(adapter);
//            TODO need to set the data to the list
        }
    }
    public class LocationAdapter extends ArrayAdapter{

        public List<LocationModel> locationModelList;
        private int resource;
        private LayoutInflater inflater;
        public LocationAdapter(Context context, int resource, List<LocationModel> objects) {
            super(context, resource, objects);
            locationModelList = objects;
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
                holder.tvLocationId = (TextView)convertView.findViewById(R.id.tvLocationId);
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
                holder.tvMasterId = (TextView)convertView.findViewById(R.id.tvMasterId);
                holder.tvCreated_at = (TextView)convertView.findViewById(R.id.tvCreated_at);
                holder.tvUpdated_at = (TextView)convertView.findViewById(R.id.tvUpdated_at);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvId.setText("Id: " + locationModelList.get(position).getId());
            holder.tvLocationId.setText("Location Id: " + locationModelList.get(position).getLocationId());
            holder.tvName.setText("Name: " + locationModelList.get(position).getName());
            holder.tvMasterId.setText("Master Id: " + locationModelList.get(position).getMasterId());
            holder.tvCreated_at.setText("Created On: " + locationModelList.get(position).getCreated_at());
            holder.tvUpdated_at.setText("Updated On: " + locationModelList.get(position).getUpdated_at());
            return convertView;
        }

        class ViewHolder{
            private TextView tvId;
            private TextView tvLocationId;
            private TextView tvName;
            private TextView tvMasterId;
            private TextView tvCreated_at;
            private TextView tvUpdated_at;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_users) {
            new JSONTask().execute("http://146.185.178.83/resttest/User");
            return true;
        }else if (id == R.id.action_floor) {
            new JSONTask2().execute("http://146.185.178.83/resttest/Floor");
            return true;
        }else if (id == R.id.action_cardBalance) {
            new JSONTask3().execute("http://146.185.178.83/resttest/CardBalance");
            return true;
        }else if (id == R.id.action_cardDetails) {
            new JSONTask4().execute("http://146.185.178.83/resttest/CardDetails");
            return true;
        }else if (id == R.id.action_categories) {
            new JSONTask5().execute("http://146.185.178.83/resttest/Categories");
            return true;
        }else if (id == R.id.action_extras) {
            new JSONTask6().execute("http://146.185.178.83/resttest/Extras");
            return true;
        }else if (id == R.id.action_item) {
            new JSONTask7().execute("http://146.185.178.83/resttest/Item");
            return true;
        }else if (id == R.id.action_modifiers) {
            new JSONTask8().execute("http://146.185.178.83/resttest/Modifiers");
            return true;
        }else if (id == R.id.action_order) {
            new JSONTask9().execute("http://146.185.178.83/resttest/Order");
            return true;
        }else if (id == R.id.action_orderItemDetails) {
            new JSONTask10().execute("http://146.185.178.83/resttest/OrderItemDetails");
            return true;
        }else if (id == R.id.action_orderPlaces) {
            new JSONTask11().execute("http://146.185.178.83/resttest/OrderPlace");
            return true;
        }else if (id == R.id.action_subcategories) {
            new JSONTask1().execute("http://146.185.178.83/resttest/SubCategories");
            return true;
        }else if (id == R.id.action_master) {
            new JSONTask12().execute("http://146.185.178.83/resttest/Master");
            return true;
        }else if (id == R.id.action_location) {
            new JSONTask13().execute("http://146.185.178.83/resttest/Location");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
