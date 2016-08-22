package online.klok.restapp.customAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import online.klok.restapp.models.CardBalanceModel;

/**
 * Created by klok on 22/8/16.
 */
public class CardBalance extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/CardBalance");

    }

    public class JSONTask extends AsyncTask<String, String, List<CardBalanceModel> > {

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
    public class CardBalanceAdapter extends ArrayAdapter {

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
}