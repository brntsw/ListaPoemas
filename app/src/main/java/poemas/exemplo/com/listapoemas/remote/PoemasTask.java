package poemas.exemplo.com.listapoemas.remote;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import poemas.exemplo.com.listapoemas.R;
import poemas.exemplo.com.listapoemas.adapter.PoemaAdapter;
import poemas.exemplo.com.listapoemas.model.JsonReturn;
import poemas.exemplo.com.listapoemas.model.Poema;

/**
 * Created by BPardini on 09/11/2016.
 */

public class PoemasTask extends AsyncTask<Void, Void, JsonReturn> {

    public static final String LOG_TAG = "PoemasTask";

    private Context context;
    private RecyclerView recyclerPoemas;
    private ProgressDialog progressDialog;

    public PoemasTask(Context context, RecyclerView recyclerPoemas){
        this.context = context;
        this.recyclerPoemas = recyclerPoemas;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.aguarde));
        progressDialog.setMessage(context.getString(R.string.carregando_poemas));
        progressDialog.show();
    }

    @Override
    protected JsonReturn doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        JsonReturn jsonReturn;

        try{
            final String LOGIN_URL = "http://brunopardini.com/ws_poemas/ws_poema.php";

            Uri uri = Uri.parse(LOGIN_URL).buildUpon().build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            //set the sending type and receiving type to json
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            urlConnection.connect();

            Reader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            jsonReturn = new JsonReturn(urlConnection.getResponseCode(), response);
        } catch (IOException e) {
            jsonReturn = new JsonReturn(999, e.getMessage());
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }
                catch (IOException e){
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return jsonReturn;
    }

    protected void onPostExecute(JsonReturn result){
        progressDialog.dismiss();

        if(result.getReturnStatus() == JsonReturn.RETORNO_SUCESSO){
            try {
                JSONArray jsonArray = new JSONArray(result.getResultString());

                List<Poema> poemas = new ArrayList<>();

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObjectPoema = jsonArray.getJSONObject(i);

                    Poema poema = new Poema();
                    poema.setTitulo(jsonObjectPoema.getString("titulo"));
                    poema.setData(jsonObjectPoema.getString("data"));
                    poema.setAutor(jsonObjectPoema.getString("autor"));

                    poemas.add(poema);
                }

                PoemaAdapter adapter = new PoemaAdapter(poemas);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
                recyclerPoemas.setLayoutManager(manager);
                recyclerPoemas.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            switch (result.getReturnStatus()){
                case JsonReturn.ERRO:
                    Log.d(LOG_TAG, context.getString(R.string.erro));
                    Toast.makeText(context, context.getString(R.string.erro_tente_novamente), Toast.LENGTH_SHORT).show();
                    break;
                case JsonReturn.NAO_ENCONTRADO:
                    Log.d(LOG_TAG, context.getString(R.string.nao_encontrado));
                    Toast.makeText(context, context.getString(R.string.nao_encontrado), Toast.LENGTH_SHORT).show();
                    break;
                case JsonReturn.NAO_DISPONIVEL:
                    Log.d(LOG_TAG, context.getString(R.string.nao_disponivel));
                    Toast.makeText(context, context.getString(R.string.nao_disponivel), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d(LOG_TAG, context.getString(R.string.erro));
                    Toast.makeText(context, context.getString(R.string.erro_tente_novamente), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
