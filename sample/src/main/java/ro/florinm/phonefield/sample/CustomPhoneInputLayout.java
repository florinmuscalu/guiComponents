package ro.florinm.phonefield.sample;

import android.content.Context;
import android.util.AttributeSet;
import ro.florinm.phonefield.PhoneInputLayout;
import com.lamudi.phonefield.sample.R;

/**
 * Custom PhoneInputLayout that allows users
 * Created by Ismail on 8/30/16.
 */

public class CustomPhoneInputLayout extends PhoneInputLayout {

  private String mCountryCode;

  public CustomPhoneInputLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomPhoneInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public CustomPhoneInputLayout(Context context, String countryCode) {
    super(context);
    mCountryCode = countryCode;
    init();
  }

  private void init() {
    setDefaultCountry(mCountryCode);
    setHint(R.string.phone_hint);
  }


}
