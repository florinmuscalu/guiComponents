package tech.zekon.sample;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import tech.zekon.gui.AppRate;
import tech.zekon.gui.FractionButton;
import tech.zekon.gui.PhoneInputLayout;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //final FractionButton fb = findViewById(R.id.fb);
    //fb.setNumerator("6");
    //fb.setDenominator("");

    final PhoneInputLayout phoneInputLayout = findViewById(R.id.phone_input_layout);

    final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
            .findViewById(android.R.id.content)).getChildAt(0);


    final Button button = findViewById(R.id.submit_button);

    assert phoneInputLayout != null;
    assert button != null;

    phoneInputLayout.setHint(R.string.phone_hint);
    phoneInputLayout.setDefaultCountry("DE");

    button.setOnClickListener(v -> {
      boolean valid = true;
      if (phoneInputLayout.isValid()) {
        phoneInputLayout.setError(null);
      } else {
        phoneInputLayout.setError(getString(R.string.invalid_phone_number));
        valid = false;
      }

      if (valid) {
        Toast.makeText(MainActivity.this, R.string.valid_phone_number, Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(MainActivity.this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
      }
    });

    AppRate.Start(this, "Sample App", "tech.zekon.sample", 0, 0);
  }
}
