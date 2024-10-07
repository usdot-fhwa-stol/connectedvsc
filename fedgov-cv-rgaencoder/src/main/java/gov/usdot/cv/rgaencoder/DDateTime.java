package gov.usdot.cv.rgaencoder;

public class DDateTime extends DDate {
    private int hour;
    private int minute;
    private int second;
    private int offset;
    
    public DDateTime()
    {
        this.hour = 0;
        this.minute = 0; 
        this.second = 0;
        this.offset = 0;
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
    
    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "RGAData [hour" + hour + ", minute=" + minute + ", second="+ second + ", offset=" + offset + "]";
    }
}
