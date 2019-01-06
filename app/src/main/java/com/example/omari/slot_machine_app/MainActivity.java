package com.example.omari.slot_machine_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView slot1, slot2, slot3, bank;
    private EditText amount;
    private Button setVal, newGame, lever;

    public void setNewGame() { // creates a new Game
        amount.setEnabled(true);
        setVal.setEnabled(false);
        lever.setEnabled(false);
        newGame.setEnabled(false);
        slot1.setText("");
        slot2.setText("");
        slot3.setText("");
        bank.setText("");
        amount.setText(String.valueOf(""));
    }

    public void afterAmount() { // enables the lever button and New Game button
        amount.setEnabled(false);
        setVal.setEnabled(false);
        lever.setEnabled(true);
        newGame.setEnabled(true);
    }

    public void checkBank() { // Checks if the bank has exceeded 1000 or reached 0, then starts a new game
        if(Integer.parseInt(bank.getText().toString()) > 1000) {
            Toast.makeText(getApplicationContext(), "You have cleared the Slot Machine! Now starting New Game", Toast.LENGTH_LONG).show();
            setNewGame();
        }
        else if(Integer.parseInt(bank.getText().toString()) <= 0) {
            Toast.makeText(getApplicationContext(), "You have lost all you money! Now starting New Game", Toast.LENGTH_LONG).show();
            setNewGame();
        }
    }

    public void enableSubmitIfReady() { // enables the set value button once input is not empty
        boolean isReady = amount.getText().toString().length() > 0;
        setVal.setEnabled(isReady);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slot1 = (TextView) findViewById(R.id.slot1);
        slot2 = (TextView) findViewById(R.id.slot2);
        slot3 = (TextView) findViewById(R.id.slot3);
        bank = (TextView) findViewById(R.id.bank);
        amount = (EditText) findViewById(R.id.amount);
        setVal = (Button) findViewById(R.id.setVal);
        newGame = (Button) findViewById(R.id.newGame);
        lever = (Button) findViewById(R.id.lever);

        setNewGame(); // only be able to set amount and set value to amount

        setVal.setOnClickListener(new View.OnClickListener() {
            // When the setVal button is clicked.
            // If 100 <= amount <= 500, the bank value is set to the amount.
            // If not a toast is prompted
            @Override
            public void onClick(View view) {
                int myNum = Integer.parseInt(amount.getText().toString());

                if(myNum >= 100 && myNum <= 500) {
                    bank.setText(amount.getText().toString());
                    afterAmount();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Enter number from 100 - 500 only", Toast.LENGTH_LONG).show();
                }
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            // Once the amount is validated using enableSubmitIfReady(), the Set Value button will be enabled
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
            }
        });

        newGame.setOnClickListener(new View.OnClickListener() {
            // Once the New Game button is clicked, everything will be reset
            @Override
            public void onClick(View view) {
                setNewGame();
            }
        });

        lever.setOnClickListener(new View.OnClickListener() {
            // Once the lever is clicked, user loses $5
            // If the slots are all equal and less than 5, user gains $40
            // If the slots are all equal and less than 9, and greater than or equal to 5 user gains $100
            // If the slots are all equal and equal to 9, user gains $1000
            // If only two slots match, the user gains $10
            // If no slots matches, user gains nothing
            @Override
            public void onClick(View view) {

                Integer bnk = Integer.parseInt(bank.getText().toString()) - 5;
                bank.setText(String.valueOf(bnk));

                Random rand = new Random();
                rand = new Random();
                slot1.setText(String.valueOf(rand.nextInt(9) + 1));
                slot2.setText(String.valueOf(rand.nextInt(9) + 1));
                slot3.setText(String.valueOf(rand.nextInt(9) + 1));

                if(Integer.parseInt(slot1.getText().toString()) == Integer.parseInt(slot2.getText().toString()) && Integer.parseInt(slot2.getText().toString()) == Integer.parseInt(slot3.getText().toString())) {
                    // if all a match
                    if(Integer.parseInt(slot1.getText().toString()) < 5) {
                        // add $40
                        Integer lessThanFive = Integer.parseInt(bank.getText().toString()) + 40;
                        bank.setText(String.valueOf(lessThanFive));
                    }
                    else if((Integer.parseInt(slot2.getText().toString())) >= 5 && (Integer.parseInt(slot1.getText().toString()) <= 8)) {
                        // add $100
                        Integer fiveToEight = Integer.parseInt(bank.getText().toString()) + 100;
                        bank.setText(String.valueOf(fiveToEight));
                    }
                    else if(Integer.parseInt(slot3.getText().toString()) == 9) {
                        // add $1000
                        Integer equalNine = Integer.parseInt(bank.getText().toString()) + 1000;
                        bank.setText(String.valueOf(equalNine));
                    }
                    //Toast.makeText(getApplicationContext(), "1-2-3 Match", Toast.LENGTH_LONG).show();
                }
                // if 2 are a match
                else if(Integer.parseInt(slot1.getText().toString()) == Integer.parseInt(slot2.getText().toString())) {
                    // add $10
                    Integer oneMatchTwo = Integer.parseInt(bank.getText().toString()) + 10;
                    bank.setText(String.valueOf(oneMatchTwo));
                    //Toast.makeText(getApplicationContext(), "1-2 Match", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(slot2.getText().toString()) == Integer.parseInt(slot3.getText().toString())) {
                    // add $10
                    Integer oneMatchTwo = Integer.parseInt(bank.getText().toString()) + 10;
                    bank.setText(String.valueOf(oneMatchTwo));
                    //Toast.makeText(getApplicationContext(), "2-3 Match", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(slot3.getText().toString()) == Integer.parseInt(slot1.getText().toString())) {
                    // add $10
                    Integer oneMatchTwo = Integer.parseInt(bank.getText().toString()) + 10;
                    bank.setText(String.valueOf(oneMatchTwo));
                    //Toast.makeText(getApplicationContext(), "3-1 Match", Toast.LENGTH_LONG).show();
                }
                checkBank();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
