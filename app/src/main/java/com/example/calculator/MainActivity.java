package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult, tvEquation;
    MaterialButton btC, btBrackOpen, btBrackClose;
    MaterialButton btDivide, btMultiply, btPlus, btMinus, btEquals;
    MaterialButton bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    MaterialButton btAc, btDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEquation = findViewById(R.id.tvEquation);
        tvResult = findViewById(R.id.tvResult);

        initialize(btC, R.id.bt_c);
        initialize(btBrackOpen, R.id.bt_open_bracket);
        initialize(btBrackClose, R.id.bt_close_braket);
        initialize(btDivide, R.id.bt_divide);
        initialize(btMultiply, R.id.bt_multiply);
        initialize(btPlus, R.id.bt_add);
        initialize(btMinus, R.id.bt_subtract);
        initialize(btEquals, R.id.bt_equal);
        initialize(bt0, R.id.bt_0);
        initialize(bt1, R.id.bt_1);
        initialize(bt2, R.id.bt_2);
        initialize(bt3, R.id.bt_3);
        initialize(bt4, R.id.bt_4);
        initialize(bt5, R.id.bt_5);
        initialize(bt6, R.id.bt_6);
        initialize(bt7, R.id.bt_7);
        initialize(bt8, R.id.bt_8);
        initialize(bt9, R.id.bt_9);
        initialize(btAc, R.id.bt_ac);
        initialize(btDot, R.id.bt_dot);

    }

    void initialize(MaterialButton bt, int id) {
        bt = findViewById(id);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton bt = (MaterialButton) view;
        String btText = bt.getText().toString();
        String dataToCalculate = tvEquation.getText().toString();

        if (btText.equals("AC")) {
            tvEquation.setText("");
            tvResult.setText("0");
            return;
        } else if (btText.equals("=")) {
            tvEquation.setText(tvResult.getText());
            return;
        } else if (btText.equals("C") & dataToCalculate.length() > 0) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
        } else if(btText.equals("C")) { // so if the button "C" is pressed but length is 0 it won't print C on screen
        } else {
            dataToCalculate = dataToCalculate + btText;
        }
        tvEquation.setText(dataToCalculate);

        if (dataToCalculate.length() == 0) tvResult.setText("");

        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")) {
            tvResult.setText(finalResult);
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }


}