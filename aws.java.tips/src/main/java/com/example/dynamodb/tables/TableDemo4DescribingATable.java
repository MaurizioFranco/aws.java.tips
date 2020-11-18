/**
 * 
 */
package com.example.dynamodb.tables;

import java.util.List;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputDescription;
import software.amazon.awssdk.services.dynamodb.model.TableDescription;

/**
 * @author maurizio
 */
public class TableDemo4DescribingATable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String table_name="Music";
		DynamoDbClient ddb = DynamoDbClient.create();

		DescribeTableRequest request = DescribeTableRequest.builder().tableName(table_name).build();

		try {
			TableDescription table_info = ddb.describeTable(request).table();

			if (table_info != null) {
				System.out.format("Table name  : %s\n", table_info.tableName());
				System.out.format("Table ARN   : %s\n", table_info.tableArn());
				System.out.format("Status      : %s\n", table_info.tableStatus());
				System.out.format("Item count  : %d\n", table_info.itemCount().longValue());
				System.out.format("Size (bytes): %d\n", table_info.tableSizeBytes().longValue());

				ProvisionedThroughputDescription throughput_info = table_info.provisionedThroughput();
				System.out.println("Throughput");
				System.out.format("  Read Capacity : %d\n", throughput_info.readCapacityUnits().longValue());
				System.out.format("  Write Capacity: %d\n", throughput_info.writeCapacityUnits().longValue());

				List<AttributeDefinition> attributes = table_info.attributeDefinitions();
				System.out.println("Attributes");
				for (AttributeDefinition a : attributes) {
					System.out.format("  %s (%s)\n", a.attributeName(), a.attributeType());
				}
			}
		} catch (DynamoDbException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}
