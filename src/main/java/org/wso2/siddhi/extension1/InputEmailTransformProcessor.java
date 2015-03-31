package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.event.in.InEvent;
import org.wso2.siddhi.core.event.in.InListEvent;
import org.wso2.siddhi.core.event.in.InStream;
import org.wso2.siddhi.core.executor.expression.ExpressionExecutor;
import org.wso2.siddhi.core.query.processor.transform.TransformProcessor;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.Variable;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danula on 2/19/15.
 */
@SiddhiExtension(namespace = "bm", function = "getEmailDetails")
public class InputEmailTransformProcessor extends TransformProcessor {
    private Map<String, Integer> paramPositions = new HashMap<String, Integer>();

    public InputEmailTransformProcessor(){
        this.outStreamDefinition = new StreamDefinition().name("DataOut").attribute("id", Attribute.Type.INT).attribute("product", Attribute.Type.STRING).attribute("time",Attribute.Type.LONG).attribute("message", Attribute.Type.STRING).attribute("emailStatus", Attribute.Type.STRING).attribute("emailType", Attribute.Type.INT).attribute("emailTo", Attribute.Type.STRING).attribute("emailFrom", Attribute.Type.STRING).attribute("emailCC", Attribute.Type.STRING);
    }

    @Override
    protected InStream processEvent(InEvent inEvent) {
        String subject = (String) inEvent.getData(paramPositions.get("subject"));
        long time = (long) inEvent.getData(paramPositions.get("timestamp"));
        String message = (String) inEvent.getData(paramPositions.get("content"));
        subject = subject.replace("[Builder]","");
        int id;
        int emailType = 0;
        String product;
        if(subject.contains("Jenkins build is back to normal")){
            subject = subject.replace("Jenkins build is back to normal :", "");
            emailType = 3;
        }else if(subject.contains("Build failed in Jenkins: ")){
            subject = subject.replace("Build failed in Jenkins: ", "");
            if (subject.contains("Re:")) {
                subject = subject.replace("Re:", "");
                emailType = 2;
            } else {
                emailType = 1;
            }
        }
        subject = subject.trim();
        String[] split = subject.split("\\s+");
        product = split[0].trim();
        split = subject.split("#");
        id= Integer.parseInt(split[1]);
        String emailStatus = "NEW";
        String emailTo = (String)inEvent.getData(paramPositions.get("to"));
        String emailFrom = (String)inEvent.getData(paramPositions.get("sender"));
        String emailCC = "";
        Object[] data = new Object[]{id,product,time,message,emailStatus,emailType,emailTo,emailFrom,emailCC};
        return new InEvent(inEvent.getStreamId(),System.currentTimeMillis(),data);
    }

    private int getId() {
        return 0;
    }

    @Override
    protected InStream processEvent(InListEvent inListEvent) {
        return null;
    }

    @Override
    protected Object[] currentState() {
        return new Object[0];
    }

    @Override
    protected void restoreState(Object[] objects) {

    }

    @Override
    protected void init(Expression[] expressions, List<ExpressionExecutor> list, StreamDefinition streamDefinition, StreamDefinition streamDefinition1, String s, SiddhiContext siddhiContext) {
        for (Expression parameter : expressions) {
            if (parameter instanceof Variable) {
                Variable var = (Variable) parameter;
                String attributeName = var.getAttributeName();
                paramPositions.put(attributeName, inStreamDefinition.getAttributePosition(attributeName));
            }
        }
    }

    @Override
    public void destroy() {

    }
}
