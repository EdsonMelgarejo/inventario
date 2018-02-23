package inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author edson
 */
public class VenderProducto extends JFrame{
    
    JLabel buscarP = new JLabel("Buscar por nombre");
    JLabel fechaVenta = new JLabel();
    JLabel cantidadP = new JLabel("cantidad:");
    JLabel totalVenta = new JLabel("Total:");
    JLabel IVAVenta = new JLabel("+ I.V.A. (16%)");
    
    JTextField buscarPtf = new JTextField();
    JTextField cantidadPtf = new JTextField();
    JTextField totalV = new JTextField();
    JTextField totalIVAtf = new JTextField();
            
    JButton buscar = new JButton("Buscar");
    JButton agregar = new JButton("Agregar");
    JButton guardarVenta = new JButton("Vender");
    
    JComboBox<String> productos = new JComboBox<>();
    
    ArrayList<Producto> listaProductosOb = new ArrayList<>();
    ArrayList<Producto> listaVenta  = new ArrayList<>();
            
    DefaultListModel<String> listaP = new DefaultListModel<>();
    JList listaProductos = new JList();
    
    int contador = 0;
    
    float total = 0;
    float IVA = 0;
    float totalIVA = 0;
    String fechaI;
    
    public VenderProducto(){
        super("Venta");
        this.setSize(500, 400);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        
        this.add(fechaVenta);
        fechaVenta.setBounds(400, 20, 100, 25);
        Calendar fecha = Calendar.getInstance();
        int diaI = fecha.get(Calendar.DATE);
        int mesI = fecha.get(Calendar.MONTH) + 1;
        int añoI = fecha.get(Calendar.YEAR);
        fechaVenta.setText(String.valueOf(diaI) + "-" + String.valueOf(mesI) + "-" + String.valueOf(añoI));
        fechaI = (añoI+"-"+mesI+"-"+diaI);        
        
        this.add(buscarP);
        buscarP.setBounds(20, 50, 150, 25);
        
        this.add(buscarPtf);
        buscarPtf.setBounds(20, 75, 130, 25);
        
        this.add(buscar);
        buscar.setBounds(155, 75, 80, 25);
        
        this.add(productos);
        productos.setBounds(20, 150, 160, 25);
        
        this.add(cantidadP);
        cantidadP.setBounds(20, 190, 60, 25);
        
        this.add(cantidadPtf);
        cantidadPtf.setBounds(75, 190, 50, 25);
        
        this.add(agregar);
        agregar.setBounds(100, 230, 80, 30);
        
        this.add(listaProductos);
        listaProductos.setBounds(200, 150, 150, 200);
        
        this.add(totalVenta);
        totalVenta.setBounds(360, 150, 100, 25);
        
        this.add(totalV);
        totalV.setBounds(360, 180, 100, 25);
        totalV.setEditable(false);
        
        this.add(IVAVenta);
        IVAVenta.setBounds(360, 215, 100, 25);
        
        this.add(totalIVAtf);
        totalIVAtf.setBounds(360, 245, 100, 25);
        totalIVAtf.setEditable(false);
        
        this.add(guardarVenta);
        guardarVenta.setBounds(380, 320, 100, 30);
        guardarVenta.setEnabled(false);
        
        llenarCombo(false);
        
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String palabra = buscarPtf.getText();                
                productos.removeAllItems();
                listaProductosOb.removeAll(listaProductosOb);
                buscarProductos(palabra);
                llenarCombo(true);
            }
        });
        
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                float cant;
                try{
                    cant = Float.parseFloat(cantidadPtf.getText());                
                    validarCantidad(cant);
                }catch(java.lang.NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Ingresa una cantidad correcta");
                }                
            }
        });
        
        guardarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guardarVenta();
            }
        });
        
        this.setVisible(true);
    }
    private void buscarProductos(String palabra){
        String sql = "SELECT * FROM `productos` WHERE `nombreProducto` LIKE '" + palabra + "%'";
        Conexion conn = new Conexion();
        ResultSet rs;
        rs = conn.consultar(sql);
        try {
            while(rs.next()){
                listaProductosOb.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getFloat(6)));                
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        conn.cerrar();
    }
    private void obtenerProductos(){
        String sql = "SELECT * FROM `productos`";
        Conexion conn = new Conexion();
        ResultSet rs;
        rs = conn.consultar(sql);
        try {
            while(rs.next()){
                listaProductosOb.add(new Producto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getFloat(6)));                
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        conn.cerrar();
    }
    public final void llenarCombo(boolean flag){
        if(flag == false){
            obtenerProductos();
        }
        for(int x = 0; x < listaProductosOb.size(); x++){
            productos.addItem(listaProductosOb.get(x).getNombreProducto());
        }
    }
    public void validarCantidad(float cantidad){
        int selecCB = productos.getSelectedIndex();
        if(cantidad <= 0){
            JOptionPane.showMessageDialog(null, "NO SE PUEDE AGREGAR UNA CANTIDAD MENOR O IGUAL A CERO");
        }
        else{
            if(cantidad <= listaProductosOb.get(selecCB).getCantidadProducto()){
                agregarProducto(selecCB, cantidad);
                guardarVenta.setEnabled(true);
            }
            else{
                JOptionPane.showMessageDialog(null, "LA CANTIDAD EXEDE NUESTRA EXISTENCIA");
            }
        }
    }
    public void agregarProducto(int index, float cant){
        listaProductos.setModel(listaP);                
        listaP.addElement(listaProductosOb.get(index).getNombreProducto());
        listaVenta.add(listaProductosOb.get(index));                
        System.out.println(contador);
        listaVenta.get(contador).setCantidadProducto(cant);
        //System.err.println(listaVenta.get(contador).getNombreProducto() + " - " + listaVenta.get(contador).getCantidadProducto());
        calcularTotal();
        contador++;
    }
    public void calcularTotal(){
        total = total + listaVenta.get(contador).getCantidadProducto() * listaVenta.get(contador).getPrecioVentaProducto();
        totalV.setText(String.valueOf(total));
        totalIVA = (float) (total * 1.16);
        totalIVAtf.setText(String.valueOf(totalIVA));
    }
    public void guardarVenta(){
        int id = generarIdVenta();
        Venta venta = new Venta(id, fechaI, total, totalIVA);
        venta.setListaProductos(listaVenta);
        restarExistencia(listaVenta);
        //String sql = "INSERT INTO `ventas`(`idVenta`, `fechaVenta`, `totalVenta`, `totalIVA`) VALUES ("+venta.getIdVenta()+",'"+venta.getFechaVenta()+"',"+venta.getTotalVenta()+","+venta.getTotalIVA()+")";
        String sql = "INSERT INTO `ventas`(`idVenta`, `fechaVenta`, `totalVenta`, `totalIVA`) VALUES ("+venta.getIdVenta()+",'2019-02-05',"+venta.getTotalVenta()+","+venta.getTotalIVA()+")";
        Conexion conn =  new Conexion();        
        conn.ejecutar(sql);
        for(int x = 0; x < venta.getListaProdV().size(); x++){
            sql = "INSERT INTO `productosventa`(`idVenta`, `nombreProducto`, `cantidadVenta`) VALUES ("+venta.getIdVenta()+",'"+venta.getListaProdV().get(x).getNombreProducto()+"',"+venta.getListaProdV().get(x).getCantidadProducto()+")";
            conn.ejecutar(sql);
        }
        JOptionPane.showMessageDialog(null, "Venta guardada");
        this.dispose();
    }
    private int generarIdVenta(){
        int idAux;
        String sql = "SELECT MAX(idVenta) FROM `ventas`";
        Conexion conn = new Conexion();
        ResultSet rs;
        rs = conn.consultar(sql);
        try {
            rs.next();
            idAux = rs.getInt(1) + 1;
        } catch (SQLException ex) {
            Logger.getLogger(VenderProducto.class.getName()).log(Level.SEVERE, null, ex);
            idAux = 1;
        }
        return idAux;
    }
    private void restarExistencia(ArrayList<Producto> lista){
        String sql1;
        String sql2;
        float cantAux;
        float cant;
        int id;
        Conexion conn = new Conexion();
        ResultSet rs;
        for(int x = 0; x < lista.size(); x++){
            sql1 = "SELECT * FROM `productos` WHERE `idProducto` = " + String.valueOf(lista.get(x).getIdProducto());
            rs = conn.consultar(sql1);
            try {
                rs.next();
                id = rs.getInt(1);
                cantAux = rs.getFloat(5);
                cant = cantAux - lista.get(x).getCantidadProducto();
                sql2 = "UPDATE `productos` SET `cantidadProducto` = "+cant+" WHERE `idProducto` = "+ id;
                conn.ejecutar(sql2);
            } catch (SQLException ex) {
                Logger.getLogger(VenderProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
