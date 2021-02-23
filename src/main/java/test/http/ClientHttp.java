package test.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientHttp {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://localhost:8081/hello/world");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.getInputStream().read();
    }
}
