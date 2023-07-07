package com.buithianhthuy.k214110823_buithianhthuy_th1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText oldNumber;
    EditText newNumber;
    EditText numHouse;
    TextView totalKwh;
    TextView totalMoney;

    Integer mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.oldNumber = findViewById(R.id.edtOldNumber);
        this.oldNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mode == 1) {
                    shbtHandler();
                } else if (mode == 2) {
                    kdHandler();
                } else if (mode == 3) {
                    sxHander();
                }
            }
        });
        this.newNumber = findViewById(R.id.edtNewNumber);
        this.newNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mode == 1) {
                    shbtHandler();
                } else if (mode == 2) {
                    kdHandler();
                } else if (mode == 3) {
                    sxHander();
                }
            }
        });
        this.numHouse = findViewById(R.id.edtNumHouse);
        this.numHouse.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mode == 1) {
                    shbtHandler();
                }
            }
        });
        this.totalKwh = findViewById(R.id.edtKwh);
        this.totalMoney = findViewById(R.id.edtTinhTien);
        this.totalKwh.setText("");
        this.totalMoney.setText("");
        setToolBarIconAndTitle();
    }

    private void setToolBarIconAndTitle() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tính Tiền Điện");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.parseColor("#000000"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_toolbar);
    }

    // calculate shbt electric money
    private Double caculateSHBT(Double a, Double b, Double c) {
        Double kwhTotal = b - a;
        Double money = 0.0;
        if (kwhTotal <= 50 * c) {
            money = kwhTotal * 1484;
        } else if (kwhTotal <= 100 * c) {
            money = 50 * c * 1484 + (kwhTotal - 50 * c) * 1533;
        } else if (kwhTotal <= 200 * c) {
            money = 50 * c * 1484 + 50 * c * 1533 + (kwhTotal - 100 * c) * 1786;
        } else if (kwhTotal <= 300 * c) {
            money = 50 * c * 1484 + 50 * c * 1533 + 100 * c * 1786 + (kwhTotal - 200 * c) * 2242;
        } else if (kwhTotal <= 400 * c) {
            money = 50 * c * 1484 + 50 * c * 1533 + 100 * c * 1786 + 100 * c * 2242 + (kwhTotal - 300 * c) * 2503;
        } else {
            money = 50 * c * 1484 + 50 * c * 1533 + 100 * c * 1786 + 100 * c * 2242 + 100 * c * 2503 + (kwhTotal - 400 * c) * 2587;
        }
        return money;
    }

    private String formatMoney(Double money) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        return nf.format(money.intValue());
    }
    public void caculateSHBT(View v) {
        this.mode = 1;
        this.shbtHandler();
    }

    public void caculateKD(View v) {
        this.mode = 2;
        this.kdHandler();
    }

    public void caculateSX(View v) {
        this.mode = 3;
        this.sxHander();
    }

    public void exitHandler(View v) {
        // exit app
        finish();
        System.exit(0);
    }

    public void deleteHandler(View v) {
        // reset every edit text + put cursor to oldNumber
        this.oldNumber.setText("");
        this.newNumber.setText("");
        this.numHouse.setText("");
        this.totalKwh.setText("");
        this.totalMoney.setText("");
        this.mode = 0;
        this.oldNumber.requestFocus();
    }


    private void shbtHandler() {
        if (this.oldNumber.getText().toString().isEmpty() || this.newNumber.getText().toString().isEmpty()) {
            return;
        }
        Double oldElectricNumber = Double.valueOf(this.oldNumber.getText().toString());
        Double newElectricNumber = Double.valueOf(this.newNumber.getText().toString());
        if (oldElectricNumber > newElectricNumber) {
            this.totalMoney.setText("Số điện mới phải lớn hơn số điện cũ");
            return;
        }
        String numHouse = this.numHouse.getText().toString();
        if (numHouse.isEmpty()) {
            this.totalMoney.setText("Vui lòng nhập số hộ sử dụng điện");
            return;
        }
        Double numberOfHouse = Double.valueOf(numHouse);
        Double kwhTotal = newElectricNumber - oldElectricNumber;
        Double money = caculateSHBT(oldElectricNumber, newElectricNumber, numberOfHouse);
        this.totalKwh.setText(kwhTotal + " kWh");
        this.totalMoney.setText("Tổng số tiền điện giá sinh hoạt(" + numberOfHouse + "hộ): " + this.formatMoney(money) + " VNĐ");
    }

    private void kdHandler() {
        if (this.oldNumber.getText().toString().isEmpty() || this.newNumber.getText().toString().isEmpty()) {
            return;
        }
        Double oldElectricNumber = Double.valueOf(this.oldNumber.getText().toString());
        Double newElectricNumber = Double.valueOf(this.newNumber.getText().toString());
        if (oldElectricNumber > newElectricNumber) {
            this.totalMoney.setText("Số điện mới phải lớn hơn số điện cũ");
            return;
        }
        Double kwhTotal = newElectricNumber - oldElectricNumber;
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        this.totalKwh.setText(kwhTotal + " kWh");
        this.totalMoney.setText("Tổng số tiền điện giá kinh doanh: " + this.formatMoney(kwhTotal * 2320) + " VNĐ");
    }

    private void sxHander() {
        if (this.oldNumber.getText().toString().isEmpty() || this.newNumber.getText().toString().isEmpty()) {
            return;
        }
        Double oldElectricNumber = Double.valueOf(this.oldNumber.getText().toString());
        Double newElectricNumber = Double.valueOf(this.newNumber.getText().toString());
        if (oldElectricNumber > newElectricNumber) {
            this.totalMoney.setText("Số điện mới phải lớn hơn số điện cũ");
            return;
        }
        Double kwhTotal = newElectricNumber - oldElectricNumber;
        this.totalKwh.setText(kwhTotal + " kWh");
        this.totalMoney.setText("Tổng số tiền điện giá sản xuất: " + this.formatMoney(kwhTotal * 2420) + " VNĐ");
    }
}

