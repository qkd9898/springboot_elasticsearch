package com.usian.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.usian.config.ElasticsearchApp;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ElasticsearchApp.class})
public class IndexWriterTest {
	@Autowired
    private RestHighLevelClient restHighLevelClient;

	@Test
	public void testCreateIndex() throws IOException {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("java1906");
		createIndexRequest.settings(Settings.builder().put("number_of_shards",2).put("number_of_replicas",0));
		createIndexRequest.mapping("course","{\n" +
				"  \"properties\": {\n" +
				"    \"name\":{\n" +
				"      \"type\": \"text\",\n" +
				"      \"analyzer\": \"ik_max_word\",\n" +
				"      \"search_analyzer\": \"ik_smart\"\n" +
				"    },\n" +
				"    \"description\":{\n" +
				"      \"type\": \"text\",\n" +
				"      \"analyzer\": \"ik_max_word\",\n" +
				"      \"search_analyzer\": \"ik_smart\"\n" +
				"    },\n" +
				"    \"pic\":{\n" +
				"      \"type\": \"text\",\n" +
				"      \"index\": false\n" +
				"    },\n" +
				"    \"studymodel\":{\n" +
				"      \"type\": \"text\"\n" +
				"    },\n" +
				"    \"studydate\":{\n" +
				"      \"type\": \"date\",\n" +
				"      \"format\": \"yyyy-MM-dd HH:mm:ss||yyyy-MM-dd\"\n" +
				"    },\n" +
				"    \"price\":{\n" +
				"      \"type\": \"double\"\n" +
				"    }\n" +
				"  }\n" +
				"}", XContentType.JSON);
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest);
		boolean acknowledged = createIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
	}

	//删除索引库
	@Test
	public void testDeleteIndex() throws IOException {
		//创建“删除索引请求”对象
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("java1906");
		//创建索引操作客户端
		IndicesClient indices = restHighLevelClient.indices();
		//创建响应对象
		DeleteIndexResponse deleteIndexResponse =
				indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		//得到响应结果
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
	}
  }