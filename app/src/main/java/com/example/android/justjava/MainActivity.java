/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    //boolean isChecked= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked and holds the message to be displayed once submitted.
     * EditText conversion to String by EditText method name.getText().toString();
     */
    public void submitOrder(View view) {
        EditText giveName = (EditText) findViewById(R.id.insert_name);
        String saveCustomer = giveName.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(saveCustomer, hasWhippedCream, hasChocolate, quantity, price);
        //displayMessage(priceMessage);

        //Created a new Intent that links directly to email once user hits order button allowing them to access email summary directly
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject) + " " + saveCustomer);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

    }

    /** The way I implemented checkbox using a separate method
     *
     * @param view
     * @return public boolean checkedBox(View view){
    isChecked = true;
    return isChecked;


    }
     */
    /**
     * This method creates an order summary for the mobile application with these params
     *
     * @param customerName
     * @param quantity
     * @param addChocolate
     * @param addWhippedCream
     * @param total
     * @return
     */
    private String createOrderSummary(String customerName, boolean addWhippedCream, boolean addChocolate, int quantity, int total) {

        return getString(R.string.order_summary_name, customerName) + "\n" + getString(R.string.add_whip_cream)+ addWhippedCream + "\n" + getString(R.string.add_chocolate) + addChocolate + "\n" + getString(R.string.quantity_jv) + quantity + "\n" + getString(R.string.total_jv)  + NumberFormat.getCurrencyInstance().format(total) + "\n" + getString(R.string.thank_you);


    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price = quantity * 5;


        // Add $1 to base price for Whipped cream for cup
        if (addWhippedCream) {
            price = quantity * (5 + 1);
        }
        //Add $2 to base price for Chocolate for cup
        if (addChocolate) {
            price = quantity * (5 + 2);
        }
        //Add $3 to base price for both cups-
        if (addChocolate && addWhippedCream) {
            price = quantity * (5 + 2 + 1);
        }
        return price;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void incrementOrder(View view) {

        //How to create a Toast to popup a message on device with warning
        Context context = getApplicationContext();
        CharSequence maxCupsOrdered = "Sorry the maximum order is 100 cups. Thanks for yor enthusiasm!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, maxCupsOrdered, duration);

        if (quantity <= 100) {
            quantity = quantity + 1;

        }
        if (quantity >= 101) {
            toast.show();
            quantity = 100;
            return;
        }

        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decreaseOrder(View view) {

        //How to create a Toast to popup a message on device with warning
        Context context = getApplicationContext();
        CharSequence belowZeroCups = "Sorry no negative cups served here only positivity!";
        int duration = Toast.LENGTH_SHORT;

        // For brevity you can also simply just Toast.makeText
        Toast toast = Toast.makeText(context, belowZeroCups, duration);

        if (quantity >= 0) {
            quantity = quantity - 1;
        }
        if (quantity < 0) {
            toast.show();
            quantity = 0;
            return;

        }
        displayQuantity(quantity);
    }


    /**
     * This method displays the given text on the screen.

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
     */   

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}