package cm.socialfitnesshub.Hidrate;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.Calendar;

import cm.socialfitnesshub.Database.Utilities;
import cm.socialfitnesshub.R;

public class DrinkDetailDialog extends Dialog implements android.view.View.OnClickListener {

    private final Drink drink;
    private EditText date, size;
    private ImageButton btnDelete;
    private Button btnCancel, btnOk;

    Calendar cal = Calendar.getInstance();

    public DrinkDetailDialog(@NonNull Context context, Drink drink) {
        super(context);
        this.drink = drink;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.h_drink_details);


        date = findViewById(R.id.drink_detail_time);
        date.setText(drink.getTimeFormatted());

        final TimePickerDialog.OnTimeSetListener timePicker = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // TODO Auto-generated method stub
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                updateLabel();
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(getContext(),
                        timePicker,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true);
                tpd.show();
            }
        });

        size = findViewById(R.id.drink_detail_size);
        size.setText(drink.getSize() + "");

        btnDelete = findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrinkManager.getInstance().removeDrink(drink);
                dismiss();
            }
        });

        btnCancel = findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drink.setSize(Integer.parseInt(size.getText().toString()));

                String[] timeParts = date.getText().toString().split(":");

                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hours);
                cal.set(Calendar.MINUTE, minutes);

                drink.setDate(cal.getTime());
                DrinkManager.getInstance().updateDrink(drink);
                dismiss();
            }
        });

    }

    private void updateLabel() {
        date.setText(Utilities.dateToString(cal.getTime(), "HH:mm"));
    }

    @Override
    public void onClick(View v) {

    }
}
