package com.example.myatm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView inHandString, accountString;
    Spinner spin_pos;
    String[] spinnerValues;
    int basicTrans = 1000;
    int atmTrans, accountValue, inHandValue, spinPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSpinner();
        deposit();
        withdraw();
    }

    private void withdraw() {
        Button btnWithdraw = (Button) findViewById(R.id.rmv_btn);
        btnWithdraw.setOnClickListener(view -> {
            init();
            Log.println(Log.INFO, "Spinner Value - Withdraw", String.valueOf(atmTrans));
            if (atmTrans == 0 && basicTrans <= accountValue) {
                accountString.setText(String.valueOf(Withdraw.onClickWithdraw(basicTrans, accountValue)));
                inHandString.setText(String.valueOf(Deposit.onClickDeposit(basicTrans, inHandValue)));
            } else if (atmTrans == 0) {
                Toast.makeText(MainActivity.this, "Not enough money in the bank to withdraw " + basicTrans + "$", Toast.LENGTH_SHORT).show();
            } else if (atmTrans > accountValue) {
                Toast.makeText(MainActivity.this, "Not enough money in the bank to withdraw " + atmTrans + "$", Toast.LENGTH_SHORT).show();
            } else {
                accountString.setText(String.valueOf(Withdraw.onClickWithdraw(atmTrans, accountValue)));
                inHandString.setText(String.valueOf(Deposit.onClickDeposit(atmTrans, inHandValue)));
            }
        });
    }

    private void deposit() {
        Button btnDeposit = (Button) findViewById(R.id.add_btn);
        btnDeposit.setOnClickListener(view -> {
            init();
            if (atmTrans == 0 && basicTrans <= inHandValue) {
                inHandString.setText(String.valueOf(Withdraw.onClickWithdraw(basicTrans, inHandValue)));
                accountString.setText(String.valueOf(Deposit.onClickDeposit(basicTrans, accountValue)));
            } else if (atmTrans == 0) {
                Toast.makeText(MainActivity.this, "Not enough in hand money to deposit " + basicTrans + "$", Toast.LENGTH_SHORT).show();
            } else if (atmTrans <= inHandValue) {
                inHandString.setText(String.valueOf(Withdraw.onClickWithdraw(atmTrans, inHandValue)));
                accountString.setText(String.valueOf(Deposit.onClickDeposit(atmTrans, accountValue)));
            } else {
                Toast.makeText(MainActivity.this, "Not enough in hand money to deposit " + atmTrans + "$", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        accountString = (TextView) findViewById(R.id.account_amount);
        inHandString = (TextView) findViewById(R.id.in_hand_amount);
        accountValue = Integer.parseInt(accountString.getText().toString());
        inHandValue = Integer.parseInt(inHandString.getText().toString());
        spinPos = spin_pos.getSelectedItemPosition();
        spinnerValues = getResources().getStringArray(R.array.amounts);
        atmTrans = Integer.parseInt(spinnerValues[spinPos]);
    }

    private void setSpinner() {
        spin_pos = (Spinner) findViewById(R.id.amounts_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_pos.setAdapter(adapter);
    }
}