package Service;
import Model.Usuario;
import Repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UsuarioService {
 private final UsuarioRepository usuarioRepository;
 @Autowired
 public UsuarioService(UsuarioRepository usuarioRepository) {
 this.usuarioRepository = usuarioRepository;
 initSampleData();
 }
 private void initSampleData() {
 Usuario camilo = new Usuario("Juan Camilo Castañeda Lopera", "juan@gmail.com", "3907654322", "JXANX", "CAMILO.REPRODUCTOR");
 Usuario sebas = new Usuario("Sebastian Betancourth Moreu", "sebas@gmail.com", "3456778990", "sebs", "SEBAS.REPRODUCTOR");
Usuario  felipe = new Usuario("Felipe Castaño Londoño", "felipe@gmail.com", "3226775434", "PIPE", "FELIPE.REPRODUCTOR");
Usuario  cesar = new Usuario("Cesar Andres Guzman", "cesar@gmail.com", "3145567788", "CESARIN", "CESAR.REPRODUCTOR");
 save(camilo);
 save(sebas);
 save(felipe);
 save(cesar);
 }
 public Usuario save(Usuario usuario) {
 return usuarioRepository.save(usuario);
 }
 public Usuario findById(String id) {
 return usuarioRepository.findById(id);
 }
 public List<Usuario> findAll() {
 return usuarioRepository.findAll();
 }
 public Usuario update(Usuario usuario) {
 return usuarioRepository.update(usuario);
 }
 public void deleteById(String id) {
 usuarioRepository.deleteById(id);
 }
 public List<Usuario> buscarPorFiltros(String usuario, String email) {
 return usuarioRepository.buscarPorFiltros(usuario, email);
 }
}
 
