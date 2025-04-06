package com.example.demo;

import com.example.demo.model.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioStorage {
    private static final String FILE_PATH = "usuarios.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Usuario> cargarUsuarios() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try {
            return mapper.readValue(file, new TypeReference<List<Usuario>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarUsuarios(List<Usuario> usuarios) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}