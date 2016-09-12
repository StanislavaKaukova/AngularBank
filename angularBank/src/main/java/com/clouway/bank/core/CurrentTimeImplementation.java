package com.clouway.bank.core;

import com.google.inject.Inject;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class CurrentTimeImplementation implements CurrentTime {
  private final int minute;

  @Inject
  public CurrentTimeImplementation(int minute) {
    this.minute = minute;
  }

  @Override
  public long getCurrentTime() {
    return new Date().getTime();
  }

  @Override
  public long expirationTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date(getCurrentTime()));
    calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);

    return calendar.getTimeInMillis();
  }
}

