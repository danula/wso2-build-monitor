package org.wso2.siddhi.extension1;

import org.wso2.siddhi.extension1.EmailIdGenerator;

/**
* Created by danula on 2/6/15.
*/
public class Main {
    public static void main(String[] args){
        String subject = "[Builder] Build failed in Jenkins: product-mdm » WSO2 MDM REST API #122";

        subject = subject.replace("[Builder]","");
        int id;
        int emailType;
        String product;
        if(subject.contains("Jenkins build is back to normal")){
            subject = subject.replace("Jenkins build is back to normal :", "");
            emailType = 3;
        }else {
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
        for(int i=0;i<split.length;i++){
            System.out.println(split[i]);
        }
        product = split[0].trim();
        split = subject.split("#");
        id = Integer.parseInt(split[1]);
        System.out.println(product+id);
      //  System.out.println(eig.getId("Build failed in Jenkins: product-as #429"));
//        System.out.println(bm.getBuildNumber("Build failed in Jenkins: product-as #429"));
//        System.out.println(bm.getProductName("Build failed in Jenkins: carbon-analytics » WSO2 Carbon - JMX Data Agent #269"));
    }


}