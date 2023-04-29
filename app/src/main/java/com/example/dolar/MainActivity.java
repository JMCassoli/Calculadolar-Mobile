package com.example.dolar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String url = "https://dolarhoy.com/";
    TextView textoCompra;
    TextView textoVenta;
    TextView textoPromed;
    TextView texto;
    TextView display;

    StringBuilder sb;

    private int dolarCompra = 0;
    private int dolarVenta = 0;
    private int dolarPromed = 0;
    private int cambioActual = 0;
    Button btnCompra;
    Button btnVenta;
    Button btnPromed;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    Button btnC, btnCE;
    Button btnPesADol;
    Button btnDolAPes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb = new StringBuilder();

        textoCompra = (TextView) findViewById(R.id.textoDolarCompra);
        textoVenta = (TextView) findViewById(R.id.textoDolarVenta);
        textoPromed = (TextView) findViewById(R.id.textoDolarPromed);
        display = (TextView) findViewById(R.id.display);
        btnCompra = (Button) findViewById(R.id.btnCompra);
        btnVenta = (Button) findViewById(R.id.btnVenta);
        btnPromed = (Button) findViewById(R.id.btnPromed);
        btn1 = (Button) findViewById(R.id.btn1); btn2 = (Button) findViewById(R.id.btn2); btn3 = (Button) findViewById(R.id.btn3); btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5); btn6 = (Button) findViewById(R.id.btn6); btn7 = (Button) findViewById(R.id.btn7); btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9); btn0 = (Button) findViewById(R.id.btn0);
        btnC = (Button) findViewById(R.id.btnC); btnCE = (Button) findViewById(R.id.btnCE);
        btnPesADol = (Button) findViewById(R.id.btnPesADol);
        btnDolAPes = (Button) findViewById(R.id.btnDolAPes);

        btnPesADol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pesoADolar();
                imprimirDisplay();
            }
        });
        btnDolAPes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dolarAPeso();
                imprimirDisplay();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("1");
                imprimirDisplay();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("2");
                imprimirDisplay();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("3");
                imprimirDisplay();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("4");
                imprimirDisplay();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("5");
                imprimirDisplay();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("6");
                imprimirDisplay();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("7");
                imprimirDisplay();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("8");
                imprimirDisplay();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("9");
                imprimirDisplay();
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sb.append("0");
                imprimirDisplay();
            }
        });
        btnC.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(sb.length()!=0){
                sb.deleteCharAt(sb.length()-1);
                imprimirDisplay();
                }
            }
        });
        btnCE.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
            borrarDisplay();
            imprimirDisplay();
            }
        });
        btnCompra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setCambioActual(getDolarCompra());
            }
        });
        btnVenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setCambioActual(getDolarVenta());
            }
        });
        btnPromed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setCambioActual(getDolarPromed());
            }
        });

        getDolar();
    }

    private void dolarAPeso() {
        if(sb.length()!=0){
        int result = Integer.parseInt(sb.toString());
        borrarDisplay();
        sb.append(Integer.toString(result * cambioActual));
        }
    }

    private void pesoADolar() {
        if(sb.length()!=0){
        int result = Integer.parseInt(sb.toString());
        borrarDisplay();
        sb.append(Float.toString(result / cambioActual));
        }
    }

    private void borrarDisplay() {
        sb.delete(0, sb.length());
    }

    private void imprimirDisplay() {
        display.setText(sb.toString());
    }

    public void imprimirDolares(){
        textoCompra.setText(String.format("$%s", getDolarCompra()));
        textoVenta.setText(String.format("$%s", getDolarVenta()));
        textoPromed.setText(String.format("$%s", getDolarPromed()));
    }

    private void getDolar() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements title = doc.getElementsByClass("val");
                    setDolarCompra(Integer.parseInt(title.get(0).text().replace("$", "")));
                    setDolarVenta(Integer.parseInt(title.get(1).text().replace("$", "")));

                } catch (IOException e) {
                    display.append("Error: ");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        display.setText("0");
                        imprimirDolares();
                        setCambioActual(getDolarVenta());
                    }
                });
            }
        }).start();

    }

    public int getDolarCompra() {
        return dolarCompra;
    }

    public void setDolarCompra(int dolarCompra) {
        this.dolarCompra = dolarCompra;
    }

    public int getDolarVenta() {
        return dolarVenta;
    }

    public void setDolarVenta(int dolarVenta) {
        this.dolarVenta = dolarVenta;
    }

    public int getDolarPromed() {
        return dolarPromed;
    }

    public void setDolarPromed(int dolarPromed) {
        this.dolarPromed = dolarPromed;
    }

    public int getCambioActual() {
        return cambioActual;
    }

    public void setCambioActual(int cambioActual) {
        this.cambioActual = cambioActual;
    }
}