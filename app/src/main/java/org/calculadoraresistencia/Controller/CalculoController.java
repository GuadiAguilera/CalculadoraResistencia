package org.calculadoraresistencia.Controller;

import org.calculadoraresistencia.Entity.CalculadoraParalelo;
import org.calculadoraresistencia.Entity.CalculadoraSerie;
import org.calculadoraresistencia.Entity.ConversorUnidades;
import org.calculadoraresistencia.Interfaces.CalculadoraStrategy;
import org.calculadoraresistencia.SubpanelResistencia;

import java.util.ArrayList;

public class CalculoController {
    private CalculadoraStrategy calcStrategy;

    public CalculoController(){

    }

    public String unidadResultado(ArrayList unidadesResistencia){
        return ConversorUnidades.unidadResultado(unidadesResistencia);
    }


    public double calcular(String estrategia, ArrayList valoresResistencia, ArrayList unidadesResistencia) {
        if(estrategia.equals("serie")){
            calcStrategy = new CalculadoraSerie();
            return calcStrategy.calcular(valoresResistencia, unidadesResistencia);
        } else if(estrategia.equals("paralelo")){
            calcStrategy = new CalculadoraParalelo();
            return calcStrategy.calcular(valoresResistencia, unidadesResistencia);
        }
        return 0;
    }

    public double truncarADecimales(double res) {
        return ConversorUnidades.truncarADecimales(res);
    }

    public double pasarAUnidad(String unidad, double resultado) {
        return ConversorUnidades.pasarAUnidad(unidad, resultado);
    }
}
