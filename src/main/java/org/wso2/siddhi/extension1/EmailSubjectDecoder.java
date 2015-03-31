package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;

/**
 * Created by danula on 2/13/15.
 */
public class EmailSubjectDecoder implements OutputAttributeAggregator {

    public String getProductName(String subject){
        subject = subject.replace("Build failed in Jenkins: ", "");
        subject = subject.replace("Re:","");
        return subject.split(" ")[0];
    }

    @Override
    public Attribute.Type getReturnType() {
        return Attribute.Type.STRING;
    }

    @Override
    public Object processAdd(Object o) {
        String subject = (String)o;
        return getProductName(subject);
    }

    @Override
    public Object processRemove(Object o) {
        String subject = (String)o;
        return getProductName(subject);
    }

    @Override
    public OutputAttributeAggregator newInstance() {
        return new EmailSubjectDecoder();
    }

    @Override
    public void destroy() {

    }
}
