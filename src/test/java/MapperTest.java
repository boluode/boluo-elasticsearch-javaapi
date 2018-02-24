import org.boluo.elasticsearchlearn.config.EsConfig;
import org.boluo.elasticsearchlearn.manager.EsManager;
import org.boluo.elasticsearchlearn.mapper.IndexMapper;
import org.boluo.elasticsearchlearn.model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
}
