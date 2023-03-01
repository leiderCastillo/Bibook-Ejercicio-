package programa.OperacionesDB;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class libro {
    private int id;
    private String autor;
    private String nombre;
    private String edicion;
    private String paginas;
    private String genero;
    Connection con;
    
    public libro(){
        con = coneccion.getConexion();
    }

    public libro(int id, String Autor, String Nombre, String Edicion, String Paginas, String Genero) {
        con = coneccion.getConexion();
        this.id = id;
        this.autor = Autor;
        this.nombre = Nombre;
        this.edicion = Edicion;
        this.paginas = Paginas;
        this.genero = Genero;
    }
    public Vector librosDisponibles(){
        Vector<String> vector = new Vector<String>();
        try {
            String sql = "call  consultarLibrosDisponibles();";
            CallableStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            while(rs.next()){
                vector.add(rs.getInt(3)+".  "+rs.getString(1)+" ("+(rs.getString(2))+")");
            }
            return vector;
           
        } catch (Exception e) {
            System.out.println("ERROR, EN libros DIsponibles"+e);
             return vector;
        }
        
        
    }
    
    public void agregarlibro(){
        try {
            String sql = "call ingresarLibro(\""+id+"\",\""+autor+"\",\""+nombre+"\",\""+edicion+"\",\""+paginas+"\",\""+genero+"\"); " ;
            CallableStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            JOptionPane.showMessageDialog(null, "Libro Agregado Correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Este usuario ya fue creado");
            System.out.print("Error Bibliotecario: "+e);
        }
    }
    

    public void LLenarDatos(DefaultTableModel modelo){
        try {
            String sql="select * from Libro";
            CallableStatement cmd=con.prepareCall(sql);
            ResultSet rs= cmd.executeQuery();
            while(rs.next()){
                Object[] datos=new Object[6];
                for(int i=0;i<6;i++){
                    datos[i]=rs.getString(i+1);
                }
                modelo.addRow(datos);
            }
            cmd.close();
            con.close();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
