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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<String> attendees = request.getAttendees();
    long duration = request.getDuration();
    Collection<TimeRange> ranges = new ArrayList<>();

    // if duration too long
    if(duration>TimeRange.WHOLE_DAY.duration()){
        return ranges;
    }
    //if no attendees
    if(events.isEmpty() && attendees.isEmpty()){
        ranges.add(TimeRange.WHOLE_DAY);
        return ranges;
    }
    //if only one attendee is in one event
    if(events.size()== 1 && attendees.size()==1 ){

        Event firstElement = events.iterator().next();

        TimeRange meetingDetails = firstElement.getWhen();
        int start = meetingDetails.start();
        int end = meetingDetails.start() + meetingDetails.duration();

        TimeRange before = TimeRange.fromStartEnd(TimeRange.START_OF_DAY, start, false);
        TimeRange after = TimeRange.fromStartEnd(end, TimeRange.END_OF_DAY, true);
   
        ranges.add(before);
        ranges.add(after);
        return ranges
    }
    return ranges;
  }
}
