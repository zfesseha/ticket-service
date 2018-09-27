package com.zfesseha.ticketservice.tasks;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.models.Seat;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.seats.SeatPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * clean timers
 */
public class ExpirationTaskManager {

    private Timer timer;
    private Map<Integer, TimerTask> tasks;
    private SeatPool seatPool;
    private SeatHoldDAO seatHoldDAO;

    public ExpirationTaskManager(SeatPool seatPool, SeatHoldDAO seatHoldDAO) {
        this.timer = new Timer();
        this.tasks = new HashMap<>();
        this.seatPool = seatPool;
        this.seatHoldDAO = seatHoldDAO;
    }

    public void registerSeatHold(SeatHold seatHold) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Seat seat : seatHold.getSeats()) {
                    ExpirationTaskManager.this.seatPool.add(seat);
                }
                ExpirationTaskManager.this.seatHoldDAO.remove(seatHold.getId());
            }
        };
        this.timer.schedule(task, seatHold.getExpirationDate().toDate());
        this.tasks.put(seatHold.getId(), task);
    }

    public void cancelSeatHold(int seatHoldId) {
        TimerTask task = tasks.get(seatHoldId);
        if (task != null) {
            task.cancel();
        }
    }
}
