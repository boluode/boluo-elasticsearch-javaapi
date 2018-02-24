package org.boluo.elasticsearchlearn.mapper;

import org.boluo.elasticsearchlearn.manager.EsManager;
import org.boluo.elasticsearchlearn.model.OrderModel;
import org.boluo.elasticsearchlearn.util.Constants;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.mapper
 *
 * @author liyanlong
 * @date 2018/2/24 15:14.
 * @since JDK 1.8
 */
public class IndexMapper {

	public static boolean insertByXContentBuilder(OrderModel orderModel) throws Exception {

		XContentBuilder builder = OrderModel.toXContentBuilder(orderModel);
		System.out.println(builder.string());

		IndexResponse response = EsManager.getClient().prepareIndex(Constants.INDEX_ORDER, Constants.TYPE_ORDER).setSource(builder).get();
		System.out.println(response.toString());
		return true;
	}
}
