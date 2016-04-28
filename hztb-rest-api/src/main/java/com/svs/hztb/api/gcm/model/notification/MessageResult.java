package com.svs.hztb.api.gcm.model.notification;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "message_id",
    "error",
    "registration_id"
})
public class MessageResult {

    @JsonProperty("message_id")
    private String messageId;
    @JsonProperty("error")
    private String error;
    @JsonProperty("registration_id")
    private String registrationId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The messageId
     */
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    /**
     * 
     * @param messageId
     *     The message_id
     */
    @JsonProperty("message_id")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 
     * @return
     *     The error
     */
    @JsonProperty("error")
    public String getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The registrationId
     */
    @JsonProperty("registration_id")
    public String getRegistrationId() {
        return registrationId;
    }

    /**
     * 
     * @param registrationId
     *     The registration_id
     */
    @JsonProperty("registration_id")
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
