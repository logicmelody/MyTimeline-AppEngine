package com.dl.mytimeline.service;

import java.util.ArrayList;
import java.util.List;

import com.dl.mytimeline.Status;

public class StatusService {

    public static List<Status> sStatuses = new ArrayList<Status>();

    public Status addStatus(Integer id, String content, Long timeStamp) throws Exception {
        // Check for already exists
        int index = sStatuses.indexOf(new Status(id));
        if (index != -1) {
            throw new Exception("Status Record already exists");
        }
        Status s = new Status(id, content, timeStamp);
        sStatuses.add(s);
        return s;
    }

    public Status updateStatus(Status s) throws Exception {
        int index = sStatuses.indexOf(s);
        if (index == -1)
            throw new Exception("Status Record does not exist");
        Status currentStatus = sStatuses.get(index);
        currentStatus.setContent(s.getContent());
        currentStatus.setTimestamp(s.getTimestamp());
        return s;
    }

    public void removeStatus(Integer id) throws Exception {
        int index = sStatuses.indexOf(new Status(id));
        if (index == -1) {
            throw new Exception("Status Record does not exist");
        }
        sStatuses.remove(index);
    }

    public List<Status> getStatuses() {
        return sStatuses;
    }

    public Status getStatus(Integer id) throws Exception {
        int index = sStatuses.indexOf(new Status(id));
        if (index == -1) {
            throw new Exception("Status Record does not exist");
        }
        return sStatuses.get(index);
    }

}
