package com.example.sctma.autosteward;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by SMAYBER8 on 6/26/2017.
 */

public class EditTextMoneyListener implements TextWatcher {
    private int money;
    private int beforeM;
    public EditTextMoneyListener()
    {}
    public EditTextMoneyListener(int money)
    {   this.money = money;
        this.beforeM = money;}

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        beforeM = money;
        if(s.length() == 0) {
            money = 0;
            SplitMoney.updateSplitText(money-beforeM);
            return;
        }
        if(s.length() > 4)
            s = s.subSequence(0,4);
        money = Integer.parseInt(s.toString());
        SplitMoney.updateSplitText(money-beforeM);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
