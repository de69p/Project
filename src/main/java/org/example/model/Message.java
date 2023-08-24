package org.example.model;

public class Message {
    private int message_id;
    private int posted_by;
    private String message_text;
    private long time_posted_epoch;

    public Message(int message_id, int posted_by, String message_text, long time_posted_epoch) {
        this.message_id = message_id;
        this.posted_by = posted_by;
        this.message_text = message_text;
        this.time_posted_epoch = time_posted_epoch;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(int posted_by) {
        this.posted_by = posted_by;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public long getTime_posted_epoch() {
        return time_posted_epoch;
    }

    public void setTime_posted_epoch(long time_posted_epoch) {
        this.time_posted_epoch = time_posted_epoch;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", posted_by=" + posted_by +
                ", message_text='" + message_text + '\'' +
                ", time_posted_epoch=" + time_posted_epoch +
                '}';
    }
}
