package com.example.enfermeria.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enfermeria.AdminSQLiteOpenHelper;
import com.example.enfermeria.BaseDeDatos;
import com.example.enfermeria.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
    Adaptador para ListView que despliega como item:
        EditText, para mostrar una cadena de texto,
        Checkbox, para asignar un status 0 o 1 al item.
        Button, para eliminar el item del ListView.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String[]> list;
    private Context context;
    private boolean dataChanged;

    public MyCustomAdapter(ArrayList<String[]> list, Context context) {
        this.list = list;
        this.context = context;
        dataChanged = false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_checklist, null);
        }

        //Comunicarse con los componentes del item.
        EditText listItemText = (EditText) view.findViewById(R.id.list_item_string);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        Button deleteBtn = (Button) view.findViewById(R.id.buttonDeleteList);

        //Desplegar los valores
        listItemText.setText(list.get(position)[1]);
        if (list.get(position)[2].equals("1"))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        //Remover un item de la lista y de la base de datos si se presiona el botón delete.
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                dataChanged = true;
                notifyDataSetChanged();
            }
        });

        //Listener para el texto dentro del EditText, para actualizar el item después de que el
        //usuario cambie el valor.
        listItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                dataChanged = true;
                list.get(position)[1] = s.toString();
            }
        });

        //Listener para el checkbox. Cambia el valor dentro de la lista.
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataChanged = true;
                if (isChecked) list.get(position)[2] = "1";
                else list.get(position)[2] = "0";
            }
        });

        return view;
    }

    public ArrayList<String[]> getList(){
        return list;
    }

    public boolean isDataChanged(){ return dataChanged; }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }
}