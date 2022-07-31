package com.transportersInc.test.utils;

import com.transportersInc.test.model.Container;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Validations {

    /**
     * Buscar los campos repetidos de una lista
     * @param list lista de contenedores obtenidos en el request
     * @return Lista de los contenedores repetidos
     */
    public static List<String> repeatedValuesInArray(List<Container> list) {

       return list.stream()
                        .collect(Collectors.groupingBy(c -> c.getName()))
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue().size() > 1)
                        .map(e -> e.getKey())
                        .collect(Collectors.toList());
    }

    /**
     * Comparar dos listas para saber si tienen alguna información igual
     * @param list1 lista de contenedores obtenidos en el request
     * @param list2 lista de contenedores ya despachado
     * @return Lista de los contenedores existentes en ambas listas
     */
    public static List<Container> compareTwoArrays(List<Container> list1, List<String> list2) {
        String[] parts = (list2!=null && !list2.isEmpty()? list2.get(0).replaceAll("\\s+",""):"").split(",");
        return list1.stream().filter(c-> Arrays.asList(parts).contains(c.getName())).collect(Collectors.toList());
    }

    /**
     * Suma los valores totales de los contenedores y valida que sea mayor o igual al presupuesto que se desea despachar
     * @param list1 lista de contenedores obtenidos en el request
     * @param budget presupuesto obtenido en el request
     * @return
     */
    public static boolean sumValuesArray(List<Container> list1,double budget){
        return list1.stream().mapToDouble(c -> c.getTransportCost()).sum()>=budget?true:false;
    }

    /**
     * formatea un double
     * @param numero numero decimales a formatear
     * @param numeroDecimales numero de decimales a formatear
     * @return
     */
    public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
        return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
    }

}
