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
import online.klok.restapp.models.OrderPlaceModel;

public class OrderPlaces extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/OrderPlace");
    }

    public class JSONTask extends AsyncTask<String, String, List<OrderPlaceModel> > {

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

    public class OrderPlaceAdapter extends ArrayAdapter {

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
}
