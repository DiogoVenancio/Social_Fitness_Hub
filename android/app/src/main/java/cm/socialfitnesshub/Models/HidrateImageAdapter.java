package cm.socialfitnesshub.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cm.socialfitnesshub.Hidrate.Drink;
import cm.socialfitnesshub.Hidrate.DrinkDetailDialog;
import cm.socialfitnesshub.R;


public class HidrateImageAdapter extends BaseAdapter {
    private Context context;
    private final List<Drink> drinks;

    public HidrateImageAdapter(Context context, List<Drink> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gv;

        if (convertView == null) {
            gv = new View(context);
            // get layout from mobile.xml
            gv = inflater.inflate(R.layout.h_drink_list, null);

            Drink drink = drinks.get(position);

            TextView size = gv.findViewById(R.id.drink_amount);
            size.setText(drink.getSize() + "");

            TextView date = gv.findViewById(R.id.drink_time);
            date.setText(drink.getTimeFormatted());

            // set image based on selected text
            ImageView imageView = gv.findViewById(R.id.drink_image);
            imageView.setImageResource(drink.getDrawable());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrinkDetailDialog drinkDetailDialog = new DrinkDetailDialog(context, drinks.get(position));
                    drinkDetailDialog.show();
                    System.out.println(drinks.get(position).getSize());
                }
            });


        } else {
            gv = (View) convertView;
        }

        return gv;
    }

    @Override
    public int getCount() {
        return drinks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}