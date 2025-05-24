package co.uco.golocal.golocalapi.utils;

public class UtilObject {

    private UtilObject(){

    }


    public static Object defaultValue(Object valorEntrada, Object valorPorDefecto){
        if(isDefault(valorEntrada)){
            return valorPorDefecto;
        }
        return  valorEntrada;
    }

    private static boolean isDefault(Object valor){
        return valor.equals(null);
    }
}
