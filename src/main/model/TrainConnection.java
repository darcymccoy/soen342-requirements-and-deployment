package main.model;

public class TrainConnection {
    private TrainRoute route1;
    private TrainRoute route2;
    private TrainRoute route3;

    private long duration;

    public TrainConnection(TrainRoute route1,long duration ) {
        this.duration = duration;
        this.route1 = route1;
    }

    public TrainConnection(TrainRoute route1, TrainRoute route2, long duration) {
        this.route1 = route1;
        this.route2 = route2;
        this.duration = duration;
    }
    public TrainConnection(TrainRoute route1, TrainRoute route2, TrainRoute route3) {
        this.route1 = route1;
        this.route2 = route2;
        this.route3 = route3;
        this.duration = -1;
    }

    public TrainConnection(TrainRoute route1, TrainRoute route2, TrainRoute route3, long duration) {
        this.route1 = route1;
        this.route2 = route2;
        this.route3 = route3;
        this.duration = duration;
    }

    public TrainRoute getRoute1() {
        return route1;
    }

    public void setRoute1(TrainRoute route1) {
        this.route1 = route1;
    }

    public TrainRoute getRoute2() {
        return route2;
    }

    public void setRoute2(TrainRoute route2) {
        this.route2 = route2;
    }

    public TrainRoute getRoute3() {
        return route3;
    }

    public void setRoute3(TrainRoute route3) {
        this.route3 = route3;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getSecondCLassRate() {
        int totalSecondRate = 0;
        totalSecondRate += route1.getSecondClassTicketRate();
        if (route2 != null) {
            totalSecondRate += route2.getSecondClassTicketRate();
        }
        if (route3 != null) {
            totalSecondRate += route3.getSecondClassTicketRate();
        }
        return totalSecondRate;
    }

    public int getFirstCLassRate() {
        int totalFirstRate = 0;
        totalFirstRate += route1.getFirstClassTicketRate();
        if (route2 != null) {
            totalFirstRate += route2.getFirstClassTicketRate();
        }
        if (route3 != null) {
            totalFirstRate += route3.getFirstClassTicketRate();
        }
        return totalFirstRate;
    }

    public String toString() {
        String legs = buildLegs();
        return String.format("%s \n(duration %s)", legs, formatDuration(duration));
    }

    private String buildLegs() {
        StringBuilder sb = new StringBuilder();
        if (route1 != null) sb.append(route1);
        if (route2 != null) sb.append(",  \n").append(route2);
        if (route3 != null) sb.append(", \n").append(route3);
        return sb.length() == 0 ? "(no routes)" : sb.toString();
    }

    private static String formatDuration(long minutes) {
        long h = minutes / 60;
        long m = minutes % 60;
        return h > 0 ? String.format("%dh %02dm", h, m) : String.format("%dm", m);
    }


}
