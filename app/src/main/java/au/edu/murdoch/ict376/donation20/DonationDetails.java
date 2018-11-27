package au.edu.murdoch.ict376.donation20;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by 20160132 on 16/09/2016.
 */

public class DonationDetails extends AppCompatActivity {


    private int totalDonated = 0;

    private TextView  mTotalAmountTextView;

    // an extra for the total amount donated so far
    private static final String EXTRA_TOTAL_DONATED = "au.edu.murdoch.ict376.total_donated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);

        mTotalAmountTextView = (TextView) findViewById(R.id.total_donation_view);

        // retrieve the value from the intent
        totalDonated = getIntent().getIntExtra(EXTRA_TOTAL_DONATED, 0);

        mTotalAmountTextView.setText(Integer.toString(totalDonated));
    }


    // new intent
    public static final Intent newIntent(Context packageContext, int _totalDonated){

        Intent i = new Intent(packageContext, DonationDetails.class);

        i.putExtra(EXTRA_TOTAL_DONATED, _totalDonated);

        return i;

    }

}