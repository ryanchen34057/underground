package record;

public class Record implements java.io.Serializable {
    private static final long serialVersionUID = 34985786283949293L;
    private int id;
    private long  time;
    private String timeString;
    private int emeraldCount;
    private int level;
    private int deathCount;

    public Record(int id, long time,String timeString, int emeraldCount, int level, int deathCount) {
        this.id = id;
        this.time = time;
        this.timeString = timeString;
        this.emeraldCount = emeraldCount;
        this.level = level;
        this.deathCount = deathCount;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }
    
    public String getTimeString() {
        return timeString;
    }

    public int getEmeraldCount() {
        return emeraldCount;
    }

    public int getLevel() {
        return level;
    }

    public int getDeathCount() {
        return deathCount;
    }
    
    @Override
    public String toString(){
      return id+","+time+","+timeString+","+emeraldCount+","+level+","+deathCount;
    } 

}
