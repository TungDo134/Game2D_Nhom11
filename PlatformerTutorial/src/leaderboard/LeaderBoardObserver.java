package leaderboard;

import java.util.TreeMap;

public interface LeaderBoardObserver {
    void updateLeaderBoard(TreeMap<Integer, Integer> timeScoreMap);
}
