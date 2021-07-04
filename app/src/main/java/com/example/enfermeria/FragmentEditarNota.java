package com.example.enfermeria;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/*
    Fragment que despliega una nota y permite editarla o crear una nueva.
 */

public class FragmentEditarNota extends Fragment {
    private EditText editTextTitulo, editTextContenido;
    private FloatingActionButton buttonGuardar, buttonEliminar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BaseDeDatos DB = new BaseDeDatos("Enfermeria", getView().getContext());

        editTextTitulo = getView().findViewById(R.id.editTextTitulo);
        editTextContenido = getView().findViewById(R.id.editTextContenido);
        buttonGuardar = getView().findViewById(R.id.floatingActionButtonGuardar);
        buttonEliminar = getView().findViewById(R.id.floatingActionButtonEliminar);

        // Obtenemos valores enviados por otro fragment.
        Bundle bundle = this.getArguments();

        // Si no recibimos un título, debemos crear una nueva nota al presionar el botón de guardar,
        // para el caso contrario, debemos sobreescribir la nota.
        if(bundle != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Editar nota");
            String titulo = bundle.getString("titulo");
            editTextTitulo.setText(titulo);
            editTextContenido.setText(DB.getContenidoNota(titulo));
            buttonEliminar.setVisibility(View.VISIBLE);
            buttonEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DB.eliminarNota(titulo);
                    // Cerrar el fragment:
                    getParentFragmentManager().popBackStack();
                    Toast.makeText(getView().getContext(),"Nota eliminada", Toast.LENGTH_SHORT)
                                    .show();
                }
            });
            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editTextTitulo.getText().toString().length() != 0 &&
                        editTextContenido.getText().toString().length() != 0) {
                        //DB.guardarNota(titulo, editTextContenido.getText().toString());
                        DB.guardarNota(editTextTitulo.getText().toString(),
                                editTextContenido.getText().toString());
                        Toast.makeText(getView().getContext(),"Nota guardada",
                                Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(getView().getContext(),"Debe llenar todos los campos",
                                        Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Crear nota");
            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editTextTitulo.getText().toString().length() != 0 &&
                            editTextContenido.getText().toString().length() != 0) {
                        DB.guardarNota(editTextTitulo.getText().toString(),
                                editTextContenido.getText().toString());
                        Toast.makeText(getView().getContext(), "Nota guardada",
                                Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getView().getContext(), "Debe llenar todos los campos",
                                Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_nota, container, false);

    }

}