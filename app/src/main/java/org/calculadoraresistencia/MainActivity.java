package org.calculadoraresistencia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.calculadoraresistencia.Controller.CalculoController;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SubpanelResistencia> subpaneles;
    private ArrayList valoresResistencia;
    private ArrayList unidadesResistencia;
    private ArrayList<String> listUnidades;
    private CalculoController gestor;
    private double res =0;
    private String seleccion;
    private TextView resultado;
    private Button btnAgregar;
    private Button btnEliminar;
    private Button btnSerie;
    private Button btnParalelo;
    private LinearLayout container;
    private Spinner cmbUnidad;

    public MainActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gestor = new CalculoController();
        this.subpaneles = new ArrayList<>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // iniciacion de botones y textos
        container = findViewById(R.id.containerLayout);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnSerie = findViewById(R.id.btnSerie);
        btnParalelo = findViewById(R.id.btnParalelo);
        resultado = findViewById(R.id.txtResultado);
        resultado.setText("");
        cmbUnidad = findViewById(R.id.cmbUnidad);

        subpanelesIniciales(container);
        crearSpinner();
        resultado.setText("");
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubpanelResistencia subpanelResistencia = crearFragmento(container);
                subpaneles.add(subpanelResistencia);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subpaneles.size() > 2){
                    SubpanelResistencia ultimoFragmento = subpaneles.remove(subpaneles.size() - 1);

                    // Obtener el FragmentManager y comenzar una transacción para quitar el fragmento
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(ultimoFragmento);
                    transaction.commit();
                }
            }
        });

        btnSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCamposVacios()) {
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                valoresResistencia = convertirValores(subpaneles);
                unidadesResistencia = convertirUnidades(subpaneles);
                res = gestor.calcular("serie", valoresResistencia, unidadesResistencia);
                double resAcomodado = gestor.pasarAUnidad(seleccion,res);
                resultado.setText(String.valueOf(gestor.truncarADecimales(resAcomodado)));
            }
        });

        btnParalelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCamposVacios()) {
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                valoresResistencia = convertirValores(subpaneles);
                unidadesResistencia = convertirUnidades(subpaneles);
                res = gestor.calcular("paralelo", valoresResistencia, unidadesResistencia);
                double resAcomodado = gestor.pasarAUnidad(seleccion,res);
                resultado.setText(String.valueOf(gestor.truncarADecimales(resAcomodado)));
            }
        });

        cmbUnidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener la opción seleccionada
                seleccion = listUnidades.get(position);

                // Establecer el texto seleccionado en el Spinner
                ((TextView) view).setText(seleccion);

                // Establecer el texto seleccionado en blanco
                ((TextView) view).setTextColor(Color.WHITE);
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                double resAcomodado = gestor.pasarAUnidad(seleccion,res);
                resultado.setText(String.valueOf(gestor.truncarADecimales(resAcomodado)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void subpanelesIniciales(LinearLayout container) {
        SubpanelResistencia subpanelResistencia1 = crearFragmento(container);
        subpaneles.add(subpanelResistencia1);
        SubpanelResistencia subpanelResistencia2 = crearFragmento(container);
        subpaneles.add(subpanelResistencia2);
    }

    private boolean isCamposVacios() {
        for(SubpanelResistencia subpanel : subpaneles){
            if(subpanel.isCamposVacios()){
                return true;
            }
        }
        return false;
    }


    private SubpanelResistencia crearFragmento(LinearLayout container){
        // Crear una instancia del fragmento.
        SubpanelResistencia subpanelResistencia = new SubpanelResistencia();

        // Obtener el FragmentManager y comenzar una transacción.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Agregar el nuevo fragmento al contenedor.
        transaction.add(container.getId(), subpanelResistencia);
        // Añadir la transacción a la pila para permitir retroceso.
        transaction.addToBackStack(null);
        // Confirmar la transacción.
        transaction.commit();

        return subpanelResistencia;
    }

    private ArrayList convertirValores(ArrayList<SubpanelResistencia> subpaneles){
        valoresResistencia = new ArrayList<>();
        for (SubpanelResistencia subpanelesResistencia : subpaneles){
            double valor = subpanelesResistencia.obtenerValor();
            valoresResistencia.add(valor);
        }
        return valoresResistencia;
    }

    private ArrayList convertirUnidades(ArrayList<SubpanelResistencia> subpaneles){
        unidadesResistencia = new ArrayList();
        for (SubpanelResistencia subpanelResistencia : subpaneles){
            String unidad = subpanelResistencia.obtenerUnidad();
            unidadesResistencia.add(unidad);
        }
        return unidadesResistencia;
    }

    private void crearSpinner(){
        // Crear una lista de opciones
        listUnidades = new ArrayList<>();
        listUnidades.add("Ohm");
        listUnidades.add("kOhm");
        listUnidades.add("MOhm");

        // Crear un adaptador para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item_main, listUnidades);
        // Establecer el estilo del desplegable
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_main);
        // Asignar el adaptador al Spinner
        cmbUnidad.setAdapter(adapter);
    }
}