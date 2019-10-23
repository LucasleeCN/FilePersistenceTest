package com.example.filepersistencetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static java.io.FileDescriptor.in;

public class MainActivity extends AppCompatActivity {
    EditText editText_name;
    EditText editText_number;
    EditText editText_class;
    Button button_submit ;
    Button button_reload;
    EditText editText_getText;
    public void init(){
        editText_name=findViewById(R.id.etext_name);
        editText_number =findViewById(R.id.etext_number);
        editText_class =findViewById(R.id.etext_class);
        button_submit =findViewById(R.id.submit);
        button_reload = findViewById(R.id.reload);
        editText_getText = findViewById(R.id.getText);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        button_submit = findViewById(R.id.submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                editText_name.setText("");
                editText_number.setText("");
                editText_class.setText("");
                Toast.makeText(MainActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }
        });

        button_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = load();
                if(!TextUtils.isEmpty(inputText)){
                    editText_getText.setText(inputText);
                    editText_getText.setSelection(inputText.length());
                    Toast.makeText(MainActivity.this,"加载成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void save(){
        FileOutputStream fileOutputStream = null;
        BufferedWriter writer = null;
        try{
            fileOutputStream=openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(editText_name.getText().toString());
            writer.write("\r\n"+editText_number.getText().toString());
            writer.write("\r\n"+editText_class.getText().toString());
            Log.d("TAG","HERE_1");

        }catch (Exception e){
            e.printStackTrace();
            Log.d("TAG","wrong_1");
        }
        finally {
            try{
                if(writer!=null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG","wrong_2");

            }
        }
    }
    public String load(){
        FileInputStream in = null;
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line ="";

            while((line=bufferedReader.readLine())!=null){
                content.append(line);
                content.append(" / ");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try{
                    bufferedReader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
