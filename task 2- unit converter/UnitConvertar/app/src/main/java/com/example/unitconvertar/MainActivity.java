package com.example.unitconvertar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to UI elements
        EditText cmInput = findViewById(R.id.cmInput);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        // Set up the button's click listener
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the value entered by the user
                String cmValue = cmInput.getText().toString();

                if (!cmValue.isEmpty()) {
                    // Convert the value to inches
                    double cm = Double.parseDouble(cmValue);
                    double inches = cm / 2.54;

                    // Display the result
                    resultText.setText(String.format("%.2f cm is %.2f inches", cm, inches));
                } else {
                    // Display an error message
                    resultText.setText("Please enter a value in cm");
                }
            }
        });
    }
}
