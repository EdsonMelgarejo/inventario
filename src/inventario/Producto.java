package inventario;

/**
 *
 * @author edson
 */
public class Producto{
    private int idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private String unidadProducto;
    private float cantidadProducto;
    private float precioCompraProducto;
    private float precioVentaProducto;
    
    public Producto(String nombre, String descripcion, String unidad, float cantidad, float precioCompra){
        this.nombreProducto = nombre;
        this.descripcionProducto = descripcion;
        this.unidadProducto = unidad;
        this.cantidadProducto = cantidad;
        this.precioCompraProducto = precioCompra;
        this.precioVentaProducto = calcularPrecioVenta(precioCompra);
    }
    public Producto(int id, String nombre, String descripcion, String unidad, float cantidad, float precioCompra){
        this.idProducto = id;
        this.nombreProducto = nombre;
        this.descripcionProducto = descripcion;
        this.unidadProducto = unidad;
        this.cantidadProducto = cantidad;
        this.precioCompraProducto = precioCompra;
        this.precioVentaProducto = calcularPrecioVenta(precioCompra);
    }
    private float calcularPrecioVenta(float precioCompra){
        float precioVenta = precioCompra * 2;
        return precioVenta;
    }
    
    //GET-----------------------------------------------------------------------

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public String getUnidadProducto() {
        return unidadProducto;
    }

    public float getCantidadProducto() {
        return cantidadProducto;
    }

    public float getPrecioCompraProducto() {
        return precioCompraProducto;
    }

    public float getPrecioVentaProducto() {
        return precioVentaProducto;
    }
    
    //SET-----------------------------------------------------------------------

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public void setUnidadProducto(String unidadProducto) {
        this.unidadProducto = unidadProducto;
    }

    public void setCantidadProducto(float cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public void setPrecioCompraProducto(float precioCompraProducto) {
        this.precioCompraProducto = precioCompraProducto;
    }

    public void setPrecioVentaProducto(float precioVentaProducto) {
        this.precioVentaProducto = precioVentaProducto;
    }
    
}
