package carlos.robert.a06recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import carlos.robert.a06recyclerview.adapters.ToDoAdapter;
import carlos.robert.a06recyclerview.databinding.ActivityMainBinding;
import carlos.robert.a06recyclerview.modelos.ToDo;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<ToDo> todoList;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoList = new ArrayList<>();
        //crearTareas();

        adapter= new ToDoAdapter(todoList, R.layout.todo_view_model, MainActivity.this);
        binding.contenedor.contentMainRecycler.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contenedor.contentMainRecycler.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo().show();
            }
        });
    }

    private AlertDialog createToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //en qué actividad la muestro
        builder.setTitle("CREAR TAREA"); //pongo un título
        builder.setCancelable(false); //si aprietan fuera del alert no se cerrará

        //preparo la vista
        View todoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert, null);
        EditText txtTitulo = todoAlert.findViewById(R.id.txtTituloToDoModelAlert);
        EditText txtContenido = todoAlert.findViewById(R.id.txtTituloToDoModelAlert);

        builder.setView(todoAlert); //añado la vista al constructor

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(txtTitulo.getText().toString().isEmpty()
                || txtContenido.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }else{ //creamos un elemento de la lista
                    todoList.add(new ToDo(
                            txtTitulo.getText().toString(),
                            txtContenido.getText().toString()
                    ));
                    adapter.notifyDataSetChanged(); //notificamos el cambio
                }
            }
        });
        return builder.create();
    }
    private void crearTareas() {
        for (int i = 0; i < 1000; i++) {
            todoList.add(new ToDo("Titulo"+i, "Contenido"+i));
        }
    }
}