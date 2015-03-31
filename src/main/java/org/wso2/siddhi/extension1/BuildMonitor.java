//package org.wso2.siddhi.extension;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.omg.CORBA.PUBLIC_MEMBER;
//import org.wso2.siddhi.core.config.SiddhiContext;
//import org.wso2.siddhi.core.exception.QueryCreationException;
//import org.wso2.siddhi.core.executor.function.FunctionExecutor;
//import org.wso2.siddhi.query.api.definition.Attribute;
//import org.wso2.siddhi.query.api.extension1.annotation.SiddhiExtension;
//
//import java.io.IOException;
//
///**
// * Created by danula on 2/5/15.
// */
//
//@SiddhiExtension(namespace = "build", function = "monitor")
//public class BuildMonitor extends FunctionExecutor {
//    Attribute.Type returnType;
//    public void init(Attribute.Type[] types, SiddhiContext siddhiContext) {
//        for (Attribute.Type attributeType : types) {
//            if (attributeType == Attribute.Type.DOUBLE) {
//                returnType = attributeType;
//                break;
//            } else if ((attributeType == Attribute.Type.STRING) || (attributeType == Attribute.Type.BOOL)) {
//                throw new QueryCreationException("Plus cannot have parameters with types String or Bool");
//            } else {
//                returnType = Attribute.Type.LONG;
//            }
//        }
//    }
//
//    @Override
//    protected Object process(Object o) {
//        return null;
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public Attribute.Type getReturnType() {
//        return null;
//    }
//
//    public String getProductName(String subject){
//        subject = subject.replace("Build failed in Jenkins: ", "");
//        int t = subject.indexOf("#");
//        subject  = subject.substring(0,t-1);
//
//        return subject.split(" ")[0];
//    }
//
//    public int getBuildNumber(String subject){
//        int t = subject.indexOf("#");
//        subject  = subject.substring(t+1);
//        return Integer.parseInt(subject);
//    }
//
//    public void help(){
//
//    }
//
//    public void readStatus(String productName){
//        try {
//            JSONObject jsonObject = JsonReader.readJsonFromUrl("https://wso2.org/jenkins/job/" + productName + "/api/json");
//            System.out.println(jsonObject.toString());
//
//            //JSONObject lastSuccessfulBuild = (JSONObject)jsonObject.get("lastSuccessfulBuild");
//            //JSONObject lastBuild = (JSONObject)jsonObject.get("lastBuild");
//
//            //System.out.println(lastSuccessfulBuild.get("number"));
//            System.out.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void notifyBuildFail(){
//
//    }
//
//    public void freezeRepo(){
//
//    }
//    public void recordEmail(){
//
//    }
//}
//
