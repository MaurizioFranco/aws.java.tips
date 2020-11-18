/**
 * 
 */
package com.example.dynamodb.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

/**
 * @author maurizio
 * 
 *
 */
public class ItemsDemo2Retrieving {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, AttributeValue> key_to_get = new HashMap<String, AttributeValue>();
		String table_name = "Music4";
		key_to_get.put("Artist", AttributeValue.builder().s("Guru").build());
//		key_to_get.put("SongTitle", AttributeValue.builder().s("Jazzmatazz").build());
String projection_expression = "Artist, SongTitle" ;
//		String projection_expression = "Artist" ;
		GetItemRequest request = null;
		if (projection_expression != null) {
			request = GetItemRequest.builder().key(key_to_get).tableName(table_name)
					.projectionExpression(projection_expression).build();
		} else {
			request = GetItemRequest.builder().key(key_to_get).tableName(table_name).build();
		}

		DynamoDbClient ddb = DynamoDbClient.create();

		try {
			Map<String, AttributeValue> returned_item = ddb.getItem(request).item();
			if (returned_item != null) {
				Set<String> keys = returned_item.keySet();
				for (String key : keys) {
					System.out.format("%s: %s\n", key, returned_item.get(key).toString());
				}
			} else {
				System.out.format("No item found with the key %s!\n", "Artist");
			}
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
