package org.boluo.elasticsearchlearn.model;

import lombok.AllArgsConstructor;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.model
 *
 * @author liyanlong
 * @date 2018/2/24 15:23.
 * @since JDK 1.8
 */
@AllArgsConstructor
public class OrderModel {

	private long groupID;

	private long shopID;

	private String shopName;

	private long reportDate;

	private String orderKey;

	private double foodAmount;

	public static XContentBuilder toXContentBuilder(OrderModel orderModel) throws Exception {
		XContentBuilder builder = jsonBuilder()
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
}
