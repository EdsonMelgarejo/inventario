package inventario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author edson
 */
public class reporteVentas extends JFrame{
    
    JComboBox diaCB = new JComboBox();
    JComboBox mesCB = new JComboBox();
    JComboBox añoCB = new JComboBox();
    
    JComboBox diaCB2 = new JComboBox();
    JComboBox mesCB2 = new JComboBox();
    JComboBox añoCB2 = new JComboBox();
    
    JButton filtrar = new JButton("Filtar");
    
    private float total = 0;
    private float totalIVA = 0;
    
    private float ganancia = 0;
    private float ivaTrasladado = 0;
    
    JLabel lb1 = new JLabel("Id");
    JLabel lb2 = new JLabel("Fecha");
    JLabel lb3 = new JLabel("Total");
    JLabel lb4 = new JLabel("Total + IVA");
    
    JLabel lb5 = new JLabel("Total:");
    JLabel lb6 = new JLabel("Total + IVA:");
    
    JLabel lb7 = new JLabel("Fecha Inicial");
    JLabel lb8 = new JLabel("Fecha Final");
    
    JTextField tf1 = new JTextField();
    JTextField tf2 = new JTextField();
    
    JLabel lb9 = new JLabel("Ganancia");
    JLabel lb10 = new JLabel("IVA Trasladado");
    
    JTextField tf3= new JTextField();
    JTextField tf4 = new JTextField();
    
    DefaultListModel<String> listaV = new DefaultListModel<>();
    JList listaVentas = new JList();
    
    ArrayList<Venta> ventas = new ArrayList<>();    
    
