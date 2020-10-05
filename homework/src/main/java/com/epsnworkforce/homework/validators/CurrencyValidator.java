package com.epsnworkforce.homework.validators;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.money.Monetary;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CurrencyValidator implements ConstraintValidator<CurrencyConstraint, String> {

    protected DecimalFormat getCurrencyFormatter(Locale currencyLocale) {

        if (currencyLocale != null) {
            return (DecimalFormat) NumberFormat.getCurrencyInstance(currencyLocale);
        }
        return null;
    }

    public enum CryptoCoins {
        BTC, LTC, XRP, XMR, ETH, EOS, XLM
    }

    @Override
    public void initialize(CurrencyConstraint currencyVal) {
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext cxt) {
        for (CryptoCoins c : CryptoCoins.values()) {
            if (c.toString().equals(currency)) {
                return true;
            }

        }
        var currencyFormatter = Monetary.getCurrency(currency);
        if (currencyFormatter != null)
            return true;
        
        return false;
    }

}
