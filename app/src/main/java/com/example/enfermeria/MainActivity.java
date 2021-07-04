package com.example.enfermeria;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.enfermeria.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
/*
    Única actividad de la aplicación.

    En esta clase únicamente se ha modificado el AppBarConfiguration para que muestre los fragments
    creados.

 NOTAS:
 - De menu/main.xml comentamos el componente de settings de la barra de menú.
 - Se añade un ScrollView en content_main.xml, para que actúe sobre todos los fragments y evitar
   problemas con dispositivos de menor resolución.
 - Se sobreescribió el método onBackPressed() para que cuando se presione el botón de retroceso
   en un fragment se regrese al fragment anterior y no al de inicio

 - Para la creación de un fragment
    + Creamos un blank fragment en la ventana de diseño de mobile_navigation.xml
    + En strings.xml le asignamos una etiqueta nueva.
    + Configuramos el nuevo fragment en menu/activity_main_drawer, para que se muestre en
      el menú lateral .
    + Añadir el ID en el activity
 */

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        /* Aquí tenemos que añadir Los ID de los fragment que hayamos creado (después de haberlo
           configurado en el activity_main_drawer) para que se muestren correctamente en el menú */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //Fragments creados por defecto.
                //R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_home, R.id.fragment_polvos, R.id.fragment_notas, R.id.fragmentCheckList)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(f instanceof HomeFragment) {
            super.onBackPressed();
        } else
            getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}