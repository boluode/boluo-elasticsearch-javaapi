import com.boluo.elasticsearchlearn.config.EsConfig;
import com.boluo.elasticsearchlearn.manager.EsManager;
import com.boluo.elasticsearchlearn.mapper.IndexMapper;
import com.boluo.elasticsearchlearn.model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * elasticsearchlearn - PACKAGE_NAME
 *
 * @author liyanlong
 * @date 2018/2/24 15:15.
 * @since JDK 1.8
 */
public class MapperTest {

	private EsManager esManager;
	@Before
	public void before() throws Exception {
		esManager = new EsManager();
		esManager.init(new EsConfig());
	}

	@After
	public void after() {
		EsManager.close();
	}

	@Test
	public void insertByXContentBuilder() throws Exception {
		OrderModel orderModel = new OrderModel(151, 72022963, "北京菠萝店", 20180101, "201801011212360002", 76);
		IndexMapper.insertByXContentBuilder(orderModel);
	}

	@Test
	public void insertByXContentBuilderArray() throws Exception {
		List<OrderModel> orderModelList = new ArrayList<OrderModel>();
		int i = 10000;
		while(i <= 10010) {
			String orderKey = "20180101121236" + String.format("%05d", i);
			OrderModel orderModel = new OrderModel(151, 72022963, "北京菠萝店", 20180101, orderKey, Double.valueOf(i));
			orderModelList.add(orderModel);
			i++;
		}

		IndexMapper.insertByXContentBuilder(orderModelList);
	}

	@Test
	public void getOrderModel() {
		OrderModel orderModel = IndexMapper.getOrderModel();

		System.out.println(orderModel == null ? "" : orderModel.toString());
	}

	@Test
	public void deteteOrderModel() {
		IndexMapper.deleteOrderModel();
	}
}
