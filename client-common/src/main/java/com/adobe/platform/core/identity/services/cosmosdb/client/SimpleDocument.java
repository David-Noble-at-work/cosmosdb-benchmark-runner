package com.adobe.platform.core.identity.services.cosmosdb.client;

import java.util.Map;

public class SimpleDocument {
    public String id;
    public Map<String, Object> properties;

    public SimpleDocument(String id, Map<String, Object> props){
        this.id = id;
        this.properties = props;
    }
}
