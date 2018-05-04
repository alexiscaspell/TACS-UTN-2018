package ar.utn.tacs.rest.wallet.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import ar.utn.tacs.model.commons.DontHaveOperationCoinException;
import ar.utn.tacs.model.commons.InsufficientCryptoCurrencyException;
import ar.utn.tacs.model.commons.InsufficientMoneyException;
import ar.utn.tacs.model.transaction.Transaction;
import ar.utn.tacs.model.wallet.CoinAmountRest;
import ar.utn.tacs.model.wallet.Wallet;
import ar.utn.tacs.rest.wallet.WalletRest;
import ar.utn.tacs.service.wallet.WalletService;

@Path(WalletRest.BASE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WalletRestImpl implements WalletRest{
	
	@Autowired
	private WalletService walletService;
	
	@POST
	@Path(WalletRest.BUY)
	@Override
	public Response buy(@HeaderParam(value = "token")String token, CoinAmountRest coinAmountRest) {
		try {
			walletService.buy(token, coinAmountRest);
			return Response.status(Response.Status.CREATED).build();

		} catch (InsufficientMoneyException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.createBasicResponse("No posee Dolares suficientes")).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path(WalletRest.SALE)
	@Override
	public Response sale(@HeaderParam(value = "token")String token, CoinAmountRest coinAmountRest) {
		
		try {
			walletService.sale(token, coinAmountRest);
			return Response.status(Response.Status.CREATED).build();

		} catch (DontHaveOperationCoinException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.createBasicResponse("No posee la cripto moneda indicada")).build();
			
		} catch (InsufficientCryptoCurrencyException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.createBasicResponse("No posee monto suficiente de la cripto moneda indicada")).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path(WalletRestImpl.USER_TRANSACTION_HISTORY)
	@Override
	public Response userTransactionHistory(@HeaderParam(value = "token")String token, @PathParam("ticker")String ticker) {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			transactions = walletService.userTransactionHistory(token, ticker);
			return Response.status(Response.Status.OK).entity(transactions).build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path(WalletRestImpl.USER_WALLET)
	@Override
	public Response userWalletByToken(@HeaderParam(value = "token")String token) {
		
		try {
			Wallet wallet = walletService.userWalletByToken(token);
			return Response.status(Response.Status.OK).entity(wallet).build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
