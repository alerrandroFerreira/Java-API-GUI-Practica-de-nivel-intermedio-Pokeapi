package com.mycompany.javapokeapi;

import java.io.BufferedReader; // IMPORT RESPONSABLE DE LEER LA RESPUESTA DEL SERVIDOR
import java.io.InputStreamReader; // LEER LOS FLUJOS DE ENTRADA
import java.net.HttpURLConnection; // PARA REALIZAR CONEXIONES HTTP 
import java.net.URL; // IMPORTA URL PARA MANEJAR URLS
import javax.swing.ImageIcon; //  MANEJAR IMÁGENES EN JLabels
import javax.swing.JLabel; //  MOSTRAR IMÁGENES Y TEXTO
import javax.swing.JOptionPane; //MOSTRAR DIALOGOS
import javax.swing.JTable; // SE NECESITA LEER DATOS EN TABLAS, BASICAMENETE INFORMACIÓN DENTRO DE TABLA
import javax.swing.JTextField; // RECIBIE ENTRADA DE TEXTO
import javax.swing.table.DefaultTableModel; // MANEJA MODELOS DE TABLA
import org.json.JSONObject; // MANIPULAR ARREGLOS/HERRAMIENTAS JSON

public class CPokeApi {
    
    /*MÉTODO QUE MUESTRA LA INFORMACIÓN DEL POKÉMON EN LA INTERFAZ*/
    public void MostrarPokemon (
                /*AQUI HE ESPECIFICADO EL CONTENIDO QUE SE VA MOSTRAR DEL ID LLAMADO DEL XML
                SE ESPECIFICAN LOS ELEMENTOS O OBJETOS QUE SE UTILIZA EN LA INTERFAZ GRAFICA DE USUARIO*/
            JTable tablapokemon, JTextField buscador, JTextField nombre, JTextField peso, JTextField altura, JTextField exp, JLabel foto) {
        
        /*CREA UN NUEVO MODELO DE TABLA PARA MOSTRAR LOS DATOS DEL POKÉMON*/
        DefaultTableModel modelo = new DefaultTableModel();
        String[] nombresColumnas = {"Nombre", "Peso", "Altura"}; // NOMBRES DE LAS COLUMNAS (ORDENADO)
        modelo.setColumnIdentifiers(nombresColumnas); // ASIGNA LOS NOMBRES DE LAS COLUMNAS AL MODELO
        
        tablapokemon.setModel(modelo); // ASIGNA EL MODELO A LA TABLA
        
        try {
            /*AQUI SE ESPECIFICA EL LOCAL DONDE ESTA GUARDADO EL XML QUE PROPORCIONA LOS DATOS NECESARIOS PARA RECIBIR
            EN LA INTERFAZ, SE MUESTRA POR PANTALLA LA INFORMACIÓN QUE EXISTE DENTRO DE ESTE DOCUMENTO, ES NECESARIO RESALTAR 
            QUE SI LOS CAMPOS DEL DOCUMENTO NO SON SIMILARES A LOS DE LOS MÉTODOS CREADOS NO VA FUNCIONAR*/
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + buscador.getText());
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // ABRE UNA CONEXIÓN HTTP
            
            conn.setRequestMethod("GET"); // ESTABLECE EL MÉTODO DE SOLICITUD A GET
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // AQUI SE CREA UN BUFFEREDREADER PARA LEER LA RESPUESTA
            StringBuilder response = new StringBuilder(); // STRINGBUILDER PARA ALMACENAR LA RESPUESTA
            String line; // VARIABLE PARA ALMACENAR CADA LÍNEA DE LA RESPUESTA
            
            /*LEER LA RESPUESTA DEL SERVIDOR LÍNEA POR LÍNEA*/
            while ((line = reader.readLine()) != null) {
                response.append(line); // AGREGAR CADA LÍNEA AL STRINGBUILDER
            }
            reader.close(); // CERRAR EL READER
            
            /*PARSEAR LA RESPUESTA JSON PARA EXTRAER LA INFORMACIÓN DEL POKÉMON*/
            JSONObject jsonObject = new JSONObject(response.toString()); // CONVERTIR LA RESPUESTA A UN OBJETO JSON
            
            /*EXTRAER LOS DATOS DEL POKÉMON DEL OBJETO JSON*/
            String name = jsonObject.getString("name"); // OBTENER EL NOMBRE DEL POKÉMON
            int weight = jsonObject.getInt("weight"); // OBTENER EL PESO DEL POKÉMON
            int height = jsonObject.getInt("height"); // OBTENER LA ALTURA DEL POKÉMON
            int experience = jsonObject.getInt("base_experience"); // OBTENER LA EXPERIENCIA BASE DEL POKÉMON
            /*TODO ESTO SE ESPECIFICA EN EL XML, AQUI SOLO LLAMA LOS JSONOBJECT NOMBRANDO LO QUE SE DEBE PONER EN LOS CAMPOS*/

            modelo.addRow(new Object[]{name, weight, height}); // AGREGAR LOS DATOS A LA TABLA
            
            /*MOSTRAR LOS DATOS EN LOS JTEXTFIELDS RESPECTIVOS*/
            nombre.setText(name); // MOSTRAR EL NOMBRE EN EL JTEXTFIELD
            peso.setText(String.valueOf(weight)); // MOSTRAR EL PESO EN EL JTEXTFIELD
            altura.setText(String.valueOf(height)); // MOSTRAR LA ALTURA EN EL JTEXTFIELD
            exp.setText(String.valueOf(experience)); // MOSTRAR LA EXPERIENCIA EN EL JTEXTFIELD
            
            /*OBTENER LA URL DE LA IMAGEN DEL POKÉMON Y CONFIGURAR EL ICONO EN EL JLabel*/
            String imageUrl = jsonObject.getJSONObject("sprites").getString("front_default"); // OBTENER LA URL DE LA IMAGEN
            
            ImageIcon icon = new ImageIcon(new URL(imageUrl)); // CREAR UN NUEVO ImageIcon CON LA URL DE LA IMAGEN
            foto.setIcon(icon); // ASIGNAR EL ICONO AL JLabel
            
        } catch (Exception e) { // CAPTURAR EXCEPCIONES
            /*MOSTRAR UN DIALOGO DE ERROR SI HAY UN PROBLEMA EN LA SOLICITUD*/
            JOptionPane.showMessageDialog(null, "Ingresse ID o nombre de Pokemon" + e.toString());
        }
    }
}
