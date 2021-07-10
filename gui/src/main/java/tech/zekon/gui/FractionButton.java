package tech.zekon.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class FractionButton  extends ConstraintLayout implements View.OnClickListener {
    private TextView mText, mNumerator, mDenominator;
    private boolean isCheckbox = false;
    private String Text, Numerator, Denominator;
    private boolean isChecked = false;
    private OnClickListener _wrappedOnClickListener;
    private int overlapPercent;

    public FractionButton(Context context) {
        this(context, null);
    }

    public FractionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FractionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), getLayoutResId(), this);
        updateLayoutAttributes();
        prepareView(attrs);
    }

    protected void updateLayoutAttributes() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //setGravity(Gravity.TOP);
        //setOrientation(HORIZONTAL);
    }

    public int getLayoutResId() {
        return R.layout.fraction_button;
    }

    @SuppressLint("ResourceType")
    protected void prepareView(AttributeSet attrSet) {
        mText = findViewWithTag(getResources().getString(R.string.tech_zekon_guiComponents_FractionButton_integer));
        mNumerator = findViewWithTag(getResources().getString(R.string.tech_zekon_guiComponents_FractionButton_numerator));
        mDenominator = findViewWithTag(getResources().getString(R.string.tech_zekon_guiComponents_FractionButton_denominator));

        if (mText == null || mNumerator == null || mDenominator == null) {
            throw new IllegalStateException("Please provide a valid xml layout");
        }

        TypedArray ta = getContext().obtainStyledAttributes(attrSet, R.styleable.FractionButton);
        isCheckbox = ta.getBoolean(R.styleable.FractionButton_isCheckbox, false);
        isChecked = ta.getBoolean(R.styleable.FractionButton_Checked, false);
        Text = ta.getString(R.styleable.FractionButton_Text);
        if (Text == null) Text = "";
        Numerator = ta.getString(R.styleable.FractionButton_Numerator);
        if (Numerator == null) Numerator = "";
        Denominator = ta.getString(R.styleable.FractionButton_Denominator);
        if (Denominator == null) Denominator = "";
        int textColor = ta.getColor(R.styleable.FractionButton_android_textColor, Color.BLACK);
        float textSize = ta.getDimension(R.styleable.FractionButton_android_textSize, 20);
        overlapPercent = ta.getInt(R.styleable.FractionButton_overlapPercent, 20);
        ta.recycle();

        mText.setTextColor(textColor);
        mNumerator.setTextColor(textColor);
        mDenominator.setTextColor(textColor);

        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mNumerator.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mDenominator.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        setCheckbox(isCheckbox);
        setChecked(isChecked);

        setText(Text);
        setNumerator(Numerator);
        setDenominator(Denominator);

        this.setClickable(true);
        super.setOnClickListener(this);

        if (mDenominator.getVisibility() == VISIBLE) {
            mNumerator.setTranslationY(textSize/(100f/overlapPercent));
            mDenominator.setTranslationY(-textSize/(100f/overlapPercent));
        }
    }

    public TextView getTextView() {
        return mText;
    }

    public TextView getNumeratorView() {
        return mNumerator;
    }

    public TextView getDenominatorView() {
        return mDenominator;
    }

    public boolean isCheckbox() {
        return isCheckbox;
    }

    public void setCheckbox(boolean checkbox) {
        isCheckbox = checkbox;
        ImageView img = findViewWithTag(getResources().getString(R.string.tech_zekon_guiComponents_FractionButton_checkbox));
        if (isCheckbox) {
            img.setVisibility(VISIBLE);
        }
        else {
            img.setVisibility(GONE);
        }
    }

    public String getText() {
        return Text;
    }

    public void setText(String strInteger) {
        this.Text = strInteger;
        mText.setText(strInteger);
        if (Text.isEmpty()) mText.setVisibility(GONE);
        else mText.setVisibility(VISIBLE);
    }

    public String getNumerator() {
        return Numerator;
    }

    public void setNumerator(String strNumerator) {
        Numerator = strNumerator;
        mNumerator.setText(Numerator);
        if (Numerator.isEmpty()) mNumerator.setVisibility(GONE);
        else mNumerator.setVisibility(VISIBLE);
    }

    public String getDenominator() {
        return Denominator;
    }

    public void setDenominator(String strDenominator) {
        Denominator = strDenominator;
        mDenominator.setText(Denominator);
        if (Denominator.isEmpty()) mDenominator.setVisibility(GONE);
        else mDenominator.setVisibility(VISIBLE);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (!isCheckbox) return;
        ImageView img = findViewWithTag(getResources().getString(R.string.tech_zekon_guiComponents_FractionButton_checkbox));
        if (isChecked)
            img.setImageResource(R.drawable.checked);
        else
            img.setImageResource(R.drawable.unchecked);
    }

    @Override
    public void onClick(View v) {
        setChecked(!isChecked);
        if (_wrappedOnClickListener != null) _wrappedOnClickListener.onClick(v);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        _wrappedOnClickListener = l;
    }

    public int getOverlapPercent() {
        return overlapPercent;
    }

    public void setOverlapPercent(int overlapPercent) {
        this.overlapPercent = overlapPercent;
    }
}
