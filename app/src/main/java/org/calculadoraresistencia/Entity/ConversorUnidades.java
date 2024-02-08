package org.calculadoraresistencia.Entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ConversorUnidades {

    public static String unidadResultado(ArrayList unidades){
        int[] arrayUnidades = {0,0,0};
        String[] stringUnidad = {"Ohm","kOhm","MOhm"};

        for(int i = 0; i< unidades.size();i++){
            if(unidades.get(i).equals("Ohm")) arrayUnidades[0]++;
            else if (unidades.get(i).equals("kOhm")) arrayUnidades[1]++;
            else if (unidades.get(i).equals("MOhm")) arrayUnidades[2]++;
        }
        int pos = obtenerPosicionMayor(arrayUnidades);
        return stringUnidad[pos];
    }

    private static int obtenerPosicionMayor(int[] arrayUnidades) {
        int pos = 0;
        int mayor = arrayUnidades[0];
        for (int i = 1; i< arrayUnidades.length; i++){
            if (arrayUnidades[i] > mayor) {
                mayor = arrayUnidades[i];
                pos = i;
            }
        }

        return pos;
    }

    public static ArrayList pasarAOhm(ArrayList valores, ArrayList<String> unidades){
        for (int i = 0; i< valores.size(); i++){
            if (unidades.get(i).equals("kOhm")){
                double valorActual = (double) valores.get(i);
                valores.set(i, valorActual * 1000);
            }
            else if(unidades.get(i).equals("MOhm")){
                double valorActual = (double) valores.get(i);
                valores.set(i, valorActual * 1000000);
            }
        }
        return valores;
    }

    public static double truncarADecimales(double valor){
        DecimalFormat df = new DecimalFormat("#.####");
        return Double.parseDouble(df.format(valor));
    }

    public static double pasarAUnidad(String unidad, double resultado){
        if(unidad.equals("kOhm")) return (resultado / 1000);
        else if (unidad.equals("MOhm")) return (resultado / 1000000);
        else return resultado;
    }


}
