package gov.usdot.cv.rgaencoder;

public class DDateTime extends DDate {
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer offset;
    
    public DDateTime()
    {
        this.hour = null;
        this.minute = null; 
        this.second = null;
        this.offset = null;
    }
    
    public DDateTime(int hour, int minute, int second, int offset) {
        this.hour = hour;
        this.minute = minute; 
        this.second = second;
        this.offset = offset;
    }

    public int getHour() {
        return hour;
    }
    
    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getOffset() {
        return offset;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
    
    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "RGAData [hour" + hour + ", minute=" + minute + ", second="+ second + ", offset=" + offset + "]";
    }
}
