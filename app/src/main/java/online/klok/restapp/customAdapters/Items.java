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
import online.klok.restapp.models.ItemModel;

public class Items extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/Item");
    }

    public class JSONTask extends AsyncTask<String, String, List<ItemModel> > {

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
    public class ItemAdapter extends ArrayAdapter {

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
}
