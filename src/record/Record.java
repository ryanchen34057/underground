package record;

public class Record implements java.io.Serializable {
    private int id;
    private String time;
    private int emeraldCount;
    private String level;
    private int deathCount;

    public Record(int id, String time, int emeraldCount, String level, int deathCount) {
        this.id = id;
        this.time = time;
        this.emeraldCount = emeraldCount;
        this.level = level;
        this.deathCount = deathCount;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public int getEmeraldCount() {
        return emeraldCount;
    }

    public String getLevel() {
        return level;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void save(String time, int emeraldCount, String level, int deathCount) {
        this.time = time;
        this.emeraldCount = emeraldCount;
        this.level = level;
        this.deathCount = deathCount;
    }
}
