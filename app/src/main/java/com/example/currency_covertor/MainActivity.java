package com.example.currency_covertor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Pattern currency = Pattern.compile("\\d+(\\.\\d+)?");
    private double usd_to_inr = 70.0;

    public boolean isCurrency(String str) {
        if (str == null)
            return false;
        return currency.matcher(str).matches();
    }

    public double strtodouble(String str) {
        if (!isCurrency(str))
            return 0;

        int i = 0;
        double result = 0;
        boolean calcDecimal = false;
        double decimal = 0.0;

        // Integral part handling.
        while (i < str.length()) {
            char digit = str.charAt(i);
            i++;
            if (digit == '.') {
                calcDecimal = true;
                break;
            }
            result = result * 10 + ( digit - '0');
        }

        //Fractional part handling.
        if (calcDecimal) {
            int divisor = 1;
            while (i < str.length()) {
                char digit = str.charAt(i);
                decimal = decimal * 10 + (digit - '0');
                divisor *= 10;
                i++;
            }
            decimal = decimal / divisor;
        }

        result += decimal;

        return result;
    }

    public void onConvert(View v) {
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etINRtoUSD = findViewById(R.id.editTextINRtoUSD);
        String userInput = etUSD.getText().toString();
        String INRto1USD = etINRtoUSD.getText().toString();

        if (INRto1USD.length() > 0) {
            if (isCurrency(INRto1USD)) {
                usd_to_inr = strtodouble(INRto1USD);
            } else {
                Toast.makeText(this, R.string.input_err_inr, Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            //Toast.makeText(this, R.string.input_default, Toast.LENGTH_LONG).show();
            usd_to_inr = 70.0;
        }

        if (isCurrency(userInput)) {
            TextView viewINR = findViewById(R.id.textView2);
            double inr = strtodouble(userInput) * usd_to_inr;
            String userResult = String.format(Locale.US, "%.2f", inr);
            userResult += " INR";
            viewINR.setText(userResult);
        } else {
            Toast.makeText(this, R.string.input_err, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}