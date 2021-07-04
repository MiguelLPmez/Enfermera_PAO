package com.example.enfermeria;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/*
    Fragment que despliega la lista de todas las notas existentes en la base de datos.

    Utiliza FragmentEditarNota para desplegar la nota seleccionada por el usuario.
 */

public class FragmentNotas extends Fragment {
    private FloatingActionButton buttonAddNote;
    private ListView listView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Notas");

        ArrayAdapter<String> adapter;

        BaseDeDatos DB = new BaseDeDatos("Enfermeria", getView().getContext());

        listView = (ListView) getView().findViewById(R.id.listView);
        buttonAddNote = (FloatingActionButton) getView().findViewById(R.id.floatingActionButton);

        // Creamos el ArrayAdapter llamando a getTitulosNotas() para obtener un arreglo con todos
        // los títulos de las notas dentro de la base de datos
        adapter = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_list_item_1, DB.getTitulosNotas());
        listView.setAdapter(adapter);

        // Acciones a realizar cuando se seleccione un elemento de la lista.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Para abrir un nuevo fragment necesitamos del FragmentManager que actualmente está
                // utilizando la Activity (parent).
                FragmentEditarNota fragmentNota = new FragmentEditarNota();
                FragmentManager manager = getParentFragmentManager();

                // Para pasar el título de la nota a desplegar al FragmentEditarNota, utilizamos un
                // bundle y lo asignamos al fragment.
                // getItemAtPosition(position) -> Devuelve el valor del ítem seleccionado, dentro
                //                                del array del adapter (String).
                Bundle bundle = new Bundle();
                bundle.putString("titulo", (String) listView.getItemAtPosition(position));
                fragmentNota.setArguments(bundle);

                // Desplegamos el FragmentEditarNota creado, seleccionando el contenedor de los
                // fragment (R.id.nav_host_fragment).
                manager.beginTransaction().
                        replace(R.id.nav_host_fragment, fragmentNota).addToBackStack(null).commit();
            }
        });

        // Acciones a realizar cuando se seleccione el botón flotante encargado de crear una nueva
        // nota.
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Siguiendo la misma lógica del controlador del ListView; creamos el nuevo
                // fragment, sin pasarle ningún argumento.
                FragmentEditarNota fragmentNota = new FragmentEditarNota();
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction().
                        replace(R.id.nav_host_fragment, fragmentNota).addToBackStack(null).commit();
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notas, container, false);
    }
}