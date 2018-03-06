package com.boluo.elasticsearchlearn.mapper;

import com.boluo.elasticsearchlearn.manager.EsManager;
import com.boluo.elasticsearchlearn.model.OrderModel;
import com.boluo.elasticsearchlearn.util.Constants;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * elasticsearchlearn - org.boluo.elasticsearchlearn.mapper
 *
 * @author liyanlong
 * @date 2018/2/24 15:14.
 * @since JDK 1.8
 */
public class IndexMapper {

	/**
	 * @Description 插入单条记录
	 */
	public static boolean insertByXContentBuilder(OrderModel orderModel) throws Exception {

		XContentBuilder builder = OrderModel.toXContentBuilder(orderModel);
		System.out.println(builder.string());

		IndexResponse response = EsManager.getClient().prepareIndex(Constants.INDEX_ORDER, Constants.TYPE_ORDER).setSource(builder).get();
		System.out.println(response.toString());
		return true;
	}

	/**
	 * @Description 批量插入数据
	 */
	public static boolean insertByXContentBuilder(List<OrderModel> orderModelList) throws Exception {

		BulkProcessor bulkProcessor = BulkProcessor.builder(EsManager.getClient(), new BulkProcessor.Listener() {

			@Override
			public void beforeBulk(long l, BulkRequest bulkRequest) {

			}

			@Override
			public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
				if (bulkResponse.hasFailures()) {

					System.out.println("插入失败:" + bulkResponse.toString());
				}
			}

			@Override
			public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
				System.out.println("异常：" + throwable);
				throwable.printStackTrace();
			}
		}).setBulkActions(5).setFlushInterval(TimeValue.timeValueSeconds(5))// 无论请求的数量是多少，我们都希望每5秒刷新一次
				.setConcurrentRequests(1) //并发请求数
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
				.build();

		OrderModel.toXContentBuilder(orderModelList, bulkProcessor);
		bulkProcessor.flush();
		bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
		return true;
	}

	/**
	 * @Description fetch
	 */
	public static OrderModel getOrderModel() {

		GetResponse response = EsManager.getClient().prepareGet(Constants.INDEX_ORDER, Constants.TYPE_ORDER, "cojExmEB5ZoxhKm7YGSw").get();
		if (response.isExists()) {
			Map<String, Object> map = response.getSourceAsMap();
			OrderModel orderModel = new OrderModel(Long.parseLong(map.get("groupID").toString())
					, Long.parseLong(map.get("shopID").toString()), map.get("shopName").toString()
					, Long.parseLong(map.get("reportDate").toString()), map.get("orderKey").toString()
					, Double.parseDouble(map.get("foodAmount").toString()));
			return orderModel;
		}

		return null;
	}

	/**
	 * @Description delete
	 */
	public static void deleteOrderModel() {

		DeleteResponse response = EsManager.getClient().prepareDelete(Constants.INDEX_ORDER, Constants.TYPE_ORDER, "pdV70WEBSo1f2JFDU8az").get();
		System.out.println(response.status().getStatus());
	}
}
