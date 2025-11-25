package Convertidor.de.monedas.Principal;

import Convertidor.de.monedas.modelos.Moneda;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.sound.midi.Soundbank;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    private static final String API_CLAVE = "dd46176de334b5c132ef7fe5";
    private  static final String API_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        boolean continuar = true;

        while (continuar){
            System.out.println("*************************************************");
            System.out.println("Sea Bienvenido/a al Convertidor de Moneda =) \n");
            menu();
            System.out.println("Elige una opcion: ");
            var opcion = lectura.nextLine();

            Moneda monedaBase = null;
            Moneda monedaDestino = null;

            switch (opcion){
                case "1":
                    monedaBase = new Moneda("USD", "Dólar");
                    monedaDestino = new Moneda("ARS", "Peso Argentino");
                    break;
                case "2":
                    monedaBase = new Moneda("ARS", "Peso Argentino");
                    monedaDestino = new Moneda("USD", "Dólar");
                    break;
                case "3":
                    monedaBase = new Moneda("USD", "Dólar");
                    monedaDestino = new Moneda("BRL", "Real Brasileño");
                    break;
                case "4":
                    monedaBase = new Moneda("BRL", "Real Brasileño");
                    monedaDestino = new Moneda("USD", "Dólar");
                    break;
                case "5":
                    monedaBase = new Moneda("USD", "Dólar");
                    monedaDestino = new Moneda("COP", "Peso Colombiano");
                    break;
                case "6":
                    monedaBase = new Moneda("COP", "Peso Colombiano");
                    monedaDestino = new Moneda("USD", "Dólar");
                    break;
                case "7":
                    System.out.println("Gracias por usar el convertidor. =)");
                    continuar = false;
                    continue;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo");
                    continue;
            }

            System.out.println("*************************************************");
            System.out.println("Ingrese el valor que desea convertir: ");
            double monto = lectura.nextDouble();
            lectura.nextLine();

            try{
                double tasaConversion = obtenerTasaConversion(monedaBase.getCodigo(), monedaDestino.getCodigo());
                monedaDestino.setTasaConversion((tasaConversion));

                double resultado = monto * tasaConversion;
                System.out.printf("El resultado es: %.3f %s \n", resultado, monedaDestino);

            }catch (Exception e){
                System.out.println("Ocurrio un error al consumer la API:");
                e.printStackTrace();
            }
        }
    }

    private  static void menu (){
        System.out.println("1) Dolar (USD) ==> Peso Argentino (ARS)");
        System.out.println("2) Peso argentino (ARS) ==> Dolar (USD)");
        System.out.println("3) Dolar (USD) ==> Real brasileño (BRL)");
        System.out.println("4) Real brasileño (BRL) ==> Dolar (USD)");
        System.out.println("5) Dolar (USD) ==> Peso colombiano (COP)");
        System.out.println("6) Peso colombiano (COP) ==> Dolar (USD)");
        System.out.println("7) Salir");
    }

    private  static  double obtenerTasaConversion(String monedaBase, String monedaDestino) throws Exception {
        String direccion = API_URL + API_CLAVE + "/latest/" + monedaBase;

        //Crear ciente y solicitud
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        //Enviar solicitud y procesar respuesta
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        //Parsear JSON
        JsonObject jsonResponse = JsonParser.parseString(json).getAsJsonObject();
        JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
        return  rates.get(monedaDestino).getAsDouble();
    }
}
