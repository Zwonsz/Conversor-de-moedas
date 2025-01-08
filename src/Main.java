import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Interface.exibirMenu();
            String moeda1 = scanner.nextLine();

            if (moeda1.equals("sair")){
                System.out.println("Programa encerrado");
                break;
            }

            String x = "https://v6.exchangerate-api.com/v6/bf3496b78032737cb5b3fb0c/latest/" + moeda1;
            System.out.println("Deseja converter: " + moeda1 + " para qual moeda? ");
            String moeda2 = scanner.nextLine();

            System.out.println("Digite o valor que deseja converter: ");
            double valorAConverter = scanner.nextDouble();
            scanner.nextLine();

            moeda1 = moeda1.toUpperCase();
            moeda2 = moeda2.toUpperCase();

            try {

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(x))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String resultadosApi = response.body();

                Gson gson = new Gson();
                JsonObject desserealizacao = gson.fromJson(resultadosApi, JsonObject.class);
                JsonObject tabelaConversao = desserealizacao.getAsJsonObject("conversion_rates");


                double taxaConversao = tabelaConversao.get(moeda2).getAsDouble();
                double valorFinal = (valorAConverter * taxaConversao);
                System.out.println(valorAConverter + " em " + moeda1 + " corresponde a " + valorFinal + " em " + moeda2);






            } catch (Exception e){
                System.out.println("Ocorreu um erro com o pedido: " + e.getMessage());
                System.out.println("Tente novamente!");
                System.out.println("");
            }



        }



    }
}
