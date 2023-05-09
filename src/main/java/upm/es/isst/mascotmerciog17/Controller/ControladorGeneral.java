package upm.es.isst.mascotmerciog17.Controller;

import java.util.List;

import javax.validation.Valid;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import upm.es.isst.mascotmerciog17.Repository.UsuarioRepository;
import upm.es.isst.mascotmerciog17.Repository.EstablecimientoRepository;
import upm.es.isst.mascotmerciog17.Repository.ValoracionRepository;
import upm.es.isst.mascotmerciog17.models.Usuario;
import upm.es.isst.mascotmerciog17.models.Establecimiento;
import upm.es.isst.mascotmerciog17.models.Valoracion;


@Controller
public class ControladorGeneral {

    private final UsuarioRepository usuarioRepository;
    private final EstablecimientoRepository establecimientoRepository;
    private final ValoracionRepository valoracionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public ControladorGeneral(UsuarioRepository usuarioRepository,
                                 EstablecimientoRepository establecimientoRepository,
                                 ValoracionRepository valoracionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.establecimientoRepository = establecimientoRepository;
        this.valoracionRepository = valoracionRepository;
    }
    
    //PAGINA DE INICIO

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String inicio() {
        return "inicio.html";
    }

    //VOLVER A LA PAG DE INICIO
    @RequestMapping("/inicio")
    public String volverInicio() {
        return "redirect:/";
    }
   


    //LOGIN
    @RequestMapping("/login")
    public String login() {
      return "login.html";
    }

    // REGISTRO

    @RequestMapping(value="/registro", method=RequestMethod.GET)    
    public String registro() {
        return "registro.html";
    }

    //AÑADIR UN NUEVO USUARIO CON ROL CLIENTE

    @RequestMapping(value="/nuevoCliente", method=RequestMethod.GET)
    public String nuevoCliente(ModelMap mp){
        mp.put("cliente", new Usuario());
        return "Cliente/registroCliente";
    }
    
    @RequestMapping(value="/anadirCliente", method=RequestMethod.POST)
    public String anadirCliente(@Valid Usuario cliente, BindingResult bindingResult, ModelMap mp) {
        String pass = cliente.getPassword();
        String passencript = passwordEncoder.encode(pass);
        if(bindingResult.hasErrors()){
            return "/registro";
        }else{
            cliente.setPassword(passencript);
            cliente.setRol("ROLE_CLIENTE");
            usuarioRepository.save(cliente);
            mp.put("cliente", cliente);
            return "/inicio";
        }
    }

    //AÑADIR UN NUEVO USUARIO CON ROL PROPIETARIO

    @RequestMapping(value="/nuevoPropietario", method=RequestMethod.GET)
    public String nuevoPropietario(ModelMap mp){
        mp.put("propietario", new Usuario());
        return "Propietario/registroPropietario";
    }
    
    @RequestMapping(value="/anadirPropietario", method=RequestMethod.POST)
    public String anadirPropietario(@Valid Usuario propietario, BindingResult bindingResult, ModelMap mp){
        String pass = propietario.getPassword();
        String passencript = passwordEncoder.encode(pass);
        if(bindingResult.hasErrors()){
            return "/registro";
        }else{
            propietario.setPassword(passencript);
            propietario.setRol("ROLE_PROPIETARIO");
            usuarioRepository.save(propietario);
            mp.put("propietario", propietario);
            return "/inicio";
        }
    }

    //MOSTRAR VISTA PRINCIPAL USUARIO ROL CLIENTE

    @RequestMapping(value="/cliente", method=RequestMethod.GET)    
    public String cliente(ModelMap mp) {
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        mp.put("establecimientos", establecimientos);
        return "Cliente/paginaPrincipal.html";
    }

