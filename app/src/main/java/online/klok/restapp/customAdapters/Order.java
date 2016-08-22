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
import online.klok.restapp.models.OrderModel;

public class Order extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/Order");

    }

    public class JSONTask extends AsyncTask<String, String, List<OrderModel> > {

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
    public class OrderAdapter extends ArrayAdapter {

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
}
