package poemas.exemplo.com.listapoemas.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import poemas.exemplo.com.listapoemas.R;
import poemas.exemplo.com.listapoemas.adapter.PoemaAdapter;
import poemas.exemplo.com.listapoemas.model.Poema;
import poemas.exemplo.com.listapoemas.remote.PoemasTask;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerPoemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerPoemas = (RecyclerView) findViewById(R.id.recycler_poemas);

        setSupportActionBar(toolbar);
    }

    protected void onResume(){
        super.onResume();

        toolbar.setTitle(getString(R.string.app_name));

        //Chamada da AsyncTask
        PoemasTask poemasTask = new PoemasTask(this, recyclerPoemas);
        poemasTask.execute();
    }
}
