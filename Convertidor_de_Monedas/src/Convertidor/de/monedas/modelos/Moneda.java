package Convertidor.de.monedas.modelos;

public class Moneda implements Comparable<Moneda>{
    private String codigo;
    private String nombre;
    private double tasaConversion;

    public Moneda(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTasaConversion() {
        return tasaConversion;
    }

    public void setTasaConversion(double tasaConversion) {
        this.tasaConversion = tasaConversion;
    }

    @Override
    public int compareTo(Moneda otraMoneda){
        return this.codigo.compareTo(otraMoneda.codigo);
    }

    @Override
    public String toString(){
        return nombre + " (" + codigo + ")";
    }
}
