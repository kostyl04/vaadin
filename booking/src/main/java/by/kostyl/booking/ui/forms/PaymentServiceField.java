package by.kostyl.booking.ui.forms;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import by.kostyl.booking.entity.Hotel;
import by.kostyl.booking.entity.PaymentService;

public class PaymentServiceField extends CustomField<PaymentService> {
	private PaymentService value;
	private RadioButtonGroup<String> cashType;
	private TextField percent;
	private Label cashLabel;
	private String changeMessage;
	@Override
	public PaymentService getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	protected Component initContent() {
		VerticalLayout lay = new VerticalLayout();
		
		cashType = new RadioButtonGroup<String>();
		cashType.setItems("cash", "credit card");
		setCaption("Choose ur payment type");

		cashType.addValueChangeListener(val -> {
			if (val.getValue().equals("cash")) {
				percent.setValue("0");
				percent.setVisible(false);
				cashLabel.setVisible(true);
				setValue(new PaymentService(0, false, true));

			} else if (val.getValue().equals("credit card")) {
				percent.setVisible(true);
				cashLabel.setVisible(false);
				setValue(new PaymentService(percent.isEmpty()?0:Integer.parseInt(percent.getValue()), true, false));
			}
		});
		this.percent = new TextField("Percent for prepay");
		percent.addValueChangeListener(e -> {
			try {
				int p = new Integer(e.getValue());
				setValue(new PaymentService(p, true, false));
			} catch (NumberFormatException exc) {
				percent.setValue("0");
				setValue(new PaymentService(0, true, false));
			}
		});
		this.cashLabel = new Label("u choose  payment by cash");
		lay.addComponents(cashType, percent, cashLabel);
		percent.setVisible(false);
		cashLabel.setVisible(false);
		return lay;
	}

	public void fillValue(PaymentService value) {
		this.value = value;
		updateField();
	}

	private void updateField() {
		if (value.getCreditCardType()) {
			cashType.setSelectedItem("credit card");
			percent.setValue(String.valueOf(value.getGuaranty_fee()));
		}

		else{
			percent.setValue("0");
			cashType.setSelectedItem("cash");
		}
			

	}
	
	@Override
	protected void doSetValue(PaymentService value) {
		this.value=value;
		updateField();

	}

}
