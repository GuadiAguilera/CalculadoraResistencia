package org.calculadoraresistencia;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SubpanelResistencia extends Fragment {
    private EditText txtNumRes;
    private Spinner cmbOpciones;
    public SubpanelResistencia() {
        // Constructor vacío requerido por Android.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.subpanel_resistencia, container, false);

        // Obtener la referencia del Spinner desde la vista del fragmento
        cmbOpciones = view.findViewById(R.id.cmbUnidad);
        txtNumRes = view.findViewById(R.id.txtNumRes);

        // Crear una lista de opciones
        List<String> opciones = new ArrayList<>();
        opciones.add("Ohm");
        opciones.add("kOhm");
        opciones.add("MOhm");

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_dropdown_item_subpanel, opciones);
        // Establecer el estilo del desplegable
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_subpanel);
        // Asignar el adaptador al Spinner
        cmbOpciones.setAdapter(adapter);

        // Manejar la selección del Spinner
        cmbOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener la opción seleccionada
                String seleccion = opciones.get(position);

                // Establecer el texto seleccionado en el Spinner
                ((TextView) parentView.getChildAt(0)).setText(seleccion);

                // Establecer el texto seleccionado en blanco
                ((TextView) selectedItemView).setTextColor(Color.WHITE);
                ((TextView) selectedItemView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acciones a realizar cuando no se selecciona ninguna opción
            }
        });

        // Devolver la vista del fragmento
        return view;
    }

    public double obtenerValor(){
        return Double.parseDouble(txtNumRes.getText().toString());
    }

    public String obtenerUnidad() {
        return cmbOpciones.getSelectedItem().toString();
    }

    public boolean isCamposVacios() {
        if(txtNumRes.getText().toString().trim().isEmpty() || (txtNumRes == null)){
            return true;
        }else{
            return false;
        }
    }
}

