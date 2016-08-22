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
import online.klok.restapp.models.OrderItemDetailsModel;

public class OrderItemDetails extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/OrderItemDetails");

    }

    public class JSONTask extends AsyncTask<String, String, List<OrderItemDetailsModel> > {

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
    public class OrderItemDetailsAdapter extends ArrayAdapter {

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
}