    //MOSTRAR LOS ESTABLECIMIENTOS POR LA CALLE INTRODUCIDA
    @RequestMapping(value="/cliente/resultadoBuscar", method = RequestMethod.POST)
    public String ClienteBuscarPorCalleONombre(ModelMap mp, @RequestParam("calleClave") String calleClave){
        List<Establecimiento> establecimientos = establecimientoRepository.findByNombreDireccion(calleClave);
        mp.put("establecimientos", establecimientos);
        return "Cliente/resultadoBuscar.html";
    }

    @RequestMapping(value="/propietario/resultadoBuscar", method = RequestMethod.POST)
    public String PropietarioBuscarPorCalleONombre(ModelMap mp, @RequestParam("calleClave") String calleClave){
        List<Establecimiento> establecimientos = establecimientoRepository.findByNombreDireccion(calleClave);
        mp.put("establecimientos", establecimientos);
        return "Propietario/resultadoBuscar.html";
    }

    
    //MOSTRAR LOS ESTABLECIMIENTOS POR LA CALLE INTRODUCIDA
    @RequestMapping(value="/cliente/resultadoSelect", method = RequestMethod.POST)
    public String ClienteBuscarPorServicios(ModelMap mp, @RequestParam("servicioClave") String servicioClave){
        List<Establecimiento> establecimientos = establecimientoRepository.findByServicios(servicioClave);
        mp.put("establecimientos", establecimientos);
        return "Cliente/resultadoBuscar.html";
    }

    @RequestMapping(value="/propietario/resultadoSelect", method = RequestMethod.POST)
    public String PropietarioBuscarPorServicios(ModelMap mp, @RequestParam("servicioClave") String servicioClave){
        List<Establecimiento> establecimientos = establecimientoRepository.findByServicios(servicioClave);
        mp.put("establecimientos", establecimientos);
        return "Propietario/resultadoBuscar.html";
    }

        
    
    //MOSTRAR VISTA PRINCIPAL USUARIO ROL PROPIETARIO

    @RequestMapping(value="/propietario", method=RequestMethod.GET)    
    public String propietario(ModelMap mp) {
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        mp.addAttribute("establecimientos", establecimientos);
        return "Propietario/paginaPrincipal.html";
    }

    //LOGIN CLIENTES

    // @RequestMapping(value="/loginCliente", method=RequestMethod.GET)
    // public String login(ModelMap mp) {
    //     mp.addAttribute("cliente", new Cliente());
    //     return "Cliente/loginCliente.html";
    // }


    
    // @RequestMapping(value="/loginClientePOST", method=RequestMethod.POST)
    // public String login(Cliente cliente, ModelMap mp) {
    // Optional<Cliente> clienteBuscado = clienteRepository.findById(cliente.getId());
    // if(clienteBuscado.isPresent() && clienteBuscado.get().getContraseña().equals(cliente.getContraseña())) {
    //     mp.addAttribute("error", "Contraseña incorrecta");
    //     return "Cliente/loginCliente.html";
    // } else if(clienteBuscado.isPresent()){
    //     mp.addAttribute("cliente", clienteBuscado.get());
    //     return "Cliente/paginaPrincipal.html";
    // } else {
    //     mp.addAttribute("error", "Cliente no encontrado");
    //     return "Cliente/loginCliente.html";
    // }
    // }
    

    //  IDEA EN DESARROLLO PARA RECUPERAR CONTRASEÑA [EVA]

    // @RequestMapping(value="/recuperar-contrasena", method=RequestMethod.GET)
    // public String recuperarContrasena(ModelMap model) {
    // model.addAttribute("cliente", new Cliente());
    // return "recuperar-contrasena.html";
    // }

    // @RequestMapping(value="/recuperar-contrasena", method=RequestMethod.POST)
    // public String enviarCorreoRecuperacion(@RequestParam("email") String email, ModelMap model) {
    // // Aquí agregarías el código para enviar un correo electrónico al usuario con un enlace para restablecer su contraseña
    // model.addAttribute("mensaje", "Se ha enviado un correo electrónico con las instrucciones para restablecer tu contraseña");
    // return "recuperar-contrasena.html";
    // }
    


