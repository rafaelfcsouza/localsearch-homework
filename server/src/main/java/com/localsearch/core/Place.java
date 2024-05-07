package com.localsearch.core;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public record Place(String name, String address, List<OpeningHours> openingHours) {
  public record OpeningHours(Set<DayOfWeek> days, List<TimeSlot> timeSlots)
      implements Comparable<OpeningHours> {

    @Override
    public int compareTo(OpeningHours toCompare) {
      if (this.days.size() > toCompare.days.size()) return -1;
      else if (this.days.size() < toCompare.days.size()) return 1;
      else return this.days.iterator().next().compareTo(toCompare.days.iterator().next());
    }
  }

  public record TimeSlot(String start, String end) {}
}
