package com.example.enfermeria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enfermeria.ui.MyCustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/*
    Fragment que despliega un checklist de tareas pendientes del usuario.

    Utiliza MyCustomAdapter como adaptador para mostrar los elementos en el ListView.

    NOTAS: https://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
    - Creamos un custom layout para representar cada elemento de la lista, es decir; un textview,
      un checkbox y un botón: item_checklist.xml
    - Creamos un ArrayAdapter que maneje los eventos de los botones y el textview
 */

public class FragmentCheckList extends Fragment {
    private ListView listViewTareas;
    private FloatingActionButton buttonAgregar;
    private ArrayList<String[]> list;
    private MyCustomAdapter adapter;
    private BaseDeDatos DB;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Pendientes");

        DB = new BaseDeDatos("Enfermeria", getView().getContext());

        listViewTareas = getView().findViewById(R.id.listViewTareas);
        buttonAgregar = getView().findViewById(R.id.floatingActionButtonAddTask);
        list = DB.getToDoList();

        //Creación del custom adapter
        adapter = new MyCustomAdapter(list, getView().getContext());
        listViewTareas.setAdapter(adapter);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verificamos si los datos en el adapter han sufrido cambios, si es así, tenemos
                //que actualizar la base de datos antes de agregar una nueva tarea.
                if(adapter.isDataChanged())
                    updateDataBase();
                //Para actualizar el ListView después de haber agregado una tarea...
                //Como la base de datos cambió, se tienen que cargar de nuevo los datos.
                //Creamos un nuevo adapter con los nuevos datos y lo asignamos.
                DB.agregarTareaNueva();
                list = DB.getToDoList();
                adapter = new MyCustomAdapter(list, getView().getContext());
                listViewTareas.setAdapter(adapter);
            }
        });

    }

    //Actualiza los valores en la base de datos según lo contenido en el ArrayAdapter
    private void updateDataBase(){
        DB.updateToDoList(adapter.getList());
    }

    @Override
    public void onDestroyView() {
        updateDataBase();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        updateDataBase();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        updateDataBase();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        updateDataBase();
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

}