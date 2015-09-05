package com.example.frank_shieh.osdesign;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    RadioGroup radioGroup;
    RadioButton time;
    RadioButton priority;
    RadioButton SPF;
    RadioButton STF;
    Button button;
    TextInputLayout textInputLayout;

    int typeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputLayout = (TextInputLayout) findViewById(R.id.til);
        textInputLayout.setHint("进程个数");

        editText = textInputLayout.getEditText();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        time=(RadioButton)findViewById(R.id.time);
        priority=(RadioButton)findViewById(R.id.priority);
        SPF=(RadioButton)findViewById(R.id.SPF);
        STF=(RadioButton)findViewById(R.id.STF);
        button = (Button) findViewById(R.id.confirm);
        editText.addTextChangedListener(new MyTextWatchername(textInputLayout, "进程个数必须为数值且大于0"));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == time.getId()) {
                    typeNumber=1;
                } else if (checkedId == priority.getId()) {
                    typeNumber=2;
                }else if (checkedId == SPF.getId()) {
                    typeNumber=3;
                }else if (checkedId == STF.getId()) {
                    typeNumber=4;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent =new Intent();
               intent.setClass(MainActivity.this, ProgressActivity.class);
               Bundle bundle=new Bundle();
               bundle.putString("number",editText.getText().toString());
               bundle.putInt("typeNumber", typeNumber);
               intent.putExtras(bundle);
               startActivity(intent);
           }
       });

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

    //内部类监听EditText的动态
    private class MyTextWatchername implements TextWatcher {
        private TextInputLayout mti;
        private String errorInfo;

        public MyTextWatchername(TextInputLayout mtextInputLayout, String errorinfo) {
            this.mti = mtextInputLayout;
            this.errorInfo = errorinfo;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (s.toString().matches("[^1-9]")){
                mti.setErrorEnabled(true);      //是否设置提示错误
                mti.setError(errorInfo);        //错误提示信息
            }else {
                mti.setErrorEnabled(false);     //不设置错误提示信息
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}