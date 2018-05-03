package ar.utn.tacs.model.operation;

import java.math.BigDecimal;

import ar.utn.tacs.model.wallet.CoinAmount;
import ar.utn.tacs.model.wallet.Wallet;

public class Buy extends Operation{

	private static final Long ID = 1l;
	private static final String DESCRIPTION = "Compra";
	
	public Buy() {
		super(ID, DESCRIPTION);
	}

	@Override
	public void doOperation() {
	
		Wallet userWallet = super.user.getWallet();
		
		BigDecimal finalPrice = super.coinAmount.getAmount().multiply(super.coinAmount.getCoin().getValueInDollars());
		if (!userWallet.haveEnoughDolar(finalPrice)) {
			//new SosUnPobreException()
			return;
		}
		
		BigDecimal finalDolarAmount = userWallet.getDolarAmount().subtract(finalPrice);
		userWallet.setDolarAmount(finalDolarAmount);
		
		CoinAmount newCoin = userWallet.getCoinAmountByCoin(super.coinAmount.getCoin());
		if (newCoin == null) {
			newCoin = new CoinAmount(super.coinAmount.getCoin(), super.coinAmount.getAmount());
			userWallet.getCoinAmaunts().add(newCoin);
		}
		BigDecimal finalCoinAmount = newCoin.getAmount().add(super.coinAmount.getAmount());
		newCoin.setAmount(finalCoinAmount);
	}

}
