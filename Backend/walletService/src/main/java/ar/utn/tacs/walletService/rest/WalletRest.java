package ar.utn.tacs.walletService.rest;

import javax.ws.rs.core.Response;

import ar.utn.tacs.rest.GenericRest;
import ar.utn.tacs.user.User;


public interface WalletRest extends GenericRest{
	
	public static final String base= "/walletService"; 
	
	public static final String getUserById= "/getUserById/{userId}";
	public static final String validateNickAndPass= "/validateNickAndPass/{nick}/{pass}";
	
	/**
	 * Retorna un usuario por su id
	 * 
	 * @param idUsuario
	 * @return {@link User}
	 */
	public Response getUserById(int userId);
	
	
	/**
	 * Valida la existencia de un usuario con ese nick y esa password
	 * 
	 * @param nick
	 * @param pass
	 * @return {@link User}
	 */
	public Response ValidateNickAndPass(String nick, String pass);
	
	/**
	 * Crea un nuevo usuario con nick y pass
	 * 
	 * @param nick
	 * @param pass
	 * @return {@link User}
	 */
	public Response newUser(String nick, String pass);
}