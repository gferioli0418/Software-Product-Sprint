// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees();
    long duration = request.getDuration();
    Collection<TimeRange> ranges = new ArrayList<>();

    //--------------------------------------------------------------------edge case
    // if duration too long
    if (duration > TimeRange.WHOLE_DAY.duration()) {
      return ranges;
    }

    // if no attendees
    if (attendees.isEmpty()) {
      ranges.add(TimeRange.WHOLE_DAY);
      return ranges;
    }

    //----------------------------------------------------------------- general case
    if (events.size() >= 0) {
      // iterate through each element in collection
      Iterator<Event> itr = events.iterator();
      ArrayList<ArrayList<Integer>> meetings = new ArrayList<ArrayList<Integer>>();

      while (itr.hasNext()) {
        // add the start time and the end time to the 2D arraylist
        Event element = itr.next();
        Set<String> peopleInEvent = element.getAttendees();
        // check if any person in the event matches a person in the request
        List<String> common = new ArrayList<String>(attendees);
        common.retainAll(peopleInEvent);
        // if it does then add it to meetings
        if (common.size() > 0) {
          TimeRange meetingDetails = element.getWhen();
          ArrayList<Integer> temp = new ArrayList<Integer>(2);
          temp.add(meetingDetails.start());
          temp.add(meetingDetails.end());
          meetings.add(temp);
        }
      }
      // if nothing in meetings list then put availability for meeting request for the whole day
      if (meetings.size() == 0) {
        ranges.add(TimeRange.WHOLE_DAY);
        return ranges;
      }

      // replace any overlaps with one big meetings that includes what was overlaped
      removeOverlap(meetings);

      TimeRange evt;

      // check if first event is in the beginning of day
      if (meetings.get(0).get(0) != TimeRange.START_OF_DAY) {
        evt = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, meetings.get(0).get(0), false);
        ranges.add(evt);
      }
      // add the in between times
      for (i = 1; i < meetings.size(); i++) {
        // check if event end time is less than the next event start time
        if (meetings.get(i - 1).get(1) < meetings.get(i).get(0)) {
          evt = TimeRange.fromStartEnd(meetings.get(i - 1).get(1), meetings.get(i).get(0), false);
          ranges.add(evt);
        }
      }

      // check if last event ends at the end of the day
      if (meetings.get(meetings.size() - 1).get(1) != 1440) {
        evt = TimeRange.fromStartEnd(
            meetings.get(meetings.size() - 1).get(1), TimeRange.END_OF_DAY, true);
        ranges.add(evt);
      }
    }
    return ranges;
  }

  public void removeOverlap(ArrayList<ArrayList<Integer>> list) {
    // removes any overlap
    for (int index = 1; index < list.size(); index++)
      if ((list.get(index - 1).get(0) <= list.get(index).get(0))
          && (list.get(index - 1).get(1) >= list.get(index).get(1)))
        list.remove(index);
  }
}
