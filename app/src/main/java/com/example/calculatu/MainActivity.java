package com.example.calculatu;

import static java.time.LocalDateTime.now;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHora,btnCalcular;
    ImageButton imgBtnHarriba,imgBtnMarriba,imgBtnHabajo,imgBtnMabajo;
    EditText etHora,etRetardo,etHorasPrograma,etMinutosPrograma;
    private int hora,minutos;

    private LocalTime ahora = LocalTime.now();

    int contadorH=0;
    int contadorM=0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHora = findViewById(R.id.btnHora);
        btnCalcular = findViewById(R.id.btnCalcular);
        imgBtnHarriba=findViewById(R.id.imgBtnHarriba);
        imgBtnHabajo=findViewById(R.id.imgBtnHabajo);
        imgBtnMarriba=findViewById(R.id.imgBtnMarriba);
        imgBtnMabajo=findViewById(R.id.imgBtnMabajo);


        etHora = findViewById(R.id.etHora);
        etRetardo = findViewById(R.id.etRetardo);
        etHorasPrograma = findViewById(R.id.ethorasPrograma);
        etMinutosPrograma = findViewById(R.id.etMinutosPrograma);

        btnHora.setOnClickListener(this);
        etHorasPrograma.setText("0");
        etMinutosPrograma.setText("0");

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();

                LocalTime horaFin = LocalTime.of(obtenerHora(etHora.getText().toString()),obtenerMinuto(etHora.getText().toString()));
                LocalTime sumaPrograma= ahora.plusHours(Integer.parseInt(etHorasPrograma.getText().toString()));
                sumaPrograma= sumaPrograma.plusMinutes(Integer.parseInt(etMinutosPrograma.getText().toString()));
                if(sumaPrograma.getHour()>horaFin.getHour()){
                    horaFin=horaFin.minusHours(12);
                }if(horaFin.getHour()==0){
                    horaFin=LocalTime.of(23,55);
                }
                Duration diferencia= Duration.between(sumaPrograma,horaFin);

                    etRetardo.setText(String.valueOf(Math.abs(diferencia.toHours())));

            }
        });


      }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        final Calendar c= Calendar.getInstance();
        hora= c.get(Calendar.HOUR_OF_DAY);
        minutos= c.get(Calendar.MINUTE);
        if(view==btnHora){

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minuto){
                    etHora.setText(hourOfDay + ":" + minuto);
                }
            }, hora,minutos,true);
              timePickerDialog.show();
        }

        imgBtnHarriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(contadorH<8){
                   contadorH++;
               }else{
                   contadorH = 0;
               }
               etHorasPrograma.setText(String.valueOf(contadorH));
            }
        });
        imgBtnHabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contadorH>0){
                    contadorH--;
                }else if(contadorH<0){
                    contadorH=0;
                }
                etHorasPrograma.setText(String.valueOf(contadorH));
            }
        });
        imgBtnMarriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contadorM>=60){
                    contadorM=0;
                }else{
                    contadorM+=5;
                }
                etMinutosPrograma.setText(String.valueOf(contadorM));
            }
        });
        imgBtnMabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contadorM>0){
                    contadorM-=5;
                }else{
                    contadorM=0;
                }
                etMinutosPrograma.setText(String.valueOf(contadorM));
            }
        });

    }
    public void validar(){
        if(etHorasPrograma.getText().toString().equals("")||etMinutosPrograma.getText().equals("")||etHora.getText().toString().equals("")){
            Toast.makeText(this, "No se han rellenado todos los campos", Toast.LENGTH_SHORT).show();
            etHorasPrograma.setText("0");
            etMinutosPrograma.setText("0");
            etHora.setText(String.valueOf(ahora.getHour()) + ":" + String.valueOf(ahora.getMinute()));
        }
       }

    public int obtenerHora (String cadena){
        validar();
        String array[] = cadena.split(":");
        int hora = Integer.parseInt(array[0]);

          return hora;
    }
    public int obtenerMinuto (String cadena){
        validar();
        String array[] = cadena.split(":");
        int minuto = Integer.parseInt(array[1]);
        return minuto;
    }


}
