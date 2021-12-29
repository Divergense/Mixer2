package com.example.mixer2;

import static com.example.mixer2.mixer.Utility.trickySwap;
import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mixer2.mixer.Mixer;
import com.example.mixer2.mixer.Mixtures;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Mixer";
    private final String FAILED_PARSE_INPUT = "Проверьте введённые данные";
    private final String CHECK_TARGET_CONCENTRATION = "Задана некорректная концентрация";
    private final Double BAD_DOUBLE = -1.0;

    private EditText sourceConcentration1;
    private EditText sourceConcentration2;
    private EditText targetConcentration;
    private EditText targetVolume;

    private TextView resultingVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceConcentration1 = findViewById(R.id.editTextConcentration1);
        sourceConcentration2 = findViewById(R.id.editTextConcentration2);
        targetConcentration = findViewById(R.id.editTextTargetConcentration);
        targetVolume = findViewById(R.id.editTextTargetVolume);
        resultingVolume = findViewById(R.id.textViewResultingVolume);
    }

    public void calcMixtureVolumes(View v) {
        String stringConcentration1 = sourceConcentration1.getText().toString();
        String stringConcentration2 = sourceConcentration2.getText().toString();
        String stringTargetConcentration = targetConcentration.getText().toString();
        String stringTargetVolume = targetVolume.getText().toString();

        double doubleConcentration1 = stringToDouble(stringConcentration1);
        double doubleConcentration2 = stringToDouble(stringConcentration2);
        double doubleTargetConcentration = stringToDouble(stringTargetConcentration);
        double doubleTargetVolume = stringToDouble(stringTargetVolume);

        if (doubleConcentration1 == BAD_DOUBLE ||
                doubleConcentration2 == BAD_DOUBLE ||
                doubleTargetConcentration == BAD_DOUBLE ||
                doubleTargetVolume == BAD_DOUBLE)
        {
            resultingVolume.setText(FAILED_PARSE_INPUT);
        } else if (doubleTargetConcentration <= doubleConcentration1 || doubleTargetConcentration >= doubleConcentration2) {
            resultingVolume.setText(CHECK_TARGET_CONCENTRATION);
        } else {
            Log.d(TAG, "Going to calc result...");
            Mixer mixer = new Mixer(doubleConcentration1, doubleConcentration2, doubleTargetConcentration);
            Mixtures targetMixtures = mixer.getMixtureVolumes(doubleTargetVolume);

            double volume1 = targetMixtures.getVolume(0);
            double volume2 = targetMixtures.getVolume(1);
            if (doubleConcentration1 > doubleConcentration2) {
                volume1 = trickySwap(volume2, volume2=volume1);
            }

            String msg = "Раствор 1: " + round(volume1) + " мл\n";
            msg += "Раствор 2: " + round(volume2) + " мл";
            resultingVolume.setText(msg);
        }
    }

    private double stringToDouble(final String stringDouble) {
        double d = BAD_DOUBLE;
        try {
            d = Double.parseDouble(stringDouble);
        } catch (NumberFormatException err) {
            Log.d(TAG, "Parsing " + stringDouble + " has finished with following error: " + err.toString());
        }
        return d;
    }

}