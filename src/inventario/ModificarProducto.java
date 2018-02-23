package inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author edson
 */
public class ModificarProducto extends JFrame{
    
    private final Producto producto;
    
    JLabel nombreP = new JLabel("Nombre");
    JLabel descripcionP = new JLabel("Descripción");
    JLabel unidadP = new JLabel("Unidad");
    JLabel cantidadP = new JLabel("Cantidad");
    JLabel precioCompraP = new JLabel("Precio Compra");
    JLabel precioVentaP = new JLabel("Precio Venta");
    
    JTextField nombrePtf = new JTextField();
    JTextField descripcionPtf = new JTextField();
    JTextField unidadPtf = new JTextField();
    JTextField cantidadPtf = new JTextField();
    JTextField precioCompraPtf = new JTextField();
    JTextField precioVentaPtf = new JTextField();
    
    JButton guardar = new JButton("GUARDAR");
    JButton borrar = new JButton("BORRAR");
    JButton actualizar = new JButton("REABASTECER");
    
    public ModificarProducto(Producto p){
        super("Editar Producto");
        this.setSize(300, 380);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        producto = p;
        
        this.add(actualizar);
        actualizar.setBounds(20, 20, 150, 30);
        
        this.add(nombreP);
        nombreP.setBounds(20, 70, 100, 25);
        this.add(descripcionP);
        descripcionP.setBounds(20, 110, 100, 25);
        this.add(unidadP);
        unidadP.setBounds(20, 150, 100, 25);
        this.add(cantidadP);
        cantidadP.setBounds(20, 190, 100, 25);
        this.add(precioCompraP);
        precioCompraP.setBounds(20, 230, 100, 25);
        this.add(precioVentaP);
        precioVentaP.setBounds(20, 270, 100, 25);        
        
        System.err.println(producto.getIdProducto());
        
        this.add(nombrePtf);
        nombrePtf.setBounds(130, 70, 150, 25);
        nombrePtf.setText(producto.getNombreProducto());
        
        this.add(descripcionPtf);
        descripcionPtf.setBounds(130, 110, 150, 25);
        descripcionPtf.setText(producto.getDescripcionProducto());
        
        this.add(unidadPtf);
        unidadPtf.setBounds(130, 150, 150, 25);
        unidadPtf.setText(producto.getUnidadProducto());
        
        this.add(cantidadPtf);
        cantidadPtf.setBounds(130, 190, 150, 25);
        cantidadPtf.setText(String.valueOf(producto.getCantidadProducto()));
        cantidadPtf.setEditable(false);
        
        this.add(precioCompraPtf);
        precioCompraPtf.setBounds(130, 230, 150, 25);
        precioCompraPtf.setText(String.valueOf(producto.getPrecioCompraProducto()));
        
        this.add(precioVentaPtf);
        precioVentaPtf.setBounds(130, 270, 150, 25);
        precioVentaPtf.setText(String.valueOf(producto.getPrecioVentaProducto()));
        precioVentaPtf.setEditable(false);
        
        this.add(guardar);
        guardar.setBounds(150, 310, 100, 30);
        
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(validaciones() == true){
                    guardar();
                }
            }
        });       
        
        this.add(borrar);
        borrar.setBounds(50, 310, 85, 25);
        
        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                borrar();
            }
        });
        
        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Reabastecer vent = new Reabastecer(producto);
            }
        });
        
        this.setVisible(true);
        
    }    
    public boolean validaciones(){
        boolean flag;
        if(nombrePtf.getText().equals("") || 
                descripcionPtf.getText().equals("") || unidadPtf.getText().equals("") 
                || cantidadPtf.getText().equals("") || precioCompraPtf.getText().equals("")){
            JOptionPane.showMessageDialog(null, "COMPLETA TODOS LOS CAMPOS");
            flag = false;
        }
        else{
            try{
                float cant = Float.parseFloat(cantidadPtf.getText());
                float prec = Float.parseFloat(precioCompraPtf.getText());  
                if(cant <= 0 || prec <= 0){
                    JOptionPane.showMessageDialog(null, "CANTIDAD Y/O PRECIO NO PUEDEN SER IGUALES O MENORES A 0");
                    flag = false;
                }
                else{
                    flag = true;
                }        
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "INGRESA LOS DATOS CORRECTOS EN CANTIDAD Y/O PRECIO");
                flag = false;
            }
        }
        return flag;
    }
    public void guardar(){
        float cantidad = Float.parseFloat(cantidadPtf.getText());
        float precio = Float.parseFloat(precioCompraPtf.getText());
        String sql = ("UPDATE `productos` SET `nombreProducto` = '"+nombrePtf.getText()+"' , `descripcionProducto` = '"+descripcionPtf.getText()+"', `unidadProducto` = '"+unidadPtf.getText()+"', `cantidadProducto` = '"+cantidad+"', `precioCompraProducto` = '"+precio+"' WHERE `productos`.`idProducto` = "+producto.getIdProducto()+";");
        Conexion conn = new Conexion();
        boolean flag = conn.ejecutar(sql);
        conn.cerrar();
        if(flag == true){
            JOptionPane.showMessageDialog(null, "PRODUCTO ACTUALIZADO");
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR");
        }
    }
    public void borrar(){
        String sql = ("DELETE FROM `productos` WHERE `idProducto` = '"+producto.getIdProducto()+"'");
        Conexion conn = new Conexion();
        boolean flag;
        if(JOptionPane.showConfirmDialog(null, "Confirmar") == 0){
            flag = conn.ejecutar(sql);
            conn.cerrar();
            if(flag == true){            
                JOptionPane.showMessageDialog(null, "PRODUCTO BORRADO");
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null, "ERROR AL BORRAR");
            }
        }                
    }
}