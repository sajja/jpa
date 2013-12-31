package test.jpa;

import org.hibernate.cfg.Configuration;
import org.hibernate.envers.event.AuditEventListener;
import org.hibernate.event.PostInsertEvent;


public class MyAuditEventListener extends AuditEventListener {
    @Override
    public void onPostInsert(PostInsertEvent event) {
        super.onPostInsert(event);
        System.out.println("ddddddddddddddd");
    }

    @Override
    public void initialize(Configuration cfg) {
        super.initialize(cfg);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