    public reporteVentas() {
        super("Reporte de ventas");
        this.setSize(510, 450);//TAMAÑO DE VENTANA
        this.setLocationRelativeTo(null);//CENTRAR A LA PANTALLA
        this.setResizable(false);//NO CAMBIE DE TAMAÑO LA VENTANA
        this.setLayout(null);//VENTANA SIN FORMATO
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.add(lb7);
        lb7.setBounds(30, 10, 100, 20);
        
        this.add(lb8);
        lb8.setBounds(30, 55, 100, 20);
                
        this.add(diaCB);
        diaCB.setBounds(30, 30, 50, 20);
        
        this.add(mesCB);
        mesCB.setBounds(90, 30, 100, 20);
        
        this.add(añoCB);
        añoCB.setBounds(200, 30, 70, 20);
        
        this.add(diaCB2);
        diaCB2.setBounds(30, 75, 50, 20);
        
        this.add(mesCB2);
        mesCB2.setBounds(90, 75, 100, 20);
        
        this.add(añoCB2);
        añoCB2.setBounds(200, 75, 70, 20);
        
        this.add(filtrar);
        filtrar.setBounds(100, 110, 100, 25);
        
        this.add(lb1);
        lb1.setBounds(30, 140, 30, 25);
        
        this.add(lb2);
        lb2.setBounds(60, 140, 100, 25);
        
        this.add(lb3);
        lb3.setBounds(130, 140, 30, 25);
        
        this.add(lb4);
        lb4.setBounds(185, 140, 100, 25);
        
        this.add(lb5);
        lb5.setBounds(95, 360, 50, 25);
        
        this.add(tf1);
        tf1.setBounds(135, 360, 50, 25);
        tf1.setEditable(false);
        
        this.add(lb6);
        lb6.setBounds(120, 385, 100, 25);
        
        this.add(tf2);
        tf2.setBounds(185, 385, 50, 25);
        tf2.setEditable(false);
        
        this.add(listaVentas);
        listaVentas.setBounds(30, 160, 250, 200);
        
        this.add(lb9);
        lb9.setBounds(300, 160, 100, 25);
        this.add(tf3);
        tf3.setBounds(400, 160, 80, 25);
        tf3.setEditable(false);
        
        this.add(lb10);        
        lb10.setBounds(300, 200, 100, 25);
        this.add(tf4);
        tf4.setBounds(400, 200, 80, 25);
        tf4.setEditable(false);
        
        llenarCombos();
        llenarLista();
        
        filtrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                total = 0;
                totalIVA = 0;
                ganancia = 0;
                ivaTrasladado = 0;
                listaV.removeAllElements();
                filtrarVentas();
            }
        });
        
        this.setVisible(true);               
    }
    public void consultarVentas(){
        String sql = "SELECT * FROM `ventas` ORDER BY `ventas`.`fechaVenta` ASC";
        Conexion conn = new Conexion();
        ResultSet rs;
        rs = conn.consultar(sql);
        try {
            while(rs.next()){
                ventas.add(new Venta(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4)));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        conn.cerrar();
    }
    public void llenarLista(){
        consultarVentas();
        listaVentas.setModel(listaV);
        calcularTotales();
        for(int x = 0; x < ventas.size(); x++){
            listaV.addElement(ventas.get(x).getIdVenta()+ "  | " + ventas.get(x).getFechaVenta() + "  | $" + ventas.get(x).getTotalVenta() +  "  | $" + ventas.get(x).getTotalIVA());
            calcularGanancias(ventas.get(x).getIdVenta());
        } 
        tf3.setText(String.format("%.2f", ganancia));
        ivaTrasladado = ivaTrasladado + (totalIVA - total);
        tf4.setText(String.format("%.2f", ivaTrasladado));
    }
    private void calcularTotales(){
        for(int x = 0; x < ventas.size(); x++){
            total = total + ventas.get(x).getTotalVenta();
            totalIVA = totalIVA + ventas.get(x).getTotalIVA();
        }
        tf1.setText(String.valueOf(total));
        tf2.setText(String.format("%.2f", totalIVA));
    }
    private void llenarCombos(){
        //DÍA-------------------------------------------------------------------
        for(int x = 1; x <=31; x++){
            diaCB.addItem(x);
            diaCB2.addItem(x);
        }
        //MES-------------------------------------------------------------------
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        for(int x = 0; x < 12; x++){
            mesCB.addItem(meses[x]);
            mesCB2.addItem(meses[x]);
        }
        //AÑO-------------------------------------------------------------------
        for(int x = 2018; x <=2025; x++){
            añoCB.addItem(x);
            añoCB2.addItem(x);
        }        
    }
    private int convertirmeses(String mes){
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        int x = 0;
        int y = 0;
        while(x == 0){
            if(mes.equals(meses[y])){
                x = 1;
                y++;
            }
            else{
                x = 0;
                y++;
            }
        }
        return y;
    }
    private void filtrarVentas(){
        int año =  (int) añoCB.getSelectedItem();
        int mes = convertirmeses((String) mesCB.getSelectedItem());
        int dia = (int) diaCB.getSelectedItem();
        String fecha1 = String.valueOf(año) + "-" + String.valueOf(mes) + "-" + String.valueOf(dia);        
        Date fechaI = Date.valueOf(fecha1);        
                
        int año2 =  (int) añoCB2.getSelectedItem();
        int mes2 = convertirmeses((String) mesCB2.getSelectedItem());
        int dia2 = (int) diaCB2.getSelectedItem();
        String fecha2 = String.valueOf(año2) + "-" + String.valueOf(mes2) + "-" + String.valueOf(dia2);        
        Date fechaF = Date.valueOf(fecha2);                        
        
        if(fechaI.before(fechaF) || fechaI.equals(fechaF)){
            for(int x = 0; x < ventas.size(); x++){
                String fechaV = ventas.get(x).getFechaVenta();            
                Date fechaIn = Date.valueOf(fechaV);
                if(fechaIn.after(fechaI) && fechaIn.before(fechaF)){
                    listaV.addElement(ventas.get(x).getIdVenta()+ "  | " + ventas.get(x).getFechaVenta() + "  | $" + ventas.get(x).getTotalVenta() +  "  | $" + ventas.get(x).getTotalIVA());
                    total = total + ventas.get(x).getTotalVenta();
                    totalIVA = totalIVA + ventas.get(x).getTotalIVA();
                    calcularGanancias(ventas.get(x).getIdVenta());
                }
                if(fechaIn.equals(fechaI) || fechaIn.equals(fechaF)){
                    listaV.addElement(ventas.get(x).getIdVenta()+ "  | " + ventas.get(x).getFechaVenta() + "  | $" + ventas.get(x).getTotalVenta() +  "  | $" + ventas.get(x).getTotalIVA());                   
                    total = total + ventas.get(x).getTotalVenta();
                    totalIVA = totalIVA + ventas.get(x).getTotalIVA();
                    calcularGanancias(ventas.get(x).getIdVenta());
                }
            }
            tf1.setText(String.valueOf(total));
            tf2.setText(String.format("%.2f", totalIVA));
            tf3.setText(String.format("%.2f", ganancia));
            ivaTrasladado = ivaTrasladado + (totalIVA - total);
            tf4.setText(String.format("%.2f", ivaTrasladado));
        }
        else{
            JOptionPane.showMessageDialog(null, "La fecha inicial debe se menor o igual que la final");
        }
    }
    private void calcularGanancias(int id){        
        String nombreProd;        
        float cant;
        float precioC;
        String sql = "SELECT `nombreProducto`, `cantidadVenta` FROM `productosventa` WHERE `idVenta` = "+id;        
        Conexion conn = new Conexion(); 
        Conexion conn2 = new Conexion(); 
        ResultSet rs;        
        ResultSet rs2;
        try {
            rs = conn.consultar(sql);            
            while(rs.next()){                
                nombreProd = rs.getString(1);
                cant = rs.getFloat(2);                
                String sql2 = "SELECT `precioCompraProducto` FROM `productos` WHERE `nombreProducto` = '"+nombreProd+"'";
                rs2 = conn2.consultar(sql2);
                rs2.next();                
                precioC = rs2.getFloat(1);                
                ganancia = ganancia + (cant * precioC);                
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        conn.cerrar();     
        conn2.cerrar();
        ganancia = total - ganancia;
    }
}