package carlos.robert.a06recyclerview.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import carlos.robert.a06recyclerview.R;
import carlos.robert.a06recyclerview.modelos.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {
    private List<ToDo> objects; //lista de filas en espera
    private int resource; //cómo se verá cada fila cargada (vista)
    private Context context; //acitividad que tiene el RecyclerView para mostrar

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    //instancia tantos elementos como quepan en pantalla
    @NonNull
    @Override
    public ToDoAdapter.ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(resource, null);

        ViewGroup.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(lp);

        return new ToDoVH(todoView);
    }

    //llama de forma automática según el elemento que tenemos que rellenar
    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ToDoVH holder, int position) {
        ToDo toDo = objects.get(position);

        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()) {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        } else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmUpdate("¿SEGURO QUE QUIERES CAMBIAR?", toDo).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete("¿SEGURO QUE QUIERES BORRAR?", holder.getAdapterPosition()).show();
            }
        });
    }

    //le decimos al adapter cuántas filas mostrar
    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmUpdate(String titulo, ToDo toDo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);//en qué actividad la muestro

        builder.setTitle(titulo);//pongo un título
        builder.setCancelable(false);//si aprietan fuera del alert no se cerrará

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged(); //notificar el cambio para que redibuje
            }
        });
        return builder.create();
    }

    private AlertDialog confirmDelete(String titulo, int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });

        return builder.create();
    }

    public class ToDoVH extends RecyclerView.ViewHolder {
        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado, btnDelete;

        public ToDoVH(@NonNull View itemView) { //al ser una clase y no una actividad,
            super(itemView);                     //no podemos usar el binding

            lbTitulo = itemView.findViewById(R.id.lbTituloToDoViewModel);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoViewModel);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
            btnDelete = itemView.findViewById(R.id.btnDeleteToDoViewModel);
        }
    }
}
