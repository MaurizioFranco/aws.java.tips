/**
 * 
 */
package com.example.dynamodb.tables;

import java.util.List;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

/**
 * @author maurizio
 *
 */
public class TableDemo3Listing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DynamoDbClient ddb = DynamoDbClient.create();

		boolean more_tables = true;
		String last_name = null;

		while (more_tables) {
			try {
				ListTablesResponse response = null;
				if (last_name == null) {
					ListTablesRequest request = ListTablesRequest.builder().build();
					response = ddb.listTables(request);
				} else {
					ListTablesRequest request = ListTablesRequest.builder().exclusiveStartTableName(last_name).build();
					response = ddb.listTables(request);
				}

				List<String> table_names = response.tableNames();

				if (table_names.size() > 0) {
					for (String cur_name : table_names) {
						System.out.format("* %s\n", cur_name);
					}
				} else {
					System.out.println("No tables found!");
					System.exit(0);
				}

				last_name = response.lastEvaluatedTableName();
				if (last_name == null) {
					more_tables = false;
				}
			} catch (DynamoDbException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}
	}

}
