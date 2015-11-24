package com.khirman.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText operand1;
    private EditText operand2;
    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonMultiply;
    private Button buttonDivide;
    private Button buttonClear;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operand1 = (EditText) findViewById(R.id.editOperand1);
        operand2 = (EditText) findViewById(R.id.editOperand2);

        buttonPlus = (Button) findViewById(R.id.btnPlus);
        buttonMinus = (Button) findViewById(R.id.btnMinus);
        buttonMultiply = (Button) findViewById(R.id.btnMultiply);
        buttonDivide = (Button) findViewById(R.id.btnDivide);

        txtResult = (TextView) findViewById(R.id.txtResult);


        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(operand1.getText().length()>0 && operand2.getText().length()>0) {
                    Double op1 = Double.parseDouble(operand1.getText().toString());
                    Double op2 = Double.parseDouble(operand2.getText().toString());

                    Double theResult;

                    switch (v.getId()) {
                        case R.id.btnPlus:
                            theResult = op1 + op2;
                            break;
                        case R.id.btnMinus:
                            theResult = op1 - op2;
                            break;
                        case R.id.btnMultiply:
                            theResult = op1 * op2;
                            break;
                        case R.id.btnDivide:
                            theResult = op1 / op2;
                            break;
                        default:
                            theResult = 0.0;
                            break;
                    }

                    txtResult.setText(Double.toString(theResult));
                }
                else{
                    Toast.makeText(MainActivity.this,"Please enter number in both opernad fields", Toast.LENGTH_SHORT).show();
                }
            }
        };

        buttonPlus.setOnClickListener(clickListener);
        buttonMinus.setOnClickListener(clickListener);
        buttonMultiply.setOnClickListener(clickListener);
        buttonDivide.setOnClickListener(clickListener);

        buttonClear = (Button)findViewById(R.id.btnClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1.setText("");
                operand2.setText("");
                txtResult.setText("0.0");
                operand1.requestFocus();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
