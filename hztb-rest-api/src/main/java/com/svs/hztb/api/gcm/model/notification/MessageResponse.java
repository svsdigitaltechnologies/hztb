package com.svs.hztb.api.gcm.model.notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "multicast_id", "success", "failure", "canonical_ids", "results" })
public class MessageResponse {

	@JsonProperty("multicast_id")
	private String multicastId;
	@JsonProperty("success")
	private Integer success;
	@JsonProperty("failure")
	private Integer failure;
	@JsonProperty("canonical_ids")
	private Integer canonicalIds;
	@JsonProperty("results")
	private List<MessageResult> results = new ArrayList<>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<>();

	/**
	 * 
	 * @return The multicastId
	 */
	@JsonProperty("multicast_id")
	public String getMulticastId() {
		return multicastId;
	}

	/**
	 * 
	 * @param multicastId
	 *            The multicast_id
	 */
	@JsonProperty("multicast_id")
	public void setMulticastId(String multicastId) {
		this.multicastId = multicastId;
	}

	/**
	 * 
	 * @return The success
	 */
	@JsonProperty("success")
	public Integer getSuccess() {
		return success;
	}

	/**
	 * 
	 * @param success
	 *            The success
	 */
	@JsonProperty("success")
	public void setSuccess(Integer success) {
		this.success = success;
	}

	/**
	 * 
	 * @return The failure
	 */
	@JsonProperty("failure")
	public Integer getFailure() {
		return failure;
	}

	/**
	 * 
	 * @param failure
	 *            The failure
	 */
	@JsonProperty("failure")
	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	/**
	 * 
	 * @return The canonicalIds
	 */
	@JsonProperty("canonical_ids")
	public Integer getCanonicalIds() {
		return canonicalIds;
	}

	/**
	 * 
	 * @param canonicalIds
	 *            The canonical_ids
	 */
	@JsonProperty("canonical_ids")
	public void setCanonicalIds(Integer canonicalIds) {
		this.canonicalIds = canonicalIds;
	}

	/**
	 * 
	 * @return The results
	 */
	@JsonProperty("results")
	public List<MessageResult> getResults() {
		return results;
	}

	/**
	 * 
	 * @param results
	 *            The results
	 */
	@JsonProperty("results")
	public void setResults(List<MessageResult> results) {
		this.results = results;
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
