package ar.utn.tacs.dao.admin.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.utn.tacs.dao.admin.AdminDao;
import ar.utn.tacs.dao.user.impl.UserDaoMockImpl;
import ar.utn.tacs.dao.wallet.impl.WalletDaoMockImpl;
import ar.utn.tacs.model.admin.Deposit;
import ar.utn.tacs.model.admin.StateDepositNumber;
import ar.utn.tacs.model.commons.ExistingDepositException;
import ar.utn.tacs.model.commons.NotExistDepositException;
import ar.utn.tacs.model.commons.RejectingApprovedDepositException;
import ar.utn.tacs.model.commons.RejectingRejectedDepositException;
import ar.utn.tacs.model.commons.ApprovingApprovedDepositException;
import ar.utn.tacs.model.transaction.Transaction;
import ar.utn.tacs.model.user.User;
import ar.utn.tacs.util.BeanUtil;

public class AdminDaoMockImpl implements AdminDao{
	
	private List<Deposit> listDeposit = new ArrayList<Deposit>();
	
	public List<Deposit> getListDeposit() {
		return listDeposit;
	}

	public void setListDeposit(List<Deposit> listDeposit) {
		this.listDeposit = listDeposit;
	}

	@Override
	public User compareBalance(String nickA, String nickB) {
		User userA = BeanUtil.getBean("userDao", UserDaoMockImpl.class).getUserByNick(nickA);
		User userB = BeanUtil.getBean("userDao", UserDaoMockImpl.class).getUserByNick(nickB);
		
		BigDecimal balanceA = userA.getWallet().getDolarFinalBalance();
		BigDecimal balanceB = userB.getWallet().getDolarFinalBalance();
		
		return balanceA.compareTo(balanceB) > 0? userA : userB;
	}

	@Override
	public BigInteger statesLastWeek() {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		BeanUtil.getBean("walletDao", WalletDaoMockImpl.class).getHistory().values().stream().forEach(t -> transactions.addAll(t));

		List<Transaction> transactionsFilter = transactions.stream().filter(t -> {
			
			Calendar transactionDate = Calendar.getInstance();
				transactionDate.setTime(t.getDateStart());
			
			Calendar minDate = Calendar.getInstance();
				minDate.setTime(new Date());
			
			return transactionDate.get(Calendar.WEEK_OF_MONTH) == minDate.get(Calendar.WEEK_OF_MONTH);
			
		}).collect(Collectors.toList());
		
		return BigInteger.valueOf(transactionsFilter.size());
	}

	@Override
	public BigInteger statesLastMonth() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		BeanUtil.getBean("walletDao", WalletDaoMockImpl.class).getHistory().values().stream().forEach(t -> transactions.addAll(t));

		List<Transaction> transactionsFilter = transactions.stream().filter(t -> {
			
			Calendar transactionDate = Calendar.getInstance();
				transactionDate.setTime(t.getDateStart());
			
			Calendar minDate = Calendar.getInstance();
				minDate.setTime(new Date());
			
			return transactionDate.get(Calendar.MONTH) == minDate.get(Calendar.MONTH);
			
		}).collect(Collectors.toList());
		
		return BigInteger.valueOf(transactionsFilter.size());
	}

	@Override
	public BigInteger statesAll() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		BeanUtil.getBean("walletDao", WalletDaoMockImpl.class).getHistory().values().stream().forEach(t -> transactions.addAll(t));
		
		return BigInteger.valueOf(transactions.size());
	}

	@Override
	public BigInteger statesByBeforeDays(Integer beforeDays) {
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		BeanUtil.getBean("walletDao", WalletDaoMockImpl.class).getHistory().values().stream().forEach(t -> transactions.addAll(t));

		List<Transaction> transactionsFilter = transactions.stream().filter(t -> {
			
			Calendar transactionDate = Calendar.getInstance();
				transactionDate.setTime(t.getDateStart());
			
			Calendar minDate = Calendar.getInstance();
				minDate.setTime(new Date());
			
			if (beforeDays != null) {
				minDate.add(Calendar.DAY_OF_YEAR, -beforeDays);
			}
			
			return transactionDate.get(Calendar.DAY_OF_YEAR) >= minDate.get(Calendar.DAY_OF_YEAR);
			
		}).collect(Collectors.toList());
		
		return BigInteger.valueOf(transactionsFilter.size());
	}

	@Override
	public void addDeposit(Deposit deposit) throws ExistingDepositException {
		
		if (this.listDeposit.contains(deposit)) {
			throw new ExistingDepositException();
		}
		
		this.listDeposit.add(deposit);
	}

	@Override
	public void approveDeposit(Deposit deposit) throws ApprovingApprovedDepositException, NotExistDepositException {
		
		Deposit depositFounded = this.getDepositByDepositNumber(deposit.getNumber());
		
		if (depositFounded.getState().equals(StateDepositNumber.APROVATED)) {
			throw new ApprovingApprovedDepositException();
		}
		
		depositFounded.setState(StateDepositNumber.APROVATED);
	}

	@Override
	public void rejectDeposit(Deposit deposit) throws RejectingRejectedDepositException, RejectingApprovedDepositException, NotExistDepositException {
		
		Deposit depositFounded = this.getDepositByDepositNumber(deposit.getNumber());
		
		if (depositFounded.getState().equals(StateDepositNumber.APROVATED)) {
			throw new RejectingApprovedDepositException();
		}
		
		if (depositFounded.getState().equals(StateDepositNumber.REJECTED)) {
			throw new RejectingRejectedDepositException();
		}
		
		depositFounded.setState(StateDepositNumber.REJECTED);
	}

	@Override
	public Deposit getDepositByDepositNumber(String depositNumber) throws NotExistDepositException {
		
		if (!this.listDeposit.stream().anyMatch(d -> d.number.equals(depositNumber))) {
			throw new NotExistDepositException();
		}
		
		return this.listDeposit.stream().filter(d -> d.number.equals(depositNumber)).findFirst().get();
	}

	@Override
	public List<Deposit> getDeposits(String statusDescription) {
		
		if (statusDescription.equals("")) {
			return new ArrayList<Deposit>();
		}
		
		StateDepositNumber stateDepositNumber = StateDepositNumber.valueOf(statusDescription);
		return this.listDeposit.stream().filter(d -> d.getState().equals(stateDepositNumber)).collect(Collectors.toList());
	}

	@Override
	public List<Deposit> getDepositsAll() {

		return this.listDeposit;
	}
	
}
