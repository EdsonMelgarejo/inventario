package inventario;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author edson
 */
public final class ListaProductos extends JFrame{
    
    JLabel label1 = new JLabel("PRODUCTOS");
    DefaultListModel<String> listaP = new DefaultListModel<>();
    JList listaProductos = new JList();
    ArrayList<Producto> alProd = new ArrayList<>();
    
    public ListaProductos(){
        super("Lista Producto");
        this.setSize(300, 350);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.add(label1);
        label1.setBounds(20, 20, 100, 25);
        
        this.add(listaProductos);
        listaProductos.setBounds(20, 55, 255, 250);
        
        llenarLista();
        seleccionarProducto();
        
        this.setVisible(true);
    }
    private void llenarArray(){
        String sql = "SELECT * FROM `productos` ORDER BY `productos`.`nombreProducto` ASC";
        Conexion conn = new Conexion();
        ResultSet rs;
        rs = conn.consultar(sql);
        try {
            while(rs.next()){
                alProd.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getFloat(6)));                
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        conn.cerrar();
    }
    public void llenarLista(){
        llenarArray();
        listaProductos.setModel(listaP);
        for(int x = 0; x < alProd.size(); x++){
            listaP.addElement(alProd.get(x).getNombreProducto() + " - " + alProd.get(x).getDescripcionProducto());
        }
    }
    public void seleccionarProducto(){
        listaProductos.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int id = listaProductos.getSelectedIndex();
                Producto p = alProd.get(id);
                ModificarProducto detalle = new ModificarProducto(p);
                cerrar();
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
    }
    private void cerrar(){
        this.dispose();
    }
}
