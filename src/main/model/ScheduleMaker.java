package main.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ScheduleMaker {

    private TrainRoute trainRoute;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("H:mm");
    private boolean nextDay = false;
    private long duration;


    private boolean valid = false;

    private TrainRoute a;
    private TrainRoute b;
    private TrainRoute c;
    private TrainRoute d;

    public ScheduleMaker(TrainRoute trainRoute)  {
        this.trainRoute = trainRoute;
        duration = getSimpleDuration();

    }


    public ScheduleMaker( TrainRoute a, TrainRoute b)
    {
        this.a = a;
        this.b = b;

        duration = timeOperation();
        valid =(duration > 0) &&  dayOperation();
    }
    public ScheduleMaker( TrainRoute a, TrainRoute b, TrainRoute c, TrainRoute d)
    {
        this.a = a;
        this.b = b;

        duration = timeOperation();

        valid =(duration > 0) && dayOperation();

        this.a = c;
        this.b = d;

        duration += timeOperation();
        valid =(duration > 0) && dayOperation();

    }


    public long getSimpleDuration()
    {

        LocalTime t1 = LocalTime.parse(trainRoute.getDepartureTime(), FMT);


        nextDay = trainRoute.getArrivalTime().contains("+1d");


        LocalTime t2 = LocalTime.parse(trainRoute.getArrivalTime().replace(" (+1d)", "").trim(), FMT);

        long unit = ChronoUnit.MINUTES.between(t1, t2);
        if (nextDay)
        {
            return unit + (24*60);
        }

        return unit;
    }


    public long getDuration()
    {
        return duration;
    }

    public boolean getValid()
    {
        return valid;
    }

    public long timeOperation() {

        nextDay = a.getArrivalTime().contains("+1d");
        LocalTime a1 = LocalTime.parse(a.getDepartureTime(), FMT);
        LocalTime a2 = LocalTime.parse(a.getArrivalTime().replace(" (+1d)", "").trim(), FMT);
        LocalTime b1 = LocalTime.parse(b.getDepartureTime(), FMT);
        LocalTime b2 = LocalTime.parse(b.getArrivalTime().replace(" (+1d)", "").trim(), FMT);


        if (ChronoUnit.MINUTES.between(a2, b1) > 0) {
            if (nextDay)
            {
                return ChronoUnit.MINUTES.between(a1, b2)+(24*60);
            }
            return ChronoUnit.MINUTES.between(a1, b2);
        }

        return -1;
    }



    public boolean dayOperation() {
        String a = this.a.getDaysOfOperation();
        String b = this.b.getDaysOfOperation();


        if (a.equals("Daily") || b.equals("Daily")) {
            return true;
        }

        if (nextDay)
        {
           return switch (a)
        {
            case("Tue,Thu") ->  !b.equals("Sat,Sun");
            case ("Sat-Sun") -> !b.equals("Tue,Thu");
            default -> true;
        };

        }

        return switch (a) {
            case ("Fri-Sun") -> !b.equals("Tue,Thu");
            case ("Mon,Wed,Fri") -> !b.equals("Tue,Thu") && !b.equals("Sat-Sun");
            case ("Tue,Thu") -> b.equals("Mon-Fri") || b.equals("Tue,Thu");
            case ("Sat-Sun") -> b.equals("Fri-Sun") || b.equals("Sat-Sun");
            case ("Mon-Fri") -> !b.equals("Sat-Sun");
            default -> true;
        };

    }

}
