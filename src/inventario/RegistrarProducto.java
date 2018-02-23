package inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author edson
 */
public class RegistrarProducto extends JFrame{    
    
    JLabel nombreP = new JLabel("Nombre");
    JLabel descripcionP = new JLabel("Descripción");
    JLabel unidadP = new JLabel("Unidad");
    JLabel cantidadP = new JLabel("Cantidad");
    JLabel precioCompraP = new JLabel("Precio Compra");
    
    JTextField nombrePtf = new JTextField();
    JTextField descripcionPtf = new JTextField();
    JTextField unidadPtf = new JTextField();
    JTextField cantidadPtf = new JTextField();
    JTextField precioCompraPtf = new JTextField();
    
    JButton guardar = new JButton("GUARDAR");
    JButton limpiar = new JButton("Limpiar");
    
    public RegistrarProducto(){
        super("Registrar Producto");
        this.setSize(300, 350);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
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
        
        this.add(nombrePtf);
        nombrePtf.setBounds(130, 70, 150, 25);
        this.add(descripcionPtf);
        descripcionPtf.setBounds(130, 110, 150, 25);
        this.add(unidadPtf);
        unidadPtf.setBounds(130, 150, 150, 25);
        this.add(cantidadPtf);
        cantidadPtf.setBounds(130, 190, 150, 25);
        this.add(precioCompraPtf);
        precioCompraPtf.setBounds(130, 230, 150, 25);
        
        this.add(guardar);
        guardar.setBounds(150, 270, 100, 30);
        
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(validaciones() == true){
                    guardarProducto(crearProducto());
                }
            }
        });
        
        this.add(limpiar);
        limpiar.setBounds(20, 270, 80, 25);
        
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                limpiar();
            }
        });
        
        this.setVisible(true);
    }
    public boolean validaciones(){
        boolean flag;
        if(nombrePtf.getText().equals("") || descripcionPtf.getText().equals("")
                || unidadPtf.getText().equals("") || cantidadPtf.getText().equals("") 
                || precioCompraPtf.getText().equals("")){
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
    public Producto crearProducto(){
        String nombre = nombrePtf.getText();
        String descripcion = descripcionPtf.getText();
        String unidad = unidadPtf.getText();
        float cantidad = Float.parseFloat(cantidadPtf.getText());
        float precioCompra = Float.parseFloat(precioCompraPtf.getText());
        Producto prod = new Producto(nombre, descripcion, unidad, cantidad, precioCompra);
        return prod;
    }
    public void guardarProducto(Producto prod){
        String sql = "INSERT INTO `productos` (`idProducto`, `nombreProducto`, `descripcionProducto`, `unidadProducto`, `cantidadProducto`, `precioCompraProducto`) VALUES (NULL, '"+prod.getNombreProducto()+"', '"+prod.getDescripcionProducto()+"', '"+prod.getUnidadProducto()+"', '"+prod.getCantidadProducto()+"', '"+prod.getPrecioCompraProducto()+"');";
        
        Conexion conn = new Conexion();
        boolean flag = conn.ejecutar(sql);        
        conn.cerrar();
        if(flag == true){
            JOptionPane.showMessageDialog(null, "PRODUCTO GUARDADO");
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR");
        }
    }
    public void limpiar(){
        nombrePtf.setText("");
        descripcionPtf.setText("");
        unidadPtf.setText("");
        cantidadPtf.setText("");
        precioCompraPtf.setText("");
    }
}
