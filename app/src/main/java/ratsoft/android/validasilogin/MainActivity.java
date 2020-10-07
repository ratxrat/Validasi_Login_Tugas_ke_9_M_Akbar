package ratsoft.android.validasilogin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public static final String FILENAME = "login";
    private String pass; // karena kita tidak mengubah password maka di simpan terlebih dahulu agar saat di update password masih bisa di gunakan tidak tertumpuk
    Button btn;
    EditText username,email,nama,sekolah,alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Halaman Depan");
        btn = findViewById(R.id.btnSave);
        username = findViewById(R.id.inputUser);
        email = findViewById(R.id.inputEmail);
        nama = findViewById(R.id.inputNama);
        sekolah = findViewById(R.id.inputSekolah);
        alamat = findViewById(R.id.inputAlamat);
        masukanData();
    }

    private void masukanData() {
        File file = new File(getFilesDir(),FILENAME);
        if(file.exists()){
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                String[] data = text.toString().split(";");
                br.close();
                username.setText(data[0]);
                pass = data[1];
                email.setText(data[2]);
                nama.setText(data[3]);
                sekolah.setText(data[4]);
                alamat.setText(data[5]);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            //pindah kembali ke aktiviti login jika data terhapus atau memang tidak ada
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuEdit:
                editProfile();
                break;
            case R.id.menuHapus:
                komprimHapus();
                break;
            case R.id.menuLogout:
               //logout disini
                konfirmLogout();
                break;


        }
        return true;
    }

    private void komprimHapus() {
        new AlertDialog.Builder(this).setTitle("Hapus Data Profile")
                .setMessage("Apkah Anda Yakin untuk Menghapus Data Profile? data yang sudah di hapus tidak dapat di kembalikan!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int which) {
                                hapusData();
                            }
                        }).setNegativeButton(android.R.string.no,null).show();
    }
    private  void  hapusData(){
        File file = new File(getFilesDir(),FILENAME);
        if(file.exists()){
            file.delete();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }
    }
    private void konfirmLogout() {
        new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Apkah Anda Yakin untuk Logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        }).setNegativeButton(android.R.string.no,null).show();
    }

    private void editProfile() {
        username.setEnabled(true);
        email.setEnabled(true);
        nama.setEnabled(true);
        sekolah.setEnabled(true);
        alamat.setEnabled(true);
        btn.setVisibility(View.VISIBLE);
    }

    public void btn(View view) {
        if(view.getId() == btn.getId()){
            konfirmUpdate();
        }
    }
    void  konfirmUpdate(){
        new AlertDialog.Builder(this).setTitle("Update Profile")
                .setMessage("Apkah Anda Yakin untuk Mengedit Profile anda?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes,
        new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                simpan();
            }
        }).setNegativeButton(android.R.string.no,null).show();
    }
    private void simpan() {
        String dataFile = username.getText()+";"+pass+";"+email.getText()+";"+nama.getText()+";"+sekolah.getText()+";"+alamat.getText();
        File file = new File(getFilesDir(),FILENAME);
        FileOutputStream output;

        try {

            output = new FileOutputStream(file,false);
            output.write(dataFile.getBytes());
            output.flush();
            output.close();
            // kasih toast biar tahu statusnya
            Toast.makeText(getApplicationContext(),"Profile Sudah Di Update",Toast.LENGTH_SHORT).show();
            username.setEnabled(false);
            email.setEnabled(false);
            nama.setEnabled(false);
            sekolah.setEnabled(false);
            alamat.setEnabled(false);
            btn.setVisibility(View.GONE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}