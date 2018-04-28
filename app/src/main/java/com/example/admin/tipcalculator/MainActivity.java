package com.example.admin.tipcalculator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private double billAmount = 0.0; // Сумма счета, введенная пользователем
    private double percent = 0.15; // Исходный процент чаевых
    private TextView amountTextView; // Для отформатированной суммы счета
    private TextView percentTextView; // Для вывода процента чаевых
    private TextView tipTextView; // Для вывода вычисленных чаевых
    private TextView totalTextView; // Для вычисленной общей суммы

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        tipTextView.setText(currencyFormat.format(0));
        totalTextView.setText(currencyFormat.format(0));

        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    private void calculate() { // Форматирование процентов и вывод в percentTextView
        percentTextView.setText(percentFormat.format(percent)); // Вычисление чаевых и общей суммы
        double tip = billAmount * percent;
        double total = billAmount + tip;// Вывод чаевых и общей суммы в формате денежной величины
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }


    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {  // Обновление процента чаевых и вызов calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    percent = progress / 100.0; // Назначение процента чаевых
                    calculate(); // Вычисление и вывод чаевых и суммы
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            };

    // Объект слушателя для событий изменения текста в EditText
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        // Вызывается при изменении пользователем величины счета
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            try { // Получить счет и вывести в формате денежной суммы
                 billAmount = Double.parseDouble(s.toString()) / 100.0;
                 amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) { // Если s пусто или не число
                  amountTextView.setText("");
                  billAmount = 0.0;
            }
            calculate(); // Обновление полей с чаевыми и общей суммой
            }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) { }
    };
    }




