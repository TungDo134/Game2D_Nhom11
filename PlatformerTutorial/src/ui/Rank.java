package ui;

import leaderboard.LeaderBoardObserver;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public abstract class Rank implements LeaderBoardObserver {

    protected TreeMap<Integer, Integer> timeScoreMap;

    public Rank() {
        timeScoreMap = new TreeMap<>(Collections.reverseOrder());
    }

    @Override
    public void updateLeaderBoard(TreeMap<Integer, Integer> timeScoreMap) {
        this.timeScoreMap.clear();
        this.timeScoreMap.putAll(timeScoreMap);

        for (Map.Entry<Integer, Integer> entry : this.timeScoreMap.entrySet()) {
            System.out.println("Map size: " + (this.timeScoreMap.size()) +
                    ", Time: " + entry.getKey() + ", Score: " + entry.getValue() + " game over");
        }
    }
}
