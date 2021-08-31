public class Injury {
    int year;
    String city;
    String state;
    String businessName;
    String industryField;
    String industryGroup;

    Injury (String[] _t){
        year = Integer.parseInt(_t[0]);
        city = _t[1];
        state = _t[2];
        businessName = _t[3];
        industryField = _t[4];
        industryGroup = _t[5];
    }
}

