public class Food {
    String desc;
    float sugar;
    float water;
    float vitaminK;

    Food (String[] _t){
        desc = _t[0];
        sugar = Float.parseFloat(_t[1]);
        water = Float.parseFloat(_t[2]);
        vitaminK = Float.parseFloat(_t[3]);
    }
}
