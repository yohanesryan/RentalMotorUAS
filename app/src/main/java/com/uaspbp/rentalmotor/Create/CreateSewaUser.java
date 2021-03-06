package com.uaspbp.rentalmotor.Create;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.GsonBuilder;
import com.uaspbp.rentalmotor.Api.ApiClient;
import com.uaspbp.rentalmotor.Api.ApiInterface;
import com.uaspbp.rentalmotor.MainActivity;
import com.uaspbp.rentalmotor.MainActivityAdmin;
import com.uaspbp.rentalmotor.R;
import com.uaspbp.rentalmotor.Response.MotorResponseObject;
import com.uaspbp.rentalmotor.Response.TransaksiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSewaUser extends AppCompatActivity {

    TextInputEditText twNamaPenyewa, twIDPenyewa, twAlamatPenyewa, twNamaMotor, twTglSewa, twLamaSewa;
    Button btnCreate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_sewa);

        twNamaPenyewa = findViewById(R.id.etNamaPenyewa);
        twAlamatPenyewa = findViewById(R.id.etAlamatPenyewa);
        twIDPenyewa = findViewById(R.id.etIDPenyewa);
        twNamaMotor = findViewById(R.id.etPilihanMotor);
        twTglSewa = findViewById(R.id.etTglSewa);
        twLamaSewa = findViewById(R.id.etLamaSewa);

        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(twNamaPenyewa.getText().toString().isEmpty()){
                    twNamaPenyewa.setError("Nama Harus Diisi");
                    twNamaPenyewa.requestFocus();
                } else if(twIDPenyewa.getText().toString().isEmpty()){
                    twIDPenyewa.setError("ID Harus Diisi");
                    twIDPenyewa.requestFocus();
                } else if(twAlamatPenyewa.getText().toString().isEmpty()){
                    twAlamatPenyewa.setError("Alamat Harus Diisi");
                    twAlamatPenyewa.requestFocus();
                } else if(twNamaMotor.getText().toString().isEmpty()) {
                    twNamaMotor.setError("Nama Motor Harus Diisi");
                    twNamaMotor.requestFocus();
                } else if(twTglSewa.getText().toString().isEmpty()) {
                    twTglSewa.setError("Tanggal Sewa Harus Diisi");
                    twTglSewa.requestFocus();
                } else if(twLamaSewa.getText().toString().isEmpty()) {
                    twLamaSewa.setError("Lama Sewa Harus Diisi");
                    twLamaSewa.requestFocus();
                } else {
                    createSewa();

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void createSewa() {
        ApiInterface apiServiceCreateSewa= ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> addSewa = apiServiceCreateSewa.createTransaksi(twNamaPenyewa.getText().toString(), twIDPenyewa.getText().toString(),
                                                                                        twAlamatPenyewa.getText().toString(), twNamaMotor.getText().toString(),
                                                                                        twTglSewa.getText().toString(), twLamaSewa.getText().toString());

        addSewa.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(com.uaspbp.rentalmotor.Create.CreateSewaUser.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                onBackPressed();

                Log.i("create", "msg: "+ new GsonBuilder().setPrettyPrinting().create().toJson(response));
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Toast.makeText(com.uaspbp.rentalmotor.Create.CreateSewaUser.this, t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("response", "msg: " +t.getMessage());
            }
        });
    }
}
