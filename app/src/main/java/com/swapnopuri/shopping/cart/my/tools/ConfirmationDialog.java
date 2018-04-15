package com.swapnopuri.shopping.cart.my.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.swapnopuri.shopping.cart.my.R;

/**
 * Created by JEWEL on 2/27/2017.
 */

public class ConfirmationDialog {
    private IYesNo iYesNo;
    private Context context;
    private Dialog dialog;

    public ConfirmationDialog(Context context, IYesNo iYesNo) {
        this.iYesNo = iYesNo;
        this.context = context;
        showDialog();
    }

    public void showDialog() {
        dialog = new Dialog(context);
        dialog.setTitle("Warning !!!!");
        dialog.setContentView(R.layout.lay_dia_yes_no);
        dialog.show();

        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (iYesNo != null)
                    iYesNo.onYes();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (iYesNo != null)
                    iYesNo.onNo();
            }
        });
    }


    public interface IYesNo {
        void onYes();

        void onNo();
    }
}
