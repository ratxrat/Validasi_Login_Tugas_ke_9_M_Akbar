package ratsoft.android.validasilogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME = "login";
    EditText inputUser, inputPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputUser = findViewById(R.id.inputUsernameLogin);
        inputPass = findViewById(R.id.inputPassLogin);

    }
    public void tombol(View v){
        if(v.getId() == R.id.btnLogin){
            // login
            login();
        }else if(v.getId() == R.id.btnRegis){
            //buka aktivity register
            register();
        }
    }
    void login(){
        File file = new File(getFilesDir(),FILENAME);
        if(file.exists()) {
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                String[] data = text.toString().split(";");
                br.close();
                //cek login disini


                if(inputUser.getText().toString().equals(data[0]) && inputPass.getText().toString().equals(data[1])){
                    //masuk ke main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Usernam Atau Password Salah!",Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Data Tidak Ditemukan Silahkan Regiter Terlebih Dahulu!",Toast.LENGTH_SHORT).show();
        }
    }
    void register(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}