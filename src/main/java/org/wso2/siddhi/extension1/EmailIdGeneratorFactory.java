package org.wso2.siddhi.extension1;

import org.wso2.siddhi.core.query.selector.attribute.factory.OutputAttributeAggregatorFactory;
import org.wso2.siddhi.core.query.selector.attribute.handler.OutputAttributeAggregator;
import org.wso2.siddhi.query.api.definition.Attribute;
import org.wso2.siddhi.query.api.extension.annotation.SiddhiExtension;

/**
 * Created by danula on 2/19/15.
 */
@SiddhiExtension(namespace = "bm", function = "getId")
public class EmailIdGeneratorFactory implements OutputAttributeAggregatorFactory {
    @Override
    public OutputAttributeAggregator createAttributeAggregator(Attribute.Type[] types) {
        return new EmailIdGenerator();
    }
}
