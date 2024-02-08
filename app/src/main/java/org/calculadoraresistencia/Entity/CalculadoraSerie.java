package org.calculadoraresistencia.Entity;

import org.calculadoraresistencia.Interfaces.CalculadoraStrategy;

import java.util.ArrayList;

public class CalculadoraSerie implements CalculadoraStrategy {
    @Override
    public double calcular(ArrayList valoresResistencia, ArrayList unidadesResistencia) {
        valoresResistencia = ConversorUnidades.pasarAOhm(valoresResistencia, unidadesResistencia);

        double resultado = 0;
        for (int i =0; i < valoresResistencia.size(); i++){
            resultado += (double) valoresResistencia.get(i);
        }
        return resultado;
    }

}
