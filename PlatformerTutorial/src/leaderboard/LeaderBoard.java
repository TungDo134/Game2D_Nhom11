package leaderboard;

import java.util.*;


public class LeaderBoard implements LeaderBoardSubject, TimeScoreObserver {

    //private Rank rank;
    private TreeMap<Integer, Integer> timeScoreMap;
    private List<LeaderBoardObserver> observers = new ArrayList<>();

    public LeaderBoard() {
        //this.rank = rank;
        timeScoreMap = new TreeMap<>(Collections.reverseOrder());

    }

    @Override
    public void updateTimeScore(int time, int score) {
        int MAX_SIZE = 10;
        if (timeScoreMap.size() >= MAX_SIZE) {
            Map.Entry<Integer, Integer> minEntry = timeScoreMap.lastEntry();
            int minScore = minEntry.getKey();
            int minTime = minEntry.getValue();
            if (score > minScore && time > minTime) {
                timeScoreMap.remove(minScore);
                timeScoreMap.put(time, score);
                notifyLeaderBoard();
            }
        } else {
            timeScoreMap.put(time, score);
            notifyLeaderBoard();
        }
        for (Map.Entry<Integer, Integer> entry : timeScoreMap.entrySet()) {
            System.out.println("Rank: " + (timeScoreMap.size()) +
                    ", Time: " + entry.getKey() + ", Score: " + entry.getValue());
        }
    }

    @Override
    public void addObserver(LeaderBoardObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(LeaderBoardObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyLeaderBoard() {
        for (LeaderBoardObserver observer : observers) {
            observer.updateLeaderBoard(timeScoreMap);
        }
    }
}
