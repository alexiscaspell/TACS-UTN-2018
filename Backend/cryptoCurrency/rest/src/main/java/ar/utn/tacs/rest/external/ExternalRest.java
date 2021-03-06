package ar.utn.tacs.rest.external;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonValue;

import ar.utn.tacs.rest.GenericRest;

public interface ExternalRest extends GenericRest {

	public static final String BASE = "/services/external";

	public static final String COIN_MARKET_CAP = "/coins";

	/**
	 * @return {@link JsonValue}
	 */
	Response coinMarketCap();
}
