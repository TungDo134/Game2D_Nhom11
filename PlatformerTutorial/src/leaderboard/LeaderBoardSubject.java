package leaderboard;

public interface LeaderBoardSubject {
    void addObserver(LeaderBoardObserver observer);
    void removeObserver(LeaderBoardObserver observer);
    void notifyLeaderBoard();
}
