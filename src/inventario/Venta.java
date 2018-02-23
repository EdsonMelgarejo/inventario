package inventario;

import java.util.ArrayList;

/**
 *
 * @author edson
 */
public class Venta {
    
    private int idVenta;
    private String fechaVenta;
    private ArrayList<Producto> listaProdV = new ArrayList<>();
    private float totalVenta;
    private float totalIVA;

    public Venta(int idVenta, String fechaVenta, float totalVenta, float IVAventa) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.totalIVA = IVAventa;
    }

    public Venta(String fechaVenta, float totalVenta, float IVAventa) {
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.totalIVA = IVAventa;
    }
    //GET-----------------------------------------------------------------------
    public int getIdVenta() {
        return idVenta;
    }
    public String getFechaVenta() {
        return fechaVenta;
    }
    public ArrayList<Producto> getListaProdV() {
        return listaProdV;
    }
    public float getTotalVenta() {
        return totalVenta;
    }
    public float getIVA() {
        return totalIVA;
    }
    public float getTotalIVA() {
        return totalIVA;
    }
    
    //SET-----------------------------------------------------------------------
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setTotalIVA(float totalIVA) {
        this.totalIVA = totalIVA;
    }
    
    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }
    public void setIVA(float IVA) {
        this.totalIVA = IVA;
    }
    public void setListaProductos(ArrayList<Producto> lista){
        this.listaProdV = lista;
    }
}
