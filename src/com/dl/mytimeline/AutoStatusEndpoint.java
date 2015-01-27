
package com.dl.mytimeline;

import com.dl.mytimeline.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "autostatusendpoint", namespace = @ApiNamespace(ownerDomain = "dl.com", ownerName = "dl.com", packagePath = "mytimeline"))
public class AutoStatusEndpoint {

    /**
     * This method lists all the entities inserted in datastore.
     * It uses HTTP GET method and paging support.
     *
     * @return A CollectionResponse class containing the list of all entities
     * persisted and a cursor to the next page.
     */
    @SuppressWarnings({
            "unchecked", "unused"
    })
    @ApiMethod(name = "listAutoStatus")
    public CollectionResponse<AutoStatus> listAutoStatus(
            @Nullable @Named("cursor") String cursorString, @Nullable @Named("limit") Integer limit) {

        PersistenceManager mgr = null;
        Cursor cursor = null;
        List<AutoStatus> execute = null;

        try {
            mgr = getPersistenceManager();
            Query query = mgr.newQuery(AutoStatus.class);
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                HashMap<String, Object> extensionMap = new HashMap<String, Object>();
                extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
                query.setExtensions(extensionMap);
            }

            if (limit != null) {
                query.setRange(0, limit);
            }

            execute = (List<AutoStatus>) query.execute();
            cursor = JDOCursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and accomodate
            // for lazy fetch.
            for (AutoStatus obj : execute)
                ;
        } finally {
            mgr.close();
        }

        return CollectionResponse.<AutoStatus> builder().setItems(execute)
                .setNextPageToken(cursorString).build();
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET method.
     *
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    @ApiMethod(name = "getAutoStatus")
    public AutoStatus getAutoStatus(@Named("id") Long id) {
        PersistenceManager mgr = getPersistenceManager();
        AutoStatus autostatus = null;
        try {
            autostatus = mgr.getObjectById(AutoStatus.class, id);
        } finally {
            mgr.close();
        }
        return autostatus;
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity already
     * exists in the datastore, an exception is thrown.
     * It uses HTTP POST method.
     *
     * @param autostatus the entity to be inserted.
     * @return The inserted entity.
     */
    @ApiMethod(name = "insertAutoStatus")
    public AutoStatus insertAutoStatus(AutoStatus autostatus) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            if (autostatus.getId() != null) {
                if (containsAutoStatus(autostatus)) {
                    throw new EntityExistsException("Object already exists");
                }
            }
            mgr.makePersistent(autostatus);
        } finally {
            mgr.close();
        }
        return autostatus;
    }

    @ApiMethod(name = "quoteAndInsertAutoStatus")
    public AutoStatus quoteAndInsertAutoStatus(AutoStatus autostatus) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            if (autostatus.getId() != null) {
                if (containsAutoStatus(autostatus)) {
                    throw new EntityExistsException("Object already exists");
                }
            }
            String content = autostatus.getContent();
            autostatus.setContent('"' + content + '"');
            mgr.makePersistent(autostatus);
        } finally {
            mgr.close();
        }
        return autostatus;
    }

    /**
     * This method is used for updating an existing entity. If the entity does not
     * exist in the datastore, an exception is thrown.
     * It uses HTTP PUT method.
     *
     * @param autostatus the entity to be updated.
     * @return The updated entity.
     */
    @ApiMethod(name = "updateAutoStatus")
    public AutoStatus updateAutoStatus(AutoStatus autostatus) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            if (!containsAutoStatus(autostatus)) {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.makePersistent(autostatus);
        } finally {
            mgr.close();
        }
        return autostatus;
    }

    /**
     * This method removes the entity with primary key id.
     * It uses HTTP DELETE method.
     *
     * @param id the primary key of the entity to be deleted.
     */
    @ApiMethod(name = "removeAutoStatus")
    public void removeAutoStatus(@Named("id") Long id) {
        PersistenceManager mgr = getPersistenceManager();
        try {
            AutoStatus autostatus = mgr.getObjectById(AutoStatus.class, id);
            mgr.deletePersistent(autostatus);
        } finally {
            mgr.close();
        }
    }

    private boolean containsAutoStatus(AutoStatus autostatus) {
        PersistenceManager mgr = getPersistenceManager();
        boolean contains = true;
        try {
            mgr.getObjectById(AutoStatus.class, autostatus.getId());
        } catch (javax.jdo.JDOObjectNotFoundException ex) {
            contains = false;
        } finally {
            mgr.close();
        }
        return contains;
    }

    private static PersistenceManager getPersistenceManager() {
        return PMF.get().getPersistenceManager();
    }

}
