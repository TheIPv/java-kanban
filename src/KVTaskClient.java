import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private URI url;
    private String API_TOKEN;
    private HttpClient client = HttpClient.newHttpClient();


    public KVTaskClient(String url) {
        this.url = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/register"))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                API_TOKEN = response.body();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка KVServer");
        }
    }

    public void put(String key, String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/save/"+key+"?API_TOKEN="+API_TOKEN))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка KVServer");
        }
    }

    public String load(String key) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/load/"+key+"?API_TOKEN="+API_TOKEN))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Не удалось получить значение по ключу: "+key);
        }
        return null;
    }
}
