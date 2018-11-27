package au.edu.murdoch.ict376.donation20;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;


public class Donate extends AppCompatActivity {

    private Button donateButton;
    private Button showStatusButton;        // to show donation status

    private RadioGroup paymentMethod;
    // private ProgressBar progressBar;
    private NumberPicker amountPicker;


    private int totalDonated = 0;
    private int donorId;

    // an extra for the total amount donated so far
    //private static final String EXTRA_TOTAL_DONATED = "au.edu.murdoch.ict376.total_donated";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().getExtras() != null)
        {
            Bundle b = getIntent().getExtras();
            donorId = b.getInt("num");
        }

        // Create the button
        donateButton = (Button) findViewById(R.id.donateButton);
        showStatusButton = (Button) findViewById(R.id.showStatusButton);

        if (donateButton != null) {
            Log.v("Donate", "Really got the donate button");
        }

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        //progressBar   = (ProgressBar)  findViewById(R.id.progressBar);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);

        // set the min and max values for the amount picker
        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void donateButtonPressed(View view) {
        int amount = amountPicker.getValue();
        int radioId = paymentMethod.getCheckedRadioButtonId();

        String method = "";
        if (radioId == R.id.PayPal) {
            method = "PayPal";
        } else {
            method = "Direct";
        }

        totalDonated = totalDonated + amount;
        //progressBar.setProgress(totalDonated);

        Log.v("Donate", "Donate Pressed! with amount " + amount + ", method: " + method);
        Log.v("Donate", "Current total " + totalDonated);

        updateDatabase(amount);

    }

    public void showStatusButtonPressed(View view) {

        Intent i = DonationDetails.newIntent(Donate.this, totalDonated);

        startActivity(i);

        Log.v("Donate", "Show status Pressed! with amount " + totalDonated);

    }

    public void updateDatabase(int amount) {

        ContentResolver resolver;
        String authority = "ict376.murdoch.edu.au.donationdatabase.DonationProvider";
        Uri mUri = Uri.parse("content://" + authority);

        ContentValues cv = new ContentValues();
        cv.put("contact_id", donorId);
        cv.put("amount_donated", amount);

        resolver = getContentResolver();
        resolver.insert(mUri, cv);
    }



}
