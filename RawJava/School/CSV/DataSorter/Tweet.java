public class Tweet {
    String createdAt;
    String text;
    String screenName;
    int followerCount;
    int friendCount;
    int retweetCount;
    int favoriteCount;

    Tweet (String[] _t){
        createdAt = _t[0].substring(_t[0].indexOf(":") + 1, _t[0].length());
        text = _t[1].substring(_t[1].indexOf(":") + 1, _t[1].length());
        screenName = _t[2].substring(_t[2].indexOf(":") + 1, _t[2].length());
        for (int i = 3; i < 7; i++){
            _t[i] = _t[i].substring(_t[i].indexOf(":") + 1, _t[i].length());
        }
        followerCount = Integer.parseInt(_t[3]);
        friendCount = Integer.parseInt(_t[4]);
        retweetCount = Integer.parseInt(_t[5]);
        favoriteCount = Integer.parseInt(_t[6]);
    }
}
