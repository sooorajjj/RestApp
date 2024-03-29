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
import online.klok.restapp.models.CardDetailsModel;

public class CardDetails extends AppCompatActivity {

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
        new JSONTask4().execute("http://146.185.178.83/resttest/CardDetails");
    }

    public class JSONTask4 extends AsyncTask<String, String, List<CardDetailsModel> > {

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
    public class CardDetailsAdapter extends ArrayAdapter {

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
}
