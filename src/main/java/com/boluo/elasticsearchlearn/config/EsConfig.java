package com.boluo.elasticsearchlearn.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.config
 *
 * @author liyanlong
 * @date 2018/2/24 14:52.
 * @since JDK 1.8
 */
@Data
public class EsConfig {

	private String clusterName;
	private List<String> clusterNodes;

	public EsConfig() {
		this.clusterName = "boluo";
		this.clusterNodes = new ArrayList<String>();
		this.clusterNodes.add("http://localhost:9300");
		this.clusterNodes.add("http://localhost:9301");
		this.clusterNodes.add("http://localhost:9302");
	}
}
