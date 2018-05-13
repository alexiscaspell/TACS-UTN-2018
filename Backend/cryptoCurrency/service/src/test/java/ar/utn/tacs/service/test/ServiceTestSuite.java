package ar.utn.tacs.service.test;

import java.math.BigDecimal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import ar.utn.tacs.commons.UtnTacsException;
import ar.utn.tacs.model.user.Login;
import ar.utn.tacs.model.user.Person;
import ar.utn.tacs.model.user.User;
import ar.utn.tacs.model.wallet.CoinAmountRest;
import ar.utn.tacs.model.wallet.Wallet;
import ar.utn.tacs.service.user.UserService;
import ar.utn.tacs.service.wallet.WalletService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContextTest.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class ServiceTestSuite {
	
	@Autowired
	UserService userService;
	
	@Autowired
	WalletService walletService;
	
	protected Login getLoginTostado() {
		return new Login("tostado", "1234", true, 0);
	}
	
	protected Login getLoginLobezzzno() {
		return new Login("lobezzzno", "1234", true, 0);
}
	
	protected User getUserTostado() {
		return new User(2l, new Login("tostado", "1234", true, 0), new Person("alexis", "taberna", "tostado@gmail.com"), null, new Wallet(null, new BigDecimal(10000f)));
	}
	protected User getUserTostadoPosta() {
		return userService.getUserById(getUserTostado().getId());
	}
	
	protected String getTokenTostado() {
		Login loginTostado = getLoginTostado();
		
		return userService.getTokenByLogin(loginTostado);
	}
	
	protected void tostadoCompraBitcoin(BigDecimal value) {
		String token = getTokenTostado();
		CoinAmountRest coinAmountRest = new CoinAmountRest();
		coinAmountRest.setTicker("BTC");
		coinAmountRest.setAmount(value.toString());
		
		try {
			walletService.buy(token, coinAmountRest);
		} catch (UtnTacsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}