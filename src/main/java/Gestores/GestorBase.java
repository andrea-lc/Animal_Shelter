    /*
    * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
    * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
    */
    package Gestores;

    import Entidades.Adoptantes;
    import Entidades.Gatos;
    import Entidades.Voluntarios;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;


    /**
     *
     * @author admin
     * @param <T>
     */
    public abstract class GestorBase <T> implements GestorInterface<T> {
        protected Map<String, T> elementos = new HashMap<>();
        //mejora :)   : antes tenia una List asi como hashmap que provoca duplicado tatno en el hashmap como en la list
        // el list se creaba con el proposito de mostrar en consola las lista ya ordenadas, sin embargo puede llegar a ser ineficiente
        // por que se queda en memoria siempre, ademas que de esta forma, el list empezaba vacio, por eso tenia que utilizar add
        // para llenarla yo misma, igual cuando hacia operaciones como agrgear y eliminar
        
        // protected List<T> elementos_lista= ;
        protected final String rutaArchivo;
        
        public GestorBase(String rutaArchivo) {
            this.rutaArchivo = rutaArchivo;
            cargarDatos();
        }
        
        public Map<String, T> getElementos() {
            return elementos;
        }
        
        
        // ahora pueodo modificar este metodo, y desde aqui se llena a la par con los elementos del hashmap, 
        // y sera como algo temporal la lista, a comparacion como estaba antes 24/7, solo se usara cuando llame este metodo
        // pero ocurre otro error, como en mi codigo ordeno y lo muestro, al llamar indepdendientemente, se ordena y al volver a llamarlo
        // aparece nuevamente la lista en su estdo original, entonces lo mejor sera ordenarlo desde aqui 
        
        //ORDEN POR NOMBRE
        public List<T> getElementos_listaporNombre() {
            List<T>ordenporNombre =new ArrayList<>(elementos.values());
            // no se puede usar el parametro T desde el if else porque debe ser una variable inicializada
            // entonces creo una variable con el primero elemento de la lista (puede ser cualquiera)
            // para confirmar su tipo 
            T tipo = ordenporNombre.get(0);
            
        
            if (tipo instanceof Adoptantes){
                ordenporNombre.sort((n1, n2) -> 
                        ((Adoptantes)n1).getNombre().compareTo(((Adoptantes)n2).getNombre()));
            } else if (tipo instanceof Voluntarios){
                ordenporNombre.sort((n1, n2) -> 
                        ((Voluntarios)n1).getNombre().compareTo(((Voluntarios)n2).getNombre()));
            }
            return ordenporNombre;
        }
        // ORDEN POR Id
            public List<T> getElementos_listaporId() {
            List<T>ordenporId =new ArrayList<>(elementos.values());
            T tipo = ordenporId.get(0);      
        
            if (tipo instanceof Gatos){
                ordenporId.sort((id1, id2) -> 
                        Integer.compare(((Gatos)id1).getId(),(((Gatos)id2).getId())));
            }
            return ordenporId;
        }
        
        //lo que hace este metodo es que no solo hace la busqueda por la clave del hashmap que es el id de las entidades
        // si no busca tambien por otro forma que es el nombre 
            //
        @Override
        public  T retornarElemento(String identificador){
            T objeto = null;
            if(getElementos().containsKey(identificador)){ //busca por id o dni :0
                objeto=getElementos().get(identificador);
            }else {
                for (T o : getElementos().values()) { // busca por nombre 
                String nombre = null;
                //para verificar si objeto es una instancia de Gatos/Adoptantes/Voluntarios 
                // (como examinar de que tipo sera objeto)
                    if (o instanceof Gatos){ 
                        nombre= (((Gatos) o).getNombre()); // aqui hace cast, osea convertir un objeto a otro
                    } else if (o instanceof Adoptantes ){
                        nombre= ((Adoptantes) o).getNombre();
                    } else if (o instanceof Voluntarios){
                        nombre=((Voluntarios) o).getNombre();
                    }          
                    if (identificador.equalsIgnoreCase(nombre)){
                        objeto=o;
                    }
                }       
            }
            return objeto;
        }  
    }
