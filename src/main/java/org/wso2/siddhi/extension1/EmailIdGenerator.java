package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;

/**
 * Created by danula on 2/19/15.
 */
public class EmailIdGenerator implements OutputAttributeAggregator {
    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.INT;
    }

    @Override
    public Object processAdd(Object o) {
        String subject = (String)o;
        return getId(subject);
    }

    @Override
    public Object processRemove(Object o) {
        String subject = (String)o;
        return getId(subject);
    }

    @Override
    public OutputAttributeAggregator newInstance() {
        return new EmailIdGenerator();
    }

    @Override
    public void destroy() {

    }

    public int getId(String subject) {
        subject = subject.replace("Build failed in Jenkins: ", "");
        String id= subject.split("#|\\s ")[1];
        return Integer.parseInt(id);
    }
}
