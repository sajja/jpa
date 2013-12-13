package test.jpa;

import org.hibernate.envers.event.AuditEventListener;
import org.hibernate.event.PostInsertEvent;


public class MyAuditEventListener extends AuditEventListener {
    @Override
    public void onPostInsert(PostInsertEvent event) {
        super.onPostInsert(event);
        System.out.println("ddddddddddddddd");
    }
}
