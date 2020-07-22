package cm.socialfitnesshub.Hidrate;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cm.socialfitnesshub.R;

public class DrinkAddDialog extends Dialog implements android.view.View.OnClickListener {

    TextView tvSize;
    ImageView btnWaterGlass, btnWaterBottle, btnGymBottle, btnCofee, btnTea, btnBeer;
    private int size;

    public DrinkAddDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.h_add_drink);

        tvSize = findViewById(R.id.hidrate_add_drink_size);


        btnWaterGlass = findViewById(R.id.hidrate_add_water_glass);
        btnWaterBottle = findViewById(R.id.hidrate_add_water_bottle);
        btnGymBottle = findViewById(R.id.hidrate_add_bottle_gym);
        btnCofee = findViewById(R.id.hidrate_add_coffee);
        btnTea = findViewById(R.id.hidrate_add_tea);
        btnBeer = findViewById(R.id.hidrate_add_beer);


        btnWaterGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.water_glass);
            }
        });

        btnWaterBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.water_bottle);
            }
        });

        btnGymBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.water_bottle_gym);
            }
        });

        btnCofee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.coffee);
            }
        });

        btnTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.tea);
            }
        });

        btnBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrink(getSelectedSize(), R.drawable.pint);
            }
        });

    }

    public int getSelectedSize() {
        return Integer.parseInt(tvSize.getText().toString());
    }

    public void addDrink(int size, int drawable) {
        Drink drink = new Drink(size, drawable);
        DrinkManager.getInstance().addDrink(drink);
        dismiss();
    }

    @Override
    public void onClick(View v) {

    }
}
