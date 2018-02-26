package org.boluo.elasticsearchlearn.model;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.boluo.elasticsearchlearn.util.Constants;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.List;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.model
 *
 * @author liyanlong
 * @date 2018/2/24 15:23.
 * @since JDK 1.8
 */
@ToString
@AllArgsConstructor
public class OrderModel {

	private long groupID;

	private long shopID;

	private String shopName;

	private long reportDate;

	private String orderKey;

	private double foodAmount;

	public static XContentBuilder toXContentBuilder(OrderModel orderModel) throws Exception {
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
				.field("groupID", orderModel.groupID)
				.field("shopID", orderModel.shopID)
				.field("shopName", orderModel.shopName)
				.field("reportDate", orderModel.reportDate)
				.field("orderKey", orderModel.orderKey)
				.field("foodAmount", orderModel.foodAmount)
				.endObject();
		return builder;
	}

	public static void toXContentBuilder(List<OrderModel> orderModelList, BulkProcessor bulkProcessor) throws Exception {

		for (OrderModel orderModel : orderModelList) {
			try {
				IndexRequest indexRequest = new IndexRequest(Constants.INDEX_ORDER, Constants.TYPE_ORDER);
				indexRequest.source(toXContentBuilder(orderModel));
				bulkProcessor.add(indexRequest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
