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
public class Reabastecer extends JFrame{
    
    Producto producto;
    
    float p = 0;
    float c = 0;
    
    JLabel cant = new JLabel("Cantidad:");
    JLabel precio = new JLabel("Precio Compra:");
    
    JTextField cantTf = new JTextField();
    JTextField precioTf = new JTextField();
    
    JButton guardar = new JButton("Guardar");
    
    public Reabastecer(Producto p){
        super("Reabastecer Producto");
        this.setSize(300, 170);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.producto = p;
        
        this.add(cant);
        cant.setBounds(20, 20, 100, 25);
        this.add(precio);
        precio.setBounds(20, 60, 100, 25);
        this.add(cantTf);
        cantTf.setBounds(130, 20, 100, 25);
        this.add(precioTf);
        precioTf.setBounds(130, 60, 100, 25);
        
        this.add(guardar);
        guardar.setBounds(90, 100, 100, 30);
        
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(validar() == true){
                    actualizarProducto();
                }
            }
        });
        
        this.setVisible(true);        
    }
    private boolean validar(){
        boolean flag = false;
        if(cantTf.getText().equals("") || precioTf.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Debes completar los campos");
        }
        else{                        
            try{
                c = Float.parseFloat(cantTf.getText());
                p = Float.parseFloat(precioTf.getText());
                if(c <= 0 || p <= 0){
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
    private void actualizarProducto(){
        if(p == producto.getPrecioCompraProducto()){
            producto.setCantidadProducto(c + producto.getCantidadProducto());
            String sql = "UPDATE `productos` SET `cantidadProducto`= "+producto.getCantidadProducto()+" WHERE `productos`.`idProducto` = "+producto.getIdProducto();
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
        else{
            float nuevoP = (producto.getCantidadProducto() * producto.getPrecioCompraProducto()) + (p * c);
            producto.setPrecioCompraProducto(nuevoP / (c + producto.getCantidadProducto()));
            producto.setCantidadProducto(c + producto.getCantidadProducto());
            String sql = "UPDATE `productos` SET `cantidadProducto`= "+producto.getCantidadProducto()+",`precioCompraProducto`= "+producto.getPrecioCompraProducto()+" WHERE `productos`.`idProducto` = "+producto.getIdProducto();
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
    }
}
