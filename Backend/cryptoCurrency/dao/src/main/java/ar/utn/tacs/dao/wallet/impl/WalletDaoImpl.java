package ar.utn.tacs.dao.wallet.impl;

import java.util.HashMap;
import java.util.List;

import ar.utn.tacs.dao.impl.GenericAbstractDaoImpl;
import ar.utn.tacs.dao.wallet.WalletDao;
import ar.utn.tacs.model.transaction.Transaction;
import ar.utn.tacs.model.wallet.Wallet;

public class WalletDaoImpl extends GenericAbstractDaoImpl<Wallet> implements WalletDao{

	@Override
	public Transaction buy(HashMap<String, String> resultMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction sale(HashMap<String, String> resultMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> userTransactionHistory(String token, String coinSymbol) {
		// TODO Auto-generated method stub
		return null;
	}
}
