package com.boluo.elasticsearchlearn.manager;

import com.boluo.elasticsearchlearn.config.EsConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.URI;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.manager
 *
 * @author liyanlong
 * @date 2018/2/24 11:57.
 * @since JDK 1.8
 */
public class EsManager {

	private static TransportClient client;

	public void init(EsConfig esConfig) throws Exception {
		Settings settings = Settings.builder().put("cluster.name", esConfig.getClusterName()).build();

		client = new PreBuiltTransportClient(settings);
		for(String node: esConfig.getClusterNodes()) {
			URI uri = URI.create(node);
			client.addTransportAddress(new TransportAddress(InetAddress.getByName(uri.getHost()), uri.getPort()));
		}
	}

	public static TransportClient getClient() {
		return client;
	}

	public static void close() {
		client.close();
	}
}