    //ACCIONES AÑADIR ESTABLECIMIENTOS

    @RequestMapping(value="/propietario/nuevoEstablecimiento", method=RequestMethod.GET)
    public String nuevoEstablecimiento(ModelMap mp){
        mp.put("establecimiento", new Establecimiento());
        return "Propietario/añadirEstablecimiento";
    }
    
    @RequestMapping(value="/propietario/anadirEstablecimiento", method=RequestMethod.POST)
    public String anadirEstablecimiento(@Valid Establecimiento establecimiento, BindingResult bindingResult, ModelMap mp, @RequestParam("file") MultipartFile imagen) throws JSONException {
        if (bindingResult.hasErrors()) {
            return "/propietario/nuevoEstablecimiento";
        } else {
            try {
                // Obtener latitud y longitud de la dirección usando OpenStreetMap API
                String address = establecimiento.getDireccion();
                String url = "https://nominatim.openstreetmap.org/search?q=" + address.replaceAll("\\s+","+") + "&format=json&limit=1";
                URL obj = new URL(url);
                Scanner scan = new Scanner(obj.openStream());
                String response = new String();
                while (scan.hasNext())
                    response += scan.nextLine();
                scan.close();
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = Double.parseDouble(jsonObject.getString("lat"));
                double lon = Double.parseDouble(jsonObject.getString("lon"));
                establecimiento.setLatitud(lat);
                establecimiento.setLongitud(lon);
                
                if(!imagen.isEmpty()) {
                    Path directorioImagenes = Paths.get("src//main//resources//static/imagenes");

                    String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
                    
                    try{
                        byte[] bytesImg = imagen.getBytes();
                        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                        Files.write(rutaCompleta, bytesImg);

                        establecimiento.setImagen(imagen.getOriginalFilename());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Guardar el establecimiento en la base de datos
                establecimientoRepository.save(establecimiento);
                mp.put("establecimiento", establecimiento);
                return "redirect:/propietario";
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }
    }

    //MOSTRAR LA VISTA DE UN ESTABLECIMIENTO

    @RequestMapping(value="/cliente/establecimientos/{id}", method=RequestMethod.GET)
    public String establecimientoCliente(@PathVariable Integer id, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);

        Integer establecimientoId = establecimiento.getId();
        List<Valoracion> valoraciones = valoracionRepository.findByEstablecimientoId(establecimientoId);

        mp.put("valoraciones", valoraciones);
        mp.put("establecimiento", establecimiento);
        return "Cliente/vistaEstablecimiento.html";
    }

    @RequestMapping(value="/propietario/establecimientos/{id}", method=RequestMethod.GET)
    public String establecimientoPropietario(@PathVariable Integer id, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);

        Integer establecimientoId = establecimiento.getId();
        List<Valoracion> valoraciones = valoracionRepository.findByEstablecimientoId(establecimientoId);

        mp.put("valoraciones", valoraciones);
        mp.put("establecimiento", establecimiento);
        return "Propietario/vistaEstablecimiento.html";
    }

    //MODIFICAR DATOS ESTABLECIMIENTO /propietario/establecimientoS/{id}/modificarEstablecimiento

    @RequestMapping(value="/propietario/establecimientos/{id}/modificar", method = RequestMethod.GET)
    public String modificar(@PathVariable Integer id, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);

        mp.put("establecimiento", establecimiento);
        return "Propietario/modificarEstablecimiento.html";
    }
    
