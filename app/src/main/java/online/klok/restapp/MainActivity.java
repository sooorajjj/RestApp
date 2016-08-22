package online.klok.restapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import online.klok.restapp.customAdapters.CardBalance;
import online.klok.restapp.customAdapters.CardDetails;
import online.klok.restapp.customAdapters.Categories;
import online.klok.restapp.customAdapters.Extras;
import online.klok.restapp.customAdapters.Floor;
import online.klok.restapp.customAdapters.Items;
import online.klok.restapp.customAdapters.Location;
import online.klok.restapp.customAdapters.Master;
import online.klok.restapp.customAdapters.Modifiers;
import online.klok.restapp.customAdapters.Order;
import online.klok.restapp.customAdapters.OrderItemDetails;
import online.klok.restapp.customAdapters.OrderPlaces;
import online.klok.restapp.customAdapters.SubCategories;
import online.klok.restapp.customAdapters.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bUsers, bFloor, bCardBalance, bCardDetails, bCategories, bExtras, bItems,
            bModifiers, bOrder, bOrderItemDetails, bOrderPlaces, bSubCategories, bMaster, bLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        listener();

    }

    private void initialize() {
        bUsers = (Button) findViewById(R.id.bUsers);
        bFloor = (Button) findViewById(R.id.bFloor);
        bCardBalance = (Button) findViewById(R.id.bCardBalance);
        bCardDetails = (Button) findViewById(R.id.bCardDetails);
        bCategories = (Button) findViewById(R.id.bCategories);
        bExtras = (Button) findViewById(R.id.bExtras);
        bItems = (Button) findViewById(R.id.bItems);
        bModifiers = (Button) findViewById(R.id.bModifiers);
        bOrder = (Button) findViewById(R.id.bOrder);
        bOrderItemDetails = (Button) findViewById(R.id.bOrderItemDetails);
        bOrderPlaces = (Button) findViewById(R.id.bOrderPlaces);
        bSubCategories = (Button) findViewById(R.id.bSubCategories);
        bMaster = (Button) findViewById(R.id.bMaster);
        bLocation = (Button) findViewById(R.id.bLocation);
    }

    private void listener() {
        bUsers.setOnClickListener(this);
        bFloor.setOnClickListener(this);
        bCardBalance.setOnClickListener(this);
        bCardDetails.setOnClickListener(this);
        bCategories.setOnClickListener(this);
        bExtras.setOnClickListener(this);
        bItems.setOnClickListener(this);
        bModifiers.setOnClickListener(this);
        bOrder.setOnClickListener(this);
        bOrderItemDetails.setOnClickListener(this);
        bOrderPlaces.setOnClickListener(this);
        bSubCategories.setOnClickListener(this);
        bMaster.setOnClickListener(this);
        bLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bUsers) {
            Intent intent = new Intent(MainActivity.this, User.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bFloor) {
            Intent intent = new Intent(MainActivity.this, Floor.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bCardBalance) {
            Intent intent = new Intent(MainActivity.this, CardBalance.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bCardDetails) {
            Intent intent = new Intent(MainActivity.this, CardDetails.class);
            startActivity(intent);

        }

        if (v.getId() == R.id.bCategories) {
            Intent intent = new Intent(MainActivity.this, Categories.class);
            startActivity(intent);

        }

        if (v.getId() == R.id.bExtras) {
            Intent intent = new Intent(MainActivity.this, Extras.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bItems) {
            Intent intent = new Intent(MainActivity.this, Items.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bModifiers) {
            Intent intent = new Intent(MainActivity.this, Modifiers.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bOrder) {
            Intent intent = new Intent(MainActivity.this, Order.class);
            startActivity(intent);

        }if (v.getId() == R.id.bOrderItemDetails) {
            Intent intent = new Intent(MainActivity.this, OrderItemDetails.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bOrderPlaces) {
            Intent intent = new Intent(MainActivity.this, OrderPlaces.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bSubCategories) {
            Intent intent = new Intent(MainActivity.this, SubCategories.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.bMaster) {
            Intent intent = new Intent(MainActivity.this, Master.class);
            startActivity(intent);

        }if (v.getId() == R.id.bLocation) {
            Intent intent = new Intent(MainActivity.this, Location.class);
            startActivity(intent);
        }

    }
}
