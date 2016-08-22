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
import online.klok.restapp.models.ModifiersModel;

public class Modifiers extends AppCompatActivity {

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
        new JSONTask().execute("http://146.185.178.83/resttest/Modifiers");

    }

    public class JSONTask extends AsyncTask<String, String, List<ModifiersModel> > {

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
    public class ModifiersAdapter extends ArrayAdapter {

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
}
