package br.com.avana.tabajara.async;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.http.WebClient;
import br.com.avana.tabajara.model.EnderecoNet;

public class EnderecoAsyncTask extends AsyncTask<String, Object, EnderecoNet> {

    private FormularioActivity activity;
    private LinearLayout progressBar;

    public EnderecoAsyncTask(FormularioActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressBar = activity.findViewById(R.id.progress_bar_layout);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected EnderecoNet doInBackground(String[] params) {

        WebClient webClient = new WebClient();
        String resposta = webClient.getJsonByCep(params[0]);

        Gson gson = new Gson();
        return gson.fromJson(resposta, EnderecoNet.class);
    }

    @Override
    protected void onPostExecute(EnderecoNet endereco) {
        if (endereco != null){
            activity.helper.setEnderecoByCep(endereco);
            Toast.makeText(activity, R.string.formulario_endereco_encontrado, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, R.string.formulario_endereco_nao_encontrado, Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
    }
}
