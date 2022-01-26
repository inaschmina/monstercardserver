package card;


public class Spellcard implements Card{
    String id;
    String name;
    double damage;
    String element;

    public Spellcard(String id, String name, double damage, String element) {
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
