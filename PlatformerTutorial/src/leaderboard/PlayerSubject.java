package leaderboard;

public interface PlayerSubject {
    void addObserver(TimeScoreObserver observer);
    void removeObserver(TimeScoreObserver observer);
    void notifyTimeScore();

}
