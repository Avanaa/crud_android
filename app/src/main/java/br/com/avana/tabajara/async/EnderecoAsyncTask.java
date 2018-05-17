package br.com.avana.tabajara.async;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import br.com.avana.tabajara.FormularioActivity;
import br.com.avana.tabajara.R;
import br.com.avana.tabajara.http.WebClient;
import br.com.avana.tabajara.model.Endereco;
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
        LinearLayout layout = activity.findViewById(R.id.formulario_linear_layout);
        InputMethodManager methodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(layout.getWindowToken(), 0);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected EnderecoNet doInBackground(String[] params) {

        WebClient webClient = new WebClient();
        String resposta = webClient.getJsonByCep(params[0]);

        Gson gson = new Gson();
        try {
            return gson.fromJson(resposta, EnderecoNet.class);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(EnderecoNet endereco) {
        if (endereco != null){
            activity.helper.setEnderecoByCep(endereco);
        }
        progressBar.setVisibility(View.INVISIBLE);
        LinearLayout layout = activity.findViewById(R.id.formulario_linear_layout);
        InputMethodManager methodManager = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.showSoftInput(layout, 0);

    }
}
