package com.ejemplo.vaadin;

import com.ejemplo.vaadin.admusuarios.UsuarioForm;
import com.ejemplo.vaadin.entidades.Usuario;
import com.ejemplo.vaadin.servicios.ServicioUsuarios;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author alex aa
 * @version
 */
public class MyApplication extends Application implements ClickListener {
	private static final long serialVersionUID = 1539466153154709086L;
	UsuarioForm formUser = null;
	Button btnReset = null;
    Button btnInsertar = null;
	
	@Autowired
    ServicioUsuarios servicioUsuarios;

    @Override
    public void init() {
        Window mainWindow = new Window("Aplicacion");
        Label label = new Label("<h2>Ejemplo de Vaadin, Hibernate y Spring MVC/REST</h2>", Label.CONTENT_XHTML);
        mainWindow.addComponent(label);
        
        formUser = new UsuarioForm();
        
        btnReset = formUser.getBtnReset();
        btnInsertar = formUser.getBtnInsertar();
        //Asociamos el listener a los botones
        btnReset.addListener(this);
        btnInsertar.addListener(this);
        
        mainWindow.addComponent(formUser);
        setMainWindow(mainWindow);
        
    }
    
    

    /**
     * Metodo que retorna el objeto injectado para que otras clases de la
     * aplicacion puedan usarlo
     *
     * @return El objeto ServicioUsuario injectado
     */
    public ServicioUsuarios getServicioUsuarios() {
        return servicioUsuarios;
    }



	@Override
	public void buttonClick(ClickEvent event) {
		formUser.getWindow().showNotification("Boton pulsado");
		
		if(event.getSource() == btnInsertar && formUser.isValid()){
			Usuario usuario = formUser.formularioEntidad();
			Integer respuesta = getServicioUsuarios().guardarUsuario(usuario);
			switch(respuesta.intValue()){
			case 0:
				formUser.getWindow().showNotification("Se ha guardado el usuario");
				break;
			case 1:	
				formUser.getWindow().showNotification("Usuario no existente");
				break;
			case 2:
				formUser.getWindow().showNotification("No dispone de permisos");
				break;
			case 4:
				formUser.getWindow().showNotification("El usuario ya existe para este correo");
				break;
			default:
				formUser.getWindow().showNotification("Error al guardar el usuario");
				break;
			}
		
		}
		else if(event.getSource() == btnReset){
			formUser.getTxtNombre().setValue("");
			formUser.getTxtApellidos().setValue("");
			formUser.getTxtCorreo().setValue("");
			formUser.getTxtClave().setValue("");
		}
		else{
			formUser.getWindow().showNotification("Rellene todos los datos del usuario");
		}
		
	}
}
