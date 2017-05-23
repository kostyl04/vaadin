package by.kostyl.booking.entity;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class PaymentService {
	@Basic
	private Integer guaranty_fee;
	@Basic
	private Boolean creditCardType;
	@Basic
	private Boolean cashType;

	public PaymentService() {

	}

	public PaymentService(int percent, boolean creditCardType, boolean cashType) {
		this.guaranty_fee = percent;
		this.creditCardType = creditCardType;
		this.cashType = cashType;
	}

	public PaymentService(PaymentService service) {
		this.cashType = service.cashType;
		this.creditCardType = service.creditCardType;
		this.guaranty_fee = service.guaranty_fee;
	}

	public Integer getGuaranty_fee() {
		return guaranty_fee;
	}

	public void setGuaranty_fee(Integer guaranty_fee) {
		this.guaranty_fee = guaranty_fee;
	}

	public Boolean getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(Boolean creditCardType) {
		this.creditCardType = creditCardType;
	}

	public Boolean getCashType() {
		return cashType;
	}

	public void setCashType(Boolean cashType) {
		this.cashType = cashType;
	}
	@Override
	public String toString(){
		String msg=cashType?"Cash":"credit card with "+guaranty_fee+" percent prepay";
		return msg;
		
	}
}