    @RequestMapping(value="/propietario/establecimientos/{id}/modificarEstablecimiento", method = RequestMethod.POST)
    public String modificarEstablecimiento(@PathVariable Integer id, @ModelAttribute Establecimiento establecimiento, BindingResult bindingResult, ModelMap mp, @RequestParam(value = "file", required = false) MultipartFile imagen) throws JSONException {
        if (bindingResult.hasErrors()) {
            return "Propietario/modificarEstablecimiento.html";
        } else {
            Establecimiento establecimientoExistente = establecimientoRepository.findById(id).orElse(null);
            establecimientoExistente.setNombre(establecimiento.getNombre());
            establecimientoExistente.setDireccion(establecimiento.getDireccion());

            try {
                // Obtener latitud y longitud de la dirección usando OpenStreetMap API
                String address = establecimientoExistente.getDireccion();
                String url = "https://nominatim.openstreetmap.org/search?q=" + address.replaceAll("\\s+","+") + "&format=json&limit=1";
                URL obj = new URL(url);
                Scanner scan = new Scanner(obj.openStream());
                String response = new String();
                while (scan.hasNext())
                    response += scan.nextLine();
                scan.close();
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                double lat = Double.parseDouble(jsonObject.getString("lat"));
                double lon = Double.parseDouble(jsonObject.getString("lon"));
                establecimientoExistente.setLatitud(lat);
                establecimientoExistente.setLongitud(lon);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }

            establecimientoExistente.setHorario(establecimiento.getHorario());
            establecimientoExistente.setServicios(establecimiento.getServicios());
            establecimientoExistente.setDescripcion(establecimiento.getDescripcion());

            establecimientoRepository.save(establecimientoExistente);
            mp.put("establecimiento", establecimientoExistente);
            return "redirect:/propietario/establecimientos/{id}";
        }
    }

    // ELIMINAR ESTABLECIMIENTO


    @RequestMapping(value="/propietario/establecimientos/{id}/eliminar", method=RequestMethod.GET)
    public String eliminarEstablecimiento(@PathVariable Integer id, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);
        List<Valoracion> valoraciones = valoracionRepository.findByEstablecimientoId(id);
        
        for (Valoracion valoracion : valoraciones) {
            valoracionRepository.delete(valoracion);
        }

        establecimientoRepository.delete(establecimiento);
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        mp.addAttribute("establecimientos", establecimientos);
        return "redirect:/propietario";
    }
    

    //VOLVER ATRAS EN LA VISTA DE UN ESTABLECIMIENTO A ESTABLECIMIENTOS EN VISTA PROPIETARIO
    @RequestMapping(value="/atrasPropietario", method=RequestMethod.GET)
    public String atrasPropietario() {
        return "redirect:/propietario";
    }

     //VOLVER ATRAS EN LA VISTA DE UN ESTABLECIMIENTO A ESTABLECIMIENTOS EN VISTA CLIENTE
     @RequestMapping(value="/atrasCliente", method=RequestMethod.GET)
     public String atrasCliente() {
         return "redirect:/cliente";
     }
 

    //AÑADIR VALORACIONES

    @RequestMapping(value="/cliente/establecimientos/{id}/nuevaValoracion", method = RequestMethod.GET)
    public String nuevaValoracion(@PathVariable Integer id, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);
        Valoracion valoracion = new Valoracion();

        mp.put("valoracion", valoracion);
        mp.put("establecimiento", establecimiento);
        return "Cliente/añadirValoracion.html";
    }
    
    @RequestMapping(value="/cliente/establecimientos/{id}/anadirValoracion", method = RequestMethod.POST)
    public String anadirValoracion(@PathVariable Integer id,@RequestParam String comentario, @RequestParam Integer calificacion, @RequestParam String nombreCliente, ModelMap mp) {
        Establecimiento establecimiento = establecimientoRepository.findById(id).orElse(null);
        Valoracion valoracion = new Valoracion();
        valoracion.setEstablecimientoId(establecimiento.getId());
        valoracion.setComentario(comentario);
        valoracion.setCalificacion(calificacion);
        valoracion.setNombreCliente(nombreCliente);
        valoracionRepository.save(valoracion);
        return "redirect:/cliente/establecimientos/{id}";
    }

}
