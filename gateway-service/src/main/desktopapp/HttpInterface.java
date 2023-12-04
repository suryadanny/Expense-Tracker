package desktopapp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpInterface {

    public static HttpResponse<String> POST(String url, String payload) throws Exception
    {
        String auth = Common_var.btoa();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .setHeader("Authorization", auth)
                .setHeader("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert(response.statusCode()== 200);
        assert(response.body().equals("{\"message\":\"ok\"}"));

        return response;
    }

    public static HttpResponse<String> GET(String url) throws Exception
    {
        String auth = Common_var.btoa();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .setHeader("Authorization", auth)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert(response.statusCode()== 200);
        assert(response.body().equals("{\"message\":\"ok\"}"));

        return response;
    }
}
