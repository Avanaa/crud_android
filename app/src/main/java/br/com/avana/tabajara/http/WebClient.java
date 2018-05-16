package br.com.avana.tabajara.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebClient {

    public static final String VIACEP_GET = "https://viacep.com.br/ws/%CEP%/json/";

    public String getJsonByCep(String cep){

        String uri = VIACEP_GET.replace("%CEP%", cep);

        try {
            URL url = new URL(uri);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return readStream(connection.getInputStream());
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private String readStream(InputStream inputStream) throws IOException {

        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder();

        try{
            bf = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while ((line = bf.readLine()) != null){
                sb.append(line);
            }
        }
        catch (IOException e){
            return null;
        } finally {
            try {
                if (bf != null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
