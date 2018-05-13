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

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                return readStream(connection.getInputStream());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readStream(InputStream inputStream) throws IOException {

        BufferedReader bf = null;
        StringBuffer sb = new StringBuffer();

        try{
            bf = new BufferedReader(new InputStreamReader(inputStream));
            String line = new String("");

            while ((line = bf.readLine()) != null){
                sb.append(line);
            }

        }
        catch (IOException e){
            throw new IOException();
        }
        finally {
            try {
                if (bf != null){
                    bf.close();
                }
            } catch (IOException e) {throw new IOException();}
        }
        return sb.toString();
    }
}
