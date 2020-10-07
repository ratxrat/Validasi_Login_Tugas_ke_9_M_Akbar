package ratsoft.android.validasilogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    public static final String FILENAME = "login";
    private EditText username, password,email,nama,sekolah,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username = findViewById(R.id.inputUser);
        password = findViewById(R.id.inputPass);
        email = findViewById(R.id.inputEmail);
        nama = findViewById(R.id.inputNama);
        sekolah = findViewById(R.id.inputSekolah);
        alamat = findViewById(R.id.inputAlamat);

    }
    public void btn(View v){
        if(v.getId() == R.id.btnSave){
            if(cekInput()){
                simpan();
            }else{
               toas("Semua Data Harus Diisi!");
            }
        }

    }

    private void simpan() {
        String dataFile = username.getText()+";"+password.getText()+";"+email.getText()+";"+nama.getText()+";"+sekolah.getText()+";"+alamat.getText();
        File file = new File(getFilesDir(),FILENAME);
        FileOutputStream output;

        try {
            file.createNewFile();
            output = new FileOutputStream(file,false);
            output.write(dataFile.getBytes());
            output.flush();
            output.close();
            // kasih toast biar tahu statusnya
            toas("berhasil register silahkan Login");

            //kembali ke activity login untuk login
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean cekInput (){
        return !username.getText().toString().equals("") && !password.getText().toString().equals("") && !email.getText().toString().equals("") &&
                !nama.getText().toString().equals("") && !sekolah.getText().toString().equals("") && !alamat.getText().toString().equals("");
    }
    void toas(String isi){
        Toast.makeText(getApplicationContext(),isi,Toast.LENGTH_SHORT).show();
    }
}