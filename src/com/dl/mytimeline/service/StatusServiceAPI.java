package com.dl.mytimeline.service;

import java.util.ArrayList;
import java.util.List;

import com.dl.mytimeline.Status;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;

@Api(name="statusapi",version="v1", description="An API to manage personal timeline status")
public class StatusServiceAPI {

    public static List<Status> sStatuses = new ArrayList<Status>();

    @ApiMethod(name="add")
    public Status addStatus(@Named("id") Integer id, @Named("content") String content,
            @Named("timestamp") Long timeStamp) throws NotFoundException {
        // Check for already exists
        int index = sStatuses.indexOf(new Status(id));
        if (index != -1) {
            throw new NotFoundException("Status Record already exists");
        }
        Status s = new Status(id, content, timeStamp);
        sStatuses.add(s);
        return s;
    }

    @ApiMethod(name="update")
    public Status updateStatus(Status s) throws NotFoundException {
        int index = sStatuses.indexOf(s);
        if (index == -1)
            throw new NotFoundException("Status Record does not exist");
        Status currentStatus = sStatuses.get(index);
        currentStatus.setContent(s.getContent());
        currentStatus.setTimestamp(s.getTimestamp());
        return s;
    }

    @ApiMethod(name="remove")
    public void removeStatus(@Named("id") Integer id) throws NotFoundException {
        int index = sStatuses.indexOf(new Status(id));
        if (index == -1) {
            throw new NotFoundException("Status Record does not exist");
        }
        sStatuses.remove(index);
    }

    @ApiMethod(name="list")
    public List<Status> getStatuses() {
        return sStatuses;
    }

    @ApiMethod(name="getStatus")
    public Status getStatus(@Named("id") Integer id) throws NotFoundException {
        int index = sStatuses.indexOf(new Status(id));
        if (index == -1) {
            throw new NotFoundException("Status Record does not exist");
        }
        return sStatuses.get(index);
    }

}
