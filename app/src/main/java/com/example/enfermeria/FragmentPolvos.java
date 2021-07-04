package com.example.enfermeria;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*
    Fragment que despliega la interfaz para realizar cálculos de dosis por medicamento en polvo.

    Los valores a mostrar por los Spinner, se encuentran definidos como array en el archivo
    string.xml
 */

public class FragmentPolvos extends Fragment {
    private double mgPedidos, indiceDisol, presentacion;
    private EditText editTextMgPedidos, editTextPresentacion, editTextIndice;
    private TextView textViewResMedicamento, textViewResSolucion;
    private Spinner spinnerPresentacion, spinnerIndice;
    private Button buttonCalcular;
    private Resources res;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Cálculo de polvos");
        // Para obtener recursos de un .xml externo usaremos la clase Resources.
        res = getResources();
        ArrayAdapter <String> adapterPresentaciones, adapterIndices;

        // [0] -> Contiene la presentación en mg/mL a desplegar por el spinner
        //        correspondiente en ambos arrays.
        // [1] -> Contiene el valor numérico de la presentación a utilizar en el cálculo y a
        //        desplegar en el EditText correspondiente.
        // El primer valor en ambos arreglos corresponde a la descripción de los valores del
        // spinner, por ejemplo, presentaciones[0][0] = "Presentaciones".
        String[][] presentaciones = new String[][]{
                res.getStringArray(R.array.presentacionesMgSobreMl),
                res.getStringArray(R.array.presentacionesValores)};
        String[][] indices = {
                res.getStringArray(R.array.indicesNombres),
                res.getStringArray(R.array.indicesValores)};

        editTextMgPedidos = getView().findViewById(R.id.editTextNumberMgPedidos);
        editTextPresentacion = getView().findViewById(R.id.editTextNumberPresentacion);
        editTextIndice = getView().findViewById(R.id.editTextNumberIndiceDisol);
        textViewResMedicamento = getView().findViewById(R.id.textViewMlMedicamento);
        textViewResSolucion = getView().findViewById(R.id.textViewMlSolucion);
        spinnerPresentacion = getView().findViewById(R.id.spinnerPresentacion);
        spinnerIndice = getView().findViewById(R.id.spinnerIndiceDisol);
        buttonCalcular = getView().findViewById(R.id.buttonCalcular);

        adapterPresentaciones = new ArrayAdapter<>(getView().getContext(),
                android.R.layout.simple_spinner_dropdown_item, presentaciones[0]);
        adapterIndices = new ArrayAdapter<>(getView().getContext(),
                android.R.layout.simple_spinner_dropdown_item, indices[0]);

        spinnerPresentacion.setAdapter(adapterPresentaciones);
        spinnerPresentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                    editTextPresentacion.setText(presentaciones[1][position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerIndice.setAdapter(adapterIndices);
        spinnerIndice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)
                    editTextIndice.setText(indices[1][position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonCalcular.setOnClickListener(l -> calcular());
    }

    // Método que despliega el resultado de los cálculos en los TextView
    // al presionar el botón correspondiente.
    private void calcular(){
        double medicamento, solucion;

        // Validación de campos no vacíos
        if(editTextMgPedidos.getText().toString().length() != 0
                && editTextIndice.getText().toString().length() != 0
                && editTextPresentacion.getText().toString().length() != 0){

            mgPedidos = Double.parseDouble(editTextMgPedidos.getText().toString());
            indiceDisol = Double.parseDouble(editTextIndice.getText().toString());
            presentacion = Double.parseDouble(editTextPresentacion.getText().toString());

            medicamento = mgPedidos / presentacion;
            solucion = mgPedidos / indiceDisol;

            textViewResMedicamento.setText(String.valueOf(medicamento) + " mL de medicamento");
            textViewResSolucion.setText(String.valueOf(solucion) +" mL de solución");
        } else
            Toast.makeText(getView().getContext(),
                        "Complete todos los campos",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_polvos, container, false);
    }
}