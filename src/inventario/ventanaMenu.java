package inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author edson
 */
public class ventanaMenu extends JFrame{
    
    JButton productos = new JButton("Lista de Productos");
    JButton registrarProducto = new JButton("Registrar producto");
    JButton venderProducto = new JButton("Vender");
    JButton reportes = new JButton("Reportes");
    
    public ventanaMenu(){
        super("INVENTARIO");
        this.setSize(300, 250);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.add(productos);
        productos.setBounds(30, 30, 150, 40);        
        productos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ListaProductos list = new ListaProductos();
            }
        });
        
        this.add(registrarProducto);
        registrarProducto.setBounds(30, 70, 150, 40);
        registrarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                RegistrarProducto vent = new RegistrarProducto();
            }
        });
        
        this.add(venderProducto);
        venderProducto.setBounds(30, 110, 150, 40);
        venderProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                VenderProducto vend = new VenderProducto();
            }
        });
        
        this.add(reportes);
        reportes.setBounds(30, 150, 150, 40);
        reportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reporteVentas rep = new reporteVentas();
            }
        });
        
        this.setVisible(true);
    }
}
