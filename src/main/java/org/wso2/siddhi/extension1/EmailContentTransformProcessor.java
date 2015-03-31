package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.config.SiddhiContext;
import org.wso2.siddhi.core.event.Event;
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

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danula on 2/18/15.
 */
@SiddhiExtension(namespace = "bm", function = "getEmailContent")
public class EmailContentTransformProcessor extends TransformProcessor {
    private static final java.lang.String DB_URL = "jdbc:mysql://localhost:3306/emails";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123";
    private Map<String, Integer> paramPositions = new HashMap<String, Integer>();

    public EmailContentTransformProcessor() {
        this.outStreamDefinition = new StreamDefinition().name("out").attribute("to", Attribute.Type.STRING).attribute("subject", Attribute.Type.STRING).attribute("content", Attribute.Type.STRING).attribute("label", Attribute.Type.STRING);
    }

    @Override
    protected InStream processEvent(InEvent inEvent) {
        int id = (int) inEvent.getData(paramPositions.get("id"));
        String product = (String) inEvent.getData(paramPositions.get("product"));
        String message = (String) inEvent.getData(paramPositions.get("message"));
        long time = (long) inEvent.getData(paramPositions.get("time"));
        //String status = (String) inEvent.getData(paramPositions.get("emailStatus"));
        String to = "danulae@wso2.com";
        String subject = "Build failed in Jenkins: "+product+" #"+id;
        String content = message +"Time :"+time+" "+System.currentTimeMillis()+" "+(time - System.currentTimeMillis())+"\n"+getEmailRecipients(product);
        String label = "NEW";
        Object[] data = new Object[]{to,subject,content,label};
        return new InEvent(inEvent.getStreamId(),System.currentTimeMillis(),data);
    }

    private String getEmailRecipients(String product) {
        Connection connection = null;
        Statement statement = null;
        String email = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            statement = connection.createStatement();
            String sql;
            sql = "SELECT teamLead FROM components WHERE product = '"+product+"' LIMIT 1";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            email = rs.getString("teamLead");
            rs.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    @Override
    protected InStream processEvent(InListEvent inListEvent) {
        InListEvent transformedListEvent = new InListEvent();
        for (Event event : inListEvent.getEvents()) {
            if (event instanceof InEvent) {
                transformedListEvent.addEvent((Event) processEvent((InEvent) event));
            }
        }
        return transformedListEvent;
    }

    @Override
    protected Object[] currentState() {
        return new Object[]{paramPositions};
    }

    @Override
    protected void restoreState(Object[] objects) {
        if (objects.length > 0 && objects[0] instanceof Map) {
            paramPositions = (Map<String, Integer>) objects[0];
        }
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
