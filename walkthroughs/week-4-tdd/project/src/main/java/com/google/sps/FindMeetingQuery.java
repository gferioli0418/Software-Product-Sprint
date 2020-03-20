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
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees();
    long duration = request.getDuration();
    Collection<TimeRange> ranges = new ArrayList<>();

    // if duration too long
    if (duration > TimeRange.WHOLE_DAY.duration()) {
      return ranges;
    }

    // if no attendees
    if (events.isEmpty() && attendees.isEmpty()) {
      ranges.add(TimeRange.WHOLE_DAY);
      return ranges;
    }

    // if only one attendee is in one event
    if (events.size() == 1) {
      Event firstElement = events.iterator().next();

      TimeRange meetingDetails = firstElement.getWhen();
      int start = meetingDetails.start();
      int end = meetingDetails.end();

      TimeRange before = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, start, false);
      TimeRange after = TimeRange.fromStartEnd(end, TimeRange.END_OF_DAY, true);

      ranges.add(before);
      ranges.add(after);
      return ranges;
    }

    // if event>1
    if (events.size() > 1) {
      // iterate through each element in collection
      Iterator<Event> itr = events.iterator();
      ArrayList<ArrayList<Integer>> meetings = new ArrayList<ArrayList<Integer>>(events.size());
      int i = 0;

      while (itr.hasNext()) {
        // add the start time and the end time to the 2D array
        Event element = itr.next();
        TimeRange meetingDetails = element.getWhen();

        ArrayList<Integer> temp = new ArrayList<Integer>(2);
        temp.add(meetingDetails.start());
        temp.add(meetingDetails.end());
        meetings.add(temp);
        i++;
      }

      // check if any times overlap
      removeOverlap(meetings);
    for(int a = 0 ;a<meetings.size();a++){
System.out.println(meetings.get(a).get(0)+","+meetings.get(a).get(1));
    }
      TimeRange evt;

      // check if first event is in the beginning of day
      if (meetings.get(0).get(0) != TimeRange.START_OF_DAY) {
        evt = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, meetings.get(0).get(0), false);
        ranges.add(evt);
      }
      // add the in between times
      for (i = 1; i < meetings.size(); i++) {
        // check start time
        if (meetings.get(i - 1).get(1) < meetings.get(i).get(0)) {
          evt = TimeRange.fromStartEnd(meetings.get(i - 1).get(1), meetings.get(i).get(0), false);
          ranges.add(evt);
        }
      }

      if (meetings.get(meetings.size() - 1).get(1) != 1440) {
        //System.out.println(meetings.get(meetings.size() - 1).get(1));
        evt = TimeRange.fromStartEnd(meetings.get(meetings.size() - 1).get(1), TimeRange.END_OF_DAY, true);
        ranges.add(evt);
      }
    }

    return ranges;
  }

  public void removeOverlap(ArrayList<ArrayList<Integer>> list) {
    // sort events into ascending start time

    // check if any events overlap meaning end time of previous event is greater than start time of
    // current event
    for (int index = 1; index < list.size(); index++)
      if ((list.get(index - 1).get(0) <= list.get(index).get(0))&& (list.get(index - 1).get(1) >= list.get(index).get(1)))
        list.remove(index);

  }
}
