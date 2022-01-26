package card;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class Monstercard implements Card{
    String id;
    String name;
    double damage;
    String element;

    public Monstercard(String id, String name, double damage, String element) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public String getElement() {
        return element;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }
}
