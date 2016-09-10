package ar.edu.utn.frsf.isi.dam.figueroa.lab01c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static java.lang.Math.pow;

public class Activity extends AppCompatActivity {
    Button boton;
    SeekBar barra;
    TextView texto_barra, ganancia_correcta_texto, ganancia_parcial_texto;
    EditText capital_texto;

    // INCOMPLETO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(handler_click_boton);

        barra = (SeekBar) findViewById(R.id.seekBar);
        barra.setOnSeekBarChangeListener(handler_seek_bar_change);

        capital_texto = (EditText) findViewById(R.id.editText3);
        capital_texto.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                cambiarGananciaParcial();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        ganancia_parcial_texto = (TextView) findViewById(R.id.textView8);
        ganancia_correcta_texto = (TextView) findViewById(R.id.textView9);
        texto_barra = (TextView) findViewById(R.id.textView6);
    }

    SeekBar.OnSeekBarChangeListener handler_seek_bar_change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
            texto_barra.setText(String.valueOf(barra.getProgress()));
            cambiarGananciaParcial();
        }
    };

    private void cambiarGananciaParcial(){
        double resul= calcularInteres(getDias(), getCapital());
        ganancia_parcial_texto.setText("$"+String.valueOf(resul));
    }

    private double getCapital(){
        if(capital_texto.getText().toString().trim().length() == 0){
            return 0;
        }
        else
        return Double.parseDouble(capital_texto.getText().toString());
    }
    private int getDias(){
        return (int) barra.getProgress();
    }

    private double calcularInteres(int dias, double capital){
        double tasa=0;
        double cap= capital;
        double resultado;

        int rango=0;
        if(capital>=0 && capital<5000)
            rango=1;
        else if (capital>=5000 && capital<10000)
            rango=2;
        else if (capital>=10000)
            rango=3;

        // PREGUNTAR: como acceder a tasas.xml (?
        if (dias<30){
            switch (rango){
                case 1: tasa=0.25; break;
                case 2: tasa=0.30; break;
                case 3: tasa=0.35; break;
                default: break;

            }
        }
        else if (dias>=30){
            switch (rango){
                case 1: tasa=0.275; break;
                case 2: tasa=0.323; break;
                case 3: tasa=0.385; break;
                default: break;
            }
        }
        if(dias==0){
            resultado=0;
        }
        else resultado=capital*(pow(1+tasa, (double)dias/360.0)-1.0);
        return round(resultado, 2);
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    View.OnClickListener handler_click_boton = new View.OnClickListener() {
        public void onClick(View v) {

            double resul= calcularInteres(getDias(), getCapital());
            ganancia_correcta_texto.setText("Plazo fijo realizado. Recibirá $"+String.valueOf(resul)+" al vencimiento.");
            ganancia_correcta_texto.setTextColor(getResources().getColor(R.color.colorCorrecto));
            ganancia_correcta_texto.setVisibility(View.VISIBLE);
        }
    };
}
