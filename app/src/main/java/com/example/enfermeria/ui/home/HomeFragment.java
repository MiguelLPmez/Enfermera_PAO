package com.example.enfermeria.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.enfermeria.FragmentCheckList;
import com.example.enfermeria.FragmentEditarNota;
import com.example.enfermeria.FragmentNotas;
import com.example.enfermeria.R;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private Button buttonNotas, buttonPendientes, buttonCalculos, buttonSalir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonNotas = getView().findViewById(R.id.buttonNotas);
        buttonPendientes = getView().findViewById(R.id.buttonPendientes);
        buttonCalculos = getView().findViewById(R.id.buttonCalculos);
        buttonSalir = getView().findViewById(R.id.buttonSalir);

        buttonSalir.setOnClickListener(l -> getActivity().finish());
        buttonNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNotas fragmentNota = new FragmentNotas();
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction().
                        replace(R.id.nav_host_fragment, fragmentNota).addToBackStack(null).commit();
            }
        });

        buttonPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCheckList fragmentCheckList = new FragmentCheckList();
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction().
                        replace(R.id.nav_host_fragment, fragmentCheckList).addToBackStack(null).commit();
            }
        });

        buttonCalculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout barra = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                barra.openDrawer(GravityCompat.START);
            }
        });
    }

}