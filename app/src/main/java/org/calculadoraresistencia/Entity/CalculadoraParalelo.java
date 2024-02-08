package org.calculadoraresistencia.Entity;

import org.calculadoraresistencia.Interfaces.CalculadoraStrategy;

import java.util.ArrayList;

public class CalculadoraParalelo implements CalculadoraStrategy {
    public CalculadoraParalelo() {

    }

    @Override
    public double calcular(ArrayList valoresResistencia, ArrayList unidadesResistencia) {
        double resultado = 0;
        valoresResistencia = ConversorUnidades.pasarAOhm(valoresResistencia, unidadesResistencia);
        if(valoresResistencia.size() == 2){
            resultado = (((double)valoresResistencia.get(0) * (double)valoresResistencia.get(1)) / ((double)valoresResistencia.get(0) + (double)valoresResistencia.get(1)));
        }
        else{
            double total =0 ;
            for (int i =0 ; i <valoresResistencia.size(); i ++){
                total += 1/ (double)valoresResistencia.get(i);
            }
            resultado = 1/ total;
        }
        return resultado;
    }
}
